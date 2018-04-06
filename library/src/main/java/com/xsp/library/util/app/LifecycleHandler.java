package com.xsp.library.util.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Application through this interface provides a set of callback methods used to allow developers
 * to focus on the Activity life cycle events
 */
public class LifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private static boolean sInBackground = false;
    private static LifecycleHandler mIns;

    public static void init(Application application) {
        if (application == null) {
            return;
        }
        if (mIns == null) {
            mIns = new LifecycleHandler();
            application.registerActivityLifecycleCallbacks(mIns);
            application.registerComponentCallbacks(mIns);
        }
    }

    public void onAppGroundSwitch(boolean background) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (sInBackground) {
            sInBackground = false;
            onAppGroundSwitch(false);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int i) {
        if (i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            if (!sInBackground) {
                sInBackground = true;
                onAppGroundSwitch(true);
            }
        }
    }

}
