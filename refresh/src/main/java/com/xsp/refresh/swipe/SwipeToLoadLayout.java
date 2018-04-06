package com.xsp.refresh.swipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;

import com.xsp.refresh.R;
import com.xsp.refresh.swipe.core.ISwipeLoadMoreTrigger;
import com.xsp.refresh.swipe.core.ISwipeRefreshTrigger;
import com.xsp.refresh.swipe.core.STATUS;
import com.xsp.refresh.swipe.core.STYLE;
import com.xsp.refresh.swipe.core.SwipeConfig;
import com.xsp.refresh.swipe.util.SwipeUtil;


public class SwipeToLoadLayout extends ViewGroup {
    private static final String TAG = SwipeToLoadLayout.class.getSimpleName();

    private static final int INVALID_POINTER    = -1;
    private static final int INVALID_COORDINATE = -1;

    private AutoScroller mAutoScroller;
    private IRefreshListener mIRefreshListener;

    private View mHeaderView;
    private View mTargetView;
    private View mFooterView;

    private int mHeaderHeight;
    private int mFooterHeight;

    // the threshold of the touch event
    private final int mTouchSlop;

    // status of SwipeToLoadLayout
    private int mStatus = STATUS.STATUS_DEFAULT;

    // header, content, footer offset
    private int mHeaderOffset;
    private int mTargetOffset;
    private int mFooterOffset;

    // init touch action down point.x, point.y
    private float mInitDownX;
    private float mInitDownY;

    // last touch point.x, point.y
    private float mLastX;
    private float mLastY;

    // action touch pointer's id
    private int mActivePointerId;

    // indicate whither is loading
    private boolean mLoading;

    private SwipeConfig mSwipeConfig;


