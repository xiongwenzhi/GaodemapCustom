package com.android.orangetravel.base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 * @author yangfei
 */
public final class ToastUitl {

    private ToastUitl() {
        // 这个类不能实例化
    }

    private static Toast mToast;

    private static void initToast(Context mContext, CharSequence message, int duration) {
        if (null == mToast) {
            mToast = Toast.makeText(mContext, message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context mContext, CharSequence message) {
        initToast(mContext, message, Toast.LENGTH_SHORT);
    }
    public static void showShort(Context mContext, int strResId) {
        initToast(mContext, mContext.getResources().getText(strResId), Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context mContext, CharSequence message) {
        initToast(mContext, message, Toast.LENGTH_LONG);
    }
    public static void showLong(Context mContext, int strResId) {
        initToast(mContext, mContext.getResources().getText(strResId), Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context mContext, CharSequence message, int duration) {
        initToast(mContext, message, duration);
    }
    public static void show(Context context, int strResId, int duration) {
        initToast(context, context.getResources().getText(strResId), duration);
    }

}