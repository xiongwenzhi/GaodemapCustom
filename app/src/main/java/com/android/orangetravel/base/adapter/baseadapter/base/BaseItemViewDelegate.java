package com.android.orangetravel.base.adapter.baseadapter.base;

import com.android.orangetravel.base.adapter.baseadapter.BaseViewHolder;

public interface BaseItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(BaseViewHolder mHolder, T t, int position);

}