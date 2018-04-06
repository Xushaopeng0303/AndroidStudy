package com.xsp.library.util.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xsp.library.LibraryBuildConfig;

import java.lang.reflect.Field;

/**
 * Dimension util
 */
public class DimenUtil {

    public static final int DENSITY_LOW = 120;
    public static final int DENSITY_MEDIUM = 160;
    public static final int DENSITY_HIGH = 240;
    public static final int DENSITY_XHIGH = 320;
    private static Context mContext = LibraryBuildConfig.getIns().getContext().getApplicationContext();
    private static Resources mResource = mContext.getResources();
    private static DisplayMetrics mMetrics = mResource.getDisplayMetrics();

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;

    // -- dimens convert

    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
        }
        return 0;
    }

    public static int dp2px(float value) {
        return (int) applyDimension(DP_TO_PX, value, mMetrics);
    }

    public static int sp2px(float value) {
        return (int) applyDimension(SP_TO_PX, value, mMetrics);
    }

    public static int px2dp(float value) {
        return (int) applyDimension(PX_TO_DP, value, mMetrics);
    }

    public static int px2sp(float value) {
        return (int) applyDimension(PX_TO_SP, value, mMetrics);
    }

    public static int getScreenWidth() {
        DisplayMetrics dm = mResource.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = mResource.getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getDensity() {
        return mMetrics.density;
    }

    // -- update layout
    public static void updateLayout(View view, int w, int h) {
        if (view == null)
            return;
        LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (w != -3)
            params.width = w;
        if (h != -3)
            params.height = h;
        view.setLayoutParams(params);
    }

    public static void updateLayoutMargin(View view, int l, int t, int r, int b) {
        if (view == null) {
            return;
        }
        LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        if (params instanceof RelativeLayout.LayoutParams) {
            updateMargin(view, (RelativeLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof LinearLayout.LayoutParams) {
            updateMargin(view, (LinearLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof FrameLayout.LayoutParams) {
            updateMargin(view, (FrameLayout.LayoutParams) params, l, t, r, b);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t, int r, int b) {
        if (view == null)
            return;
        if (l != -3)
            params.leftMargin = l;
        if (t != -3)
            params.topMargin = t;
        if (r != -3)
            params.rightMargin = r;
        if (b != -3)
            params.bottomMargin = b;
        view.setLayoutParams(params);
    }

    public static void createListViewLayout(View view, int w, int h) {
        if (view == null)
            return;
        ListView.LayoutParams lp = (ListView.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            if (w == -3)
                w = ListView.LayoutParams.MATCH_PARENT;
            if (h == -3)
                h = ListView.LayoutParams.MATCH_PARENT;
            lp = new ListView.LayoutParams(w, h);
            view.setLayoutParams(lp);
        }
    }

    public static void disappearLayout(View view) {
        updateLayoutMargin(view, 0, 0, 0, 0);
        updateLayout(view, 0, 0);
    }

    // -- window dimens

    public static boolean isLowDensity() {
        float densityDpi = mMetrics.densityDpi;
        return densityDpi == DENSITY_LOW || densityDpi == DENSITY_MEDIUM;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        if (activity != null && activity.getWindow() != null) {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        }
        return rect.top;
    }

    public static int getStatusBarHeight2() {
        int statusBarHeight = 0;
        try {
            Class<?> cl = Class.forName("com.android.internal.R$dimen");
            Object obj = cl.newInstance();
            Field field = cl.getField("status_bar_height");

            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = mResource.getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取屏幕英寸数
     *
     * @return 屏幕英寸数
     */
    public static double getDiagonalInch() {
        int width = mMetrics.widthPixels; // 屏幕宽度（像素）
        int height = mMetrics.heightPixels; // 屏幕高度（像素）
        int densityDpi = mMetrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / densityDpi;
    }

    public static int getWindowWidth() {
        return mMetrics.widthPixels;
    }

    public static int getWindowHeight() {
        return mMetrics.heightPixels;
    }

    public static int getContentHeight2() {
        return mMetrics.heightPixels - getStatusBarHeight2();
    }

    public static String getRatio() {
        return String.format("%s*%s", mMetrics.widthPixels, mMetrics.heightPixels);
    }

    /**
     * @return 小尺寸
     */
    public static boolean isSmallSize() {
        int width = mMetrics.widthPixels; // 屏幕宽度（像素）
        int height = mMetrics.heightPixels; // 屏幕高度（像素）
        return width * height <= 480 * 800;
    }
}