package com.xsp.framework.util;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.xsp.framework.activity.MainActivity;

public class LauncherUtil {

    /**
     * <p>add shortcut</p>
     * need permission : com.android.launcher.permission.INSTALL_SHORTCUT
     *
     * @param context        context
     * @param shortcutName   shortcut name
     * @param shortcutBitmap shortcut bitmap
     */
    public static void createShortcut(Context context, String shortcutName, int shortcutBitmap) {
        if (context == null) {
            return;
        }

        // 快捷方式设置，文本，Icon
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);        // 快捷名称
        shortcut.putExtra("duplicate", false);                              // 快捷图标是否允许重复
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, shortcutBitmap);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);    // 快捷方式Icon

        // 快捷方式点击事件
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.putExtra("params1", "value1");                       // 参数
        shortcutIntent.setClass(context, MainActivity.class);               // 启动页面
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        // 发送广播
        context.sendBroadcast(shortcut);
    }

    /**
     * <p>delete shortcut</p>
     * need permission : com.android.launcher.permission.UNINSTALL_SHORTCUT
     *
     * @param context        context
     * @param shortcutName   shortcut name
     * @param className      shortcut launch class name，No package name
     */
    public static void deleteShortcut(Context context, String shortcutName, Class<?> className) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);

        // 带包名的全路径类名
        String appClass = className.getCanonicalName();
        ComponentName comp = new ComponentName(context.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        // 发送广播
        context.sendBroadcast(shortcut);
    }

}
