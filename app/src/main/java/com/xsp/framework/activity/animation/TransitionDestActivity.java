package com.xsp.framework.activity.animation;

import android.os.Bundle;
import android.util.Log;

import com.xsp.framework.R;
import com.xsp.library.activity.BaseActivity;
import com.xsp.library.util.KShareViewActivityManager;

/**
 * 跳转到的第一个页面
 */
public class TransitionDestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_translate_desc_activity);
        Log.d("xsp", "onCreate: 第二个界面");
    }

    @Override
    public void onBackPressed() {
        KShareViewActivityManager.getInstance(TransitionDestActivity.this).finish(TransitionDestActivity.this);
    }

    @Override
    protected void onResume() {
        Log.d("xsp", "onResume: 第二个界面");
        super.onResume();
    }
}
