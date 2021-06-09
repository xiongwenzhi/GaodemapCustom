package com.android.orangetravel.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.android.orangetravel.base.base.BaseApp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SharedPreferences保存数据的工具类
 *
 * @author yangfei
 */
public final class SPUtil {

    private SPUtil() {
        // 这个类不能实例化
    }

    // 保存在手机里面的文件名
    public static final String FILE_NAME = ConstantUtil.APP_NAME;

    private static SharedPreferences getSP() {
        return BaseApp.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void put(String key, Object value) {

        if (null == value) {
            return;
        }

        SharedPreferences sp = getSP();
        SharedPreferences.Editor editor = sp.edit();

        // 判断数据类型
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param key      键
     * @param defValue 值
     * @return 取出的数据
     */
    public static Object get(String key, @NonNull Object defValue) {

        SharedPreferences sp = getSP();

        // 判断数据类型
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }

        return null;
    }

    /**
     * 移除某个key对应的值
     */
    public static void remove(String key) {
        SharedPreferences sp = getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method mApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class cls = SharedPreferences.Editor.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行,否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (null != mApplyMethod) {
                    mApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    public static void write(Context context, String fileName, String k, String v) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(k, v);
        editor.commit();
    }

    public static String readString(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preference.getString(k, null);
    }

    public static void remove(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(k);
        editor.commit();
    }

//    /**
//     * 查询某个key是否已经存在
//     */
//    public static boolean contains(Context mContext, String key) {
//        SharedPreferences sp = getSP(mContext);
//        return sp.contains(key);
//    }

//    /**
//     * 返回所有的键值对
//     */
//    public static Map<String, ?> getAll(Context mContext) {
//        SharedPreferences sp = getSP(mContext);
//        return sp.getAll();
//    }

}