package com.xsp.library.util.app;

import android.app.ActivityManager;
import android.content.Context;

import com.xsp.library.util.BaseUtil;
import com.xsp.library.util.java.ArrayUtil;

import java.util.List;

/**
 * Process util, manager process and thread info
 */
public class ProcessUtil extends BaseUtil {

    /**
     * get current process id
     */
    public static int getProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * get current process name
     */
    public static String getProcessName(Context context) {
        return context == null ? "" : context.getApplicationInfo().processName;
    }

    /**
     * whether current process is main process
     */
    public static boolean isMainProcess(Context context) {
        String processName = getProcessName(context);
        String name = context.getPackageName();
        return processName.equals(name);
    }

    /**
     * get current thread id
     */
    public static long getThreadId() {
        return Thread.currentThread().getId();
    }

    /**
     * get current thread name
     */
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * if current thread is main thread
     */
    public static boolean isMainThread() {
        long tid = getThreadId();
        return tid == 1;
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(150);
        if (ArrayUtil.isEmpty(list)) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo info : list) {
            if (info.service.getClassName().equalsIgnoreCase(serviceName)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}
