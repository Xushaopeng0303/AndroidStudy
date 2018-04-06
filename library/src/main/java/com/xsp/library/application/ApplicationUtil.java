package com.xsp.library.application;


import android.app.Application;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class ApplicationUtil {

    static boolean judgeProcess(Application application) {
        if (application == null) {
            return false;
        }
        String mProcessName = getProcessName(application);
        String packageName = application.getPackageName();
        return mProcessName.equals(packageName);
    }

    private static String getProcessName(Application application) {
        File cmdFile = new File("/proc/self/cmdline");

        if (cmdFile.exists() && !cmdFile.isDirectory()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
                String processName = reader.readLine();

                if (!TextUtils.isEmpty(processName))
                    return processName.trim();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return application.getApplicationInfo().processName;
    }
}
