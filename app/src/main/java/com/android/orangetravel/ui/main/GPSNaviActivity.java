package com.android.orangetravel.ui.main;

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
import com.amap.api.navi.enums.MapStyle;
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
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.statusBar.YangStatusBar;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.bean.NaviIntentBean;
import com.android.orangetravel.bean.NavigationSettingBean;
import com.android.orangetravel.ui.widgets.NaviSettingPopup;
import com.android.orangetravel.ui.widgets.TrafficReportPopup;
import com.android.orangetravel.ui.widgets.utils.navi.NaviUtil;
import com.android.orangetravel.ui.widgets.view.DriveWayLinear;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

import static com.amap.api.navi.AMapNaviView.CAR_UP_MODE;
import static com.amap.api.navi.AMapNaviView.NORTH_UP_MODE;
import static com.android.orangetravel.ui.main.NavigationSettingActivity.SAVENAVAGI_KET;

public class GPSNaviActivity extends BaseNaviActivity implements AMapNaviListener, View.OnClickListener {
    private TextView textNextRoadDistance;
    private TextView textNextRoadName;
    private NextTurnTipView nextTurnTipView;
    private TextView RetainDistance, RetainTime;
    private TrafficProgressBar mTrafficBarView;
    /*????????????*/
    TextView speed;
    /*??????????????????*/
    TextView speed_title;
    /**
     * ??????Overlay
     */
    RouteOverLay routeOverLay;
    /**
     * ??????????????????
     */
    private int roadIndex;

    boolean isQuanlan = false;

