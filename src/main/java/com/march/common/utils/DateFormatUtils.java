package com.march.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * CreateAt : 7/11/17
 * Describe : 时间格式化转换
 *
 * @author chendong
 */
public class DateFormatUtils {

    public static final String PATTERN_yyyyMMdd       = "yyyy-MM-dd";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyy-MM-dd:HH:mm:ss";
    public static final String PATTERN_HHmmss         = "HH:mm:ss";

    private static Map<String, SimpleDateFormat> sDateFormatMap;

    private static SimpleDateFormat getFormat(String pattern) {
        if (sDateFormatMap == null) {
            sDateFormatMap = new HashMap<>();
        }
        SimpleDateFormat format = sDateFormatMap.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern, Locale.getDefault());
            sDateFormatMap.put(pattern, format);
        }
        return format;
    }

    public static String format(String pattern, long time) {
        return getFormat(pattern).format(time);
    }

    public static String formatNow(String pattern) {
        return getFormat(pattern).format(System.currentTimeMillis());
    }

    public static long parse(String pattern, String formatStr) {
        long time = 0;
        try {
            time = getFormat(pattern).parse(formatStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static void clear() {
        sDateFormatMap.clear();
    }
}
