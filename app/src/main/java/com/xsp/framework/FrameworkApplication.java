package com.xsp.framework;

import com.xsp.library.application.BaseApplication;
import com.xsp.library.image.ImageMgr;
import com.xsp.library.util.LogUtil;
import com.xsp.library.util.app.LifecycleHandler;
import com.xsp.library.util.unused.CrashHandlerUtil;


public class FrameworkApplication extends BaseApplication {
    private boolean mIsBackground = false;

    @Override
    public void onCreate() {
        // Fresco 初始化
        ImageMgr.init(this.getApplicationContext());
        super.onCreate();

        // 日志处理
        LogUtil.debug(true);

        //崩溃处理
//        handlerCrash();

        // Activity生命周期处理
        LifecycleHandler.init(this);
    }

    @Override
    public void masterProcess() {
    }

    @Override
    public void nonMasterProcess() {

    }

    private void handlerCrash() {
        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
        crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
    }

    private void activityLifecycle() {
        LifecycleHandler handler = new LifecycleHandler() {
            @Override
            public void onAppGroundSwitch(boolean background) {
                super.onAppGroundSwitch(background);
                if (mIsBackground != background) {
                    mIsBackground = background;
                    if (!background) {
                        LogUtil.d("Application is switch to foreground");
                    } else {
                        LogUtil.d("Application is switch to background");
                    }
                }
            }
        };

        this.registerActivityLifecycleCallbacks(handler);
        this.registerComponentCallbacks(handler);

    }

}
