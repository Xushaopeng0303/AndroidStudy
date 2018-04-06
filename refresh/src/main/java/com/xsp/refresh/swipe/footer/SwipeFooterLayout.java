package com.xsp.refresh.swipe.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xsp.refresh.swipe.core.ISwipeLoadMoreTrigger;


public class SwipeFooterLayout extends FrameLayout implements ISwipeLoadMoreTrigger {

    public SwipeFooterLayout(Context context) {
        this(context, null);
    }

    public SwipeFooterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onLoadMore() {

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
