package com.xsp.library;

import android.app.Application;
import android.content.Context;

public class LibraryBuildConfig {

    private boolean mDebug = false;
    private static Application mContext;

    private static LibraryBuildConfig mIns;

    public static void init(Application application) {
        if (application == null) {
            throw new RuntimeException("Library application can't be null");
        }

        if (mIns == null) {
            mContext = application;
            mIns = new LibraryBuildConfig();
        }
    }

    public static LibraryBuildConfig getIns() {
        return mIns;
    }


    public boolean isDebug() {
        return mDebug;
    }

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    public Context getContext() {
        return mContext.getApplicationContext();
    }

}
