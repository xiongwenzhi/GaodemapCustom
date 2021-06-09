package com.android.orangetravel.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.statusBar.YangStatusBar;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.base.widgets.StatusBarView;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.NavigationSettingBean;
import com.android.orangetravel.ui.widgets.utils.navi.NaviUtil;
import com.android.orangetravel.ui.widgets.view.CustomSlideToUnlockView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/2/19
 * 接预约乘客导航
 */

public class PickUppassengersActivity extends BaseNaviActivity implements View.OnClickListener {
    YangTitleBar id_title_bar;
    /*获取导航设置*/
    private NavigationSettingBean settingBean;
    /*行驶速度*/
    TextView speed;
    /*行驶速度提示*/
    TextView speed_title;
    AMapNaviViewOptions options;
    StatusBarView id_status_bar_view;
    protected YangStatusBar mYangStatusBar;
    private CustomSlideToUnlockView customSlideToUnlockView;
    /*导航位置详细*/
    private LinearLayout customer_info;
    /*上车*/
    private LinearLayout slide_up_car;
    /*确认上车*/
    private LinearLayout comfir_shangche;
    /*修改乘车人数*/
    private LinearLayout update_count;
    /*确认上车*/
    private TextView comfirm_up_car_tv;
    private int carmerSpeed = -1; //摄像头超速
    /*速度值*/
    LinearLayout speed_layout;
    /*是否是开始行程*/
    private boolean isStartNav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式状态栏
        mYangStatusBar = YangStatusBar.with(this).setDarkColor(true);
        if (null != mYangStatusBar) {
            mYangStatusBar.init();
        }
        setContentView(R.layout.activity_pickuppassengers);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        id_status_bar_view = (StatusBarView) findViewById(R.id.id_status_bar_view);
        customSlideToUnlockView = (CustomSlideToUnlockView) findViewById(R.id.slide_to_unlock);
        id_title_bar = (YangTitleBar) findViewById(R.id.id_title_bar);
        mAMapNaviView.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNavi.pauseNavi();
        mAMapNavi.stopNavi();
    }

    private void initView() {
        id_status_bar_view.setBackgroundColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.title_bar_black));
        id_title_bar.setTitle("接预约乘客");
        id_title_bar.setRightText("行程详情");
        id_title_bar.setRightTextColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.white));
        id_title_bar.setRightTextVisible(true);
        mAMapNaviView.setAMapNaviViewListener(this);
        options = new AMapNaviViewOptions();
        options.setLayoutVisible(false);
        options.setScreenAlwaysBright(false);
        options.setAutoNaviViewNightMode(true);
        options.setRouteListButtonShow(true);
        options.setLeaderLineEnabled(ContextCompat.getColor(this, R.color.red));
        options.setSettingMenuEnabled(false);
        Bundle bundle = getIntent().getExtras();
        mAMapNaviView.setViewOptions(options);
        speed = findViewById(R.id.speed);
        speed_title = findViewById(R.id.speed_title);
        customer_info = findViewById(R.id.customer_info);
        slide_up_car = findViewById(R.id.slide_up_car);
        comfir_shangche = findViewById(R.id.comfir_shangche);
        update_count = findViewById(R.id.update_count);
        comfirm_up_car_tv = findViewById(R.id.comfirm_up_car_tv);
        speed_layout = (LinearLayout) findViewById(R.id.speed_layout);
        comfirm_up_car_tv.setOnClickListener(this);
        update_count.setOnClickListener(this);
        mAMapNavi.setEmulatorNaviSpeed(120);
        Log.e("nxt", "来了");
