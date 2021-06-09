package com.android.orangetravel.base.widgets;//package com.yang.base.widgets;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yang.base.R;
//import com.yang.base.utils.StringUtil;
//
///**
// * 加载数据弹窗框
// *
// * @author yangfei
// */
//public class LoadingDialog {
//
//    private static Dialog mDialog;
//
//    /**
//     * 显示加载弹窗框
//     */
//    public static void showLoadingDialog(Activity mActivity) {
//        showLoadingDialog(mActivity, null, true);
//    }
//    public static void showLoadingDialog(Activity mActivity, String msg) {
//        showLoadingDialog(mActivity, msg, true);
//    }
//    public static void showLoadingDialog(Activity mActivity, String msg, boolean cancelable) {
//        if (null != mDialog && mDialog.isShowing()) {
//            return;
//        }
//        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_loading, null);
//        TextView dialog_loading_tv = (TextView) view.findViewById(R.id.dialog_loading_tv);
//        if (StringUtil.isEmpty(msg)) {
//            dialog_loading_tv.setText(mActivity.getResources().getString(R.string.loading));
//        } else {
//            dialog_loading_tv.setText(msg);
//        }
//        mDialog = new Dialog(mActivity, R.style.style_dialog_fuzzy);
//        mDialog.setCancelable(cancelable);
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//
//        Window mWindow = mDialog.getWindow();
//        mWindow.setWindowAnimations(R.style.DialogCentreAnim);
//
//        mDialog.show();
//    }
//
//    /**
//     * 关闭加载弹窗框
//     */
//    public static void dismissLoadingDialog() {
//        if (null != mDialog && mDialog.isShowing()) {
//            mDialog.dismiss();
//        }
//    }
//
//}