package com.android.orangetravel.base.widgets.dialog;//package com.yang.base.widgets.dialog;
//
//import android.app.Activity;
//import android.graphics.drawable.ColorDrawable;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupWindow;
//
//import com.yang.base.R;
//
///**
// * PopupWindow基类
// *
// * @author yangfei
// */
//public class BasePopupWindow2 {
//
//    private Activity mActivity;
//    private PopupWindow mPopupWindow;
//    private View contentView;
//
//    public BasePopupWindow2(Activity mActivity, int layoutId) {
//        this.mActivity = mActivity;
//        contentView = mActivity.getLayoutInflater().inflate(layoutId, null);
//        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopupWindow.setFocusable(true);
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setAnimationStyle(R.style.DialogCentreAnim);
//    }
//
//    /**
//     * 设置宽高
//     */
//    public BasePopupWindow2 setWidthAndHeight(int width, int height) {
//        mPopupWindow.setWidth(width);
//        mPopupWindow.setHeight(height);
//        return this;
//    }
//
//    public void showAsDropDown(View view) {
//        mPopupWindow.showAsDropDown(view);
//    }
//
//}