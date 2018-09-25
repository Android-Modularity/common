package com.march.common.mgrs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * CreateAt : 2018/9/25
 * Describe : 时间戳转换, 线程安全
 *
 * @author chendong
 */
public class DateFormatMgr implements IMgr {

    public static final String PATTERN_yyyyMMdd = "yyyy-MM-dd";
    public static final String PATTERN_HHmmss = "HH:mm:ss";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyy-MM-dd:HH:mm:ss";

    private Map<String, ThreadLocal<SimpleDateFormat>> mDateFmtThreadLocalMap;

    /**
     * 根据模板转换时间
     *
     * @param pattern 模板
     * @param time    时间戳
     * @return 转换后的时间
     */
    public String format(String pattern, long time) {
        return getFormat(pattern).format(time);
    }

    /**
     * 根据模板转换当前时间
     *
     * @param pattern 模板
     * @return 转换后的时间
     */
    public String format(String pattern) {
        return getFormat(pattern).format(System.currentTimeMillis());
    }

    /**
     * 根据模板将 字符串 转换为时间
     *
     * @param pattern   模板
     * @param formatStr 字符串
     * @return 时间戳
     */
    public long parse(String pattern, String formatStr) {
        long time = 0;
        try {
            time = getFormat(pattern).parse(formatStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    // 根据 pattern 缓存 SimpleDateFormat
    private SimpleDateFormat getFormat(String pattern) {
        if (mDateFmtThreadLocalMap == null) {
            mDateFmtThreadLocalMap = new HashMap<>();
        }
        ThreadLocal<SimpleDateFormat> threadLocal = mDateFmtThreadLocalMap.get(pattern);
        if (threadLocal == null || threadLocal.get() == null) {
            threadLocal = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(pattern, Locale.getDefault());
                }
            };
            mDateFmtThreadLocalMap.put(pattern, threadLocal);
        }
        return mDateFmtThreadLocalMap.get(pattern).get();
    }

    @Override
    public void recycle() {
        if (mDateFmtThreadLocalMap != null) {
            mDateFmtThreadLocalMap.clear();
        }
    }

    @Override
    public boolean isRecycled() {
        return mDateFmtThreadLocalMap == null;
    }
}
