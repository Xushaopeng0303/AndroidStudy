package com.xsp.library.util.java;

import android.text.TextUtils;

import com.xsp.library.util.BaseUtil;

/**
 * Char util
 */
public class CharUtil extends BaseUtil {

    /**
     * determine whether the character is Chinese，include
     * @param c character
     * @return return true if c is a character, else false
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * calculate the number of Chinese characters in a string
     * @param strName string name
     * @return  return the number of Chinese characters in a string
     */
    public static int countChinese(String strName) {
        if (TextUtils.isEmpty(strName)) {
            return 0;
        }

        int count = 0;
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                count++;
            }
        }
        return count;
    }
}
