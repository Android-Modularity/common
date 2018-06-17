package com.march.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CreateAt : 2018/6/16
 * Describe :
 *
 * @author chendong
 */
public class RegexUtils {

    public static boolean isIp(String ipAddress) {
        if (ipAddress.length() < 7 || ipAddress.length() > 15) {
            return false;
        }
        String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(ipAddress);
        return mat.find();
    }

}
