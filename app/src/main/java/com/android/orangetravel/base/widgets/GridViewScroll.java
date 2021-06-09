package com.android.orangetravel.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 无限高的GridView
 *
 * @author yangfei
 */
public class GridViewScroll extends GridView {

    public GridViewScroll(Context context) {
        super(context);
    }
    public GridViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GridViewScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

}