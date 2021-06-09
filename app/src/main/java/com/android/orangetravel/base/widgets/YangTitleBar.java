package com.android.orangetravel.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.utils.DensityUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.widgets.StatusBarView;

/**
 * 标题栏
 *
 * @author yangfei
 */
public class YangTitleBar extends FrameLayout {

    public YangTitleBar(@NonNull Context mContext) {
        super(mContext);
    }

    public YangTitleBar(@NonNull Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        // 初始化布局
        initView(mContext, attrs);
    }

    private TextView mTvTitle;
    private StatusBarView mStatusBarView;
    private ImageView mImageBack;
    private ImageView mid_image_x;
    private TextView mTvRight;
    private ImageView mImageRight;
    private ImageView mid_image_right_icon_one; //右边两个图标 第一个位置
    // 标题的设置
    private String title;
    private int titleColor;
    private int titleSize;
    private boolean titleVisible;
    // 状态栏颜色
    private int statusBarColor;
    // 返回键
    private int leftIcon;
    private boolean leftIconVisible;
    // 右边文字
    private String rightText;
    private int rightTextColor;
    private int rightTextSize;
    private boolean rightTextVisible;
    // 右边的图标
    private int rightIcon;
    private boolean rightIconVisible;

    private View line_title;

    /*左边小红点*/
    private ImageView left_red_yuan;
    /*右边小红点*/
    private ImageView right_red_yuan;

    /**
     * 初始化布局
     */
    private void initView(final Context mContext, AttributeSet attrs) {
        /*添加布局文件*/
        LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);

        /*找到控件*/
        mTvTitle = (TextView) findViewById(R.id.id_tv_title);
        mStatusBarView = (StatusBarView) findViewById(R.id.id_status_bar_view);
        mImageBack = (ImageView) findViewById(R.id.id_image_back);
        mid_image_x = (ImageView) findViewById(R.id.id_image_x);
        mTvRight = (TextView) findViewById(R.id.id_tv_right_text);
        mImageRight = (ImageView) findViewById(R.id.id_image_right_icon);
        mid_image_right_icon_one = findViewById(R.id.id_image_right_icon_one);
        line_title = findViewById(R.id.line_title);
        left_red_yuan = findViewById(R.id.left_red_yuan);
        right_red_yuan = findViewById(R.id.right_red_yuan);


