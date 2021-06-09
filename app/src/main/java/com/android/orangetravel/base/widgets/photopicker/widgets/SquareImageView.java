package com.android.orangetravel.base.widgets.photopicker.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.orangetravel.base.widgets.photopicker.utils.OtherUtils;

/**
 * SquareImageView
 * <p/>
 * 正方形的ImageView
 */
public class SquareImageView extends ImageView {

    private Context mContext;
    private int mWidth;

    public SquareImageView(Context mContext) {
        this(mContext, null);
    }
    public SquareImageView(Context mContext, AttributeSet attrs) {
        this(mContext, attrs, 0);
    }
    public SquareImageView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        this.mContext = mContext;
        int screenWidth = OtherUtils.getWidthInPx(mContext);
        mWidth = (screenWidth - OtherUtils.dip2px(mContext, 6)) / 4;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mWidth);
    }

}