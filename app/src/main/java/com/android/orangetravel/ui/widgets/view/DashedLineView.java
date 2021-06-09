package com.android.orangetravel.ui.widgets.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.android.orangetravel.R;

/**
 * @author Mr Xiong
 * @date 2021/3/29
 */

public class DashedLineView extends View {
    private int lineColor;
    private int lineLength;
    private int lineSpace;
    private Paint mPaint = null;
    private Path mPath = null;
    private PathEffect pe = null;
    private float[] arrayOfFloat;

    public DashedLineView(Context paramContext) {
        this(paramContext, null);
    }

    public DashedLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.dashedline);
        lineColor = typedArray.getColor(R.styleable.dashedline_lineColor, Color.BLACK);
        lineLength =(int) typedArray.getDimension(R.styleable.dashedline_lineLength,10);
        lineSpace =(int) typedArray.getDimension(R.styleable.dashedline_lineSpace,10);
        typedArray.recycle();

        this.mPaint = new Paint();
        this.mPath = new Path();
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(lineColor);
        this.mPaint.setAntiAlias( true);//消除锯齿
        this.mPaint.setStrokeWidth(4);//画笔的尺寸
        arrayOfFloat = new float[2];
        //假如arrayOfFloat = { 1, 2, 3, 4};效果即是：绘制长度1的实线,再绘制长度2的空白,再绘制长度3的实线,再绘制长度4的空白,依次重复下去
        arrayOfFloat[0] =px2sp(context,lineLength);//线长度
        arrayOfFloat[1] =px2sp(context,lineSpace);//空隙长度
        this.pe = new DashPathEffect(arrayOfFloat, 1);//参数1为偏移量
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this. mPath.moveTo(0.0F, 0.0F);//画笔移动到x=0；y=0位置，
        this. mPath.lineTo(getMeasuredWidth(), 0.0F);//从到（0,0）位置，画线到（getMeasuredWidth(),0）；
        this. mPaint.setPathEffect(this.pe);
        canvas.drawPath(this.mPath,this.mPaint);
    }

    public void setMyStyle(PathEffect p){
        this.pe = p;
        invalidate();
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}