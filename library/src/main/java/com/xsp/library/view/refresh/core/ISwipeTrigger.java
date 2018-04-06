package com.xsp.library.view.refresh.core;

interface ISwipeTrigger {
    void onPrepare();

    void onSwipe(int yOffset, boolean isComplete);

    void onRelease();

    void onComplete();

    void onReset();
}
