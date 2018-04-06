package com.xsp.library.util;

/**
 * Log util class, you can view the details of the use of keywords, default is 'library_log'
 */
public class LogUtil {
    private static String mTag = "library_log";
    private static boolean mDebug = true;

    public static void setTag(String tag) {
        LogUtil.mTag = tag;
    }

    public static void debug(boolean log) {
        LogUtil.mDebug = log;
    }

    public static void i(String msg) {
        if (mDebug) {
            android.util.Log.i(mTag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (mDebug) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void d(String msg) {
        if (mDebug) {
            android.util.Log.d(mTag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (mDebug) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void w(String msg) {
        if (mDebug) {
            android.util.Log.w(mTag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (mDebug) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void v(String msg) {
        if (mDebug) {
            android.util.Log.v(mTag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (mDebug) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void e(String msg) {
        android.util.Log.e(mTag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }
}
