package com.android.orangetravel.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 接口回调(滑动的距离)
 *
 * @author yangfei
 */
public class ObservableScrollView extends ScrollView {

    public ObservableScrollView(Context context) {
        super(context);
    }
    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 接口回调(滑动的距离)
    public interface OnScrollChangeListener {
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }
    private OnScrollChangeListener scrollViewListener = null;
    public void setOnScrollChangeListener(OnScrollChangeListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}