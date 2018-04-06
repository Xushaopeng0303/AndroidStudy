package com.xsp.refresh.swipe.core;


public class SwipeConfig {
    private static final int RELEASE_TO_REFRESHING_SCROLLING_DURATION           = 200;
    private static final int REFRESH_COMPLETE_DELAY_DURATION                    = 300;
    private static final int REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION     = 500;
    private static final int DEFAULT_TO_REFRESHING_SCROLLING_DURATION           = 500;

    private static final int RELEASE_TO_LOADING_MORE_SCROLLING_DURATION         = 200;
    private static final int LOAD_MORE_COMPLETE_DELAY_DURATION                  = 300;
    private static final int LOAD_MORE_COMPLETE_TO_DEFAULT_SCROLLING_DURATION   = 300;
    private static final int DEFAULT_TO_LOADING_MORE_SCROLLING_DURATION         = 300;

    // how hard to drag
    private static final float DEFAULT_DRAG_RATIO = 0.5f;

    // refresh
    private int mReleaseToRefreshingScrollingDuration = RELEASE_TO_REFRESHING_SCROLLING_DURATION;
    private int mRefreshCompleteDelayDuration = REFRESH_COMPLETE_DELAY_DURATION;
    private int mRefreshCompleteToDefaultScrollingDuration = REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION;
    private int mDefaultToRefreshingScrollingDuration = DEFAULT_TO_REFRESHING_SCROLLING_DURATION;

    // load more
    private int mReleaseToLoadingMoreScrollingDuration = RELEASE_TO_LOADING_MORE_SCROLLING_DURATION;
    private int mLoadMoreCompleteDelayDuration = LOAD_MORE_COMPLETE_DELAY_DURATION;
    private int mLoadMoreCompleteToDefaultScrollingDuration = LOAD_MORE_COMPLETE_TO_DEFAULT_SCROLLING_DURATION;
    private int mDefaultToLoadingMoreScrollingDuration = DEFAULT_TO_LOADING_MORE_SCROLLING_DURATION;

    private boolean mDebug = false;

    // offset to trigger refresh(load more), the max value of top(bottom) offset
    private float mRefreshTriggerOffset;
    private float mLoadMoreTriggerOffset;
    private float mRefreshFinalDragOffset;
    private float mLoadMoreFinalDragOffset;

    // how hard to drag
    private float mDragRatio = DEFAULT_DRAG_RATIO;

    // the style default classic
    private int mStyle = STYLE.CLASSIC;

    // a switcher indicate whiter refresh, load more function is enabled
    private boolean mRefreshEnabled  = true;
    private boolean mLoadMoreEnabled = true;

    private static SwipeConfig ourInstance = new SwipeConfig();

    public static SwipeConfig getInstance() {
        return ourInstance;
    }

    private SwipeConfig() {

    }

    /**
     * set release to refreshing scrolling duration in milliseconds
     */
    public SwipeConfig setReleaseToRefreshingScrollingDuration(int duration) {
        if (duration >= 0) {
            this.mReleaseToRefreshingScrollingDuration = duration;
        }
        return this;
    }

    /**
     * set refresh complete delay duration in milliseconds
     */
    public SwipeConfig setRefreshCompleteDelayDuration(int duration) {
        if (duration >= 0) {
            this.mRefreshCompleteDelayDuration = duration;
        }
        return this;
    }

    /**
     * set refresh complete to default scrolling duration in milliseconds
     */
    public SwipeConfig setRefreshCompleteToDefaultScrollingDuration(int duration) {
        if (duration >= 0) {
            this.mRefreshCompleteToDefaultScrollingDuration = duration;
        }
        return this;
    }

    /**
     * set default to refreshing scrolling duration in milliseconds
     */
    public SwipeConfig setDefaultToRefreshingScrollingDuration(int duration) {
        if (duration >= 0) {
            this.mDefaultToRefreshingScrollingDuration = duration;
        }
        return this;
    }

    /**
     * set release to loading more scrolling duration in milliseconds
     */
    public SwipeConfig setReleaseToLoadingMoreScrollingDuration(int duration) {
        if (duration >= 0) {
            this.mReleaseToLoadingMoreScrollingDuration = duration;
        }
        return this;
    }

    /**
     * set load more complete delay duration in milliseconds
     */
    public SwipeConfig setLoadMoreCompleteDelayDuration(int duration) {
        if (duration >= 0) {
            this.mLoadMoreCompleteDelayDuration = duration;
        }
        return this;
    }

    /**
     * set load more complete to default scrolling duration in milliseconds
     */
    public SwipeConfig setLoadMoreCompleteToDefaultScrollingDuration(int duration) {
        if (duration >= 0) {
            this.mLoadMoreCompleteToDefaultScrollingDuration = duration;
        }
        return this;
    }

    /**
     * set default to loading more scrolling duration in milliseconds
     */
    public SwipeConfig setDefaultToLoadingMoreScrollingDuration(int duration) {
        if (duration >= 0) {
            this.mDefaultToLoadingMoreScrollingDuration = duration;
        }
        return this;
    }

