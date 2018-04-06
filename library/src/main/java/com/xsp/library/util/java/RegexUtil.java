package com.xsp.library.util.java;


import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * <p>Regex tool class</p>
 * <pre>
 *     Provide verification email, cell phone number, phone number, ID number, number, etc.
 * </pre>
 */
public final class RegexUtil {

    /**
     * replace the 4 figures in the middle of the phone number with *
     *
     * @param phone phone number
     * @return * replacement phone number
     */
    public static String phoneNoHide(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            return phone;
        }
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * bank card number reserved for the last 4, the other with * replacement
     *
     * @param cardId back card
     * @return * replacement back card
     */
    public static String cardIdHide(String cardId) {
        if(TextUtils.isEmpty(cardId)) {
            return cardId;
        }
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }

    /**
     * replace the 10 figures in the middle of the id card with *
     *
     * @param id id card
     * @return * replacement id card
     */
    public static String idHide(String id) {
        if (TextUtils.isEmpty(id) || id.length() != 18) {
            return id;
        }

        return id.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1** **** ****$2");
    }

    /**
     * whether is License plate number（沪A88888）
     *
     * @param vehicleNo license plate number
     * @return whether is License plate number
     */

    public static boolean checkVehicleNo(String vehicleNo) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$");
        return pattern.matcher(vehicleNo).find();

    }

    /**
     * whether is id card， 15 or 18
     *
     * @param idCard id card
     * @return if is id card return true，else return false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * whether is phone number
     *
     * @param mobile phone number
     * @return if is phone number return true，else return false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * whether is Email
     *
     * @param email email address，eg：zhangsan@sina.com，zhangsan@xxx.com.cn
     * @return if is email return true，else return false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * Verify integers (positive and negative integers)
     *
     * @param digit input
     * @return if is an integers return true，else return false
     */
    public static boolean checkDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }

    /**
     * verify whether the str is all chinese characters
     *
     * @param str string to verified
     * @return if is chinese return true，else return false
     */
    public static boolean checkChinese(String str) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, str);
    }

    /**
     * whether it is a url
     *
     * @param url eg：http://blog.csdn.net:80/xyang81/article/details/7705960? or http://www.csdn.net:80
     * @return if is url return true，else return false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * whether it is China Post Code
     *
     * @param postcode China Post Code
     * @return if is china post code return true，else return false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

}