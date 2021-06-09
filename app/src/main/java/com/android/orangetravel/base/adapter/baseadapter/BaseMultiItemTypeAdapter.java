package com.android.orangetravel.base.adapter.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.orangetravel.base.adapter.baseadapter.base.BaseItemViewDelegate;
import com.android.orangetravel.base.adapter.baseadapter.base.BaseItemViewDelegateManager;
import com.android.orangetravel.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseMultiItemTypeAdapter<T> extends BaseAdapter {

    /*获取所有数据*/
    public List<T> getAllData() {
        return mDatas;
    }
    /*根据position获取单个数据*/
    public T getDataByPosition(int position) {
        return mDatas.get(position);
    }
    /*添加单个数据*/
    public void addData(T t) {
        if (null != t) {
            mDatas.add(t);
            this.notifyDataSetChanged();
        }
    }
    /*添加所有数据*/
    public void addAllData(List<T> list) {
        if (StringUtil.isListNotEmpty(list)) {
            mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }
    /*清除所有数据*/
    public void clearData() {
        mDatas.clear();
        this.notifyDataSetChanged();
    }
    /*清除单个数据*/
    public void removeData(int index) {
        mDatas.remove(index);
        this.notifyDataSetChanged();
    }
    /*替换数据*/
    public void replaceData(List<T> list) {
        mDatas.clear();
        if (StringUtil.isListNotEmpty(list)) {
            mDatas.addAll(list);
        }
        this.notifyDataSetChanged();
    }
    /*设置数据*/
    public void setData(int index, T element) {
        if (null != element) {
            mDatas.set(index, element);
            this.notifyDataSetChanged();
        }
    }

    protected Context mContext;
    protected List<T> mDatas;

    private BaseItemViewDelegateManager mDelegateManager;

    public BaseMultiItemTypeAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>(2);
        mDelegateManager = new BaseItemViewDelegateManager();
    }

    public BaseMultiItemTypeAdapter addItemViewDelegate(BaseItemViewDelegate<T> itemViewDelegate) {
        mDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            int viewType = mDelegateManager.getItemViewType(mDatas.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseItemViewDelegate itemViewDelegate = mDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        BaseViewHolder mHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            mHolder = new BaseViewHolder(mContext, itemView, parent, position);
            mHolder.mLayoutId = layoutId;
            onViewHolderCreated(mHolder, mHolder.getConvertView());
        } else {
            mHolder = (BaseViewHolder) convertView.getTag();
            mHolder.mPosition = position;
        }
        convert(mHolder, getItem(position), position);
        return mHolder.getConvertView();
    }

    protected void convert(BaseViewHolder mHolder, T item, int position) {
        mDelegateManager.convert(mHolder, item, position);
    }

    public void onViewHolderCreated(BaseViewHolder mHolder, View itemView) {
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}