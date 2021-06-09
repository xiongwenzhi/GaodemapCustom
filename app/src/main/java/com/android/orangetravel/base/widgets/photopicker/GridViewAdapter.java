package com.android.orangetravel.base.widgets.photopicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.orangetravel.base.glide.GlideUtil;
import com.yang.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择图片的适配器(添加商品用的)
 *
 * @author yangfei
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mLists;

    public GridViewAdapter(Context mContext, List<String> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    @Override
    public int getCount() {
        if (null != mLists) {
            return mLists.size() + 1;
        } else {
            return 1;
        }
    }

    @Override
    public String getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_gridview, null);
            mViewHolder.adapter_gridview_image = (ImageView) convertView.findViewById(R.id.adapter_gridview_image);
            mViewHolder.adapter_gridview_delete = (LinearLayout) convertView.findViewById(R.id.adapter_gridview_delete);
//            mViewHolder.mgridview_tv = (TextView) convertView.findViewById(R.id.gridview_tv);
            convertView.setTag(mViewHolder);
        }
        mViewHolder = (ViewHolder) convertView.getTag();
        ImageView image = mViewHolder.adapter_gridview_image;
        LinearLayout delete = mViewHolder.adapter_gridview_delete;
//        if (getmLists.size() - 1 >= position) {
//            mViewHolder.mgridview_tv.setText(getmLists.get(position));
//        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDeleteListener) {
                    onDeleteListener.onDelete(position);
                }
            }
        });
        if (position == mLists.size()) {
            GlideUtil.loadImg(mContext, R.mipmap.photopicker_add_image, image);
            delete.setVisibility(View.GONE);
        } else {
            GlideUtil.loadImg(mContext, getItem(position), image);
            delete.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView adapter_gridview_image;
        private LinearLayout adapter_gridview_delete;
        private TextView mgridview_tv;
    }

    /**
     * 接口回调
     */
    public interface OnDeleteListener {
        void onDelete(int position);
    }

    private OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    private List<String> getmLists = new ArrayList<>();

    public void setListTv(List<String> listTv) {
        this.getmLists = listTv;
    }
}