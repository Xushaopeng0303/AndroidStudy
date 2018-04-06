package com.xsp.library.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.xsp.library.R;
import com.xsp.library.application.BaseApplication;
import com.xsp.library.util.ui.DimenUtil;

/**
 * 自定义Drawable，免去在XML中定义，减少代码
 *
 * @author Sam
 */
public class CustomDrawable {
    private static Resources mRes;
    private static Resources getResources() {
        if (mRes == null) {
            mRes = BaseApplication.getInstance().getResources();
        }

        return mRes;
    }


    public static Drawable getDefaultBlueDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(5);
        drawable.setColor(getResources().getColor(R.color.blue));
        return drawable;
    }

    public static GradientDrawable getDrawable(String fillColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(Color.parseColor(fillColor));
        return drawable;
    }

    public static GradientDrawable getDrawable(int fillColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(getResources().getColor(fillColor));
        return drawable;
    }

    public static Drawable getDrawable(int strokeColor, int fillColor, int radius) {
        final int strokeWidth = DimenUtil.dp2px(2);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(getResources().getColor(fillColor));
        drawable.setStroke(strokeWidth, getResources().getColor(strokeColor));
        drawable.setCornerRadius(radius);
        return drawable;
    }



}
