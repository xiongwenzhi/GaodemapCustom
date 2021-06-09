package com.android.orangetravel.ui.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.android.orangetravel.base.utils.DensityUtil;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 圆角图片
 *
 * @author yangfei
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;

    public GlideRoundTransform() {
        this(5f);
    }
    public GlideRoundTransform(float radiusDp) {
        this.radius = DensityUtil.dp2px(radiusDp);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i1) {
        return roundCrop(bitmapPool, bitmap);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (null == source) return null;
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (null == result) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas mCanvas = new Canvas(result);
        Paint mPaint = new Paint();
        mPaint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        mPaint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        mCanvas.drawRoundRect(rectF, radius, radius, mPaint);
        return result;
    }

}