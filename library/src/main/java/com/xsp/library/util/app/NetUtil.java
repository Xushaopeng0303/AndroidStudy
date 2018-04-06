package com.xsp.library.util.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.xsp.library.util.BaseUtil;

/**
 * Net util
 */
public class NetUtil extends BaseUtil {

    public static final int NETWORK_WIFI    = 1;       // wifi network
    public static final int NETWORK_4G      = 4;       // "4G" networks
    public static final int NETWORK_3G      = 3;       // "3G" networks
    public static final int NETWORK_2G      = 2;       // "2G" networks
    public static final int NETWORK_UNKNOWN = 0;       // unknown network
    public static final int NETWORK_NO      = -1;      // no network

    private static final int NETWORK_TYPE_GSM       = 16;
    private static final int NETWORK_TYPE_TD_SCDMA  = 17;
    private static final int NETWORK_TYPE_IWLAN     = 18;

    /**
     * Open the network settings interface
     */
    public static void openWirelessSettings(Context context) {
        if (null == context) {
            return;
        }
        if (android.os.Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * Get active network information
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        if (null == context) {
            return null;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return null == cm ? null : cm.getActiveNetworkInfo();
    }

    /**
     * Determine whether the network is available
     * <p>need permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     */
    public static boolean isAvailable(Context context) {
        if (null == context) {
            return false;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        return null != info && info.isAvailable();
    }

    /**
     * To determine whether the network connection
     * <p>need permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     */
    public static boolean isConnected(Context context) {
        if (null == context) {
            return false;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        return null != info && info.isConnected();
    }

    /**
     * To determine whether the network is 4G
     * <p>need permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     */
    public static boolean is4G(Context context) {
        if (null == context) {
            return false;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        return null != info && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * To determine whether the network is 4G
     * <p>need permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     */
    public static boolean isWifi(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return null != cm && null != cm.getActiveNetworkInfo() && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Get the name of the mobile network operator
     * @return Mobile network operator name, such as China Unicom, China Mobile, China Telecom
     */
    public static String getNetworkOperatorName(Context context) {
        if (null == context) {
            return null;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return null != tm ? tm.getNetworkOperatorName() : null;
    }

    /**
     * Get the terminal type of the mobile
     * <p>{@link TelephonyManager#PHONE_TYPE_NONE }
     * <p>{@link TelephonyManager#PHONE_TYPE_GSM  }
     * <p>{@link TelephonyManager#PHONE_TYPE_CDMA }
     * <p>{@link TelephonyManager#PHONE_TYPE_SIP  }
     */
    public static int getPhoneType(Context context) {
        if (null == context) {
            return -1;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return null != tm ? tm.getPhoneType() : -1;
    }


    /**
     * Gets the current network type, such as WIFI, 2G, 3G, 4G
     * <p>need permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     * <li>{@link #NETWORK_WIFI   } = 1;</li>
     * <li>{@link #NETWORK_4G     } = 4;</li>
     * <li>{@link #NETWORK_3G     } = 3;</li>
     * <li>{@link #NETWORK_2G     } = 2;</li>
     * <li>{@link #NETWORK_UNKNOWN} = 5;</li>
     * <li>{@link #NETWORK_NO     } = -1;</li>
     */
    private static int getNetWorkType(Context context) {
        int netType = NETWORK_NO;
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NETWORK_2G;
                        break;
                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NETWORK_3G;
                        break;
                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NETWORK_4G;
                        break;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            netType = NETWORK_3G;
                        } else {
                            netType = NETWORK_UNKNOWN;
                        }
                        break;
                }
            } else {
                netType = NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    /**
     * Gets the current network type, such as WIFI, 2G, 3G, 4G
     * <p>need permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     * <li>{@link #NETWORK_WIFI   } = 1;</li>
     * <li>{@link #NETWORK_4G     } = 4;</li>
     * <li>{@link #NETWORK_3G     } = 3;</li>
     * <li>{@link #NETWORK_2G     } = 2;</li>
     * <li>{@link #NETWORK_UNKNOWN} = 5;</li>
     * <li>{@link #NETWORK_NO     } = -1;</li>
     */
    public static String getNetWorkTypeName(Context context) {
        switch (getNetWorkType(context)) {
            case NETWORK_WIFI:
                return "NETWORK_WIFI";
            case NETWORK_4G:
                return "NETWORK_4G";
            case NETWORK_3G:
                return "NETWORK_3G";
            case NETWORK_2G:
                return "NETWORK_2G";
            case NETWORK_NO:
                return "NETWORK_NO";
            default:
                return "NETWORK_UNKNOWN";
        }
    }
}
