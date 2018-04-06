package com.xsp.library.util.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.xsp.library.util.BaseUtil;
import com.xsp.library.util.ExceptionUtil;

/**
 * Key Board util
 */
public class KeyBoardUtil extends BaseUtil {

    /**
     * hide keyboard
     */
    public static void hideKeyBoard(Activity activity) {
        if (null == activity) {
            return;
        }
        try {
            InputMethodManager iMM = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != iMM && iMM.isActive() && null != activity.getCurrentFocus()) {
                iMM.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            ExceptionUtil.printStackTrace(e);
        }
    }

    /**
     * hide virtual keyboard
     */
    public static void hideKeyBoard(View v) {
        if (null == v) {
            return;
        }
        InputMethodManager iMM = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (iMM.isActive()) {
            iMM.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * display virtual keyboard
     */
    public static void showKeyboard(View v) {
        if (null == v) {
            return;
        }
        InputMethodManager iMM = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!iMM.isActive()) {
            iMM.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void controlKeyBoard(final View v, final boolean showKeyBoard) {
        if (null == v) {
            return;
        }

        InputMethodManager iMM = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (showKeyBoard) {
            iMM.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        } else {
            iMM.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
