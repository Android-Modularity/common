package com.march.common.exts;

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
}
