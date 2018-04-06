package com.xsp.library.view.refresh.util;

import android.view.View;

import com.xsp.library.view.refresh.core.ISwipeLoadMoreTrigger;
import com.xsp.library.view.refresh.core.ISwipeRefreshTrigger;


public class SwipeUtil {

    public static boolean isRefreshTrigger(View refreshHeader) {
        return null != refreshHeader && refreshHeader instanceof ISwipeRefreshTrigger;
    }

    public static boolean isLoadMoreTrigger(View loadMoreFooter) {
        return null != loadMoreFooter && loadMoreFooter instanceof ISwipeLoadMoreTrigger;
    }
}
