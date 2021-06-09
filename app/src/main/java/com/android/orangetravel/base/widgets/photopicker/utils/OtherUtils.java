package com.android.orangetravel.base.widgets.photopicker.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.android.orangetravel.base.utils.FileUtil;

import java.io.File;
import java.util.Date;

/**
 * 工具类
 */
public class OtherUtils {

    /**
     * 判断外部存储卡是否可用
     */
    public static boolean isExternalStorageAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static final int getHeightInPx(Context context) {
        final int height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static final int getWidthInPx(Context context) {
        final int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static final int getHeightInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int heightInDp = px2dip(context, height);
        return heightInDp;
    }

    public static final int getWidthInDp(Context context) {
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        int widthInDp = px2dip(context, width);
        return widthInDp;
    }

    /**
     * dp --> px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据string.xml资源格式化字符串
     */
    public static String formatResourceString(Context context, int resource, Object... args) {
        String str = context.getResources().getString(resource);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return String.format(str, args);
    }

    /**
     * 获取拍照相片存储文件
     */
    public static File createFile(Context mContext) {
        File mFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            /*String timeStamp = String.valueOf(new Date().getTime());
            mFile = new File(Environment.getExternalStorageDirectory() + File.separator + timeStamp + ".jpg");*/
            mFile = FileUtil.getPictureFile(mContext);
        } else {
            File cacheDir = mContext.getCacheDir();
            String timeStamp = String.valueOf(new Date().getTime());
            mFile = new File(cacheDir, timeStamp + ".jpg");
        }
        return mFile;
    }

    /**
     * 获取磁盘缓存文件
     */
    public static File getDiskCacheDir(Context mContext, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

}