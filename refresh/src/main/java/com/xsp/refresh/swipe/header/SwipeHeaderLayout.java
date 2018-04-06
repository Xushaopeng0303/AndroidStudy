package com.xsp.refresh.swipe.header;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xsp.refresh.swipe.core.ISwipeRefreshTrigger;

public class SwipeHeaderLayout extends FrameLayout implements ISwipeRefreshTrigger {

    public SwipeHeaderLayout(Context context) {
        this(context, null);
    }

    public SwipeHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onSwipe(int yOffset, boolean isComplete) {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReset() {

    }

}
