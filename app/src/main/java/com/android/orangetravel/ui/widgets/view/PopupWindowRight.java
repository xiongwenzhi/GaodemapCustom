package com.android.orangetravel.ui.widgets.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.utils.DisplayUtil;
import com.android.orangetravel.base.widgets.view.CircleRelativeLayout;
import com.android.orangetravel.bean.ChooseCarColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/1/26
 */

public class PopupWindowRight extends PopupWindow {
    private static final String TAG = "PopupWindowRight";
    private CommonAdapter<ChooseCarColor> mRvAdapter;
    private Context context;
    private RecyclerView car_list_color;

    public PopupWindowRight(Context context) {
        this.context = context;
        //设置view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popup_dialog_right, null);
        setContentView(view);
        car_list_color = view.findViewById(R.id.car_list_color);
        initView();
        //其他设置
        setWidth(dp2px(120));//必须设置宽度
        setHeight(DisplayUtil.getScreenHeight(context));//必须设置高度
        setFocusable(true);//是否获取焦点
        setOutsideTouchable(true);//是否可以通过点击屏幕外来关闭
        initRv();
        setAnimationStyle(R.style.RightAnimStyle);
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<ChooseCarColor>(context,
                R.layout.item_car_color) {
            @Override
            protected void convert(ViewHolder mHolder, final ChooseCarColor item,
                                   final int position) {
                CircleRelativeLayout circle_color = mHolder.getView(R.id.circle_color);
                circle_color.setColor(item.getColor());
                TextView textView = mHolder.getView(R.id.wiite_bg);
                if ("白色".equals(item.getName())) {
                    textView.setVisibility(View.VISIBLE);
                    circle_color.setVisibility(View.GONE);
                    textView.setBackgroundResource(R.drawable.red_bg_yuan);
                } else {
                    textView.setVisibility(View.GONE);
                    circle_color.setVisibility(View.VISIBLE);
                    textView.setBackgroundResource(0);
                }

                mHolder.setText(R.id.circle_tv, item.getName());
            }
        };
        car_list_color.setLayoutManager(new LinearLayoutManager(context));
        car_list_color.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                if (onColorChoose != null) {
                    onColorChoose.getColor(mRvAdapter.getAllData().get(position).getName());
                }
            }
        });
        mRvAdapter.addData(new ChooseCarColor("黑色", ContextCompat.getColor(context, R.color.black)));
        mRvAdapter.addData(new ChooseCarColor("灰色", Color.GRAY));
        mRvAdapter.addData(new ChooseCarColor("白色", ContextCompat.getColor(context, R.color.white)));
        mRvAdapter.addData(new ChooseCarColor("红色", ContextCompat.getColor(context, R.color.red)));
        mRvAdapter.addData(new ChooseCarColor("金色(米/香槟)", ContextCompat.getColor(context, R.color.xiangbin)));
        mRvAdapter.addData(new ChooseCarColor("蓝色", Color.BLUE));
        mRvAdapter.addData(new ChooseCarColor("棕色", ContextCompat.getColor(context, R.color.zongse)));
        mRvAdapter.addData(new ChooseCarColor("紫色", ContextCompat.getColor(context, R.color.zs)));
        mRvAdapter.addData(new ChooseCarColor("绿色", ContextCompat.getColor(context, R.color.green)));
        mRvAdapter.addData(new ChooseCarColor("粉色", ContextCompat.getColor(context,
                R.color.fs)));
        mRvAdapter.addData(new ChooseCarColor("黄色", Color.YELLOW));
        mRvAdapter.addData(new ChooseCarColor("橙色", ContextCompat.getColor(context, R.color.cs)));
    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        //加入动画
        ObjectAnimator.ofFloat(getContentView(), "translationX", getWidth(), 0).setDuration(500).start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //加入动画
        ObjectAnimator.ofFloat(getContentView(), "translationX", 0).setDuration(500).start();
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
    private static int[] getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initView() {

    }

    public interface OnColorChoose {
        void getColor(String value);
    }

    OnColorChoose onColorChoose;

    public void setOnChooseColor(OnColorChoose onChooseColor) {
        this.onColorChoose = onChooseColor;

    }
}
