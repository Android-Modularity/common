package com.march.common.exts;

import java.util.regex.Pattern;

/**
 * CreateAt : 2018/6/16
 * Describe :
 *
 * @author chendong
 */
public class RegexX {

    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$";
    public static final String REGEX_IP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";


    public static boolean isIp(String ipAddress) {
        if (ipAddress.length() < 7 || ipAddress.length() > 15) {
            return false;
        }
        return isMatch(REGEX_IP, ipAddress);
    }


    public static boolean isMobileExact(CharSequence phone) {
        if (phone.length() != 11) {
            return false;
        }
        return isMatch(REGEX_MOBILE_EXACT, phone);
    }


    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }


}
