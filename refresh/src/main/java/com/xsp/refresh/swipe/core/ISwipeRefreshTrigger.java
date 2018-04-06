package com.xsp.refresh.swipe.core;


public interface ISwipeRefreshTrigger {

    void onRefresh();

    void onPrepare();

    void onSwipe(int yOffset, boolean isComplete);

    void onRelease();

    void onComplete();

    void onReset();

}
