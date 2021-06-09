package com.android.orangetravel.base.statusBar;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Class
 *
 * @author yangfei
 */
public class OsUtil {

    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    // private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";
    private static final String KEY_DISPLAY = "ro.build.display.id";

    /**
     * 判断是否为MIUI
     */
    public static boolean isMIUI() {
        String property = getSystemProperty(KEY_MIUI_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断MIUI版本是否大于等于6
     */
    public static boolean isMIUI6Later() {
        String version = getMIUIVersion();
        int num;
        if ((!version.isEmpty())) {
            try {
                num = Integer.valueOf(version.substring(1));
                return num >= 6;
            } catch (NumberFormatException e) {
                return false;
            }
        } else
            return false;
    }

    /**
     * 获得MIUI的版本
     */
    public static String getMIUIVersion() {
        return isMIUI() ? getSystemProperty(KEY_MIUI_VERSION_NAME, "") : "";
    }

    /**
     * 判断是否为FlymeOS
     */
    public static boolean isFlymeOS() {
        return getFlymeOSFlag().toLowerCase().contains("flyme");
    }

    /**
     * 判断FlymeOS的版本是否大于等于4
     */
    public static boolean isFlymeOS4Later() {
        String version = getFlymeOSVersion();
        int num;
        if (!version.isEmpty()) {
            try {
                if (version.toLowerCase().contains("os")) {
                    num = Integer.valueOf(version.substring(9, 10));
                } else {
                    num = Integer.valueOf(version.substring(6, 7));
                }
                return num >= 4;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 得到FlymeOS的版本
     */
    public static String getFlymeOSVersion() {
        return isFlymeOS() ? getSystemProperty(KEY_DISPLAY, "") : "";
    }

    private static String getFlymeOSFlag() {
        return getSystemProperty(KEY_DISPLAY, "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

//        public static boolean isMIUI() {
//            return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
//        }
//
//        public static boolean isFlyme() {
//            try {
//                final Method method = Build.class.getMethod("hasSmartBar");
//                return method != null;
//            } catch (final Exception e) {
//                return false;
//            }
//        }
//
//        private static String getSystemProperty(String propName) {
//            String line;
//            BufferedReader input = null;
//            try {
//                Process p = Runtime.getRuntime().exec("getprop " + propName);
//                input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
//                line = input.readLine();
//                input.close();
//            } catch (IOException ex) {
//                return null;
//            } finally {
//                try {
//                    if (null != input) {
//                        input.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return line;
//        }

}