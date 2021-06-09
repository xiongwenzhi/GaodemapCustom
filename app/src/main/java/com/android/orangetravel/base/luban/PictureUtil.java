package com.android.orangetravel.base.luban;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.android.orangetravel.base.utils.FileUtil;

import java.io.File;

/**
 * 图片处理
 * <p/>
 * Bitmap mBitmap = CompressUtil.getCompressImage("图片路径", 1024, 1024);
 * Bitmap mBitmap2 = CompressUtil.getRectangleBitmap(mBitmap);
 */
public class PictureUtil {

    /**
     * 得到压缩图像
     */
    public static Bitmap getCompressImage(String srcFilePath, int requestWidth, int requestHeight) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        // 开始读入图片，此时把mOptions.inJustDecodeBounds 设为 true
        mOptions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeFile(srcFilePath, mOptions);// 此时返回mBitmap为空

        int w = mOptions.outWidth;
        int h = mOptions.outHeight;

        float hh = requestHeight;
        float ww = requestWidth;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放

        if (w >= h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (mOptions.outWidth / ww) + 1;
        } else if (w <= h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (mOptions.outHeight / hh) + 1;
        }
        if (be > 2) {
            be = be + 1;
        }
        if (be <= 0)
            be = 1;
        mOptions.inJustDecodeBounds = false;
        mOptions.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把mOptions.inJustDecodeBounds 设回 false
        mBitmap = BitmapFactory.decodeFile(srcFilePath, mOptions);
        return mBitmap;
    }

    /**
     * 对图片进行圆角处理
     */
//    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = bitmap.getWidth() / 2 < bitmap.getHeight() / 2 ? bitmap.getWidth() : bitmap.getHeight();
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//        // paint.setXfermode(new PorterDuffXfermode(Mode.SCREEN));
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        return output;
//    }

    /**
     * 对图片进行处理
     */
    public static Bitmap getRectangleBitmap(Bitmap mBitmap) {
        Bitmap outputBtp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
        Canvas mCanvas = new Canvas(outputBtp);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        final RectF rectF = new RectF(rect);
        // final float roundPx = bitmap.getWidth() / 2 < bitmap.getHeight() / 2 ? bitmap.getWidth() : bitmap.getHeight();
        paint.setAntiAlias(true);
        mCanvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        mCanvas.drawRoundRect(rectF, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// Mode.SCREEN
        mCanvas.drawBitmap(mBitmap, rect, rect, paint);
        return outputBtp;
    }

//        16条Porter-Duff规则
//
//        1.PorterDuff.Mode.CLEAR
//        所绘制不会提交到画布上。
//        2.PorterDuff.Mode.SRC
//        显示上层绘制图片
//        3.PorterDuff.Mode.DST
//        显示下层绘制图片
//        4.PorterDuff.Mode.SRC_OVER
//        正常绘制显示，上下层绘制叠盖。
//        5.PorterDuff.Mode.DST_OVER
//        上下层都显示。下层居上显示。
//        6. PorterDuff.Mode.SRC_IN
//        取两层绘制交集。显示上层。
//        7.PorterDuff.Mode.DST_IN
//        取两层绘制交集。显示下层。
//        8.PorterDuff.Mode.SRC_OUT
//        取上层绘制非交集部分。
//        9.PorterDuff.Mode.DST_OUT
//        取下层绘制非交集部分。
//        10.PorterDuff.Mode.SRC_ATOP
//        取下层非交集部分与上层交集部分
//        11.PorterDuff.Mode.DST_ATOP
//        取上层非交集部分与下层交集部分
//        12.PorterDuff.Mode.XOR
//        13.PorterDuff.Mode.DARKEN
//        14.PorterDuff.Mode.LIGHTEN
//        15.PorterDuff.Mode.MULTIPLY
//        16.PorterDuff.Mode.SCREEN

    /**
     * 创建全屏图像
     */
//    public static Bitmap createFullScreenImage(Bitmap mBitmap) {
//        int w = mBitmap.getWidth();
//        int h = mBitmap.getHeight();
//        float sx = (float) BaseApp.screenWidth / w;
//        float sy = (float) BaseApp.screenHeight / h;
//        float scale = sx < sy ? sx : sy;
//        Matrix mMatrix = new Matrix();
//        mMatrix.postScale(scale, scale);// 长和宽放大缩小的比例
//        Bitmap resultsBit = Bitmap.createBitmap(mBitmap, 0, 0, w, h, mMatrix, true);
//        return resultsBit;
//    }

    /**
     * 压缩图像
     */
//    public static Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        if (baos.toByteArray().length / 1024 > 1024) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.PNG, 100, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//            return bitmap;
//        } else {
//            return image;
//        }
//    }

//    /**
//     * 获取图片的旋转角度
//     *
//     * @param path 图片绝对路径
//     * @return 图片的旋转角度
//     */
//    public static int getBitmapDegree(String path) {
//        int degree = 0;
//        try {
//            // 从指定路径下读取图片，并获取其EXIF信息
//            ExifInterface exifInterface = new ExifInterface(path);
//            // 获取图片的旋转信息
//            int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            switch (attributeInt) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    degree = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    degree = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    degree = 270;
//                    break;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return degree;
//    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param pathString 需要旋转的图片的路径
     * @param degree     旋转角度
     * @return 旋转后的图片
     */
    public static void rotateBitmapByDegree(String pathString, int degree) {

        // 本地图片路径获取Bitmap
        Bitmap bm = FileUtil.getBitmap(pathString);
        if (bm == null)
            return;

        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        FileUtil.saveBitmap(new File(pathString), returnBm);
    }

}
