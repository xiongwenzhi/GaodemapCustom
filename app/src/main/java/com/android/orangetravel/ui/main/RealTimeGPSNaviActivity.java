package com.android.orangetravel.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.CameraType;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.NextTurnTipView;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.navi.view.TrafficProgressBar;
import com.android.orangetravel.R;
import com.android.orangetravel.base.statusBar.YangStatusBar;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.bean.NaviIntentBean;
import com.android.orangetravel.bean.NavigationSettingBean;
import com.android.orangetravel.ui.widgets.NaviSettingPopup;
import com.android.orangetravel.ui.widgets.TrafficReportPopup;
import com.android.orangetravel.ui.widgets.utils.navi.NaviUtil;
import com.android.orangetravel.ui.widgets.view.CustomSlideToUnlockView;
import com.android.orangetravel.ui.widgets.view.DriveWayLinear;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

import static com.amap.api.navi.AMapNaviView.CAR_UP_MODE;
import static com.amap.api.navi.AMapNaviView.NORTH_UP_MODE;
import static com.android.orangetravel.ui.main.NavigationSettingActivity.SAVENAVAGI_KET;

/**
 * 送乘客导航
 */
public class RealTimeGPSNaviActivity extends BaseNaviActivity implements AMapNaviListener, View.OnClickListener {
    private TextView textNextRoadDistance;
    private TextView textNextRoadName;
    private NextTurnTipView nextTurnTipView;
    private TextView RetainDistance, RetainTime;
    private TrafficProgressBar mTrafficBarView;
    /*行驶速度*/
    TextView speed;
    /*行驶速度提示*/
    TextView speed_title;
    /**
     * 路线Overlay
     */
    RouteOverLay routeOverLay;
    /**
     * 绘制转弯箭头
     */
    private int roadIndex;

    boolean isQuanlan = false;

    private NaviIntentBean naviStartIntentBean;
    private NaviIntentBean naviStopIntentBean;
    /*导航设置*/
    private CardView setting_nav;
    /*交通上报*/
    private CardView navigation_layout;
    /*导航设置底部弹出框*/
    private NaviSettingPopup naviSettingPopup;
    /*交通上报底部弹出框*/
    private TrafficReportPopup trafficReportPopup;
    /*获取导航设置*/
    private NavigationSettingBean settingBean;
    /*交通上报按钮*/
    ImageView shangbao_nav;
    /*设置*/
    ImageView setting_img_nav;
    /*全览*/
    ImageView quanlan_nav;
    /*速度值*/
    LinearLayout speed_layout;
    /*是否是白天模式*/
    boolean isDay = true;
    /*车道控件*/
    private DriveWayLinear mDriveWayView;
    /*滑动插件*/
    private CustomSlideToUnlockView slide_to_unlock;
    AMapNaviViewOptions options;
    private String type = "";//导航类型
    private int carmerSpeed = -1; //摄像头超速

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YangStatusBar.translucentStatusBar(this, true);
        setContentView(R.layout.activity_real_time_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        initView();

    }


