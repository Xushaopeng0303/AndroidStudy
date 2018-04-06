package com.xsp.framework.activity.material.behavior;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.xsp.framework.R;

import java.lang.ref.WeakReference;

/**
 * CoordinatorLayout 滚动时行为 Behavior
 */
public class HeaderScrollingBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private boolean mIsExpanded  = false;
    private boolean mIsScrolling = false;

    private WeakReference<View> mDependentView;  // 是依赖视图的一个弱引用
    private Scroller mScroller;                  // 用来实现用户释放手指后的滑动动画
    private Handler mHandler;                    // 用来驱动 Scroller 的运行

    public HeaderScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mHandler = new Handler();
    }

    public boolean isIsExpanded() {
        return mIsExpanded;
    }

    /**
     * 负责查询该 Behavior 是否依赖于某个视图，我们在这里判读视图是否为 Header View，如果是则返回 true，那么之后其他操作就会围绕这个依赖视图而进行了
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.id_coordinator_scrolling_header) {
            mDependentView = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    /**
     * 负责对被 Behavior 控制的视图进行布局，就是将 ViewGroup 的 onLayout 针对该视图的部分抽出来给 Behavior 处理
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.getWidth(), (int) (parent.getHeight() - getDependentViewStickyHeight()));
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /**
     * 根据依赖视图进行调整的方法，当依赖视图发生变化时，这个方法就会被调用
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        Resources resources = getDependentView().getResources();
        float progress = 1.f - Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.coordinator_sticky_header_height)));

        child.setTranslationY(dependency.getHeight() + dependency.getTranslationY());

        float scale = 1 + 0.4f * (1.f - progress);
        dependency.setScaleX(scale);
        dependency.setScaleY(scale);
        dependency.setAlpha(progress);

        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 当 NSP 接受要处理本次滑动后，这个回调被调用，可以做一些准备工作
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        mScroller.abortAnimation();
        mIsScrolling = false;

        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /**
     * 当 NSC 即将被滑动时调用，在这里你可以做一些处理
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        if (dy < 0) {
            return;
        }

        View dependentView = getDependentView();
        float newTranslateY = dependentView.getTranslationY() - dy;
        float minHeaderTranslate = -(dependentView.getHeight() - getDependentViewStickyHeight());

        Log.d("xsp", "onNestedPreScroll: newTranslateY = " + newTranslateY + " ; minHeaderTranslate = " + minHeaderTranslate);
        if (newTranslateY > minHeaderTranslate) {
            dependentView.setTranslationY(newTranslateY);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed > 0) {
            return;
        }

        View dependentView = getDependentView();
        float newTranslateY = dependentView.getTranslationY() - dyUnconsumed;
        final float maxHeaderTranslate = 0;
        Log.d("xsp", "onNestedScroll: newTranslateY = " + newTranslateY + " ; maxHeaderTranslate = " + maxHeaderTranslate);

        if (newTranslateY < maxHeaderTranslate) {
            dependentView.setTranslationY(newTranslateY);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY) {
        return onUserStopDragging(velocityY);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target) {
        if (!mIsScrolling) {
            onUserStopDragging(800);
        }
    }

    private boolean onUserStopDragging(float velocity) {
        View dependentView = getDependentView();
        float translateY = dependentView.getTranslationY();
        float minHeaderTranslate = -(dependentView.getHeight() - getDependentViewStickyHeight());

        if (translateY == 0 || translateY == minHeaderTranslate) {
            return false;
        }

        boolean targetState; // Flag indicates whether to expand the content.
        if (Math.abs(velocity) <= 800) {
            if (Math.abs(translateY) < Math.abs(translateY - minHeaderTranslate)) {
                targetState = false;
            } else {
                targetState = true;
            }
            velocity = 800; // Limit velocity's minimum value.
        } else {
            if (velocity > 0) {
                targetState = true;
            } else {
                targetState = false;
            }
        }

        float targetTranslateY = targetState ? minHeaderTranslate : 0;

        mScroller.startScroll(0, (int) translateY, 0, (int) (targetTranslateY - translateY), (int) (1000000 / Math.abs(velocity)));
        mHandler.post(flingRunnable);
        mIsScrolling = true;
        return true;
    }

    private float getDependentViewStickyHeight() {
        return getDependentView().getResources().getDimension(R.dimen.coordinator_sticky_header_height);
    }

    private View getDependentView() {
        return mDependentView.get();
    }

    private Runnable flingRunnable = new Runnable() {
        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                getDependentView().setTranslationY(mScroller.getCurrY());
                mHandler.post(this);
            } else {
                mIsExpanded = getDependentView().getTranslationY() != 0;
                mIsScrolling = false;
            }
        }
    };

}
