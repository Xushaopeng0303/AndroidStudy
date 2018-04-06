package com.xsp.framework.activity.material.behavior;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.xsp.framework.R;

import java.lang.ref.WeakReference;

/**
 * CoordinatorLayout 悬浮头行为 Behavior
 */
public class HeaderFloatBehavior extends CoordinatorLayout.Behavior<View> {

    private WeakReference<View> mDependentView;
    private ArgbEvaluator mArgbEvaluator;

    public HeaderFloatBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        mArgbEvaluator = new ArgbEvaluator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.id_coordinator_scrolling_header) {
            mDependentView = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Resources resources = getDependentView().getResources();
        float progress = 1.f - Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.coordinator_sticky_header_height)));

        // Vertical Translation
        final float stickyVerticalOffset = resources.getDimension(R.dimen.coordinator_sticky_y_offset);
        final float floatVerticalOffset = resources.getDimension(R.dimen.coordinator_float_y_offset);
        final float translateY = stickyVerticalOffset + (floatVerticalOffset - stickyVerticalOffset) * progress;
        child.setTranslationY(translateY);

        // Background
        child.setBackgroundColor((int) mArgbEvaluator.evaluate(
                progress,
                resources.getColor(R.color.coordinator_sticky_bg_color),
                resources.getColor(R.color.coordinator_float_bg_color)));

        // Horizontal Margins
        final float stickyHorizontalMargin = resources.getDimension(R.dimen.coordinator_sticky_x_margin);
        final float floatHorizontalMargin = resources.getDimension(R.dimen.coordinator_float_x_margin);
        final int margin = (int) (stickyHorizontalMargin + (floatHorizontalMargin - stickyHorizontalMargin) * progress);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.setMargins(margin, 0, margin, 0);
        child.setLayoutParams(lp);

        return true;
    }

    private View getDependentView() {
        return mDependentView.get();
    }

}
