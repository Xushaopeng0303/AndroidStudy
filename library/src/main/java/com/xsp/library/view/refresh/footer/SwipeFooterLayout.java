package com.xsp.library.view.refresh.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xsp.library.view.refresh.core.ISwipeLoadMoreTrigger;


public abstract class SwipeFooterLayout extends FrameLayout implements ISwipeLoadMoreTrigger {

    public SwipeFooterLayout(Context context) {
        this(context, null);
    }

    public SwipeFooterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
