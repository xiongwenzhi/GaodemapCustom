package com.android.orangetravel.base.luban;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

import com.android.orangetravel.base.utils.log.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Bitmap.createBitmap;

/**
 * Responsible for starting compress and managing active and cached resources.
 */
class Engine {

    private ExifInterface srcExif;      // 记录图片信息,判断是否旋转
    private String srcImg;              // 原图路径
    private File tagImg;                // 压缩后保存路径
    private int srcWidth;               // 原图宽
    private int srcHeight;              // 原图高

    Engine(String srcImg, File tagImg) throws IOException {
        if (Checker.isJPG(srcImg)) {
            this.srcExif = new ExifInterface(srcImg);
        }
        this.srcImg = srcImg;
        this.tagImg = tagImg;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 开始读入图片设置成true
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;
    }

    /**
     * 计算压缩比例
     */
    private int computeSize() {
        // srcWidth = srcWidth % 2 == 1 ? srcWidth + 1 : srcWidth;
        // srcHeight = srcHeight % 2 == 1 ? srcHeight + 1 : srcHeight;
        if (srcWidth > srcHeight) {
            return getInSampleSize(srcWidth);
        } else {
            return getInSampleSize(srcHeight);
        }
        /*float maxWidth = 1024;
        float maxHeight = 1024;
        int be = 1;
        if (srcWidth >= maxHeight && srcWidth > maxWidth) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (srcWidth / maxWidth) + 1;
        } else if (srcHeight >= srcWidth && srcHeight > maxHeight) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (srcHeight / maxHeight) + 1;
        }
        if (be > 2) {
            be = be + 1;
        }
        if (be <= 0) {
            be = 1;
        }
        return be;*/
    }

    private int getInSampleSize(int length) {
        float ratio = length / 1024;
        if (ratio > 1.5f && ratio <= 3) {
            return 1 << 1;
        } else if (ratio > 3) {
            return 1 << 2;
        } else {
            return 1;
        }
    }

    /**
     * 旋转图片
     */
    private Bitmap rotatingImage(Bitmap mBitmap) {
        if (null == srcExif) {
            return mBitmap;
        }
        int angle = 0;// 旋转角度
        int orientation = srcExif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90: {
                angle = 90;
                break;
            }
            case ExifInterface.ORIENTATION_ROTATE_180: {
                angle = 180;
                break;
            }
            case ExifInterface.ORIENTATION_ROTATE_270: {
                angle = 270;
                break;
            }
        }
        Matrix mMatrix = new Matrix();
        mMatrix.postRotate(angle);
        return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mMatrix, true);
    }

    File compress() throws IOException {
        /*// 记录开始时间
        long begin = SystemClock.elapsedRealtime();*/
        // 计算好压缩比例后,重新获取Bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = computeSize();
        Bitmap tagBitmap = BitmapFactory.decodeFile(srcImg, options);
        // 如果有被旋转的话,就转回来
        tagBitmap = rotatingImage(tagBitmap);
        // 取两层绘制交集,显示上层
//        tagBitmap = getRectangleBitmap(tagBitmap);
        // 按质量压缩
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100;
        tagBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        int length = stream.toByteArray().length / 1024;
        LogUtil.e("原图大小(kb)" + length);
        if (length > 500) {
            quality = 70;
        } else if (length > 200) {
            quality = 80;
        } else if (length > 100) {
            quality = 90;
        }
        stream.reset();
        tagBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        LogUtil.e("压缩后大小(kb)" + (stream.toByteArray().length / 1024));
        /*LogUtil.e("原图几kb", (stream.toByteArray().length / 1024) + "");
        while ((stream.toByteArray().length / 1024) > 200) {// 如果大于200kb
            quality = quality - 10;
            stream.reset();
            LogUtil.e("quality", quality + "");
            tagBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        }*/
        tagBitmap.recycle();

        FileOutputStream fos = new FileOutputStream(tagImg);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.flush();
        stream.close();
        /*// 记录结束时间
        long end = SystemClock.elapsedRealtime();
        long elapsed = end - begin;
        LogUtil.e("压缩时间", elapsed + "");*/
        return tagImg;
    }

    /**
     * 取两层绘制交集,显示上层
     */
    private Bitmap getRectangleBitmap(Bitmap mBitmap) {
        Bitmap outBitmap = createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(outBitmap);
        final int color = 0xff424242;
        final Paint mPaint = new Paint();
        final Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        final RectF mRectF = new RectF(mRect);
        // final float roundPx = mBitmap.getWidth() / 2 < mBitmap.getHeight() / 2 ? mBitmap.getWidth() : mBitmap.getHeight();
        mPaint.setAntiAlias(true);
        mCanvas.drawARGB(0, 0, 0, 0);
        mPaint.setColor(color);
        mCanvas.drawRoundRect(mRectF, 0, 0, mPaint);
        // 取两层绘制交集,显示上层
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);
        mBitmap.recycle();
        return outBitmap;
    }

}