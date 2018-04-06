package com.xsp.library.view.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xsp.library.view.refresh.core.ISwipeRefreshTrigger;


public abstract class SwipeHeaderLayout extends FrameLayout implements ISwipeRefreshTrigger {

    public SwipeHeaderLayout(Context context) {
        this(context, null);
    }

    public SwipeHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
