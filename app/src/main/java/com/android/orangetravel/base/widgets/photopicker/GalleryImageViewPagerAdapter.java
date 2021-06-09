package com.android.orangetravel.base.widgets.photopicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.android.orangetravel.base.glide.GlideUtil;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yang.base.R;

import java.util.List;

/**
 * 显示大图的适配器
 */
public class GalleryImageViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mLists;
    private List<String> mPathLists;

    public GalleryImageViewPagerAdapter(Context mContext, List<View> mLists, List<String> mPathLists) {
        this.mContext = mContext;
        this.mLists = mLists;
        this.mPathLists = mPathLists;
    }

    @Override
    public int getCount() {
        return mLists.size() > 0 ? mLists.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mLists.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View contentView = mLists.get(position);
        ImageView mImageView = (ImageView) contentView.findViewById(R.id.act_show_larger_item_image);
        final ProgressBar mProgressBar = (ProgressBar) contentView.findViewById(R.id.act_show_larger_item_pb);

        mImageView.setImageResource(R.mipmap.app_logo);
        GlideUtil.loadImg(mContext, mPathLists.get(position), mImageView, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                mProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object o2, Target target, DataSource dataSource, boolean b) {
                mProgressBar.setVisibility(View.GONE);
                return false;
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onViewPagerItemClick) {
                    onViewPagerItemClick.onClick();
                }
            }
        });

        container.addView(contentView);
        return contentView;
    }

    /**
     * 接口回调
     */
    public interface OnViewPagerItemClick {
        void onClick();
    }

    private OnViewPagerItemClick onViewPagerItemClick = null;

    public void setOnViewPagerItemClick(OnViewPagerItemClick onViewPagerItemClick) {
        this.onViewPagerItemClick = onViewPagerItemClick;
    }

}