package com.android.orangetravel.base.adapter.rvadapter.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.orangetravel.base.utils.DensityUtil;
import com.yang.base.R;

/**
 * RecyclerView-GridView的间隔线
 *
 * @author yangfei
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int mDrawableHeight;
    private Paint mPaint;

    /**
     * @param mContext      上下文
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public GridItemDecoration(Context mContext, float dividerHeight, @ColorRes int dividerColor) {
        this.mDrawableHeight = DensityUtil.dp2px(dividerHeight);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(ContextCompat.getColor(mContext, dividerColor));
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public GridItemDecoration(Context mContext, @ColorRes int dividerColor) {
        this(mContext, 0.5f, dividerColor);
    }

    public GridItemDecoration(Context mContext) {
        this(mContext, 0.5f, R.color.gray3);
    }

    @Override
    public void onDraw(Canvas mCanvas, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        // 画横向
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + mDrawableHeight;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDrawableHeight;

            if (mPaint != null) {
                mCanvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
        // 画竖向
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDrawableHeight;

            if (mPaint != null) {
                mCanvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        final int spanCount = getSpanCount(parent);
        final int childCount = parent.getAdapter().getItemCount();
        itemPosition++;

        if (itemPosition % spanCount == 0) {// 不画右边的
            outRect.set(0, 0, 0, mDrawableHeight);
        } else {
            outRect.set(0, 0, mDrawableHeight, mDrawableHeight);
        }
//        if (childCount == (itemPosition + 1)) {
//            outRect.set(0, 0, 0, 0);
//        } else if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行,则不需要绘制底部
//            outRect.set(0, 0, mDrawableHeight, 0);
//        } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {// 如果是最后一列,则不需要绘制右边
//            outRect.set(0, 0, 0, mDrawableHeight);
//        } else {
//            outRect.set(0, 0, mDrawableHeight, mDrawableHeight);
//        }
    }

    /**
     * 获得列数
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

//    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
//        // pos++;
//        LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            childCount = childCount - childCount % spanCount;
//
//            if ((childCount % spanCount) > 0) {// 有余数
//                childCount = childCount - childCount % spanCount;
//            } else {
//                childCount = childCount - spanCount;
//            }
//
//            if (pos > childCount) {// 如果是最后一行,则不需要绘制底部
//                return true;
//            }
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//            // StaggeredGridLayoutManager 且纵向滚动
//            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
//                childCount = childCount - childCount % spanCount;
//                // 如果是最后一行,则不需要绘制底部
//                if (pos >= childCount)
//                    return true;
//            } else
//            // StaggeredGridLayoutManager 且横向滚动
//            {
//                // 如果是最后一行,则不需要绘制底部
//                if ((pos + 1) % spanCount == 0) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
//        LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            if ((pos + 1) % spanCount == 0) {// 如果是最后一列,则不需要绘制右边
//                return true;
//            }
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
//                if ((pos + 1) % spanCount == 0) {// 如果是最后一列,则不需要绘制右边
//                    return true;
//                }
//            } else {
//                childCount = childCount - childCount % spanCount;
//                if (pos >= childCount) {// 如果是最后一列,则不需要绘制右边
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

}