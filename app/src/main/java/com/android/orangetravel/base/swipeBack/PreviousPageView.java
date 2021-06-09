package com.android.orangetravel.base.swipeBack;//package com.yang.base.swipeBack;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.view.View;
//
///**
// * 前一页控件
// */
//class PreviousPageView extends View {
//
//    private View mView;
//
//    public PreviousPageView(Context mContext) {
//        super(mContext);
//    }
//
//    public void cacheView(View mView) {
//        this.mView = mView;
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (null != mView) {
//            mView.draw(canvas);
//            mView = null;
//        }
//    }
//
//}