package com.android.orangetravel.base.widgets.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.orangetravel.base.utils.StringUtil;
import com.yang.base.R;

/**
 * 提示弹窗框
 *
 * @author yangfei
 */
public class PromptDialog extends BaseDialog {

    public PromptDialog(Context mContext, String content) {
        super(mContext);
        // 设置宽度比例
        setWidthPercent(0.8f);
        // 初始化控件
        initView(content);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_prompt;
    }

    private TextView dialog_prompt_title;
    private TextView dialog_prompt_content;
    private TextView dialog_prompt_cancel;
    private View dialog_prompt_line;
    private TextView dialog_prompt_confirm;

    /**
     * 初始化控件
     */
    private void initView(String content) {
        dialog_prompt_title = (TextView) findViewById(R.id.dialog_prompt_title);
        dialog_prompt_content = (TextView) findViewById(R.id.dialog_prompt_content);
        dialog_prompt_cancel = (TextView) findViewById(R.id.dialog_prompt_cancel);
        dialog_prompt_line = findViewById(R.id.dialog_prompt_line);
        dialog_prompt_confirm = (TextView) findViewById(R.id.dialog_prompt_confirm);
        // 设置提示文字
        if (StringUtil.isNotEmpty(content)) {
            dialog_prompt_content.setText(content);
        }
        // 取消
        dialog_prompt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickCancelListener) {
                    onClickCancelListener.onClick();
                }
                dismiss();
            }
        });
        // 确定
        dialog_prompt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickConfirmListener) {
                    onClickConfirmListener.onClick();
                }
                dismiss();
            }
        });
    }

    /**
     * 隐藏取消按钮
     */
    public void hiddenCancel() {
        dialog_prompt_cancel.setVisibility(View.GONE);
        dialog_prompt_line.setVisibility(View.GONE);
    }

    /**
     * 设置按钮文字
     */
    public void setBtnText(String cancelText, String confirmText) {
        if (StringUtil.isNotEmpty(cancelText)) {
            dialog_prompt_cancel.setText(cancelText);
        }
        if (StringUtil.isNotEmpty(confirmText)) {
            dialog_prompt_confirm.setText(confirmText);
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(String titleText) {
        if (StringUtil.isNotEmpty(titleText)) {
            dialog_prompt_title.setVisibility(View.VISIBLE);
            dialog_prompt_title.setText(titleText);
        }
    }

    /**
     * 设置提示文字
     */
    public void setContent(String content) {
        if (StringUtil.isNotEmpty(content)) {
            dialog_prompt_content.setText(content);
        }
    }

    /**
     * 接口回调
     */
    public interface OnClickCancelListener {
        void onClick();
    }

    private OnClickCancelListener onClickCancelListener;

    public void setOnClickCancelListener(OnClickCancelListener onClickCancelListener) {
        this.onClickCancelListener = onClickCancelListener;
    }

    public interface OnClickConfirmListener {
        void onClick();
    }

    private OnClickConfirmListener onClickConfirmListener;

    public void setOnClickConfirmListener(OnClickConfirmListener onClickConfirmListener) {
        this.onClickConfirmListener = onClickConfirmListener;
    }

}
//    // 提示弹窗框
//    PromptDialog mPromptDialog = new PromptDialog(mContext, "您是否已经确定选择张三的报价，选择后将不能再修改？");
//    mPromptDialog.setTitle("温馨提示");
//    mPromptDialog.setBtnText("再看看", "确定");
//    mPromptDialog.setOnClickCancelListener(new PromptDialog.OnClickCancelListener() {
//    @Override
//    public void onClick() {
//            showShortToast("取消");
//        }
//    });
//    mPromptDialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
//    @Override
//    public void onClick() {
//            showShortToast("确定");
//        }
//    });
//    mPromptDialog.show();