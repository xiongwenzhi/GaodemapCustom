package com.android.orangetravel.base.adapter.rvadapter.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.CommonOnLongClickListener;
import com.android.orangetravel.base.adapter.CommonOnTouchListener;
import com.android.orangetravel.base.glide.GlideUtil;

/**
 * RecyclerView的ViewHolder
 *
 * @author yangfei
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        this.mConvertView = itemView;
        this.mViews = new SparseArray<View>();
    }

    public static ViewHolder createViewHolder(Context mContext, View itemView) {
        ViewHolder holder = new ViewHolder(mContext, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context mContext, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        ViewHolder mHolder = new ViewHolder(mContext, itemView);
        return mHolder;
    }

    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /*---------以下为辅助方法---------*/

    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setTextColor(int viewId, @ColorInt int textColorInt) {
        TextView view = getView(viewId);
        view.setTextColor(textColorInt);
        return this;
    }

    public ViewHolder setTextColorNew(int viewId, int textColorInt) {
        TextView view = getView(viewId);
        view.setTextColor(ContextCompat.getColor(mContext, textColorInt));
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, @ColorRes int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(ContextCompat.getColor(mContext, textColorRes));
        return this;
    }

    public ViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, @ColorRes int colorRes) {
        View view = getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(mContext, colorRes));
        return this;
    }

    public ViewHolder setBackgroundResource(int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setInVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /*---------关于事件的---------*/

    public ViewHolder setOnClickListener(int viewId, final int position, final CommonOnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickListener(v, position);
            }
        });
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, final int position, final CommonOnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.longClickListener(v, position);
                return false;
            }
        });
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, final int position, final CommonOnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listener.touchListener(v, event, position);
                return false;
            }
        });
        return this;
    }

    /*---------自己加的---------*/

    public ViewHolder loadImage(Context mContext, String imgUrl, int viewId) {
        GlideUtil.loadImg(mContext, imgUrl, (ImageView) getView(viewId));
        return this;
    }
    /*---------自己加的---------*/

    public ViewHolder loadImageErrorImage(Context mContext, String imgUrl, int viewId, int ErrorImage) {
        GlideUtil.loadImg(mContext, imgUrl, (ImageView) getView(viewId), ErrorImage);
        return this;
    }
}