package com.xsp.library.util.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xsp.library.LibraryBuildConfig;
import com.xsp.library.util.BaseUtil;


public class SharedPreferencesUtil extends BaseUtil {

    private static final String SP_FILE_NAME = "_sp";
    private static SharedPreferencesUtil mInstance;
    private SharedPreferences mPref;

    public static SharedPreferencesUtil getIns() {
        if (null == mInstance) {
            synchronized (SharedPreferencesUtil.class) {
                if (null == mInstance) {
                    mInstance = new SharedPreferencesUtil();
                }
            }
        }
        return mInstance;
    }

    private SharedPreferencesUtil() {
        Context context = LibraryBuildConfig.getIns().getContext();
        mPref = context.getSharedPreferences(context.getPackageName() + SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    private void applyToEditor(Editor editor) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public int getIntValue(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public long getLongValue(String key, long defValue) {
        return mPref.getLong(key, defValue);
    }

    public boolean getBooleanValue(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public String getStringValue(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public float getFloatValue(String key, float defValue) {
        return mPref.getFloat(key, defValue);
    }

    public void setIntValue(String key, int value) {
        mPref.edit().putInt(key, value).apply();
    }

    public void setLongValue(String key, long value) {
        mPref.edit().putLong(key, value).apply();
    }

    public void setBooleanValue(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public void setStringValue(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public void setFloatValue(String key, float value) {
        mPref.edit().putFloat(key, value).apply();
    }

    public void remove(String key) {
        mPref.edit().remove(key).apply();
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

}
