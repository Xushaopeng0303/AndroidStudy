package com.xsp.library.application;

import android.app.Application;

import com.xsp.library.LibraryBuildConfig;

public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        LibraryBuildConfig.init(this);

        // 判断是否是主进程
        if (ApplicationUtil.judgeProcess(this)) {
            masterProcess();
        } else {
            nonMasterProcess();
        }

    }

    /**
     * 主进程
     */
    public abstract void masterProcess();

    /**
     * 非主进程
     */
    public abstract void nonMasterProcess();
}
