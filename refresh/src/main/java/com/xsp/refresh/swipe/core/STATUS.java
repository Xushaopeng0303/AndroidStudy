package com.xsp.refresh.swipe.core;

import android.util.Log;


public final class STATUS {
    public static final String TAG = "SwipeToLoad";

    // refresh
    public static final int REFRESH_RETURNING    = -5;
    public static final int REFRESH_COMPLETE     = -4;
    public static final int REFRESHING           = -3;
    public static final int RELEASE_TO_REFRESH   = -2;
    public static final int SWIPING_TO_REFRESH   = -1;

    public static final int STATUS_DEFAULT       = 0;

    // load more
    public static final int SWIPING_TO_LOAD_MORE = 1;
    public static final int RELEASE_TO_LOAD_MORE = 2;
    public static final int LOADING_MORE         = 3;
    public static final int LOAD_MORE_COMPLETE   = 4;
    public static final int LOAD_MORE_RETURNING  = 5;

    public static boolean isRefreshing(final int status) {
        return status == STATUS.REFRESHING;
    }

    public static boolean isLoadingMore(final int status) {
        return status == STATUS.LOADING_MORE;
    }

    public static boolean isRefreshComplete(final int status) {
        return status == REFRESH_COMPLETE;
    }

    public static boolean isLoadMoreComplete(final int status) {
        return status == LOAD_MORE_COMPLETE;
    }

    public static boolean isRefreshReturning(final int status) {
        return status == STATUS.REFRESH_RETURNING;
    }

    public static boolean isLoadMoreReturning(final int status) {
        return status == STATUS.LOAD_MORE_RETURNING;
    }

    public static boolean isReleaseToRefresh(final int status) {
        return status == STATUS.RELEASE_TO_REFRESH;
    }

    public static boolean isReleaseToLoadMore(final int status) {
        return status == STATUS.RELEASE_TO_LOAD_MORE;
    }

    public static boolean isSwipingToRefresh(final int status) {
        return status == STATUS.SWIPING_TO_REFRESH;
    }

    public static boolean isSwipingToLoadMore(final int status) {
        return status == STATUS.SWIPING_TO_LOAD_MORE;
    }

    public static boolean isRefreshStatus(final int status) {
        return status < STATUS.STATUS_DEFAULT;
    }

    public static boolean isLoadMoreStatus(final int status) {
        return status > STATUS.STATUS_DEFAULT;
    }

    public static boolean isStatusDefault(final int status) {
        return status == STATUS.STATUS_DEFAULT;
    }

    public static String getStatus(int status) {
        final String statusInfo;
        switch (status) {
            case REFRESH_RETURNING:
                statusInfo = "status_refresh_returning";
                break;
            case REFRESH_COMPLETE:
                statusInfo = "stats_refresh_complete";
                break;
            case REFRESHING:
                statusInfo = "status_refreshing";
                break;
            case RELEASE_TO_REFRESH:
                statusInfo = "status_release_to_refresh";
                break;
            case SWIPING_TO_REFRESH:
                statusInfo = "status_swiping_to_refresh";
                break;
            case STATUS_DEFAULT:
                statusInfo = "status_default";
                break;
            case SWIPING_TO_LOAD_MORE:
                statusInfo = "status_swiping_to_load_more";
                break;
            case RELEASE_TO_LOAD_MORE:
                statusInfo = "status_release_to_load_more";
                break;
            case LOADING_MORE:
                statusInfo = "status_loading_more";
                break;
            case LOAD_MORE_COMPLETE:
                statusInfo = "status_load_more_complete";
                break;
            case LOAD_MORE_RETURNING:
                statusInfo = "status_load_more_returning";
                break;
            default:
                statusInfo = "status_illegal!";
                break;
        }
        return statusInfo;
    }

    public static void printStatus(int status) {
        Log.d(TAG, "printStatus:" + getStatus(status));
    }
}
