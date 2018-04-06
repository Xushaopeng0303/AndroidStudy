package com.xsp.library.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xsp.library.standard.IActivity;
import com.xsp.library.util.security.SecurityUtils;

/**
 * The base class for each activity
 */
public class BaseActivity extends FragmentActivity implements IActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 为了防范拒绝服务漏洞攻击，加catch
        if (SecurityUtils.checkActivityRefuseServiceAndFinish(this)) {
            return;
        }
    }

    @Override
    public void exitApp() {

    }
}
