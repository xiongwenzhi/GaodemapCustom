package com.android.orangetravel.base.utils.log;//package com.yang.base.utils.log;
//
//import android.util.Log;
//
//import com.yang.base.utils.JsonUtil;
//import com.yang.base.utils.StringUtil;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//public class LogUtil {
//
//    private static final boolean DEBUG = true;// 是否打印Log
//
//    /**
//     * 正常的Log
//     */
//    public static void eNormal(String tag, String msg) {
//        if (LogUtil.DEBUG) {
//            if (StringUtil.isEmpty(tag))
//                tag = LogUtil.TAG_IS_EMPTY;
//            if (StringUtil.isEmpty(msg))
//                msg = LogUtil.MSG_IS_EMPTY;
//            Log.e(tag, msg);
//        }
//    }
//
//    /**
//     * 很长的Log
//     */
//    public static void eLong(String tag, String msg) {
//        LogUtil.eLong(tag, "", msg);
//    }
//
//    /**
//     * 很长的Log
//     *
//     * @param head 会拼接到每条Log的msg前面
//     */
//    public static void eLong(String tag, String head, String msg) {
//        msg = msg.trim();
//        int msgLength = msg.length();
//        int start = 0;
//        int end = LogUtil.MAX_LENGTH;
//        for (int i = 0; i < 100; i++) {
//            if (msgLength > end) {
//                LogUtil.eNormal(tag, head + msg.substring(start, end));
//                start = end;
//                end = end + LogUtil.MAX_LENGTH;
//            } else {
//                LogUtil.eNormal(tag, head + msg.substring(start, msgLength));
//                break;
//            }
//        }
//    }
//
//    /**
//     * 超级的Log
//     */
//    public static void eSuper(Object msg) {
//        if (LogUtil.DEBUG) {
//            StackTraceElement ste = new Exception().getStackTrace()[1];
//            // 包名.MainActivity
//            String className = ste.getClassName();
//            // MainActivity
//            className = className.substring(className.lastIndexOf(".") + 1);
//            // MainActivity.java
//            String fileName = ste.getFileName();
//            // initData
//            String methodName = ste.getMethodName();
//            // 30
//            int lineNum = ste.getLineNumber();
//            // main
//            String threadName = Thread.currentThread().getName();
//
//            Log.e(LogUtil.TAG, LogUtil.START_LINE);
//            Log.e(LogUtil.TAG, LogUtil.HEAD + "(" + fileName + ":" + lineNum + ")  " + "  Class：" + className + "  Method：" + methodName + "  Thread：" + threadName);
//            Log.e(LogUtil.TAG, LogUtil.CENTRE_LINE);
//
//            List<String> msgList = new ArrayList<>(2);
//            if (null == msg) {
//                msgList.add(LogUtil.MSG_IS_NULL);
//
//            } else if (msg instanceof String) {
//                msgList.add((String) msg);
//
//            } else if (msg instanceof String[]) {
//                String[] msgArray = (String[]) msg;
//                List<String> msgArrayList = Arrays.asList(msgArray);
//                msgList.addAll(msgArrayList);
//
//            } else if (msg instanceof List) {
//                msgList.addAll((Collection<? extends String>) msg);
//
//            } else {
//                msgList.add(JsonUtil.toString(msg));
//            }
//
//            for (int i = 0; i < msgList.size(); i++) {
//                String str = msgList.get(i);
//                if (StringUtil.isEmpty(str))
//                    str = LogUtil.MSG_IS_EMPTY;
//                Log.e(LogUtil.TAG, LogUtil.HEAD + str);
//            }
//            Log.e(LogUtil.TAG, LogUtil.END_LINE);
//        }
//    }
//
//    private static final String TAG = LogUtil.class.getSimpleName();
//    private static final String START_LINE = "┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
//    private static final String CENTRE_LINE = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
//    private static final String END_LINE = "└─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
//    private static final String HEAD = "│ ";
//    private static final String TAG_IS_EMPTY = "tagIsEmpty";
//    private static final String MSG_IS_EMPTY = "msgIsEmpty";
//    private static final String MSG_IS_NULL = "msgIsNull";
//    private static final int MAX_LENGTH = 3000;
//
//    /**
//     * ╔════════════════════════════════════════════════════════════════════════
//     * ║
//     * ║
//     * ╚════════════════════════════════════════════════════════════════════════
//     *
//     * ┌─────────────────────────────────────────────────────────────────────────────
//     * │
//     * ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
//     * │
//     * ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
//     * │
//     * └─────────────────────────────────────────────────────────────────────────────
//     */
//
//}