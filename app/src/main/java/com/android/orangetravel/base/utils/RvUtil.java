package com.android.orangetravel.base.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView嵌套问题工具类
 *
 * @author yangfei
 */
public final class RvUtil {

    private RvUtil() {
        // 这个类不能实例化
    }

    /**
     * 解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
     */
    public static LinearLayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                // 如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
                // return super.canScrollVertically()
            }
        };
    }

    /**
     * 解决嵌套问题
     */
    public static void solveNestQuestion(RecyclerView rv) {
        // 解决数据加载不完的问题
        rv.setNestedScrollingEnabled(false);
        rv.setHasFixedSize(true);
        // 解决数据加载完成后,没有停留在顶部的问题
        rv.setFocusable(false);
    }

}