    private NaviIntentBean naviStartIntentBean;
    private NaviIntentBean naviStopIntentBean;
    /*????????????*/
    private CardView setting_nav;
    /*????????????*/
    private CardView navigation_layout;
    /*???????????????????????????*/
    private NaviSettingPopup naviSettingPopup;
    /*???????????????????????????*/
    private TrafficReportPopup trafficReportPopup;
    /*??????????????????*/
    private NavigationSettingBean settingBean;
    /*??????????????????*/
    ImageView shangbao_nav;
    /*??????*/
    ImageView setting_img_nav;
    /*??????*/
    ImageView quanlan_nav;
    /*?????????*/
    LinearLayout speed_layout;
    /*?????????????????????*/
    boolean isDay = true;
    /*????????????*/
    private DriveWayLinear mDriveWayView;
    AMapNaviViewOptions options;
    private String type = "";//????????????
    private int carmerSpeed = -1; //???????????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YangStatusBar.translucentStatusBar(this, true);
        setContentView(R.layout.activity_basic_navi);
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
        mAMapNaviView.setAMapNaviViewListener(this);
        options = new AMapNaviViewOptions();
        options.setLayoutVisible(false);
        options.setScreenAlwaysBright(false);
        options.setAutoNaviViewNightMode(true);
//        options.setNaviNight(true); //?????????????????????
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
         * ?????????????????????
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
        Log.e("nxt", "??????");
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
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * ??????: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost,
         * hightspeed, multipleroute); ??????:
         *
         * @congestion ????????????
         * @avoidhightspeed ????????????
         * @cost ????????????
         * @hightspeed ????????????
         * @multipleroute ?????????
         *
         *  ??????: ??????????????????boolean???????????????multipleroute??????????????????????????????????????????true????????????????????????????????????
         *  ??????: ??????????????????????????????????????????true ??????????????????????????????????????????true
         */
        int strategy = 0;
        try {
            //????????????????????????????????????true??????????????????????????????????????????
            strategy = mAMapNavi.strategyConvert(true, false, false,
                    false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AMapCarInfo carInfo = new AMapCarInfo();
//        carInfo.setCarNumber("???DFZ588");
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
        //?????????????????????????????????
//        mAMapNavi.setEmulatorNaviSpeed(75);
    }


    /**
     * ------- ??????????????????????????? -----
     */
    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        super.onNaviInfoUpdate(naviInfo);
        int allLength = mAMapNavi.getNaviPath().getAllLength();
        // ??????????????? ??????
        List<AMapTrafficStatus> trafficStatuses = mAMapNavi.getTrafficStatuses(0, 0);
        RetainDistance.setText(NaviUtil.formatKM(naviInfo.getPathRetainDistance()));
        RetainTime.setText(DateUtil.getDate(naviInfo.getPathRetainTime()) + "");
        Log.e("????????????????????????", NaviUtil.formatKM(naviInfo.getPathRetainDistance())
                + "????????????????????????" + naviInfo.getPathRetainTime() + "--" + naviInfo.getCurrentSpeed());
//        mTrafficBarView.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);
        speed.setText(naviInfo.getCurrentSpeed() + "");
        if (carmerSpeed > 0 && naviInfo.getCurrentSpeed() > carmerSpeed) { //??????????????? ??????????????????
            speed_layout.setBackgroundResource(R.drawable.speed_red);
            speed.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.red));
            speed_title.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.red));
        } else {
            speed_layout.setBackgroundResource(R.drawable.speed_blue);
            speed.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.cblue));
            speed_title.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.cblue));
        }
        // ????????????????????????
        nextTurnTipView.setIconType(naviInfo.getIconType());
        /**
         * ?????????????????? ????????? ??????
         */
        textNextRoadName.setText(naviInfo.getNextRoadName());
        textNextRoadDistance.setText(NaviUtil.formatKM(naviInfo.getCurStepRetainDistance()).replace("???", ""));
        /**
         * ?????????????????????
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
    public void onMapTypeChanged(int i) { //???????????????AMap???, 3-?????????4-??????
        super.onMapTypeChanged(i);
        isDay = i == 4 ? true : false;
//        if (settingBean == null) { //?????????????????????????????????????????????????????????????????????
        setNightImage(i == 4 ? true : false);
//        }
    }

    //??????????????????
    private void setNightImage(boolean isDay) {
        if (isDay) { //???????????????
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
        if (aMapCameraInfos.length > 0) { //??????????????????????????????
            if (aMapCameraInfos[0].getCameraType() == CameraType.SPEED) { //????????????
                Log.e("nxt??????????????????", aMapCameraInfos[0].getCameraSpeed() + "");
                carmerSpeed = aMapCameraInfos[0].getCameraSpeed();
            } else {
                carmerSpeed = -1;
                Log.e("nxt????????????", aMapCameraInfos[0].getCameraType() + "");
                speed_layout.setBackgroundResource(R.drawable.speed_blue);
                speed.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.cblue));
                speed_title.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.cblue));
            }
        } else {
            carmerSpeed = -1;
            speed_layout.setBackgroundResource(R.drawable.speed_blue);
            speed.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.cblue));
            speed_title.setTextColor(ContextCompat.getColor(GPSNaviActivity.this, R.color.cblue));
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
                naviSettingPopup = new NaviSettingPopup(GPSNaviActivity.this);
                naviSettingPopup.setNightModel(isDay);
                new XPopup.Builder(GPSNaviActivity.this)
                        .moveUpToKeyboard(false) //????????????????????????????????????????????????????????????
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                        .asCustom(naviSettingPopup)
                        .show();
                naviSettingPopup.setOnItemClick(new NaviSettingPopup.OnItemMenuClick() {
                    @Override
                    public void trafficClick(boolean isCheck) {
                        mAMapNaviView.getMap().setTrafficEnabled(isCheck); //????????????
                    }

                    @Override
                    public void voice(boolean isCheck) {
                        if (isCheck) {
                            mAMapNavi.startSpeak();
                        } else {
                            mAMapNavi.stopSpeak();
                        }

                    }

                    @Override
                    public void xiangbei(String fangxiang) {
                        Log.e("mt", fangxiang);
                        if ("chetou".equals(fangxiang)) { //????????????
                            mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);
                        } else {
                            mAMapNaviView.setNaviMode(AMapNaviView.CAR_UP_MODE);
                        }
                    }
                });
                break;
            case R.id.navigation_layout:
                trafficReportPopup = new TrafficReportPopup(GPSNaviActivity.this);
                trafficReportPopup.setNightModel(isDay);
                new XPopup.Builder(GPSNaviActivity.this)
                        .moveUpToKeyboard(false) //????????????????????????????????????????????????????????????
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
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


    //????????????????????????
    private void getNavaiSetting() {
        String setting = SPUtil.get(SAVENAVAGI_KET, "").toString();
        if (!TextUtils.isEmpty(setting)) {  //?????????????????????
            settingBean = new Gson().fromJson(setting, NavigationSettingBean.class);
            if ("chetou".equals(settingBean.getFangxiang())) { //????????????
                mAMapNaviView.setNaviMode(CAR_UP_MODE);
            } else {
                mAMapNaviView.setNaviMode(NORTH_UP_MODE);
            }
            if ("auto".equals(settingBean.getDayModel())) {
                options.setMapStyle(MapStyle.AUTO,"");
//                options.setAutoNaviViewNightMode(true);
            } else if ("night".equals(settingBean.getDayModel())) {
//                options.setNaviNight(true); //?????????????????????
                options.setMapStyle(MapStyle.NIGHT,"");
            } else {
//                options.setAutoNaviViewNightMode(false);
                options.setMapStyle(MapStyle.DAY,"");
//                options.setNaviNight(false); //?????????????????????
            }
            mAMapNaviView.getMap().setTrafficEnabled(settingBean.getTraffic()); //????????????
            mAMapNavi.setBroadcastMode(settingBean.getVocie() ? 2 : 3);// 1-???????????? 2-???????????? 3-????????????
            options.setLeaderLineEnabled(ContextCompat.getColor(this, settingBean.getQianyinLine() ? R.color.red : R.color.trans));
            mTrafficBarView.setVisibility(settingBean.getLukuang() ? View.VISIBLE : View.GONE);
            mAMapNaviView.setViewOptions(options);
        }
    }

    /**
     * ----- start ????????????????????? start -------
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
    /** ----- ebd ????????????????????? end -------*/


}
