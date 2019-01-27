package com.march.common.x;

import android.text.TextUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * CreateAt : 2017/1/13
 * Describe : 检测操作工具类
 *
 * @author chendong
 */
public class EmptyX {

    public static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static boolean isAnyEmpty(CharSequence... charSequences) {
        for (CharSequence charSequence : charSequences) {
            if (isEmpty(charSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(File file) {
        return file == null || FileX.isNotExist(file);
    }

    public static boolean isEmpty(Integer integer) {
        return integer == null || integer == 0;
    }

    public static boolean isEmpty(Long l) {
        return l == null || l == 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || isEmpty(map.keySet());
    }

}
