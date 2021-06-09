package com.android.orangetravel.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.amap.api.services.core.PoiItem;
import com.android.orangetravel.R;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.ui.widgets.citypicker.CityPicker;
import com.android.orangetravel.ui.widgets.citypicker.adapter.OnPickListener;
import com.android.orangetravel.ui.widgets.citypicker.model.City;
import com.android.orangetravel.ui.widgets.citypicker.model.LocatedCity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;

/**
 * Description: 仿知乎底部评论弹窗
 */
public class IndexAroundPopup extends BottomPopupView implements View.OnClickListener {
    /*订单热力图*/
    private FrameLayout frl_hot_map;
    /*路况右上角图片*/
    private ImageView img_hot_map_rightImage;
    /*关闭*/
    private TextView tv_dismiss;
    /*路况是否选中*/
    private boolean hotMapCheck = false;
    /*点击厕所*/
    private ImageView cesuo_img;
    /*点击加油站*/
    private ImageView jiayou_img;
    /*卫星*/
    private FrameLayout frl_satellite;
    /*卫星右上角图片*/
    private ImageView img_satellite_rightImage;
    /*卫星是否选中*/
    private boolean satelliteCheck = false;
    /*去搜索*/
    private TextView go_search;

    public IndexAroundPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_index_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        frl_hot_map = findViewById(R.id.hot_map);
        cesuo_img = findViewById(R.id.cesuo_img);
        jiayou_img = findViewById(R.id.jiayou_img);
        frl_hot_map = findViewById(R.id.hot_map);
        img_hot_map_rightImage = findViewById(R.id.hot_map_rightImage);
        frl_satellite = findViewById(R.id.satellite);
        img_satellite_rightImage = findViewById(R.id.satellite_image);
        tv_dismiss = findViewById(R.id.tv_dismiss);
        go_search = findViewById(R.id.go_search);
        go_search.setOnClickListener(this);
        frl_hot_map.setOnClickListener(this);
        frl_satellite.setOnClickListener(this);
        tv_dismiss.setOnClickListener(this);
        cesuo_img.setOnClickListener(this);
        jiayou_img.setOnClickListener(this);
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "知乎评论 onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getAppHeight(getContext()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_map: //点击路况
                frl_hot_map.setBackgroundResource(hotMapCheck ? R.drawable.
                        index_menu_check_bg : R.drawable.index_menu_bg);
                img_hot_map_rightImage.setVisibility(hotMapCheck ? VISIBLE : GONE);
                hotMapCheck = !hotMapCheck;
                if (onItemMenuClick != null) {
                    onItemMenuClick.trafficClick(hotMapCheck);
                }
                break;
            case R.id.satellite://点击卫星
                frl_satellite.setBackgroundResource(satelliteCheck ? R.drawable.index_menu_check_bg : R.drawable.index_menu_bg);
                img_satellite_rightImage.setVisibility(satelliteCheck ? VISIBLE : GONE);
                satelliteCheck = !satelliteCheck;
                if (onItemMenuClick != null) {
                    onItemMenuClick.satelliteClick(satelliteCheck);
                }
                break;
            case R.id.cesuo_img: //点击厕所
                setDefault(true);
                if (onItemMenuClick != null) {
                    onItemMenuClick.chooseMenu(true);
                }
                break;
            case R.id.jiayou_img: //点击加油站
                setDefault(false);
                if (onItemMenuClick != null) {
                    onItemMenuClick.chooseMenu(false);
                }
                break;
            case R.id.tv_dismiss:
                dismiss();
                break;
            case R.id.go_search:
                dismiss();
                CityPicker.from((FragmentActivity) getContext())
                        .enableAnimation(true)
//                        .setType(1)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setLocatedCity(new LocatedCity(Constant.LocationCity, "", ""))
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
//                                currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                                Toast.makeText(
                                        getContext(),
                                        String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onCancel() {
//                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onClickList(PoiItem poiItem) {
                                if (onItemMenuClick != null) {
                                    onItemMenuClick.chooseAddress(poiItem);
                                }
                            }

                            @Override
                            public void onClickTop(String city) {

                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        CityPicker.from(MainActivity.this).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
//                                    }
//                                }, 3000);
                            }
                        })
                        .show();
                break;
        }
    }

    //设置默认
    private void setDefault(boolean isCheckceSuo) {
        cesuo_img.setBackgroundResource(R.drawable.border_one_yuan);
        jiayou_img.setBackgroundResource(R.drawable.border_one_yuan);
        if (isCheckceSuo) { //如果选中厕所
            cesuo_img.setBackgroundResource(R.drawable.border_one_yuan_check);
        } else {
            jiayou_img.setBackgroundResource(R.drawable.border_one_yuan_check);
        }
    }

    public interface OnItemMenuClick {
        void trafficClick(boolean isCheck);

        void satelliteClick(boolean isCheck);

        void chooseMenu(boolean isCheckWc);

        void chooseAddress(PoiItem clickAddress);
    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}