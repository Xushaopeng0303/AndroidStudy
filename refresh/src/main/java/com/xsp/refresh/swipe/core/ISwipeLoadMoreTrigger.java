package com.xsp.refresh.swipe.core;


public interface ISwipeLoadMoreTrigger {
    void onLoadMore();

    void onPrepare();

    void onSwipe(int yOffset, boolean isComplete);

    void onRelease();

    void onComplete();

    void onReset();
}
