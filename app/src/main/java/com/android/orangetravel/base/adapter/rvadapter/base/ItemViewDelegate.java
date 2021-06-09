package com.android.orangetravel.base.adapter.rvadapter.base;

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder mHolder, T t, int position);

}