        /*获得属性值*/
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.YangTitleBar);
        try {
            // 标题的设置
            title = a.getString(R.styleable.YangTitleBar_title);
            titleColor = a.getColor(R.styleable.YangTitleBar_titleColor, ContextCompat.getColor(getContext(), R.color.light_black));
            titleSize = a.getDimensionPixelSize(R.styleable.YangTitleBar_titleSize, DensityUtil.sp2px(17));
            titleVisible = a.getBoolean(R.styleable.YangTitleBar_titleVisible, true);
            // 状态栏颜色
            statusBarColor = a.getColor(R.styleable.YangTitleBar_statusBarColor, ContextCompat.getColor(getContext(), R.color.trans));
            // 返回键
            leftIcon = a.getResourceId(R.styleable.YangTitleBar_leftIcon, R.mipmap.back_black);
            leftIconVisible = a.getBoolean(R.styleable.YangTitleBar_leftIconVisible, true);
            // 右边文字
            rightText = a.getString(R.styleable.YangTitleBar_rightText);
            rightTextColor = a.getColor(R.styleable.YangTitleBar_rightTextColor, ContextCompat.getColor(getContext(), R.color.light_black));
            rightTextSize = a.getDimensionPixelSize(R.styleable.YangTitleBar_rightTextSize, DensityUtil.sp2px(16));
            rightTextVisible = a.getBoolean(R.styleable.YangTitleBar_rightTextVisible, false);
            // 右边的图标
            rightIcon = a.getResourceId(R.styleable.YangTitleBar_rightIcon, R.mipmap.back_black);
            rightIconVisible = a.getBoolean(R.styleable.YangTitleBar_rightIconVisible, false);
        } finally {
            /*回收资源*/
            a.recycle();
        }

        // 标题的设置
        setTitle(title);
        setTitleColor(titleColor);
        setTitleSize(titleSize);
        setTitleVisible(titleVisible);
        // 状态栏颜色
        setStatusBarColor(statusBarColor);
        // 返回键
        setLeftIcon(leftIcon);
        setLeftIconVisible(leftIconVisible);
        // 右边文字
        setRightText(rightText);
        setRightTextColor(rightTextColor);
        setRightTextSize(rightTextSize);
        setRightTextVisible(rightTextVisible);
        // 右边的图标
        setRightIcon(rightIcon);
        setRightIconVisible(rightIconVisible);
    }

    /*设置图标红点是否显示*/
    public void setLeftRedYuan(int visible) {
        left_red_yuan.setVisibility(visible);
    }
    /*设置右边图标红点是否显示*/
    public void setRightRedYuan(int visible) {
        right_red_yuan.setVisibility(visible);
    }

    // ---------------------------------------------------------------------------------------------
    // 标题的设置

    public void setTitle(String title) {
        if (StringUtil.isNotEmpty(title)) {
            this.title = title;
            mTvTitle.setText(title);
        }
    }

    public void setTitleColor(@ColorInt int titleColor) {
        if (titleColor != 0) {
            this.titleColor = titleColor;
            mTvTitle.setTextColor(titleColor);
        }
    }

    public void setTitleBottomLineGone() {
        line_title.setVisibility(GONE);
    }

    public void setTitleSize(int titleSize) {
        if (titleSize != 0) {
            this.titleSize = titleSize;
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        }
    }

    public void setTitleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
        if (titleVisible) {
            mTvTitle.setVisibility(View.VISIBLE);
        } else {
            mTvTitle.setVisibility(View.GONE);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 状态栏颜色

    public void setStatusBarColor(@ColorInt int statusBarColor) {
        if (statusBarColor != 0) {
            this.statusBarColor = statusBarColor;
            mStatusBarView.setBackgroundColor(statusBarColor);
        }
    }

    public void setVisivleStatuBar() {
        mStatusBarView.setVisibility(GONE);
    }

    // ---------------------------------------------------------------------------------------------
    // 返回键

    public void setLeftIcon(@DrawableRes int leftIcon) {
        if (leftIcon != 0) {
            this.leftIcon = leftIcon;
            mImageBack.setImageResource(leftIcon);
        }
    }

    public void setLeftIconVisible(boolean leftIconVisible) {
        this.leftIconVisible = leftIconVisible;
        if (leftIconVisible) {
            mImageBack.setVisibility(View.VISIBLE);
        } else {
            mImageBack.setVisibility(View.GONE);
        }
    }

    //设置右边两个图标的时候  第一个图标
    public void setRightImageOne(@DrawableRes int RightIcon) {
        if (leftIcon != 0) {
            mid_image_right_icon_one.setVisibility(VISIBLE);
            mid_image_right_icon_one.setImageResource(RightIcon);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 右边文字

    public void setRightText(String rightText) {
        if (StringUtil.isNotEmpty(rightText)) {
            this.rightText = rightText;
            mTvRight.setText(rightText);
        }
    }

    public void setRightTextColor(@ColorInt int rightTextColor) {
        if (rightTextColor != 0) {
            this.rightTextColor = rightTextColor;
            mTvRight.setTextColor(rightTextColor);
        }
    }

    public void setRightTextSize(int rightTextSize) {
        if (rightTextSize != 0) {
            this.rightTextSize = rightTextSize;
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        }
    }

    public void setWebviewDelteVisible(boolean isvisible) {
        if (isvisible) {
            mid_image_x.setVisibility(VISIBLE);
        }
    }

    public void setDeleteImageClick(OnClickListener onClickListener) {
        mid_image_x.setOnClickListener(onClickListener);
    }

    public void setRightTextVisible(boolean rightTextVisible) {
        this.rightTextVisible = rightTextVisible;
        if (rightTextVisible) {
            mTvRight.setVisibility(View.VISIBLE);
        } else {
            mTvRight.setVisibility(View.GONE);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 右边的图标

    public void setRightIcon(@DrawableRes int rightIcon) {
        if (rightIcon != 0) {
            this.rightIcon = rightIcon;
            mImageRight.setImageResource(rightIcon);
        }
    }

    public void setRightIconVisible(boolean rightIconVisible) {
        this.rightIconVisible = rightIconVisible;
        if (rightIconVisible) {
            mImageRight.setVisibility(View.VISIBLE);
        } else {
            mImageRight.setVisibility(View.GONE);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 设置点击事件

    /*设置标题的点击事件*/
    public void setTitleClickListener(OnClickListener listener) {
        mTvTitle.setOnClickListener(listener);
    }

    /*设置状态栏的点击事件*/
    public void setStatusBarClickListener(OnClickListener listener) {
        mStatusBarView.setOnClickListener(listener);
    }

    /*设置返回键的点击事件*/
    public void setBackClickListener(OnClickListener listener) {
        mImageBack.setOnClickListener(listener);
    }

    /*设置右边文字的点击事件*/
    public void setRightTvClickListener(OnClickListener listener) {
        mTvRight.setOnClickListener(listener);
    }


    /*设置右边图标的点击事件*/
    public void setRightIconClickListener(OnClickListener listener) {
        mImageRight.setOnClickListener(listener);
    }

    /*设置右边第一个图标的点击事件*/
    public void setRightIconOneClickListener(OnClickListener listener) {
        mid_image_right_icon_one.setOnClickListener(listener);
    }
}
//<com.yang.yangframe.views.YangtitleBar
//      android:layout_width="match_parent"
//      android:layout_height="wrap_content"
//
//      左边的图标(返回键)
//      app:leftIcon="@mipmap/logo"
//      app:leftIconVisible="true"
//
//      右边的图标
//      app:rightIcon="@mipmap/photopicker_delete"
//      app:rightIconVisible="true"
//
//      右边的文字
//      app:rightText="确定"
//      app:rightTextColor="@color/red"
//      app:rightTextSize="@dimen/font_size_20sp"
//      app:rightTextVisible="true"
//
//      状态栏的颜色
//      app:statusBarColor="@color/theme_color"
//
//      标题文字
//      app:title="杨飞的框架/杨飞的框架"
//      app:titleColor="@color/black"
//      app:titleSize="@dimen/font_size_12sp"
//      app:titleVisible="true" />