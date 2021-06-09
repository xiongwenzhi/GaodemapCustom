package com.android.orangetravel.base.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.orangetravel.base.utils.StringUtil;
import com.yang.base.R;

/**
 * 加载中弹出框
 *
 * @author yangfei
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context mContext, String content) {
        super(mContext, R.style.style_dialog_no_fuzzy);
        // 点击返回键
        this.setCancelable(true);
        // 点击外部
        this.setCanceledOnTouchOutside(false);
        // 设置布局
        this.setContentView(R.layout.dialog_loading);
        // 设置宽高
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置进入和退出的动画
        this.getWindow().setWindowAnimations(R.style.DialogCentreAnim);
        // 初始化控件
        initView(content);
    }

    private TextView dialog_loading_tv;

    /**
     * 初始化控件
     */
    private void initView(String content) {
        dialog_loading_tv = (TextView) findViewById(R.id.dialog_loading_tv);
        if (StringUtil.isNotEmpty(content)) {
            dialog_loading_tv.setText(content);
        }
    }

    /**
     * 设置提示文字
     */
    public void setContent(String content) {
        if (StringUtil.isNotEmpty(content)) {
            dialog_loading_tv.setText(content);
        }
    }

}