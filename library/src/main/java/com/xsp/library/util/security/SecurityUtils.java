package com.xsp.library.util.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xsp.library.util.BaseUtil;


/**
 * Security function related tools and methods
 */
public final class SecurityUtils extends BaseUtil {

    /**
     * 对Activity进行拒绝服务漏洞检查，如果发生崩溃，会finish activity
     * @param activity activity
     * @return 是否命中拒绝服务漏洞，发生崩溃
     */
    public static boolean checkActivityRefuseServiceAndFinish(Activity activity) {
        if (activity != null) {
            Intent intent = activity.getIntent();
            boolean isCrash = checkIntentRefuseService(intent);
            if (isCrash) {
                try {
                    activity.finish();
                } catch (Exception e) {
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 对传入的intent数据，进行拒绝服务漏洞检查
     * @param intent intent
     * @return 是否命中拒绝服务漏洞，发生崩溃
     */
    public static boolean checkIntentRefuseService(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
                    bundle.isEmpty();
                } catch (Exception e) {
                    return true;
                }
            }
        }
        return false;
    }
}