    private void initView() {
        shangbao_nav = (ImageView) findViewById(R.id.shangbao_nav);
        setting_img_nav = (ImageView) findViewById(R.id.setting_img_nav);
        quanlan_nav = (ImageView) findViewById(R.id.quanlan_nav);
        speed_layout = (LinearLayout) findViewById(R.id.speed_layout);
        mDriveWayView = (DriveWayLinear) findViewById(R.id.myDriveWayView);
        slide_to_unlock = (CustomSlideToUnlockView) findViewById(R.id.slide_to_unlock);
        slide_to_unlock.setTv_right("8.88元");
        slide_to_unlock.setmCallBack(new CustomSlideToUnlockView.CallBack() {
            @Override
            public void onSlide(int distance) {

            }

            @Override
            public void onUnlocked() {
                startActivity(new Intent(RealTimeGPSNaviActivity.this, ConfirmbillActivity.class));
            }
        });
        mAMapNaviView.setAMapNaviViewListener(this);
        options = new AMapNaviViewOptions();
        options.setLayoutVisible(false);
        options.setScreenAlwaysBright(false);
        options.setAutoNaviViewNightMode(true);
//        options.setNaviNight(true); //设置黑夜模式。
        options.setRouteListButtonShow(true);
        options.setLeaderLineEnabled(ContextCompat.getColor(this, R.color.red));
        options.setSettingMenuEnabled(false);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("start")) {
            naviStartIntentBean = (NaviIntentBean) bundle.getSerializable("start");
        }
        if (bundle.containsKey("stop")) {
            naviStopIntentBean = (NaviIntentBean) bundle.getSerializable("stop");
        }
        mAMapNaviView.setViewOptions(options);
        textNextRoadDistance = (TextView) findViewById(R.id.text_next_road_distance);
        setting_nav = (CardView) findViewById(R.id.setting_nav);
        setting_nav.setOnClickListener(this);
        textNextRoadName = (TextView) findViewById(R.id.text_next_road_name);
        nextTurnTipView = (NextTurnTipView) findViewById(R.id.icon_next_turn_tip);
        RetainDistance = (TextView) findViewById(R.id.RetainDistance);
        RetainTime = (TextView) findViewById(R.id.RetainTime);
        navigation_layout = (CardView) findViewById(R.id.navigation_layout);
        navigation_layout.setOnClickListener(this);
        speed = findViewById(R.id.speed);
        speed_title = findViewById(R.id.speed_title);
        AMapNaviPath naviPath = mAMapNavi.getNaviPath();
        /**
         * 初始化路线参数
         */
        routeOverLay = new RouteOverLay(mAMapNaviView.getMap(), naviPath, this);
        mTrafficBarView = (TrafficProgressBar) findViewById(R.id.myTrafficBar);
        mTrafficBarView.setUnknownTrafficColor(Color.parseColor("#0091FF"));
        mTrafficBarView.setSmoothTrafficColor(Color.parseColor("#00BA1F"));
        mTrafficBarView.setSlowTrafficColor(Color.parseColor("#FFBA00"));
        mTrafficBarView.setJamTrafficColor(Color.parseColor("#F31D20"));
        mTrafficBarView.setVeryJamTrafficColor(Color.parseColor("#A8090B"));
        findViewById(R.id.close_navi).setOnClickListener(this);
        findViewById(R.id.quanlan).setOnClickListener(this);
        mAMapNavi.setEmulatorNaviSpeed(120);
        Log.e("nxt", "来了");
        if (bundle.containsKey("type")) {
            mAMapNavi.startNavi(NaviType.EMULATOR);
//            mAMapNavi.startNavi(NaviType.GPS);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getNavaiSetting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNavi.pauseNavi();
        mAMapNavi.destroy();
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
//        carInfo.setCarNumber("京DFZ588");
        mAMapNavi.setCarInfo(carInfo);
        List<NaviLatLng> start = new ArrayList<>();
        start.add(new NaviLatLng(naviStartIntentBean.getLat(), naviStartIntentBean.getLon()));

        List<NaviLatLng> stop = new ArrayList<>();
        stop.add(new NaviLatLng(naviStopIntentBean.getLat(), naviStopIntentBean.getLon()));
        mAMapNavi.calculateDriveRoute(start, stop, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);
//        mAMapNavi.setEmulatorNaviSpeed(75);
//        mAMapNavi.startNavi(NaviType.GPS);
//        mAMapNavi.startNavi(NaviType.EMULATOR);
        //设置模拟导航的行车速度
//        mAMapNavi.setEmulatorNaviSpeed(75);
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
        RetainDistance.setText(NaviUtil.formatKM(naviInfo.getPathRetainDistance()));
        RetainTime.setText(DateUtil.getDate(naviInfo.getPathRetainTime()) + "");
        Log.e("获取路线剩余距离", NaviUtil.formatKM(naviInfo.getPathRetainDistance())
                + "获取路线剩余时间" + naviInfo.getPathRetainTime() + "--" + naviInfo.getCurrentSpeed());
//        mTrafficBarView.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);
        speed.setText(naviInfo.getCurrentSpeed() + "");
        if (carmerSpeed > 0 && naviInfo.getCurrentSpeed() > carmerSpeed) { //如果超速了 则图标要修改
            speed_layout.setBackgroundResource(R.drawable.speed_red);
            speed.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.red));
            speed_title.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.red));
        } else {
            speed_layout.setBackgroundResource(R.drawable.speed_blue);
            speed.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.cblue));
            speed_title.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.cblue));
        }
        // 更新路口转向图标
        nextTurnTipView.setIconType(naviInfo.getIconType());
        /**
         * 更新下一路口 路名及 距离
         */
        textNextRoadName.setText(naviInfo.getNextRoadName());
        textNextRoadDistance.setText(NaviUtil.formatKM(naviInfo.getCurStepRetainDistance()).replace("米", ""));
        /**
         * 绘制转弯的箭头
         */
        drawArrow(naviInfo);
        final AMap aMap = mAMapNaviView.getMap();
        aMap.getUiSettings().setLogoPosition(-100);
        aMap.getUiSettings().setLogoLeftMargin(900);
        aMap.getUiSettings().setLogoBottomMargin(600);
        mTrafficBarView.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);


