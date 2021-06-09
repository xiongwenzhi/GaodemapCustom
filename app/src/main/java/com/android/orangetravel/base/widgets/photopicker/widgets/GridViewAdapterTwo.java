package com.android.orangetravel.base.widgets.photopicker.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.utils.DensityUtil;
import com.android.orangetravel.base.utils.SystemUtil;
import com.yang.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择图片的适配器(添加商品用的)
 *
 * @author yangfei
 */
public class GridViewAdapterTwo extends BaseAdapter {

    private Context mContext;
    private List<String> mLists;

    public GridViewAdapterTwo(Context mContext, List<String> mLists) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_gridview_two, null);
            mViewHolder.adapter_gridview_image = (ImageView) convertView.findViewById(R.id.adapter_gridview_image);
            mViewHolder.adapter_gridview_delete = (LinearLayout) convertView.findViewById(R.id.adapter_gridview_delete);
            mViewHolder.mgridview_tv = (TextView) convertView.findViewById(R.id.grid_two_tv);
            mViewHolder.mchoose_image = (FrameLayout) convertView.findViewById(R.id.choose_image);
            mViewHolder.minputgrid = (EditText) convertView.findViewById(R.id.inputgrid);
            convertView.setTag(mViewHolder);
        }
        mViewHolder = (ViewHolder) convertView.getTag();
        mViewHolder.mgridview_tv.getBackground().setAlpha(150);
        ImageView image = mViewHolder.adapter_gridview_image;
        LinearLayout delete = mViewHolder.adapter_gridview_delete;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) image.getLayoutParams();
        layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = SystemUtil.getScreenWidth() / 3 - DensityUtil.dp2px(10) * 3;
        mViewHolder.adapter_gridview_image.setLayoutParams(layoutParams);
        mViewHolder.mchoose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onDeleteListener) {
                    onDeleteListener.chooseImage(position);
                }
            }
        });
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
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            switch (position) {
                case 0:
                    mViewHolder.mgridview_tv.setText("全景图");
                    mViewHolder.minputgrid.setHint( "全景图介绍");
                    break;
                case 1:
                    mViewHolder.mgridview_tv.setText("特点图");
                    mViewHolder.minputgrid.setHint( "特点图介绍");
                    break;
                case 2:
                    mViewHolder.mgridview_tv.setText("细节图一");
                    mViewHolder.minputgrid.setHint( "细节图介绍");
                    break;
                case 3:
                    mViewHolder.mgridview_tv.setText("细节图二");
                    mViewHolder.minputgrid.setHint( "细节图介绍");
                    break;
                case 4:
                    mViewHolder.mgridview_tv.setText("细节图三");
                    mViewHolder.minputgrid.setHint( "细节图介绍");
                    break;
                case 5:
                    mViewHolder.mgridview_tv.setText("细节图四");
                    mViewHolder.minputgrid.setHint( "细节图介绍");
                    break;
                case 6:
                    mViewHolder.mgridview_tv.setText("最多上传6张图片");
                    mViewHolder.minputgrid.setHint( "最多上传6张图片");
                    break;
            }
        } else {
            GlideUtil.loadImg(mContext, getItem(position), image);
            delete.setVisibility(View.VISIBLE);
            if (getmLists.size() > 0) {
                mViewHolder.mgridview_tv.setVisibility(View.VISIBLE);
                mViewHolder.mgridview_tv.setText(getmLists.get(position));
                mViewHolder.minputgrid.setHint(getmLists.get(position) + "介绍");
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView adapter_gridview_image;
        private LinearLayout adapter_gridview_delete;
        private TextView mgridview_tv;
        private FrameLayout mchoose_image;//选择图片
        private EditText minputgrid;
    }

    /**
     * 接口回调
     */
    public interface OnDeleteListener {
        void onDelete(int position);

        void chooseImage(int position);
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