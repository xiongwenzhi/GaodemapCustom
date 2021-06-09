package com.android.orangetravel.base.utils.log;//package com.yang.base.utils;
//
//import android.util.Log;
//
///**
// * 打印Log工具类
// *
// * @author yangfei
// */
//public final class LogUtil {
//
//    private LogUtil() {
//        // 这个类不能实例化
//    }
//
//    public static final boolean DEBUG = true;// 是否打印Log
//
//    public static void e(String tag, String msg) {
//        if (DEBUG) {
//            if (StringUtil.isEmpty(tag)) {
//                tag = "tag is null";
//            }
//            if (StringUtil.isEmpty(msg)) {
//                msg = "msg is null";
//            }
//            Log.e(tag, msg);
//        }
//    }
//
//    public static void e(String msg) {
//        if (DEBUG) {
//            StackTraceElement ste = new Exception().getStackTrace()[1];
//            String className = ste.getClassName();// com.yang.base.activity.MainActivity
//            className = className.substring(className.lastIndexOf(".") + 1);// MainActivity
//            String fileName = ste.getFileName();// MainActivity.java
//            String methodName = ste.getMethodName();// initData
//            int lineNum = ste.getLineNumber();// 30
//
//            if (StringUtil.isEmpty(msg)) {
//                msg = "msg is null";
//            }
//            Log.e(className, "┌─────────────────────────────────────────────────────────────────────────────");
//            Log.e(className, "│ Thread:" + Thread.currentThread().getName() + " - " + "Method:" + methodName + " (" + fileName + ":" + lineNum + ")");
//            // Log.e(className, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
//            Log.e(className, "│ -> " + msg);
//            Log.e(className, "└─────────────────────────────────────────────────────────────────────────────");
//        }
//    }
//
//    /**Log.e("LogUtil", "╔════════════════════════════════════════════════════════════════════════");
//     * Log.e("LogUtil", "║ " + className + ".java -> " + methodName + "() -> ThreadName:" + Thread.currentThread().getName());
//     * Log.e("LogUtil", "║ msg -> " + msg);
//     * Log.e("LogUtil", "╚════════════════════════════════════════════════════════════════════════");
//     *
//     * PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
//     * PRETTY_LOGGER: │ Thread: main
//     * PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
//     * PRETTY_LOGGER: │ Fragment.performResume  (Fragment.java:2238)
//     * PRETTY_LOGGER: │    ParkingFragment.onResume  (ParkingFragment.java:269)
//     * PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
//     * PRETTY_LOGGER: │ hello
//     * PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
//     */
//
//    /*public static void e(String msg) {
//        if (DEBUG) {
//            StackTraceElement ste = new Exception().getStackTrace()[1];
//            String className = ste.getClassName().substring(ste.getClassName().lastIndexOf('.') + 1) + ":";
//            String methodName = ste.getMethodName() + " -> ";
//            if (StringUtil.isEmpty(msg)) {
//                msg = "msg is null";
//            }
//            Log.e("════", "╔════════════════════════════════════════════════════════════════════════");
//            Log.e("════", "║ " + className + methodName + msg);
//            Log.e("════", "╚════════════════════════════════════════════════════════════════════════");
//        }
//    }*/
//
//}