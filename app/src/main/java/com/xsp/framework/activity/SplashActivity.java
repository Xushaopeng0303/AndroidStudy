package com.xsp.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;

/**
 * 闪屏页
 */
public class SplashActivity extends BaseActivity {

    private static final int SPLASH_SURVIVAL_TIME = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_SURVIVAL_TIME);
    }
}