//        aMap.getUiSettings().setLogoBottomMargin(500);

    }

    @Override
    public void onMapTypeChanged(int i) { //枚举值参考AMap类, 3-黑夜，4-白天
        super.onMapTypeChanged(i);
        isDay = i == 4 ? true : false;
//        if (settingBean == null) { //如果用户没有设置过或者是设置自动，则就取默认的
        setNightImage(i == 4 ? true : false);
//        }
    }

    //设置白天照片
    private void setNightImage(boolean isDay) {
        if (isDay) { //如果是白天
            shangbao_nav.setImageResource(R.mipmap.dmk_fullscreen_trafficreport_day);
            setting_img_nav.setImageResource(R.mipmap.dmk_fullscreen_setting_day);
            quanlan_nav.setImageResource(R.mipmap.dmk_fullscreen_overview_day);
        } else {
            shangbao_nav.setImageResource(R.mipmap.dmk_fullscreen_trafficreport_night);
            setting_img_nav.setImageResource(R.mipmap.dmk_fullscreen_setting_night);
            quanlan_nav.setImageResource(R.mipmap.dmk_fullscreen_overview_night);
        }
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {
        super.updateCameraInfo(aMapCameraInfos);
        if (aMapCameraInfos.length > 0) { //如果有摄像头导航信息
            if (aMapCameraInfos[0].getCameraType() == CameraType.SPEED) { //测速摄像
                Log.e("nxt测速摄像超速", aMapCameraInfos[0].getCameraSpeed() + "");
                carmerSpeed = aMapCameraInfos[0].getCameraSpeed();
            } else {
                carmerSpeed = -1;
                Log.e("nxt其他摄像", aMapCameraInfos[0].getCameraType() + "");
                speed_layout.setBackgroundResource(R.drawable.speed_blue);
                speed.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.cblue));
                speed_title.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.cblue));
            }
        } else {
            carmerSpeed = -1;
            speed_layout.setBackgroundResource(R.drawable.speed_blue);
            speed.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.cblue));
            speed_title.setTextColor(ContextCompat.getColor(RealTimeGPSNaviActivity.this, R.color.cblue));
        }
    }

    public void drawArrow(NaviInfo naviInfo) {
        try {
            if (roadIndex != naviInfo.getCurStep()) {
                List<NaviLatLng> arrow = routeOverLay.getArrowPoints(naviInfo.getCurStep());
                if (routeOverLay != null && arrow != null && arrow.size() > 0) {
                    routeOverLay.drawArrow(arrow);
                    roadIndex = naviInfo.getCurStep();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_nav:
                naviSettingPopup = new NaviSettingPopup(RealTimeGPSNaviActivity.this);
                naviSettingPopup.setNightModel(isDay);
                new XPopup.Builder(RealTimeGPSNaviActivity.this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(naviSettingPopup)
                        .show();
                naviSettingPopup.setOnItemClick(new NaviSettingPopup.OnItemMenuClick() {
                    @Override
                    public void trafficClick(boolean isCheck) {
                        mAMapNaviView.getMap().setTrafficEnabled(isCheck); //显示路况
                    }

                    @Override
                    public void voice(boolean isCheck) {
                        mAMapNavi.setBroadcastMode(isCheck ? 2 : 3);// 1-简洁播报 2-详细播报 3-静音模式
                    }

                    @Override
                    public void xiangbei(String fangxiang) {
                        if ("chetou".equals(settingBean.getFangxiang())) { //车头方向
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAMapNaviView.setNaviMode(AMapNaviView.CAR_UP_MODE);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);
                                }
                            });

                        }
                    }
                });
                break;
            case R.id.navigation_layout:
                trafficReportPopup = new TrafficReportPopup(RealTimeGPSNaviActivity.this);
                trafficReportPopup.setNightModel(isDay);
                new XPopup.Builder(RealTimeGPSNaviActivity.this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(trafficReportPopup)
                        .show();
                break;
            case R.id.close_navi:
                finish();
                break;
            case R.id.quanlan:
                if (isQuanlan) {
                    mAMapNaviView.recoverLockMode();
                    isQuanlan = false;
                    if (isDay) {
                        quanlan_nav.setImageResource(R.mipmap.dmk_fullscreen_overview_day);
                    } else {
                        quanlan_nav.setImageResource(R.mipmap.dmk_fullscreen_overview_night);
                    }
                } else {
                    mAMapNaviView.displayOverview();
                    isQuanlan = true;
                    if (isDay) {
                        quanlan_nav.setImageResource(R.mipmap.guiwei_day);
                    } else {
                        quanlan_nav.setImageResource(R.mipmap.guiwei_night);
                    }
                }
                break;
        }
    }


    //获取导航设置选项
    private void getNavaiSetting() {
        String setting = SPUtil.get(SAVENAVAGI_KET, "").toString();
        if (!TextUtils.isEmpty(setting)) {  //如果没有设置过
            settingBean = new Gson().fromJson(setting, NavigationSettingBean.class);
            if ("chetou".equals(settingBean.getFangxiang())) { //车头方向
                mAMapNaviView.setNaviMode(CAR_UP_MODE);
            } else {
                mAMapNaviView.setNaviMode(NORTH_UP_MODE);
            }
            if ("auto".equals(settingBean.getDayModel())) {
                options.setAutoNaviViewNightMode(true);
            } else if ("night".equals(settingBean.getDayModel())) {
                options.setNaviNight(true); //设置黑夜模式。
            } else {
                options.setAutoNaviViewNightMode(false);
                options.setNaviNight(false); //设置黑夜模式。
            }
            mAMapNaviView.getMap().setTrafficEnabled(settingBean.getTraffic()); //显示路况
            mAMapNavi.setBroadcastMode(settingBean.getVocie() ? 2 : 3);// 1-简洁播报 2-详细播报 3-静音模式
            options.setLeaderLineEnabled(ContextCompat.getColor(this, settingBean.getQianyinLine() ? R.color.red : R.color.trans));
            mTrafficBarView.setVisibility(settingBean.getLukuang() ? View.VISIBLE : View.GONE);
            mAMapNaviView.setViewOptions(options);
        }
    }

    /**
     * ----- start 车道信息的回调 start -------
     */
    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
        mDriveWayView.setVisibility(View.VISIBLE);
        mDriveWayView.buildDriveWay(aMapLaneInfo);

    }

    @Override
    public void hideLaneInfo() {
        mDriveWayView.hide();
    }
    /** ----- ebd 车道信息的回调 end -------*/


}
