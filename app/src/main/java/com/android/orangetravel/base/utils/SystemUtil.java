package com.android.orangetravel.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 获取系统信息
 *
 * @author yangfei
 */
public final class SystemUtil {

    private SystemUtil() {
        // 这个类不能实例化
    }

    /**
     * 屏幕宽度
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int statusBarHeight;
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            statusBarHeight = Resources.getSystem().getDimensionPixelSize(resId);
        } else {
            statusBarHeight = DensityUtil.dp2px(24);
        }
        return statusBarHeight;
    }

    /**
     * 获取文件Uri
     */
    public static Uri getFileUri(Context mContext, Object obj) {
        // File
        File mFile = null;
        if (obj instanceof String) {
            mFile = new File(obj.toString());
        } else if (obj instanceof File) {
            mFile = (File) obj;
        }
        // Uri
        Uri uri = null;
        if (null != mFile) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(mContext, "com.jiarui.btw.fileprovider", mFile);
            } else {
                uri = Uri.fromFile(mFile);
            }
        }
        return uri;
    }

}