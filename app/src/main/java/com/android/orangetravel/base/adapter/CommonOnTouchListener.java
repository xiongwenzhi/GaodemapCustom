package com.android.orangetravel.base.adapter;

import android.view.MotionEvent;
import android.view.View;

/**
 * 公共的触摸事件
 *
 * @author yangfei
 */
public interface CommonOnTouchListener {

    void touchListener(View view, MotionEvent event, int position);

}