package com.android.orangetravel.base.utils.log;

import android.util.Log;

import androidx.annotation.IntDef;

import com.android.orangetravel.base.utils.JsonUtil;
import com.android.orangetravel.base.utils.StringUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * 打印Log
 *
 * @author yangfei
 */
public class LogUtil {

    // 是否打印Log
    public static final boolean DEBUG = true;

    public static void e(String tag, String msg) {
        if (LogUtil.DEBUG) {
            if (StringUtil.isEmpty(tag))
                tag = "tag is null";
            if (StringUtil.isEmpty(msg))
                msg = "msg is null";
            Log.e(tag, msg);
        }
    }

    /*public static void i(Object msg) {
        log(LogUtil.I, false, msg);
    }
    public static void w(Object msg) {
        log(LogUtil.W, false, msg);
    }*/

    public static void e(Object msg) {
        log(LogUtil.E, false, msg);
    }

    /*public static void iSuper(Object msg) {
        log(LogUtil.I, true, msg);
    }
    public static void wSuper(Object msg) {
        log(LogUtil.W, true, msg);
    }*/

    public static void eSuper(Object msg) {
        log(LogUtil.E, true, msg);
    }

    private static final int I = 1;
    private static final int W = 2;
    private static final int E = 3;

    @IntDef({LogUtil.I, LogUtil.W, LogUtil.E})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private static final String SOLID_LINE = "────────────────────────────────────────────────────────────";
    private static final String DOTTED_LINE = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_LINE = "┌" + LogUtil.SOLID_LINE + LogUtil.SOLID_LINE;
    private static final String MIDDLE_LINE = "├" + LogUtil.DOTTED_LINE + LogUtil.DOTTED_LINE;
    private static final String BOTTOM_LINE = "└" + LogUtil.SOLID_LINE + LogUtil.SOLID_LINE;// + "\n\u0020";
    private static final String HEAD_LINE = "│ ";
    private static final int MAX_LENGTH = 3000;

    private static void log(@TYPE int type, boolean isSuper, Object msg) {

        if (!LogUtil.DEBUG) {
            return;
        }

        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement ste = stackTrace[2];
        // 文件名
        String fileNameLong = ste.getFileName();
        String fileName = fileNameLong.substring(0, fileNameLong.lastIndexOf("."));
        // 行数
        int lineNumber = ste.getLineNumber();
        // 类名(包含包名)
        // String classNameLong = ste.getClassName();
        // 方法名
        // String methodName = ste.getMethodName();
        // 线程名
        String threadName = Thread.currentThread().getName();

        print(type, fileName, LogUtil.TOP_LINE);

        if (isSuper) {

            print(type, fileName, LogUtil.HEAD_LINE +
                    "Thread:" + threadName + "\u3000(" + fileNameLong + ":" + lineNumber + ")"
            );

            print(type, fileName, LogUtil.MIDDLE_LINE);
        }

        String msgStr = objToString(msg);
        msgStr = msgStr.trim();

        int msgLength = msgStr.length();
        int start = 0;
        int end = LogUtil.MAX_LENGTH;
        for (int i = 0; i < 100; i++) {
            if (msgLength > end) {
                print(type, fileName, LogUtil.HEAD_LINE + msgStr.substring(start, end));
                start = end;
                end = end + LogUtil.MAX_LENGTH;
            } else {
                print(type, fileName, LogUtil.HEAD_LINE + msgStr.substring(start, msgLength));
                break;
            }
        }

        print(type, fileName, LogUtil.BOTTOM_LINE);
    }

    private static void print(@TYPE int type, String tag, String msg) {
        switch (type) {
            case LogUtil.I: {
                Log.i(tag, msg);
                break;
            }
            case LogUtil.W: {
                Log.w(tag, msg);
                break;
            }
            case LogUtil.E: {
                Log.e(tag, msg);
                break;
            }
        }
    }

    private static String objToString(Object msg) {
        if (null == msg) {
            return "msg is null";
        }
        if (msg instanceof String) {
            return (String) msg;
        }
        if (msg.getClass().isArray()) {
            if (msg instanceof boolean[]) {
                return Arrays.toString((boolean[]) msg);
            }
            if (msg instanceof byte[]) {
                return Arrays.toString((byte[]) msg);
            }
            if (msg instanceof char[]) {
                return Arrays.toString((char[]) msg);
            }
            if (msg instanceof short[]) {
                return Arrays.toString((short[]) msg);
            }
            if (msg instanceof int[]) {
                return Arrays.toString((int[]) msg);
            }
            if (msg instanceof long[]) {
                return Arrays.toString((long[]) msg);
            }
            if (msg instanceof float[]) {
                return Arrays.toString((float[]) msg);
            }
            if (msg instanceof double[]) {
                return Arrays.toString((double[]) msg);
            }
            if (msg instanceof Object[]) {
                return Arrays.deepToString((Object[]) msg);
            }
        }
        return JsonUtil.toString(msg);
    }

}