    /**
     * set debug mode(default value false), if true log on, false log off
     */
    public SwipeConfig setDebug(boolean debug) {
        this.mDebug = debug;
        return this;
    }

    /**
     * set the value of refresh trigger offset. Default value is the refresh header view height
     * If the offset you set is smaller than header height or not set, using that as default value
     */
    public SwipeConfig setRefreshTriggerOffset(int offset) {
        mRefreshTriggerOffset = offset;
        return this;
    }

    /**
     * set the value of load more trigger offset. Default value is the load more footer view height
     * If the offset you set is smaller than footer height or not set, using that as default value
     */
    public SwipeConfig setLoadMoreTriggerOffset(int offset) {
        mLoadMoreTriggerOffset = offset;
        return this;
    }

    /**
     * Set the final offset you can swipe to refresh. If the offset you set is 0(default value) or
     * smaller than refresh trigger offset, there no final offset
     */
    public SwipeConfig setRefreshFinalDragOffset(int offset) {
        mRefreshFinalDragOffset = offset;
        return this;
    }

    /**
     * Set the final offset you can swipe to load more. If the offset you set is 0(default value) or
     * smaller than load more trigger offset, there no final offset
     */
    public SwipeConfig setLoadMoreFinalDragOffset(int offset) {
        mLoadMoreFinalDragOffset = offset;
        return this;
    }

    /**
     * set how hard to drag, the ratio must smaller than 1.0f, bigger than 0.0f
     */
    public SwipeConfig setDragRatio(float ratio) {
        if (ratio > 0.0f && ratio <= 1.0f) {
            mDragRatio = ratio;
        }
        return this;
    }

    /**
     * the refresh style, default classic, the style must in range [0, 3]
     */
    public SwipeConfig setRefreshStyle(int style) {
        if (style < 0 && style > 3) {
            style = STYLE.CLASSIC;
        }
        mStyle = style;
        return this;
    }

    /**
     * set refresh function on or off
     */
    public SwipeConfig setRefreshEnabled(boolean enable) {
        this.mRefreshEnabled = enable;
        return this;
    }

    /**
     * set load more function on or off
     */
    public SwipeConfig setLoadMoreEnabled(boolean enable) {
        this.mLoadMoreEnabled = enable;
        return this;
    }

    /**
     * get release to refreshing scrolling duration in milliseconds
     */
    public int getReleaseToRefreshingScrollingDuration() {
        return mReleaseToRefreshingScrollingDuration;
    }

    /**
     * get refresh complete delay duration in milliseconds
     */
    public int getRefreshCompleteDelayDuration() {
        return mRefreshCompleteDelayDuration;
    }

    /**
     * get refresh complete to default scrolling duration in milliseconds
     */
    public int getRefreshCompleteToDefaultScrollingDuration() {
        return mRefreshCompleteToDefaultScrollingDuration;
    }

    /**
     * get default to refreshing scrolling duration in milliseconds
     */
    public int getDefaultToRefreshingScrollingDuration() {
        return mDefaultToRefreshingScrollingDuration;
    }

    /**
     * get release to loading more scrolling duration in milliseconds
     */
    public int getReleaseToLoadingMoreScrollingDuration() {
        return mReleaseToLoadingMoreScrollingDuration;
    }

    /**
     * get load more complete delay duration in milliseconds
     */
    public int getLoadMoreCompleteDelayDuration() {
        return mLoadMoreCompleteDelayDuration;
    }

    /**
     * get load more complete to default scrolling duration in milliseconds
     */
    public int getLoadMoreCompleteToDefaultScrollingDuration() {
        return mLoadMoreCompleteToDefaultScrollingDuration;
    }

    /**
     * get default to loading more scrolling duration in milliseconds
     */
    public int getDefaultToLoadingMoreScrollingDuration() {
        return mDefaultToLoadingMoreScrollingDuration;
    }

    /**
     * get debug mode(default value false), if true log on, false log off
     */
    public boolean getDebug() {
        return mDebug;
    }

    /**
     * get the value of refresh trigger offset. Default value is the refresh header view height
     */
    public float getRefreshTriggerOffset() {
        return mRefreshTriggerOffset;
    }

    /**
     * get the value of load more trigger offset. Default value is the load more footer view height
     */
    public float getLoadMoreTriggerOffset() {
        return mLoadMoreTriggerOffset;
    }

    /**
     * get the final offset you can swipe to refresh.
     */
    public float getRefreshFinalDragOffset() {
        return mRefreshFinalDragOffset;
    }

    /**
     * get the final offset you can swipe to load more.
     */
    public float getLoadMoreFinalDragOffset() {
        return mLoadMoreFinalDragOffset;
    }

    /**
     * get how hard to drag
     */
    public float getDragRatio() {
        return mDragRatio;
    }

    /**
     * get the refresh style
     */
    public int getRefreshStyle() {
        return mStyle;
    }

    /**
     * get refresh function on or off
     */
    public boolean getRefreshEnabled() {
        return mRefreshEnabled;
    }

    /**
     * get load more function on or off
     */
    public boolean getLoadMoreEnabled() {
        return mLoadMoreEnabled;
    }

}
