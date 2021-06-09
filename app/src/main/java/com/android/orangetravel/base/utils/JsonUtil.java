package com.android.orangetravel.base.utils;

import com.google.gson.Gson;

/**
 * Json工具类
 *
 * @author yangfei
 */
public final class JsonUtil {

    private JsonUtil() {
        // 这个类不能实例化
    }

    /**
     * Json --> Bean
     */
    public static <T> T getObject(String jsonStr, Class<T> cls) {
        return new Gson().fromJson(jsonStr, cls);
    }

    /**
     * Bean --> Json
     */
    public static String toString(Object obj) {
        return new Gson().toJson(obj);
    }

}