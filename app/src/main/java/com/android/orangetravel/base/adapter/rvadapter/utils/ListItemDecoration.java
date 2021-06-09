package com.android.orangetravel.base.adapter.rvadapter.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.base.utils.DensityUtil;
import com.yang.base.R;

/**
 * RecyclerView-ListView的间隔线
 *
 * @author yangfei
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {

    private int mDrawableHeight;
    private Paint mPaint;
    private boolean isDrawLast;

    /**
     * @param mContext      上下文
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     * @param isDrawLast    是否绘制最后一个Item的间隔线
     */
    public ListItemDecoration(Context mContext, float dividerHeight, @ColorRes int dividerColor, boolean isDrawLast) {
        this.mDrawableHeight = DensityUtil.dp2px(dividerHeight);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(mContext, dividerColor));
        this.mPaint.setStyle(Paint.Style.FILL);
        this.isDrawLast = isDrawLast;
    }

    public ListItemDecoration(Context mContext, float dividerHeight, @ColorRes int dividerColor) {
        this(mContext, dividerHeight, dividerColor, false);
    }

    public ListItemDecoration(Context mContext, @ColorRes int dividerColor) {
        this(mContext, 0.5f, dividerColor, false);
    }

    public ListItemDecoration(Context mContext) {
        this(mContext, 0.5f, R.color.gray3, false);
    }

    /**
     * 计算偏移量
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // super.getItemOffsets(outRect, view, parent, state);
        int childPosition = parent.getChildAdapterPosition(view);
        int lastCount = parent.getAdapter().getItemCount() - 1;
        // 是否绘制最后一个Item的间隔线
        if (childPosition == lastCount && !isDrawLast) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        outRect.set(0, 0, 0, mDrawableHeight);
    }

    /**
     * 画间隔线
     */
    @Override
    public void onDraw(Canvas mCanvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(mCanvas, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDrawableHeight;
            if (mPaint != null) {
                mCanvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}