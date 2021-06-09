package com.android.orangetravel.base.base;

import android.content.Context;

/**
 * 加载弹出框接口
 *
 * @author yangfei
 */
public interface LoadingDialogInter {

    /**
     * 显示加载弹出框
     */
    void showLoadingDialog(Context context, String msg);

    /**
     * 显示加载弹出框
     */
    void showLoadingDialog(Context context);

    /**
     * 关闭加载弹出框
     */
    void dismissLoadingDialog();

}