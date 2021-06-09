package com.android.orangetravel.base.adapter.rvadapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.android.orangetravel.base.adapter.rvadapter.base.ItemViewDelegate;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;

/**
 * 公共的RecyclerView.Adapter
 *
 * @author yangfei
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context mContext, final int layoutId) {
        super(mContext);
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder mHolder, T t, int position) {
                CommonAdapter.this.convert(mHolder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder mHolder, T item, int position);

}