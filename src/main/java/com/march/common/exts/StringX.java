package com.march.common.exts;

import java.util.Locale;

/**
 * CreateAt : 2018/10/25
 * Describe :
 *
 * @author chendong
 */
public class StringX {

    public static String thumb(String source, int length, String ending) {
        if(EmptyX.isEmpty(source)) {
            return "";
        }
        if (source.length() < length) {
            return source + ending;
        } else {
            return source.substring(0, length) + ending;
        }
    }

    public static String format(String template, Object... args) {
        return String.format(Locale.getDefault(), template, args);
    }
}
