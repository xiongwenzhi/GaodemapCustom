package com.android.orangetravel.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.android.orangetravel.base.utils.log.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

/**
 * File工具类
 *
 * @author yangfei
 */
public final class FileUtil {

    private FileUtil() {
        // 这个类不能实例化
    }

    /**
     * 获取外部缓存路径
     */
    public static File getExternalCacheDir(Context mContext) {
        File mFile;
        // 判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mFile = new File(mContext.getExternalCacheDir(), ConstantUtil.APP_NAME);
        } else {
            mFile = new File(mContext.getCacheDir(), ConstantUtil.APP_NAME);
        }
        // 如果不存在,就创建
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
        return mFile;
    }

    /**
     * 创建一个图片路径
     */
    public static File getPictureFile(Context mContext) {
        String timeStamp = String.valueOf(new Date().getTime());
        return new File(getExternalCacheDir(mContext), timeStamp + ".jpg");// File.separator
    }

    /**
     * 本地路径获取Bitmap
     */
    public static Bitmap getBitmap(String pathName) {
        return BitmapFactory.decodeFile(pathName);
    }

    /**
     * Bitmap保存到本地
     */
    public static void saveBitmap(File mFile, Bitmap mBitmap) {
        FileOutputStream fos;
        BufferedOutputStream bos;
        try {
            fos = new FileOutputStream(mFile);
            bos = new BufferedOutputStream(fos);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            if (null != bos) {
                bos.flush();
                bos.close();
            }
            if (null != fos) {
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bitmap保存到本地
     */
    public static String saveBitmap(Context mContext, Bitmap mBitmap) {
        File mFile = FileUtil.getPictureFile(mContext);
        FileUtil.saveBitmap(mFile, mBitmap);
        return mFile.getAbsolutePath();
    }

    /**
     * 删除该应用保存在SD卡的文件
     */
    public static void deleteFile(Context mContext) {
        File mFile = getExternalCacheDir(mContext);
        if (mFile.isDirectory()) {
            File[] mFileArray = mFile.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    LogUtil.e("dir" + dir);
                    LogUtil.e("name" + name);
                    // /storage/emulated/0/Android/data/com.yang.yangframe/cache/YangFrame
                    // 1516434374665.jpg
                    if (name.contains(".jpg") || name.contains(".png") || name.contains(".jpeg")) {
                        return true;
                    }
                    return false;
                }
            });
            if (null != mFileArray && mFileArray.length > 0) {
                for (File f : mFileArray) {
                    f.delete();
                }
            }
        }
    }

}