    public SwipeToLoadLayout(Context context) {
        this(context, null);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // init config info before xml attrs
        if (null == mSwipeConfig) {
            mSwipeConfig = SwipeConfig.getInstance();
        }

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeToLoadLayout, defStyleAttr, 0);
        try {
            final int attrCount = a.getIndexCount();
            for (int i = 0; i < attrCount; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.SwipeToLoadLayout_refresh_enabled) {
                    mSwipeConfig.setRefreshEnabled(a.getBoolean(attr, true));
                } else if (attr == R.styleable.SwipeToLoadLayout_load_more_enabled) {
                    mSwipeConfig.setLoadMoreEnabled(a.getBoolean(attr, true));
                } else if (attr == R.styleable.SwipeToLoadLayout_swipe_style) {
                    this.mSwipeConfig.setRefreshStyle(a.getInt(attr, STYLE.CLASSIC));
                    requestLayout();
                }
            }
        } finally {
            a.recycle();
        }

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mAutoScroller = new AutoScroller();
    }

    public void setSwipeConfig(SwipeConfig config) {
        if (null != config) {
            this.mSwipeConfig = config;
        }
    }

    /**
     * is current status refreshing
     */
    public boolean isRefreshing() {
        return STATUS.isRefreshing(mStatus) && mLoading;
    }

    /**
     * is current status loading more
     */
    public boolean isLoadingMore() {
        return STATUS.isLoadingMore(mStatus) && mLoading;
    }

    /**
     * set refresh header view, the view must at lease be an implement of {@code ISwipeRefreshTrigger}.
     * the view can also implement {@code ISwipeRefreshTrigger} for more extension functions
     */
    public void setRefreshHeaderView(View view) {
        if (view instanceof ISwipeRefreshTrigger) {
            if (null != mHeaderView && mHeaderView != view) {
                removeView(mHeaderView);
            }
            if (mHeaderView != view) {
                this.mHeaderView = view;

                // TODO 可能需要添加位置
                addView(view);
            }
        } else {
            Log.e(TAG, "Refresh header view must be an implement of ISwipeRefreshTrigger");
        }
    }

    /**
     * set load more footer view, the view must at least be an implement of {@code ISwipeLoadMoreTrigger}
     * the view can also implement {@code ISwipeLoadMoreTrigger} for more extension functions
     */
    public void setLoadMoreFooterView(View view) {
        if (view instanceof ISwipeLoadMoreTrigger) {
            if (null != mFooterView && mFooterView != view) {
                removeView(mFooterView);
            }
            if (mFooterView != view) {
                this.mFooterView = view;
                addView(mFooterView);
            }
        } else {
            Log.e(TAG, "Load more footer view must be an implement of SwipeLoadTrigger");
        }
    }

    /**
     * set an {@link IRefreshListener} to listening refresh event
     */
    public void setOnRefreshListener(IRefreshListener listener) {
        this.mIRefreshListener = listener;
    }

    /**
     * auto refresh or cancel
     */
    public void setRefreshing(boolean refreshing) {
        if (!mSwipeConfig.getRefreshEnabled() || null == mHeaderView) {
            return;
        }
        this.mLoading = refreshing;
        if (refreshing) {
            // can not perform refresh when it is refreshing or loading more
            if (STATUS.isLoadingMore(mStatus)) {
                return;
            }
            setStatus(STATUS.REFRESHING);
            int duration;
            if (mHeaderOffset > mSwipeConfig.getRefreshTriggerOffset()) {
                duration = mSwipeConfig.getReleaseToRefreshingScrollingDuration();
            } else {
                duration = mSwipeConfig.getDefaultToRefreshingScrollingDuration();
            }
            mAutoScroller.autoScroll(mHeaderHeight - mHeaderOffset, duration);
        } else {
            if (STATUS.isRefreshing(mStatus)) {
                setStatus(STATUS.REFRESH_COMPLETE);
                mRefreshCallback.onComplete();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAutoScroller.autoScroll(-mHeaderOffset, mSwipeConfig.getRefreshCompleteToDefaultScrollingDuration());
                    }
                }, mSwipeConfig.getRefreshCompleteDelayDuration());
            } else if (STATUS.isSwipingToRefresh(mStatus)) {
                mAutoScroller.autoScroll(-mHeaderOffset, mSwipeConfig.getRefreshCompleteToDefaultScrollingDuration());
            }
        }
    }

    /**
     * auto loading more or cancel
     */
    public void setLoadingMore(boolean loadingMore) {
        if (!mSwipeConfig.getLoadMoreEnabled() || null == mFooterView) {
            return;
        }
        this.mLoading = loadingMore;
        if (loadingMore) {
            // can not perform load more when it is refreshing or loading more
            if (STATUS.isRefreshing(mStatus)) {
                return;
            }
            setStatus(STATUS.LOADING_MORE);
            int duration;
            if (-mFooterOffset > mSwipeConfig.getLoadMoreTriggerOffset()) {
                duration = mSwipeConfig.getReleaseToLoadingMoreScrollingDuration();
            } else {
                duration = mSwipeConfig.getDefaultToLoadingMoreScrollingDuration();
            }
            mAutoScroller.autoScroll(-mFooterOffset - mFooterHeight, duration);
        } else {
            if (STATUS.isLoadingMore(mStatus)) {
                setStatus(STATUS.LOAD_MORE_COMPLETE);
                mLoadMoreCallback.onComplete();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAutoScroller.autoScroll(-mFooterOffset, mSwipeConfig.getLoadMoreCompleteToDefaultScrollingDuration());
                    }
                }, mSwipeConfig.getLoadMoreCompleteDelayDuration());
            } else if (STATUS.isSwipingToLoadMore(mStatus)) {
                mAutoScroller.autoScroll(-mFooterOffset, mSwipeConfig.getLoadMoreCompleteToDefaultScrollingDuration());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childNum = getChildCount();
        if (childNum == 0) {            // no child return
            return;
        } else if (childNum == 1) {
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.layout_classic_header, SwipeToLoadLayout.this, false);
            mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_classic_footer, SwipeToLoadLayout.this, false);
            addView(mHeaderView);
            mTargetView = findViewById(R.id.swipe_target);
            addView(mFooterView);
        } else {
            throw new IllegalStateException("Children num must equal or less than 1");
        }
        if (null == mTargetView) {
            return;
        }
        if (null != mHeaderView && mHeaderView instanceof ISwipeRefreshTrigger) {
            mHeaderView.setVisibility(GONE);
        }
        if (null != mFooterView && mFooterView instanceof ISwipeLoadMoreTrigger) {
            mFooterView.setVisibility(GONE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // header
        if (null != mHeaderView) {
            final View headerView = mHeaderView;
            measureChildWithMargins(headerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = ((MarginLayoutParams) headerView.getLayoutParams());
            mHeaderHeight = headerView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (mSwipeConfig.getRefreshTriggerOffset() < mHeaderHeight) {
                mSwipeConfig.setRefreshTriggerOffset(mHeaderHeight);
            }
        }
        // target
        if (null != mTargetView) {
            final View targetView = mTargetView;
            measureChildWithMargins(targetView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        // footer
        if (null != mFooterView) {
            final View footerView = mFooterView;
            measureChildWithMargins(footerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = ((MarginLayoutParams) footerView.getLayoutParams());
            mFooterHeight = footerView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (mSwipeConfig.getLoadMoreTriggerOffset() < mFooterHeight) {
                mSwipeConfig.setLoadMoreTriggerOffset(mFooterHeight);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    /**
     * @see #onLayout(boolean, int, int, int, int)
     */
    private void layoutChildren() {
        final int height = getMeasuredHeight();

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        if (null == mTargetView) {
            return;
        }

        int refreshStyle = mSwipeConfig.getRefreshStyle();
        // layout header
        if (null != mHeaderView) {
            final View headerView = mHeaderView;
            MarginLayoutParams lp = (MarginLayoutParams) headerView.getLayoutParams();
            final int headerLeft = paddingLeft + lp.leftMargin;
            final int headerTop;
            switch (refreshStyle) {
                case STYLE.BELOW:
                    headerTop = paddingTop + lp.topMargin;
                    break;
                case STYLE.SCALE:
                    headerTop = paddingTop + lp.topMargin - mHeaderHeight / 2 + mHeaderOffset / 2;
                    break;
                case STYLE.CLASSIC:
                case STYLE.ABOVE:
                default:
                    headerTop = paddingTop + lp.topMargin - mHeaderHeight + mHeaderOffset;
                    break;
            }
            int headerRight = headerLeft + headerView.getMeasuredWidth();
            int headerBottom = headerTop + headerView.getMeasuredHeight();
            headerView.layout(headerLeft, headerTop, headerRight, headerBottom);
        }

        // layout target
        if (null != mTargetView) {
            final View targetView = mTargetView;
            MarginLayoutParams lp = (MarginLayoutParams) targetView.getLayoutParams();
            final int targetLeft = paddingLeft + lp.leftMargin;
            final int targetTop;

            switch (refreshStyle) {
                case STYLE.ABOVE:
                    targetTop = paddingTop + lp.topMargin;
                    break;
                case STYLE.CLASSIC:
                case STYLE.BELOW:
                case STYLE.SCALE:
                default:
                    targetTop = paddingTop + lp.topMargin + mTargetOffset;
                    break;
            }
            int targetRight = targetLeft + targetView.getMeasuredWidth();
            int targetBottom = targetTop + targetView.getMeasuredHeight();
            targetView.layout(targetLeft, targetTop, targetRight, targetBottom);
        }

        // layout footer
        if (null != mFooterView) {
            final View footerView = mFooterView;
            MarginLayoutParams lp = (MarginLayoutParams) footerView.getLayoutParams();
            final int footerLeft = paddingLeft + lp.leftMargin;
            final int footerBottom;
            switch (refreshStyle) {
                case STYLE.BELOW:
                    footerBottom = height - paddingBottom - lp.bottomMargin;
                    break;
                case STYLE.SCALE:
                    footerBottom = height - paddingBottom - lp.bottomMargin + mFooterHeight / 2 + mFooterOffset / 2;
                    break;
                case STYLE.ABOVE:
                case STYLE.CLASSIC:
                default:
                    footerBottom = height - paddingBottom - lp.bottomMargin + mFooterHeight + mFooterOffset;
                    break;
            }
            int footerTop = footerBottom - footerView.getMeasuredHeight();
            int footerRight = footerLeft + footerView.getMeasuredWidth();
            footerView.layout(footerLeft, footerTop, footerRight, footerBottom);
        }

        if (refreshStyle == STYLE.CLASSIC || refreshStyle == STYLE.ABOVE) {
            if (null != mHeaderView) {
                mHeaderView.bringToFront();
            }
            if (null != mFooterView) {
                mFooterView.bringToFront();
            }
        } else if (refreshStyle == STYLE.BELOW || refreshStyle == STYLE.SCALE) {
            if (null != mTargetView) {
                mTargetView.bringToFront();
            }
        }
    }

    private boolean onCheckCanRefresh() {
        return mSwipeConfig.getRefreshEnabled() && !canChildScrollUp() && null != mHeaderView && mSwipeConfig.getRefreshTriggerOffset() > 0;
    }

    private boolean onCheckCanLoadMore() {
        return mSwipeConfig.getLoadMoreEnabled() && !canChildScrollDown() && null != mFooterView && mSwipeConfig.getLoadMoreTriggerOffset() > 0;
    }

    /**
     * copy from {@link android.support.v4.widget.SwipeRefreshLayout#canChildScrollUp()}
     *
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    protected boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTargetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTargetView, -1) || mTargetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetView, -1);
        }
    }

    /**
     * Whether it is possible for the child view of this layout to
     * scroll down. Override this if the child view is a custom view.
     */
    protected boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTargetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetView;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(mTargetView, 1) || mTargetView.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetView, 1);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (STATUS.isSwipingToRefresh(mStatus)) {
                    // simply return
                    setRefreshing(false);
                } else if (STATUS.isSwipingToLoadMore(mStatus)) {
                    // simply return
                    setLoadingMore(false);
                } else if (STATUS.isReleaseToRefresh(mStatus)) {
                    // return to header height and perform refresh
                    mRefreshCallback.onRelease();
                    setRefreshing(true);
                } else if (STATUS.isReleaseToLoadMore(mStatus)) {
                    // return to footer height and perform loadMore
                    mLoadMoreCallback.onRelease();
                    setLoadingMore(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        Log.d("xsp", "onInterceptTouchEvent， action：" + action + " pointerId : " + mActivePointerId + " ; mLastX : " + mLastX + " ； mLastY : " + mLastY);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (STATUS.isSwipingToRefresh(mStatus) || STATUS.isSwipingToLoadMore(mStatus) ||
                        STATUS.isReleaseToRefresh(mStatus) || STATUS.isReleaseToLoadMore(mStatus)) {
                    mAutoScroller.abortIfRunning();
                }
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                mInitDownY = getMotionEventY(event, mActivePointerId);
                mInitDownX = getMotionEventX(event, mActivePointerId);
                if (mInitDownY == INVALID_COORDINATE) {
                    return false;
                }
                mLastY = mInitDownY;
                mLastX = mInitDownX;
                // let children view handle the ACTION_DOWN;

                // 1. children consumed:
                // if at least one of children onTouchEvent() ACTION_DOWN return true.
                // ACTION_DOWN event will not return to SwipeToLoadLayout#onTouchEvent().
                // but the others action can be handled by SwipeToLoadLayout#onInterceptTouchEvent()

                // 2. children not consumed:
                // if children onTouchEvent() ACTION_DOWN return false.
                // ACTION_DOWN event will return to SwipeToLoadLayout's onTouchEvent().
                // SwipeToLoadLayout#onTouchEvent() ACTION_DOWN return true to consume the ACTION_DOWN event.

                // anyway: handle action down in onInterceptTouchEvent() to init is an good option
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                float y = getMotionEventY(event, mActivePointerId);
                float x = getMotionEventX(event, mActivePointerId);
                final float yInitDiff = y - mInitDownY;
                final float xInitDiff = x - mInitDownX;
                mLastY = y;
                mLastX = x;
                boolean moved = Math.abs(yInitDiff) > Math.abs(xInitDiff);
                boolean triggerCondition = (yInitDiff > 0 && moved && onCheckCanRefresh())
                        || (yInitDiff < 0 && moved && onCheckCanLoadMore());
                if (triggerCondition) {
                    // the trigger condition refresh or load more is true
                    // intercept the move action event and pass it to SwipeToLoadLayout#onTouchEvent()
                    return true;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP: {
                onSecondaryPointerUp(event);
                mLastY = getMotionEventY(event, mActivePointerId);
                mLastX = getMotionEventX(event, mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        Log.d("xsp", "onTouchEvent, action: " + action + " pointerId : " + mActivePointerId + " ; mLastX : " + mLastX + " mLastY : " + mLastY);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                return true;
            case MotionEvent.ACTION_MOVE:
                // take over the ACTION_MOVE event from SwipeToLoadLayout#onInterceptTouchEvent()
                // if condition is true
                final float y = getMotionEventY(event, mActivePointerId);
                final float x = getMotionEventX(event, mActivePointerId);

                final float yDiff = y - mLastY;
                final float xDiff = x - mLastX;
                mLastY = y;
                mLastX = x;

                if (Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(xDiff) > mTouchSlop) {
                    return false;
                }

                if (STATUS.isStatusDefault(mStatus)) {
                    if (yDiff > 0 && onCheckCanRefresh()) {
                        mRefreshCallback.onPrepare();
                        setStatus(STATUS.SWIPING_TO_REFRESH);
                    } else if (yDiff < 0 && onCheckCanLoadMore()) {
                        mLoadMoreCallback.onPrepare();
                        setStatus(STATUS.SWIPING_TO_LOAD_MORE);
                    }
                }

                if (STATUS.isSwipingToRefresh(mStatus)
                        || STATUS.isSwipingToLoadMore(mStatus)
                        || STATUS.isReleaseToRefresh(mStatus)
                        || STATUS.isReleaseToLoadMore(mStatus)) {
                    //refresh or loadMore
                    fingerScroll(yDiff);
                }
                return true;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                if (pointerId != INVALID_POINTER) {
                    mActivePointerId = pointerId;
                }
                mLastY = getMotionEventY(event, mActivePointerId);
                mLastX = getMotionEventX(event, mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                onSecondaryPointerUp(event);
                mLastY = getMotionEventY(event, mActivePointerId);
                mLastX = getMotionEventX(event, mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                mActivePointerId = INVALID_POINTER;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * scrolling by physical touch with your fingers
     */
    private void fingerScroll(final float yDiff) {
        float defaultDragRatio = mSwipeConfig.getDragRatio();
        float ratio = defaultDragRatio;
        if (STATUS.isSwipingToRefresh(mStatus) || STATUS.isReleaseToRefresh(mStatus)) {
            if (yDiff > 0) {
                // swiping to refresh
                ratio = defaultDragRatio;
            } else if (yDiff < 0) {
                // refresh returning
                ratio = defaultDragRatio * 4;
            }
        } else if (STATUS.isSwipingToLoadMore(mStatus) || STATUS.isReleaseToLoadMore(mStatus)) {
            if (yDiff > 0) {
                // load more returning
                ratio = defaultDragRatio * 4;
            } else if (yDiff < 0) {
                // swiping to load more
                ratio = defaultDragRatio;
            }
        } else if (STATUS.isRefreshing(mStatus)) {
            if (yDiff < 0 && mTargetOffset <= mSwipeConfig.getRefreshTriggerOffset()) {
                // Refreshing + swipe up
                ratio = 0;
            } else if (yDiff > 0) {
                // Refreshing + swipe down
                ratio = defaultDragRatio;
            } else {
                ratio = defaultDragRatio * 4;
            }
        } else if (STATUS.isLoadingMore(mStatus)) {
            if (yDiff > 0 && -mTargetOffset <= mSwipeConfig.getLoadMoreTriggerOffset()) {
                // loading more + swipe down
                ratio = 0;
            } else if (yDiff < 0) {
                // swiping + swipe up
                ratio = defaultDragRatio;
            } else {
                ratio = defaultDragRatio * 4;
            }
        }

        float yScrolled = yDiff * ratio;

        // make sure (refresh -> default -> load more) or (load more -> default -> refresh)
        // forbidden fling jump default status (refresh -> load more)
        // I am so smart :)

        float tmpTargetOffset = yScrolled + mTargetOffset;
        if ((tmpTargetOffset > 0 && mTargetOffset < 0)
                || (yScrolled + mTargetOffset < 0 && mTargetOffset > 0)) {
            yScrolled = -mTargetOffset;
        }

        float refreshFinalDragOffset = mSwipeConfig.getRefreshFinalDragOffset();
        float loadMoreFinalDragOffset = mSwipeConfig.getLoadMoreFinalDragOffset();
        if (refreshFinalDragOffset >= mSwipeConfig.getRefreshTriggerOffset()
                && tmpTargetOffset > refreshFinalDragOffset) {
            yScrolled = refreshFinalDragOffset - mTargetOffset;
        } else if (loadMoreFinalDragOffset >= mSwipeConfig.getLoadMoreTriggerOffset()
                && -tmpTargetOffset > loadMoreFinalDragOffset) {
            yScrolled = -loadMoreFinalDragOffset - mTargetOffset;
        }
        updateScroll(yScrolled);
    }

    /**
     * Process the scrolling(auto or physical) and append the diff values to mTargetOffset
     * I think it's the most busy and core method. :) a ha ha ha ha...
     */
    private void updateScroll(final float yScrolled) {
        if (yScrolled == 0) {
            return;
        }
        mTargetOffset += yScrolled;
        if (mTargetOffset > 0) {
            if (STATUS.isRefreshStatus(mStatus)) {
                mHeaderOffset = mTargetOffset;
                mFooterOffset = 0;
                if (mTargetOffset < mSwipeConfig.getRefreshTriggerOffset()) {
                    if (STATUS.isReleaseToRefresh(mStatus)) {
                        setStatus(STATUS.SWIPING_TO_REFRESH);
                    }
                } else if (mTargetOffset >= mSwipeConfig.getRefreshTriggerOffset()) {
                    if (!STATUS.isRefreshing(mStatus)) {
                        setStatus(STATUS.RELEASE_TO_REFRESH);
                    }
                }
            } else if (STATUS.isStatusDefault(mStatus)) {
                mHeaderOffset = mTargetOffset;
                mFooterOffset = 0;
            }
        } else if (mTargetOffset < 0) {
            if (STATUS.isLoadMoreStatus(mStatus)) {
                mFooterOffset = mTargetOffset;
                mHeaderOffset = 0;
                if (-mTargetOffset < mSwipeConfig.getLoadMoreTriggerOffset()) {
                    if (STATUS.isReleaseToLoadMore(mStatus)) {
                        setStatus(STATUS.SWIPING_TO_LOAD_MORE);
                    }
                } else if (-mTargetOffset >= mSwipeConfig.getLoadMoreTriggerOffset()) {
                    if (!STATUS.isLoadingMore(mStatus)) {
                        setStatus(STATUS.RELEASE_TO_LOAD_MORE);
                    }
                }
            } else if (STATUS.isStatusDefault(mStatus)) {
                mFooterOffset = mTargetOffset;
                mHeaderOffset = 0;
            }
        } else if (mTargetOffset == 0) {
            if (STATUS.isRefreshing(mStatus) && mLoading) {
                mTargetOffset = 1;
                mHeaderOffset = mTargetOffset;
                mFooterOffset = 0;
            } else if (STATUS.isLoadingMore(mStatus) && mLoading) {
                mTargetOffset = -1;
                mFooterOffset = mTargetOffset;
                mHeaderOffset = 0;
            } else {
                mLoading = false;
                mHeaderOffset = 0;
                mFooterOffset = 0;
                if (STATUS.isRefreshComplete(mStatus) || STATUS.isSwipingToRefresh(mStatus)
                        //FIX quick scrolling jump (swiping to refresh) directly lead mTargetOffset < 0 bug
                        // release to refresh(quick scroll) -> mTargetOffset ==0 (not change status to default) -> mTargetOffset<0
                        || STATUS.isReleaseToRefresh(mStatus)) {
                    setStatus(STATUS.STATUS_DEFAULT);
                    mRefreshCallback.onReset();
                } else if (STATUS.isLoadMoreComplete(mStatus) || STATUS.isSwipingToLoadMore(mStatus)
                        //FIX quick scrolling release to load more jump swiping to load more directly lead mTargetOffset > 0 bug
                        // scrolling release(quick scroll) -> mTargetOffset ==0 (not change status to default) -> mTargetOffset>0
                        || STATUS.isReleaseToLoadMore(mStatus)) {
                    setStatus(STATUS.STATUS_DEFAULT);
                    mLoadMoreCallback.onReset();
                }
            }
        }

        if (mTargetOffset > 0) {
            mRefreshCallback.onSwipe(mTargetOffset, STATUS.isRefreshComplete(mStatus));
        } else if (mTargetOffset < 0) {
            mLoadMoreCallback.onSwipe(mTargetOffset, STATUS.isLoadMoreComplete(mStatus));
        }
        layoutChildren();
        invalidate();
    }

    /**
     * on not active finger up
     */
    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private float getMotionEventY(MotionEvent event, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(event, activePointerId);
        if (index < 0) {
            return INVALID_COORDINATE;
        }
        return MotionEventCompat.getY(event, index);
    }

    private float getMotionEventX(MotionEvent event, int activePointId) {
        final int index = MotionEventCompat.findPointerIndex(event, activePointId);
        if (index < 0) {
            return INVALID_COORDINATE;
        }
        return MotionEventCompat.getX(event, index);
    }

    private void setStatus(int status) {
        mStatus = status;
        if (mSwipeConfig.getDebug()) {
            STATUS.printStatus(status);
        }
    }


    private class AutoScroller implements Runnable {
        private Scroller scroller;
        private int lastY;
        private boolean running = false;
        private boolean abort = false;

        public AutoScroller() {
            scroller = new Scroller(getContext());
        }

        @Override
        public void run() {
            boolean finish = !scroller.computeScrollOffset() || scroller.isFinished();
            int currY = scroller.getCurrY();
            int yDiff = currY - lastY;
            if (finish) {
                finish();
            } else {
                lastY = currY;
                updateScroll(yDiff);
                post(this);
            }
        }

        /**
         * remove the post callbacks and reset default values
         */
        private void finish() {
            lastY = 0;
            running = false;
            removeCallbacks(this);

            if (mLoading) {
                if (STATUS.isRefreshing(mStatus) && !abort) {
                    mRefreshCallback.onRefresh();
                } else if (STATUS.isLoadingMore(mStatus) && !abort) {
                    mLoadMoreCallback.onLoadMore();
                }
            }
        }

        /**
         * abort scroll if it is scrolling
         */
        public void abortIfRunning() {
            if (running) {
                if (!scroller.isFinished()) {
                    abort = true;
                    scroller.forceFinished(true);
                }
                finish();
                abort = false;
            }
        }

        /**
         * The param yScrolled here isn't final pos of y.
         * It's just like the yScrolled param in the
         * {@link #updateScroll(float yScrolled)}
         */
        private void autoScroll(int yScrolled, int duration) {
            removeCallbacks(this);
            lastY = 0;
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
            scroller.startScroll(0, 0, 0, yScrolled, duration);
            post(this);
            running = true;
        }
    }

    RefreshCallback mRefreshCallback = new RefreshCallback() {
        @Override
        public void onPrepare() {
            if (SwipeUtil.isRefreshTrigger(mHeaderView) && STATUS.isStatusDefault(mStatus)) {
                mHeaderView.setVisibility(VISIBLE);
                ((ISwipeRefreshTrigger) mHeaderView).onPrepare();
            }
        }

        @Override
        public void onSwipe(int yOffset, boolean isComplete) {
            if (SwipeUtil.isRefreshTrigger(mHeaderView) && STATUS.isRefreshStatus(mStatus)) {
                if (mHeaderView.getVisibility() != VISIBLE) {
                    mHeaderView.setVisibility(VISIBLE);
                }
                ((ISwipeRefreshTrigger) mHeaderView).onSwipe(yOffset, isComplete);
            }
        }

        @Override
        public void onRelease() {
            if (SwipeUtil.isRefreshTrigger(mHeaderView) && STATUS.isReleaseToRefresh(mStatus)) {
                ((ISwipeRefreshTrigger) mHeaderView).onRelease();
            }
        }

        @Override
        public void onRefresh() {
            if (mHeaderView != null && STATUS.isRefreshing(mStatus) && mLoading) {
                if (mHeaderView instanceof ISwipeRefreshTrigger) {
                    ((ISwipeRefreshTrigger) mHeaderView).onRefresh();
                }
                if (mIRefreshListener != null) {
                    mIRefreshListener.onRefresh();
                }
            }
        }

        @Override
        public void onComplete() {
            if (SwipeUtil.isRefreshTrigger(mHeaderView)) {
                ((ISwipeRefreshTrigger) mHeaderView).onComplete();
            }
        }

        @Override
        public void onReset() {
            if (SwipeUtil.isRefreshTrigger(mHeaderView) && STATUS.isStatusDefault(mStatus)) {
                ((ISwipeRefreshTrigger) mHeaderView).onReset();
                mHeaderView.setVisibility(GONE);
            }
        }
    };

    LoadMoreCallback mLoadMoreCallback = new LoadMoreCallback() {
        @Override
        public void onPrepare() {
            if (SwipeUtil.isLoadMoreTrigger(mFooterView) && STATUS.isStatusDefault(mStatus)) {
                mFooterView.setVisibility(VISIBLE);
                ((ISwipeLoadMoreTrigger) mFooterView).onPrepare();
            }
        }

        @Override
        public void onSwipe(int yOffset, boolean isComplete) {
            if (SwipeUtil.isLoadMoreTrigger(mFooterView) && STATUS.isLoadMoreStatus(mStatus)) {
                if (mFooterView.getVisibility() != VISIBLE) {
                    mFooterView.setVisibility(VISIBLE);
                }
                ((ISwipeLoadMoreTrigger) mFooterView).onSwipe(yOffset, isComplete);
            }
        }

        @Override
        public void onRelease() {
            if (SwipeUtil.isLoadMoreTrigger(mFooterView) && STATUS.isReleaseToLoadMore(mStatus)) {
                ((ISwipeLoadMoreTrigger) mFooterView).onRelease();
            }
        }

        @Override
        public void onLoadMore() {
            if (mFooterView != null && STATUS.isLoadingMore(mStatus) && mLoading) {
                if (mFooterView instanceof ISwipeLoadMoreTrigger) {
                    ((ISwipeLoadMoreTrigger) mFooterView).onLoadMore();
                }
                if (mIRefreshListener != null) {
                    mIRefreshListener.onLoadMore();
                }
            }
        }

        @Override
        public void onComplete() {
            if (SwipeUtil.isLoadMoreTrigger(mFooterView)) {
                ((ISwipeLoadMoreTrigger) mFooterView).onComplete();
            }
        }

        @Override
        public void onReset() {
            if (SwipeUtil.isLoadMoreTrigger(mFooterView) && STATUS.isStatusDefault(mStatus)) {
                ((ISwipeLoadMoreTrigger) mFooterView).onReset();
                mFooterView.setVisibility(GONE);
            }
        }
    };

    /**
     * refresh event callback
     */
    abstract class RefreshCallback implements ISwipeRefreshTrigger {

    }

    /**
     * load more event callback
     */
    abstract class LoadMoreCallback implements ISwipeLoadMoreTrigger {

    }

}