//        if (bundle.containsKey("type")) {
//            mAMapNavi.startNavi(NaviType.EMULATOR);
//        }
        customSlideToUnlockView.setmCallBack(new CustomSlideToUnlockView.CallBack() {
            @Override
            public void onSlide(int distance) {

            }

            @Override
            public void onUnlocked() {
                if (isStartNav) { //如果是开始行程 则要跳转到新页面开始导航
                    Intent intent = new Intent();
                    intent.setClass(PickUppassengersActivity.this, RealTimeGPSNaviActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "GPS");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    slide_up_car.setVisibility(View.GONE);
//                    startActivity(new Intent(PickUppassengersActivity.this, RealTimeGPSNaviActivity.class));
                } else {
                    customer_info.setVisibility(View.GONE);
                    slide_up_car.setVisibility(View.GONE);
                    comfir_shangche.setVisibility(View.VISIBLE);
                }
            }
        });
        startNav();
    }

    private NaviLatLng startLatlng, endLatlng;
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /* 终点坐标集合［建议就一个终点］*/
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();

    private void startNav() {
        startLatlng = new NaviLatLng(28.705447, 115.998173);
        startList.clear();
        startList.add(startLatlng);
        endLatlng = new NaviLatLng(28.661623, 116.021777);
        endList.clear();
        endList.add(endLatlng);
        calculate(); //规划路线
    }

    private void calculate() {
        int strategyFlag = 0;
        try {
            strategyFlag = mAMapNavi.strategyConvert(false, false,
                    false, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            mAMapNavi.calculateDriveRoute(endList, null, 10);
        }
    }


    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost,
         * hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false,
                    false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AMapCarInfo carInfo = new AMapCarInfo();
        mAMapNavi.setCarInfo(carInfo);
        List<NaviLatLng> start = new ArrayList<>();
//        start.add(new NaviLatLng(naviStartIntentBean.getLat(), naviStartIntentBean.getLon()));
//
//        List<NaviLatLng> stop = new ArrayList<>();
//        stop.add(new NaviLatLng(naviStopIntentBean.getLat(), naviStopIntentBean.getLon()));
//        mAMapNavi.calculateDriveRoute(start, stop, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);
//        mAMapNavi.setEmulatorNaviSpeed(75);
//        mAMapNavi.startNavi(NaviType.GPS);
        mAMapNavi.startNavi(NaviType.EMULATOR);
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);

        int[] routeIds = aMapCalcRouteResult.getRouteid();
        changeRoute(routeIds);


    }

    //选择线路导航
    public void changeRoute(int[] routeIds) {
        if (routeIds.length > 0) {
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeIds[0]);
            return;
        }
    }


    /**
     * ------- 导航基本信息的回调 -----
     */
    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        super.onNaviInfoUpdate(naviInfo);
        int allLength = mAMapNavi.getNaviPath().getAllLength();
        // 导航路况条 更新
        List<AMapTrafficStatus> trafficStatuses = mAMapNavi.getTrafficStatuses(0, 0);
//        RetainDistance.setText(NaviUtil.formatKM(naviInfo.getPathRetainDistance()));
//        RetainTime.setText(DateUtil.getDate(naviInfo.getPathRetainTime()) + "");
        Log.e("获取路线剩余距离", NaviUtil.formatKM(naviInfo.getPathRetainDistance())
                + "获取路线剩余时间" + naviInfo.getPathRetainTime() + "--" + naviInfo.getCurrentSpeed());
//        mTrafficBarView.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);
        speed.setText(naviInfo.getCurrentSpeed() + "");
        if (carmerSpeed > 0 && naviInfo.getCurrentSpeed() > carmerSpeed) { //如果超速了 则图标要修改
            speed_layout.setBackgroundResource(R.drawable.speed_red);
            speed.setTextColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.red));
            speed_title.setTextColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.red));
        } else {
            speed_layout.setBackgroundResource(R.drawable.speed_blue);
            speed.setTextColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.cblue));
            speed_title.setTextColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.cblue));
        }
        // 更新路口转向图标
//        nextTurnTipView.setIconType(naviInfo.getIconType());
        /**
         * 更新下一路口 路名及 距离
         */
//        textNextRoadName.setText(naviInfo.getNextRoadName());
//        textNextRoadDistance.setText(NaviUtil.formatKM(naviInfo.getCurStepRetainDistance()).replace("米", ""));
        /**
         * 绘制转弯的箭头
         */
//        drawArrow(naviInfo);
        final AMap aMap = mAMapNaviView.getMap();
        aMap.getUiSettings().setLogoPosition(-100);
        aMap.getUiSettings().setLogoLeftMargin(900);
        aMap.getUiSettings().setLogoBottomMargin(600);
//        mTrafficBarView.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);


//        aMap.getUiSettings().setLogoBottomMargin(500);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_count:
                new XPopup.Builder(PickUppassengersActivity.this).
                        asInputConfirm("", "请输入乘车人数",
                                new OnInputConfirmListener() {
                                    @Override
                                    public void onConfirm(String text) {

                                    }
                                })
                        .show();
                break;
            case R.id.comfirm_up_car_tv:
                comfirm_up_car_tv.setText("已上车");
                comfirm_up_car_tv.setTextColor(ContextCompat.getColor(PickUppassengersActivity.this, R.color.theme_color));
                comfirm_up_car_tv.setBackgroundResource(R.drawable.border_theme_1_yuan5);
                slide_up_car.setVisibility(View.VISIBLE);
                customSlideToUnlockView.resetView();
                customSlideToUnlockView.setBgResource(R.drawable.shape_round_normal_green);
                customSlideToUnlockView.setHintText("确认并开始行程");
                isStartNav = true;
                break;
        }
    }
}
