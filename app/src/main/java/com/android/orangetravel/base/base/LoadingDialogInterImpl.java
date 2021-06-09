package com.android.orangetravel.base.base;

import android.content.Context;

import com.android.orangetravel.base.widgets.dialog.LoadingDialog;
import com.yang.base.R;

/**
 * 加载弹出框接口的实现
 *
 * @author yangfei
 */
public class LoadingDialogInterImpl implements LoadingDialogInter {

    private LoadingDialog mLoadingDialog;

    @Override
    public void showLoadingDialog(Context context, String msg) {
        if (null != context) {
            if (null == mLoadingDialog)
                mLoadingDialog = new LoadingDialog(context, context.getString(R.string.loading));
            mLoadingDialog.setContent(msg);
            if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }

    @Override
    public void showLoadingDialog(Context context) {
        if (null != context) {
            if (null == mLoadingDialog)
                mLoadingDialog = new LoadingDialog(context, context.getString(R.string.loading));
            if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

}