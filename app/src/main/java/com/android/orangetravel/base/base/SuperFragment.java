package com.android.orangetravel.base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.yang.base.R;

import butterknife.Unbinder;

/**
 * 超级Fragment
 *
 * @author yangfei
 */
public class SuperFragment extends Fragment {

    protected Context mContext;
    protected View rootView;
    protected Unbinder mUnbinder;
    protected String start_time;
    protected String end_time;
    // 软键盘管理
    private InputMethodManager imm;

    /**
     * 执行销毁前的操作
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 关闭加载弹出框
        dismissLoadingDialog();
        // 注解
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
        mYangTitleBar = null;
    }

    /**
     * 自定义标题栏
     */
    protected YangTitleBar mYangTitleBar;

    protected void setTitleBar(String title) {
        mYangTitleBar = (YangTitleBar) findViewById(R.id.id_title_bar);
        if (null != mYangTitleBar) {
            if (StringUtil.isNotEmpty(title))
                mYangTitleBar.setTitle(title);
            mYangTitleBar.setBackClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 提供给子类调用的方法

    /**
     * 跳转Activity
     */
    protected void gotoActivity(Class<?> cls, @Nullable Bundle mBundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (null != mBundle) {
            intent.putExtras(mBundle);
        }
        if (requestCode < 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
        getActivity().overridePendingTransition(R.anim.act_enter_right, R.anim.act_exit_left);
    }

    protected void gotoActivity(Class<?> cls, @Nullable Bundle mBundle) {
        gotoActivity(cls, mBundle, -1);
    }

    protected void gotoActivity(Class<?> cls, int requestCode) {
        gotoActivity(cls, null, requestCode);
    }

    protected void gotoActivity(Class<?> cls) {
        gotoActivity(cls, null, -1);
    }

    /**
     * 显示Toast
     */
    protected void showToast(String message) {
        if (StringUtil.isNotEmpty(message)) {
            ToastUitl.showShort(getActivity(), message);
        }
    }

    /**
     * 找控件
     */
    protected View findViewById(@IdRes int viewId) {
        if (null != rootView) {
            return rootView.findViewById(viewId);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------
    // 加载弹出框

    private LoadingDialogInter mLoadingDialogInter = new LoadingDialogInterImpl();

    // 显示加载弹出框
    public void showLoadingDialog(String msg) {
        mLoadingDialogInter.showLoadingDialog(mContext, msg);
    }

    // 显示加载弹出框
    public void showLoadingDialog() {
        mLoadingDialogInter.showLoadingDialog(mContext);
    }

    // 关闭加载弹出框
    public void dismissLoadingDialog() {
        mLoadingDialogInter.dismissLoadingDialog();
    }


    @Override
    public void onResume() {
        start_time = DateUtil.currentTimeStamp2();
        super.onResume();

    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard() {
        View localView = getActivity().getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((null != localView) && (null != this.imm)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    public void onPause() {
        end_time = DateUtil.currentTimeStamp2();
        super.onPause();

    }
}