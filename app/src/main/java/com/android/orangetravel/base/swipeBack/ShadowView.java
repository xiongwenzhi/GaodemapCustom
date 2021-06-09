package com.android.orangetravel.base.swipeBack;//package com.yang.base.swipeBack;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.GradientDrawable;
//import android.view.View;
//
///**
// * 阴影控件
// */
//class ShadowView extends View {
//
//    public ShadowView(Context mContext) {
//        super(mContext);
//    }
//
//    private Drawable mDrawable;
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (null == mDrawable) {// 0x00000000 0x17000000 0x43000000
//            final int colors[] = {0x00000000, 0x55000000, 0x88000000};// 分别为开始颜色,中间夜色,结束颜色
//            mDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
//        }
//        mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
//        mDrawable.draw(canvas);
//    }
//
//}