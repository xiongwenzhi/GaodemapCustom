package com.android.orangetravel.base.widgets.photopicker;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * BaseAdapter 的基类
 *
 * @author yangfei
 */
public abstract class YangBaseAdapter<T> extends BaseAdapter
{

    protected Context mContext;
    protected List<T> mLists;
    protected Handler mHandler;

    public YangBaseAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    public YangBaseAdapter(Context mContext, List<T> mLists)
    {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    public YangBaseAdapter(Context mContext, List<T> mLists, Handler mHandler)
    {
        this.mContext = mContext;
        this.mLists = mLists;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount()
    {
        if (mLists != null)
        {
            return mLists.size();
        } else
        {
            return 0;
        }
    }

    @Override
    public T getItem(int position)
    {
        if (mLists != null)
        {
            return mLists.get(position);
        } else
        {
            return null;
        }
    }

    public Context getContext()
    {
        if (mContext != null)
        {
            return mContext;
        } else
        {
            return null;
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * 数据更新
     *
     * @param newItems
     */
    public void updateData(List<T> newItems)
    {
        if (newItems != null)
        {
            mLists = newItems;
            notifyDataSetChanged();
        }
    }

    /**
     * 获取items
     */
    public List<T> getData()
    {
        return mLists;
    }

    /**
     * 添加数据
     */
    public void addData(List<T> newItems)
    {
        if (mLists != null && newItems != null)
        {
            int count = newItems.size();
            for (int i = 0; i < count; i++)
            {
                mLists.add(newItems.get(i));
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public synchronized View getView(int position, View convertView, ViewGroup parent)
    {
        return getSpecifiedView(position, convertView, parent);
    }

    /**
     * 与普通的Adapter的getView方法完全一样
     */
    protected abstract View getSpecifiedView(int position, View convertView, ViewGroup parent);

}