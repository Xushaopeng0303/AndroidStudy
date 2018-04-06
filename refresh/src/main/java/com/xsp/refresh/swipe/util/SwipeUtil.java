package com.xsp.refresh.swipe.util;

import android.view.View;

import com.xsp.refresh.swipe.core.ISwipeLoadMoreTrigger;
import com.xsp.refresh.swipe.core.ISwipeRefreshTrigger;


public class SwipeUtil {

    public static boolean isRefreshTrigger(View refreshHeader) {
        return null != refreshHeader && refreshHeader instanceof ISwipeRefreshTrigger;
    }

    public static boolean isLoadMoreTrigger(View loadMoreFooter) {
        return null != loadMoreFooter && loadMoreFooter instanceof ISwipeLoadMoreTrigger;
    }
}
