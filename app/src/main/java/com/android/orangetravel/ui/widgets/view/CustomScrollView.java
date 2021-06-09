package com.android.orangetravel.ui.widgets.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author Mr Xiong
 * @date 2020/12/20
 */

public class CustomScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* ScrollView下嵌套GridView或ListView默认不在顶部的解决方法*/
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }


    public void setOnScrollListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {

            if (oldy < y ) {// 手指向上滑动，屏幕内容下滑
                scrollViewListener.onScroll(oldy,y,false);

            } else if (oldy > y ) {// 手指向下滑动，屏幕内容上滑
                scrollViewListener.onScroll(oldy,y,true);
            }

        }
    }

    public  interface ScrollViewListener{//dy Y轴滑动距离，isUp 是否返回顶部
        void onScroll(int oldy, int dy, boolean isUp);
    }
}
