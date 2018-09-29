package com.march.common.exts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.march.common.funcs.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 16/8/15
 * Describe : 日志打印操作
 * <p> basic
 * LogUtils.v(msg);
 * LogUtils.d(msg);
 * LogUtils.i(msg);
 * LogUtils.w(msg);
 * LogUtils.e(msg);
 * <p> advance
 * LogUtils.object(); // 按照格式打印 object，support list/map/intent...
 * LogUtils.json(); // 将 json 格式化打印
 * LogUtils.e(error); // 打印异常堆栈
 *
 * @author chendong
 */
public class LogX {

    private static String TAG = LogX.class.getSimpleName();

    private static boolean DEBUG = true;
    private static OnLogListener sOnLogListener;

    public static void setDEBUG(boolean DEBUG) {
        LogX.DEBUG = DEBUG;
    }

    public static void setTAG(String TAG) {
        LogX.TAG = TAG;
    }

    public static void setOnLogListener(OnLogListener onLogListener) {
        sOnLogListener = onLogListener;
    }

    public static void myThread() {
        dispatch(Log.ERROR, findTag(), Thread.currentThread().getName());
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
            sb.append("[").append(object.getClass().getSimpleName()).append("]@").append(object.hashCode()).append(getEnding());
            if (object instanceof Map) {
                Map map = (Map) object;
                sb.append("{").append(getEnding());
                for (Object key : map.keySet()) {
                    sb.append("\t").append(key).append("  =>  ").append(map.get(key)).append(getEnding());
                }
                sb.append("}").append(getEnding());
            } else if (object instanceof Intent) {
                Intent intent = (Intent) object;
                Bundle extras = intent.getExtras();
                sb.append("{").append(getEnding());
                sb.append("\t").append(intent.toString()).append(getEnding());
                if (extras != null) {
                    for (String key : extras.keySet()) {
                        sb.append("\t").append(key).append("  =>  ").append(extras.get(key)).append(getEnding());
                    }
                }
                sb.append("}").append(getEnding());
            } else if (object instanceof List) {
                List list = (List) object;
                sb.append("{").append(getEnding());
                for (Object item : list) {
                    sb.append("\t").append(item.toString()).append(getEnding());
                }
                sb.append("}").append(getEnding());
            } else {
                sb.append("{").append(getEnding());
                sb.append("\t").append(object.toString());
                sb.append("}").append(getEnding());

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

    // 拼接打印所有 obj
    public static void all(String tag, Object... objs) {
        StringBuilder sb = newStringBuilder();
        for (Object obj : objs) {
            sb.append(obj == null ? " [null] " : obj.toString()).append(" ");
        }
        e(tag, sb.toString());
    }

    // 拼接打印所有 obj
    public static void all(Object... objs) {
        all(findTag(), objs);
    }

    // 带格式打印异常
    public static void e(Throwable throwable) {
        e(findTag(), throwable);
    }

    public static void e(String tag, Throwable throwable) {
        StringBuilder sb = newStringBuilder();
        sb.append(throwable.toString()).append("\n");

        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace == null || stackTrace.length == 0) {
            sb.append("throwable stackTrace is empty");
        } else if (stackTrace.length == 1) {
            sb.append("\t─ ").append(stackTrace[0].toString());
        } else {
            sb.append("\t❌").append("异常堆栈").append(getEnding());
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

    private static void dispatch(int level, final String tag, final String msg) {
        if (!DEBUG) {
            return;
        }
        if (sOnLogListener != null && sOnLogListener.beforeLog(level, tag, msg)) {
            return;
        }
        Log.e(tag, "✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️");
        printTrace(tag, "点击跳转指定位置");
        breakLog4K(msg, s -> {
            switch (level) {
                case Log.VERBOSE:
                    Log.v(tag, s);
                    break;
                case Log.DEBUG:
                    Log.d(tag, s);
                    break;
                case Log.INFO:
                    Log.i(tag, s);
                    break;
                case Log.WARN:
                    Log.w(tag, s);
                    break;
                case Log.ERROR:
                    Log.e(tag, s);
                    break;
            }
        });
        Log.e(tag, "✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️✈️️");
    }

    private static void breakLog4K(String msg, Consumer<String> consumer) {
        if (msg.length() < 3800) {
            consumer.accept(msg);
            return;
        }
        int index = 0; // 当前位置
        int max = 3800;// 需要截取的最大长度,别用4000
        String sub;    // 进行截取操作的string
        while (index < msg.length()) {
            if (msg.length() < max) { // 如果长度比最大长度小
                max = msg.length();   // 最大长度设为length,全部截取完成.
                sub = msg.substring(index, max);
            } else {
                sub = msg.substring(index, max);
            }
            consumer.accept(sub);      // 进行输出
            index = max;
            max += 3800;
        }
    }

    // 3
    // getContent()
    // printTrace
    // breakLog4K
    // dispatch
    // Log.X
    public static final int OFFSET = 6;
    public static final int LIMIT  = 1;

    public interface OnLogListener {
        boolean beforeLog(int level, String tag, String msg);
    }

    public static void printTrace(String tag, String content, Object... args) {
        try {
            for (int i = OFFSET; i < OFFSET + LIMIT; i++) {
                String realContent = getContent(content, i, args);
                Log.e(tag, realContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getNameFromTrace(StackTraceElement[] traceElements, int place) {
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }

    private static String getContent(String msg, int place, Object... args) {
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), place);
            return sourceLinks + String.format(msg, args);
        } catch (Throwable throwable) {
            return msg;
        }
    }


}
