package com.android.orangetravel.base.utils;

/**
 * 防止快速点击工具类
 *
 * @author yangfei
 */
public class ClickUtil {

    // 最小点击时间间隔时长(毫秒)
    private static final int MIN_CLICK_DELAY_TIME = 800;
    // 最后点击时间
    private static long lastClickTime;

    /**
     * @return true:可以点击
     */
    /*public static boolean isCanClick() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return true;
        } else {
            lastClickTime = currentTime;
            return false;
        }
    }*/

    /**
     * @return true:不可以点击
     */
    public static boolean isNoCanClick() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            lastClickTime = currentTime;
            return true;
        }
    }

}