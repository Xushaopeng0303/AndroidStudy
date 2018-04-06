package com.xsp.library.util.ui;

import android.view.View;

/**
 * 防止快速点击的监听器
 */
public abstract class NoFastClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private int clickIntervalTime = MIN_CLICK_DELAY_TIME;
    private long lastClickTime = 0;

    /**
     * 默认构造器
     */
    protected NoFastClickListener() {

    }

    /**
     * 构造函数
     * @param milliSecond 设置快速点击限制时间间隔
     */
    protected NoFastClickListener(int milliSecond) {
        this.clickIntervalTime = milliSecond;
    }


    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > clickIntervalTime) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    /**
     * 防止快速点击
     * @param view 点击的 View
     */
    protected abstract void onNoDoubleClick(View view);
}
