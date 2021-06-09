package com.android.orangetravel.base.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.android.orangetravel.base.utils.SystemUtil;
import com.yang.base.R;

/**
 * Dialog基类
 *
 * @author yangfei
 */
public abstract class BaseDialog extends Dialog {

    // 获取布局资源Id
    public abstract int getLayoutResId();

    // 布局参数
    private WindowManager.LayoutParams mParams;
    // 屏幕宽高
    private int screenWidth;
    private int screenHeight;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.style_dialog_fuzzy);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        // 布局参数
        mParams = this.getWindow().getAttributes();
        // 屏幕宽度
        screenWidth = SystemUtil.getScreenWidth();
        screenHeight = SystemUtil.getScreenHeight();

        // 设置可以取消---
        setCanCancel(true);
        // 弹出框位置---
        setGravity(Gravity.CENTER);

        // 获取布局资源Id
        this.setContentView(this.getLayoutResId());

        // 设置宽度比例---
        setWidthPercent(1.0f);
        // 设置动画---
        setAnimation(R.style.DialogCentreAnim);
    }

    /**
     * 设置可以取消
     */
    public void setCanCancel(boolean cancel) {
        this.setCancelable(cancel);
        this.setCanceledOnTouchOutside(cancel);
    }

    /**
     * 弹出框位置
     */
    public void setGravity(int gravity) {
        mParams.gravity = gravity;
        this.onWindowAttributesChanged(mParams);
    }

    /**
     * 设置宽度比例
     */
    public void setWidthPercent(@FloatRange(from = 0.0, to = 1.0) float percent) {
        mParams.width = (int) (screenWidth * percent);
        this.getWindow().setAttributes(mParams);
    }

    /**
     * 设置宽高
     */
    public void setWidthHeight(@FloatRange(from = 0.0, to = 1.0) float widthPercent,
                               @FloatRange(from = 0.0, to = 1.0) float heightPercent) {

        mParams.width = (int) (screenWidth * widthPercent);
        mParams.height = (int) (screenHeight * heightPercent);
        this.getWindow().setAttributes(mParams);
    }

    /**
     * 设置动画
     */
    public void setAnimation(@StyleRes int resId) {
        this.getWindow().setWindowAnimations(resId);
    }

}