package com.android.orangetravel.base.utils;

import com.android.orangetravel.base.utils.log.LogUtil;
import com.google.gson.Gson;

/**
 * SharedPreferences保存JavaBean的工具类
 *
 * @author yangfei
 */
public final class SPSaveClass {

    private SPSaveClass() {
        // 这个类不能实例化
    }

    /**
     * 保存类
     */
    public static <T> void saveClass(T obj) {
        if (null != obj) {
            String jsonStr = new Gson().toJson(obj);
            SPUtil.put(obj.getClass().getSimpleName(), jsonStr);
        }
    }

    /**
     * 取出类
     */
    public static <T> T getClass(Class<T> key) {
        try {
            String jsonStr = (String) SPUtil.get(key.getSimpleName(), "");
            if (null != jsonStr && jsonStr.length() > 0) {
                return JsonUtil.getObject(jsonStr, key);
            }
            return null;
        } catch (Exception e) {
            LogUtil.e("取出SP保存的类时，出现错误：" + key.getSimpleName() + "\n" + e.toString());
            return null;
        }
    }

    /**
     * 清除类
     */
    public static <T> void removeClass(Class<T> key) {
        SPUtil.remove(key.getSimpleName());
    }

}