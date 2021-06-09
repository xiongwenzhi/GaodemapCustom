package com.android.orangetravel.base.adapter.baseadapter;

import android.content.Context;

import com.android.orangetravel.base.adapter.baseadapter.base.BaseItemViewDelegate;

/**
 * 公共的BaseAdapter
 *
 * @author yangfei
 */
public abstract class BaseCommonAdapter<T> extends BaseMultiItemTypeAdapter<T> {

    public BaseCommonAdapter(Context mContext, final int layoutId) {
        super(mContext);
        addItemViewDelegate(new BaseItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder mHolder, T t, int position) {
                BaseCommonAdapter.this.convert(mHolder, t, position);
            }
        });
    }

    protected abstract void convert(BaseViewHolder mHolder, T item, int position);

}