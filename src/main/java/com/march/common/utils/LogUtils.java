package com.march.common.utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.march.common.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 16/8/15
 * Describe : 日志打印操作
 *
 * @author chendong
 */
public class LogUtils {

    private static boolean DEBUG = BuildConfig.DEBUG;
    private static String TAG = "LogUtils";
    private static OnLogListener sOnLogListener;

    public static void setTAG(String TAG) {
        LogUtils.TAG = TAG;
    }

    public static void setOnLogListener(OnLogListener onLogListener) {
        sOnLogListener = onLogListener;
    }

    public interface OnLogListener {
        boolean beforeLog(int level, String tag, String msg);
    }

    // 打印其他类型的 object
    public static void object(Object object) {
        object(findTag(), object);
    }

    public static void object(String tag, Object object) {
        StringBuilder sb = newStringBuilder();
        if (object == null) {
            sb.append("object is null");
        } else {
            sb.append("[").append(object.getClass().getSimpleName()).append("] => ");
            if (object instanceof Map) {
                Map map = (Map) object;
                sb.append("{").append(getEnding());
                for (Object key : map.keySet()) {
                    sb.append("\t").append(key).append("=>").append(map.get(key));
                }
                sb.append("}").append(getEnding());
            } else if (object instanceof Intent) {
                Intent intent = (Intent) object;
                Bundle extras = intent.getExtras();
                sb.append(intent.toString());
                if (extras != null) {
                    sb.append(extras.toString());
                }
            } else if (object instanceof List) {
                List list = (List) object;
                sb.append("{").append(getEnding());
                for (Object item : list) {
                    sb.append("\t").append(item.toString()).append(getEnding());
                }
                sb.append("}").append(getEnding());
            } else {
                sb.append(object.toString());
            }
        }
        e(tag, sb.toString());
    }

    // 带格式打印 json，默认使用 error 打印
    public static void json(String json) {
        json(findTag(), json);
    }

    public static void json(String tag, String json) {
        StringBuilder sb = newStringBuilder();
        if (json == null || json.trim().length() == 0) {
            sb.append("json isEmpty => ").append(json);
        } else {
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    sb.append(jsonObject.toString(2));
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    sb.append(jsonArray.toString(2));
                } else {
                    sb.append("json 格式错误 => ").append(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sb.append("json formatError => ").append(json);
            }
        }
        e(tag, sb.toString());
    }

    // 带格式打印异常
    public static void e(Throwable throwable) {
        e(findTag(), throwable);
    }

    public static void e(String tag, Throwable throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        StringBuilder sb = newStringBuilder();
        if (stackTrace == null || stackTrace.length == 0) {
            sb.append("throwable stackTrace is empty");
        } else if (stackTrace.length == 1) {
            sb.append("\t─ ").append(stackTrace[0].toString());
        } else {
            sb.append("\t「").append("异常堆栈");
            for (int i = 0, N = stackTrace.length; i < N; i++) {
                if (i != N - 1) {
                    sb.append("\t├ ").append(stackTrace[i].toString()).append(getEnding());
                } else {
                    sb.append("\t└ ").append(stackTrace[i].toString());
                }
            }
        }
        e(tag, sb.toString());
    }


    public static void v(String msg) {
        dispatch(Log.VERBOSE, findTag(), msg);
    }

    public static void v(String tag, String msg) {
        dispatch(Log.VERBOSE, tag, msg);
    }

    public static void d(String msg) {
        dispatch(Log.DEBUG, findTag(), msg);
    }

    public static void d(String tag, String msg) {
        dispatch(Log.DEBUG, tag, msg);
    }

    public static void i(String msg) {
        dispatch(Log.INFO, findTag(), msg);
    }

    public static void i(String tag, String msg) {
        dispatch(Log.INFO, tag, msg);
    }

    public static void w(String msg) {
        dispatch(Log.WARN, findTag(), msg);
    }

    public static void w(String tag, String msg) {
        dispatch(Log.WARN, tag, msg);
    }

    public static void e(String msg) {
        dispatch(Log.ERROR, findTag(), msg);
    }

    public static void e(String tag, String msg) {
        dispatch(Log.ERROR, tag, msg);
    }

    private static String findTag() {
        return TAG;
    }

    private static StringBuilder newStringBuilder() {
        return new StringBuilder(256);
    }

    private static String getEnding() {
        return "\n";
    }

    private static String space() {
        return "   ";
    }

    private static void dispatch(int level, String tag, String msg) {
        if (!DEBUG)
            return;
        if (sOnLogListener != null && sOnLogListener.beforeLog(level, tag, msg))
            return;
        switch (level) {
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
        }
    }
}
