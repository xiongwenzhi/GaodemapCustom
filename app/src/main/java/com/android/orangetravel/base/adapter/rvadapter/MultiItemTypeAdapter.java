package com.android.orangetravel.base.adapter.rvadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.base.adapter.rvadapter.base.ItemViewDelegate;
import com.android.orangetravel.base.adapter.rvadapter.base.ItemViewDelegateManager;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    /*获取所有数据*/
    /*根据position获取单个数据*/

    public List<T> getAllData() {
        return mDatas;
    }
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

    protected ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>(2);
        this.mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder mHolder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(mHolder, mHolder.getConvertView());
        setListener(parent, mHolder, viewType);
        return mHolder;
    }

    public void onViewHolderCreated(ViewHolder mHolder, View itemView) {
    }

    public void convert(ViewHolder mHolder, T t) {
        mItemViewDelegateManager.convert(mHolder, t, mHolder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder mHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        mHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    int position = mHolder.getAdapterPosition();
                    onItemClickListener.onItemClick(v, mHolder, position);
                }
            }
        });
        mHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != onItemLongClickListener) {
                    int position = mHolder.getAdapterPosition();
                    return onItemLongClickListener.onItemLongClick(v, mHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position) {
        convert(mHolder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * 接口回调(点击)
     */
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position);
    }

    protected OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 接口回调(长按)
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, RecyclerView.ViewHolder mHolder, int position);
    }

    protected OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

}