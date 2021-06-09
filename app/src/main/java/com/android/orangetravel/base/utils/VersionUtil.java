package com.android.orangetravel.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 获取APP版本名和版本号的工具类
 *
 * @author yangfei
 */
public final class VersionUtil {

    private VersionUtil() {
        // 这个类不能实例化
    }

    /**
     * 获取App版本名
     */
    public static String getVersionName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /**
     * 获取App版本号
     */
    public static int getVersionCode(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

}