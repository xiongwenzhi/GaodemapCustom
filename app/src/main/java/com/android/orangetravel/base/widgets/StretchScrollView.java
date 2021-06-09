package com.android.orangetravel.base.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 拉伸ScrollView
 *
 * @author yangfei
 */
public class StretchScrollView extends ScrollView {

    // 只允许拖动屏幕的1/3
    private static final int size = 3;
    // 子View
    private View childView;
    // 点击时y坐标
    private float y;
    // 矩形(这里只是个形式,只是用于判断是否需要动画.)
    private Rect normal = new Rect();
    // 是否开始计算
    private boolean isCount = false;

    public StretchScrollView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (null != childView) {
            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commonTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (isNeedAnimation()) {
                    animation();
                    isCount = false;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY) / size;// 滑动距离
                if (!isCount) {
                    deltaY = 0;// 归0
                }
                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动,这时移动布局
                if (isNeedMove()) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                    }
                    // 移动布局
                    childView.layout(childView.getLeft(), childView.getTop() - deltaY / 2, childView.getRight(), childView.getBottom() - deltaY / 2);
                }
                isCount = true;
                break;
            }
        }
    }

    /***
     * 回缩动画
     */
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, childView.getTop(), normal.top);
        ta.setDuration(200);
        childView.startAnimation(ta);
        // 设置回到正常的布局位置
        childView.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 是否需要移动布局
     * childView.getMeasuredHeight():获取的是控件的总高度
     * getHeight():获取的是屏幕的高度
     */
    public boolean isNeedMove() {
        int offset = childView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

}