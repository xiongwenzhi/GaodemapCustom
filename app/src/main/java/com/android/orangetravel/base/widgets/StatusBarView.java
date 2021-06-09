package com.android.orangetravel.base.widgets;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.orangetravel.base.utils.SystemUtil;

/**
 * 状态栏高度的View
 *
 * @author yangfei
 */
public class StatusBarView extends View {

    public StatusBarView(Context mContext) {
        super(mContext);
    }
    public StatusBarView(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
    }
    public StatusBarView(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置高度
        setHeight();
    }

    /**
     * 设置高度
     */
    private void setHeight() {
        int statusBarHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarHeight = SystemUtil.getStatusBarHeight();
        }
        ViewGroup.LayoutParams mParams = this.getLayoutParams();
        mParams.height = statusBarHeight;
        this.setLayoutParams(mParams);
    }

}