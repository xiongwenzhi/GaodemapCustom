package com.android.orangetravel.base.widgets.photopicker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.widgets.photopicker.beans.PhotoFolder;
import com.android.orangetravel.base.widgets.photopicker.utils.OtherUtils;
import com.yang.base.R;

import java.util.List;

/**
 * FolderAdapter
 * <p/>
 * 图片目录适配器
 */
public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private List<PhotoFolder> mLists;
    private int minWidth;

    public FolderAdapter(Context mContext, List<PhotoFolder> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
        this.minWidth = OtherUtils.dip2px(mContext, 90);
    }

    @Override
    public int getCount() {
        if (null == mLists) {
            return 0;
        }
        return mLists.size();
    }

    @Override
    public PhotoFolder getItem(int position) {
        if (mLists == null || mLists.size() == 0) {
            return null;
        }
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (null == convertView) {
            mHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_item_folder_layout, null);
            mHolder.photoIV = (ImageView) convertView.findViewById(R.id.imageview_folder_img);
            mHolder.folderNameTV = (TextView) convertView.findViewById(R.id.textview_folder_name);
            mHolder.photoNumTV = (TextView) convertView.findViewById(R.id.textview_photo_num);
            mHolder.selectIV = (ImageView) convertView.findViewById(R.id.imageview_folder_select);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PhotoFolder mFolde = getItem(position);
        if (mFolde == null) {
            return convertView;
        }
        if (mFolde.getPhotoList() == null || mFolde.getPhotoList().size() == 0) {
            return convertView;
        }
        mHolder.selectIV.setVisibility(View.GONE);
        mHolder.photoIV.setImageResource(R.mipmap.photo_loading);
        if (mFolde.isSelected()) {
            mHolder.selectIV.setVisibility(View.VISIBLE);
        }
        mHolder.folderNameTV.setText(mFolde.getName());
        mHolder.photoNumTV.setText(mFolde.getPhotoList().size() + "张");
        GlideUtil.loadImg(mContext, mFolde.getPhotoList().get(0).getPath(), mHolder.photoIV);
        // ImageLoader.getInstance().display(mFolde.getPhotoList().get(0).getPath(), mHolder.photoIV, minWidth, minWidth);
        return convertView;
    }

    private class ViewHolder {
        private ImageView photoIV;
        private TextView folderNameTV;
        private TextView photoNumTV;
        private ImageView selectIV;
    }

}