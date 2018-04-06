package com.xsp.library.util.java;

import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.xsp.library.util.BaseUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * String util
 */
public class StringUtil extends BaseUtil {

    /**
     * is null or its length is 0 or it is made by space
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * compare two string
     */
    public static boolean isEquals(String actual, String expected) {
        return ObjectUtil.isEquals(actual, expected);
    }

    /**
     * capitalize first letter
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * @param str source str
     * @return  utf-8 string
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }


    /**
     * highlight part str
     * @param str           complete str
     * @param strHighLight  str high light
     * @param color         high light color
     */
    public static void strHighLight(String str, String strHighLight, @ColorInt int color) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(strHighLight)) {
            return;
        }
        int start = str.indexOf(strHighLight);
        int end   = start + strHighLight.length();
        int len   = str.length() - 1;
        if ((start < 0 || start > len) || (end < 0 || end > len) || start > end) {
            return;
        }
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * assemble two strings
     */
    public static String assembleStr(final String str1, final String str2, char separator) {
        boolean str1Empty = TextUtils.isEmpty(str1);
        boolean str2Empty = TextUtils.isEmpty(str2);
        if (str1Empty) {
            return str2Empty ? "" : str2;
        } else {
            return str2Empty ? str1 : str1 + separator + str2;
        }
    }

    /**
     * get the number of messages, when more than 100 then display 99+
     */
    public static String getMsgMaxInt(int value) {
        return value < 100 ? String.valueOf(value) : "99+";
    }
}
