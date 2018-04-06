package com.xsp.library.util.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.location.LocationManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

import com.xsp.library.util.BaseUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Equipment related features util
 */
public class DeviceUtil extends BaseUtil {

    /**
     * mobile phone is open GPS positioning
     */
    public static boolean isOpenGPS(final Context context) {
        if (context == null) {
            return false;
        }

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * get app name
     */
    public static String getAppName(final Context context) {
        if (context == null) {
            return null;
        }
        ApplicationInfo appInfo = context.getApplicationInfo();
        return appInfo.loadLabel(context.getPackageManager()).toString();
    }

    /**
     * get IMEI
     */
    public static String getIMEI(Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static int getWidth(Context context) {
        if (context == null) {
            return 0;
        }

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getWidth();
    }

    public static int getHeight(Context context) {
        if (context == null) {
            return 0;
        }

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getHeight();
    }

    public static List<String> getAllInstallApp(Context context) {
        if (context == null) {
            return null;
        }

        List<String> list = new ArrayList<>();
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo i : packages) {
            if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                list.add(i.applicationInfo.loadLabel(context.getPackageManager()).toString());
            }
        }
        return list;
    }

    /**
     * whether has SdCard
     */
    public static boolean hasSdCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * get SdCard path
     */
    public static String getSDPath() {
        return hasSdCard() ? Environment.getExternalStorageDirectory().getPath() : null;
    }

    /**
     * calculate the remaining space on SdCard, (unit : byte)
     */
    public static long freeSpaceOnSd() {
        String sdCardPath = getSDPath();
        if (TextUtils.isEmpty(sdCardPath)) {
            return 0;
        }
        StatFs stat = new StatFs(sdCardPath);
        return ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
    }

    public static File getSystemPictureFile(Context context) {
        return context == null ? null : context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

}
