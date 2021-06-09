package com.android.orangetravel;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.AnimationSet;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.base.annotation.BindEventBus;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.rx.AccountFreezeEvent;
import com.android.orangetravel.base.utils.MapUtils;
import com.android.orangetravel.base.utils.UserBiz;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.ui.main.GPSNaviActivity;
import com.android.orangetravel.ui.main.LoginActivity;
import com.android.orangetravel.ui.main.MineActivity;
import com.android.orangetravel.ui.main.MyMessageActivity;
import com.android.orangetravel.ui.main.MyTripActivity;
import com.android.orangetravel.ui.main.TestActivity;
import com.android.orangetravel.ui.mvp.IndexPresenter;
import com.android.orangetravel.ui.mvp.IndexView;
import com.android.orangetravel.ui.widgets.IndexAroundPopup;
import com.android.orangetravel.ui.widgets.ReceivingOrdersPopup;
import com.android.orangetravel.ui.widgets.SettingModelPopup;
import com.android.orangetravel.ui.widgets.utils.CommonUtil;
import com.android.orangetravel.ui.widgets.utils.tts.TTSController;
import com.lxj.xpopup.XPopup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.Annotation;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 *
 * @author xiongwenzhi
 */
@BindEventBus
public class MainActivity extends BaseActivity<IndexPresenter> implements LocationSource,
        AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener,
        DistanceSearch.OnDistanceSearchListener,
        PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener, AMapNaviListener, IndexView {
    @BindView(R.id.id_title_bar)
    YangTitleBar titleBar;
    @BindView(R.id.map)
    MapView mMapView;
    /*每日消息*/
    @BindView(R.id.card_close__layout)
    LinearLayout card_close__layout;
    /*司机日报*/
    @BindView(R.id.daily_layout)
    CardView daily_layout;
    /*导航界面*/
    @BindView(R.id.navigation_layout)
    CardView navigation_layout;
    /*距离*/
    @BindView(R.id.distance)
    TextView distance_tv;
    /*耗时*/
    @BindView(R.id.timeconsuming)
    TextView timeconsuming;
    /*公共服务界面*/
    @BindView(R.id.public_service_layout)
    CardView public_service_layout;
    /*公共服务距离*/
    @BindView(R.id.public_service_distance)
    TextView public_service_distance;
    /*公共服务耗时*/
    @BindView(R.id.public_service_timeconsuming)
    TextView public_service_timeconsuming;
    /*公共服务 显示当前位置*/
    @BindView(R.id.public_service_daohang)
    TextView public_service_daohang;
    /*显示当前位置*/
    @BindView(R.id.address_daohang)
    TextView address_daohang;
    /*公众服务具体地址*/
    @BindView(R.id.service_address)
    TextView service_address;
    /*导航按钮*/
    @BindView(R.id.navi_btn)
    TextView navi_btn;
    /*出车*/
    @BindView(R.id.Driving)
    TextView Driving;
    /*收车*/
    @BindView(R.id.shouche)
    LinearLayout shouche;
    /*流水*/
    @BindView(R.id.sumWater)
    TextView sumWater;
    /*已接订单*/
    @BindView(R.id.sumOrder)
    TextView sumOrder;
    //定位样式
    private MyLocationStyle myLocationStyle;
    //获取当前定位
    LatLonPoint startPoint, navEndPoint;
    /*定位监听*/
    OnLocationChangedListener mListener;
    /*定位*/
    AMapLocationClient mlocationClient;
    /*定位参数*/
    AMapLocationClientOption mLocationOption;
    /*地图放大比例*/
    private int zoomTo = 14;
    /*你编码对象*/
    private GeocodeSearch geocoderSearch;
    /*周边底部弹出框*/
    private IndexAroundPopup indexAroundPopup;
    /*设置模式底部弹出框*/
    private SettingModelPopup settingModelPopup;
    /*长按marker*/
    private Marker clickMaker;
    /*附近所有makrer*/
    private List<Marker> GasstationMarker = new ArrayList<>();
    /*是否是显示附近的厕所 否则显示附近的加油站*/
    private boolean isShowWc;
    /*判断是否显示公共服务还是显示具体位置*/
    private boolean isShowPublicService;
    private Marker breatheMarker;// 定位雷达小图标
    private NaviLatLng startLatlng, endLatlng;
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /* 终点坐标集合［建议就一个终点］*/
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    /*途径点坐标集合*/
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    /* 导航对象(单例)*/
    private AMapNavi mAMapNavi;
    private long mSaveTime;
    protected TTSController mTtsManager;

    private android.view.animation.ScaleAnimation scaleAnimation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取地图控件引用
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public IndexPresenter initPresenter() {
        return new IndexPresenter(this);
    }

    @Override
    public void initView() {
        setTitleBar(getString(R.string.app_name));
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoActivity(TestActivity.class);
            }
        });
        XPopup.setPrimaryColor(getResources().getColor(R.color.theme_color));
        titleBar.setLeftIcon(R.mipmap.xtabprofile);
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        setmYangStatusBar(ContextCompat.getColor(MainActivity.this, R.color.title_bar_black));
        titleBar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.title_bar_black));
        titleBar.setRightIcon(R.mipmap.xtabward);
        titleBar.setRightIconVisible(true);
        titleBar.setLeftRedYuan(View.GONE);

        titleBar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(MineActivity.class);
            }
        });
        titleBar.setRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(MyMessageActivity.class);
            }
        });
        showLoacation();
        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();
        mTtsManager.setTTSType(TTSController.TTSType.SYSTEMTTS);
        initGPS();
    }

    //显示地图
    private void showLoacation() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // 设置定位监听
        mMapView.getMap().setLocationSource(this);

//        startLocation();
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        mMapView.getMap().setMyLocationEnabled(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.heatmap_driver_annotation));
        myLocationStyle.strokeColor(ContextCompat.getColor(mContext, R.color.trans));//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(ContextCompat.getColor(mContext, R.color.trans));//设置定位蓝点精度圆圈的填充颜色的方法。
        mMapView.getMap().setMyLocationStyle(myLocationStyle);
        UiSettings mUiSettings;//定义一个UiSettings对象
        mMapView.getMap().setTrafficEnabled(true); //显示路况
        mUiSettings = mMapView.getMap().getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo
        //设置希望展示的地图缩放级别
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(zoomTo);
        mMapView.getMap().moveCamera(mCameraUpdate);
        mMapView.getMap().setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Animation annotation = AnimationUtils.loadAnimation(mContext, R.anim.navigator_sign_out_layout);
                //点击地图的时候 把长按出现的marker移除
                if (navigation_layout.getVisibility() == View.VISIBLE) {
                    navigation_layout.startAnimation(annotation);
                }
                if (daily_layout.getVisibility() == View.VISIBLE) {
                    daily_layout.startAnimation(annotation);
                }
                if (public_service_layout.getVisibility() == View.VISIBLE) {
                    public_service_layout.startAnimation(annotation);
                }
                if (card_close__layout.getVisibility() == View.VISIBLE) {
                    card_close__layout.startAnimation(annotation);
                }
                annotation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        navigation_layout.setVisibility(View.GONE);
                        daily_layout.setVisibility(View.GONE);
                        public_service_layout.setVisibility(View.GONE);
                        card_close__layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                clickMaker.remove();
            }
        });
        mMapView.getMap().setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            //长按地图显示距离导航过去
            @Override
            public void onMapLongClick(LatLng latLng) {
                isShowPublicService = false;
                showNaviLayout(latLng);
            }
        });
    }


    private void showAnim(LatLng locll) {
        if (breatheMarker != null) {
            breatheMarker.remove();
        }
        // 呼吸动画
        breatheMarker = mMapView.getMap().addMarker(new MarkerOptions().position(locll).zIndex(1).
                anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.yuan)));
        // 中心的marker
        // 动画执行完成后，默认会保持到最后一帧的状态
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 0f);
        alphaAnimation.setDuration(2000);
        // 设置不断重复
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 3.5f, 1, 3.5f);
        scaleAnimation.setDuration(2000);
        // 设置不断重复
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setInterpolator(new LinearInterpolator());
        breatheMarker.setAnimation(animationSet);
        breatheMarker.startAnimation();
    }


    private void showNaviLayout(LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.long_click_marker)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//                markerOption.setFlat(true);//设置marker平贴地图效果
        if (clickMaker != null) {
            clickMaker.remove();
        }
        LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(latLng.latitude), Double.valueOf(latLng.longitude));
        navEndPoint = latLonPoint;
        clickMaker = mMapView.getMap().addMarker(markerOption);
        getAddress(latLng);
        mMapView.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomTo));
    }

    /**
     * 根据经纬度得到地址
     */
    public void getAddress(final LatLng latLonPoint) {
        showLoadingDialog();
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(MapUtils.convertToLatLonPoint(latLonPoint), 500, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
        //下面的是通过两个位置计算距离 并且算出驾车时间
        DistanceSearch.DistanceQuery distanceQuery = new DistanceSearch.DistanceQuery();
        DistanceSearch distanceSearch = new DistanceSearch(this);
        LatLonPoint dest = new LatLonPoint(Double.valueOf(latLonPoint.latitude), Double.valueOf(latLonPoint.longitude));
        List<LatLonPoint> latLonPoints = new ArrayList<LatLonPoint>();
        latLonPoints.add(startPoint);
        distanceQuery.setOrigins(latLonPoints);
        distanceQuery.setDestination(dest);
//        设置测量方式，支持直线和驾车
        distanceQuery.setType(DistanceSearch.TYPE_DRIVING_DISTANCE);
        distanceSearch.calculateRouteDistanceAsyn(distanceQuery);
        distanceSearch.setDistanceSearchListener(this);
    }


    //回到原点
    @OnClick(R.id.location)
    void location() {
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mMapView.getMap().setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        mMapView.getMap().setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        mMapView.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
    }

    /*我的行程*/
    @OnClick(R.id.my_trip)
    void my_trip() {
        gotoActivity(MyTripActivity.class);
    }


    //周边
    @OnClick(R.id.around)
    void around() {
        if (indexAroundPopup == null) {
            indexAroundPopup = new IndexAroundPopup(MainActivity.this);
        }
        new XPopup.Builder(MainActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                .asCustom(indexAroundPopup/*.enableDrag(false)*/)
                .show();
        indexAroundPopup.setOnItemClick(new IndexAroundPopup.OnItemMenuClick() {
            @Override
            public void trafficClick(boolean isCheck) {
                //判断路况图层是否显示
                mMapView.getMap().setTrafficEnabled(!isCheck);
            }

            @Override
            public void satelliteClick(boolean isCheck) {
                if (!isCheck) {
                    mMapView.getMap().setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                } else {
                    mMapView.getMap().setMapType(AMap.MAP_TYPE_NORMAL);
                }
            }

            @Override
            public void chooseMenu(boolean isCheckWc) {
                isShowWc = isCheckWc;
                if (GasstationMarker.size() > 0) {
                    for (int i = 0; i < GasstationMarker.size(); i++) {
                        GasstationMarker.get(i).remove();
                    }
                }
                //是否选择显示附近厕所
                getNearByWc();
            }

            @Override
            public void chooseAddress(PoiItem clickAddress) {
                showNaviLayout(new LatLng(clickAddress.getLatLonPoint().getLatitude(), clickAddress.getLatLonPoint().getLongitude()));
            }
        });
    }

    //设置模式
    @OnClick(R.id.model)
    void model() {
        mTtsManager.playText("设置模式");
        if (settingModelPopup == null) {
            settingModelPopup = new SettingModelPopup(MainActivity.this);
        }
        new XPopup.Builder(MainActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                .asCustom(settingModelPopup/*.enableDrag(false)*/)
                .show();
    }

    //出车
    @OnClick(R.id.Driving)
    void Driving() {
        scaleAnimation = CommonUtil.startAnim(Driving);
        Driving.setText("听单中");
        shouche.setVisibility(View.VISIBLE);
        mTtsManager.playText("您已开启出车，现在可以听单了");
//        您已开启出车，现在可以听单了
    }

    //收车
    @OnClick(R.id.shouche)
    void shouche() {
        PromptDialog dialog = new PromptDialog(MainActivity.this,
                "设置收车状态将不再接到订单，请谨慎选择");
        dialog.setTitle("收车警告提示");
        dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
            @Override
            public void onClick() {
                shouche.setVisibility(View.INVISIBLE);
                Driving.setText("出车");
                mTtsManager.playText("您已收车");
                if (scaleAnimation != null) {
                    scaleAnimation.cancel();
                }
            }
        });
        dialog.setOnClickCancelListener(new PromptDialog.OnClickCancelListener() {
            @Override
            public void onClick() {
                ReceivingOrdersPopup ordersPopup = new ReceivingOrdersPopup(MainActivity.this);
                new XPopup.Builder(MainActivity.this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(ordersPopup)
                        .show();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.card_close_image)
    void card_close_image() {
        card_close__layout.setVisibility(View.GONE);
    }

    @OnClick(R.id.daily_dismiss)
    void daily_dismiss() {
        Animation annotation = AnimationUtils.loadAnimation(mContext, R.anim.navigator_sign_out_layout);
        daily_layout.startAnimation(annotation);
        annotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                daily_layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @OnClick(R.id.card_close_image_dh)
    void card_close_image_dh() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.navigator_sign_out_layout);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navigation_layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        navigation_layout.startAnimation(animation);
    }

    @OnClick(R.id.public_service_image_dh)
    void public_service_image_dh() {
        public_service_layout.setVisibility(View.GONE);
    }

    @OnClick(R.id.navi_btn)
    void navi_btn() {
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
    }

    private void setRouter(double Latitude, double Longitude) {
        if (startPoint == null) {
            return;
        }
        startLatlng = new NaviLatLng(startPoint.getLatitude(), startPoint.getLongitude());
        startList.clear();
        startList.add(startLatlng);
        endLatlng = new NaviLatLng(Latitude, Longitude);
        endList.clear();
        endList.add(endLatlng);
        calculate(); //规划路线
//        mMapView.getMap().setMyLocationEnabled(false);

    }


    private void calculate() {
        int strategyFlag = 0;
        try {
//            avoidCongestion - 是否躲避拥堵
//            avoidHighway - 是否不走高速
//            avoidCost - 是否避免收费
//            prioritiseHighway - 是否高速优先
            strategyFlag = mAMapNavi.strategyConvert(false, false,
                    false, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            mAMapNavi.calculateDriveRoute(endList, wayList, 10);
        }
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        int[] routeIds = aMapCalcRouteResult.getRouteid();
        changeRoute(routeIds);
    }

    //选择线路导航
    public void changeRoute(int[] routeIds) {
        if (routeIds.length > 0) {
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeIds[0]);
            Bundle bundle = new Bundle();
            bundle.putString("type", "GPS");
            gotoActivity(GPSNaviActivity.class, bundle);
            return;
        }
    }

    //获取附近的厕所 和加油站
    private void getNearByWc() {
        PoiSearch.Query query = new PoiSearch.Query("", isShowWc ? "Public Toilet" : "Filling Station", "南昌");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(100);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(startPoint, 10000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void requestData() {
        getPresenter().index();
        getPresenter().daily("南昌", "2021-04-02");//司机日报
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        if (mAMapNavi != null) {
            mAMapNavi.addAMapNaviListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
        if (mAMapNavi != null) {
            mAMapNavi.removeAMapNaviListener(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);

    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
            LogUtil.e("开始定位");

        } else {
            mlocationClient.startLocation();
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                startPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Constant.LocationCity = amapLocation.getCity();
                Constant.locationLatLonPoint = startPoint;
//                showToast(Constant.LocationCity);
                mlocationClient.stopLocation();
                showAnim(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) { //通过经纬度得到的地址
            dismissLoadingDialog();
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getFormatAddress());
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.navigator_into_layout);
                if (isShowPublicService) {
                    public_service_layout.setVisibility(View.VISIBLE);
                    public_service_layout.startAnimation(animation);
                    navigation_layout.setVisibility(View.GONE);
                    public_service_daohang.setVisibility(View.VISIBLE);
                    public_service_daohang.setText("公众服务");
                    service_address.setText(result.getRegeocodeAddress().getFormatAddress());
                } else {
                    if(navigation_layout.getVisibility()==View.GONE){
                        navigation_layout.startAnimation(animation);
                        navigation_layout.setVisibility(View.VISIBLE);
                    }
                    public_service_layout.setVisibility(View.GONE);
                    public_service_daohang.setVisibility(View.GONE);
                    address_daohang.setText(result.getRegeocodeAddress().getFormatAddress());
                }

            }
        }
    }

    //公众服务导航
    @OnClick(R.id.navi_service)
    void navi_service() {
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int i) {
        Log.d("距离", "onDistanceSearched: " + i);
        if (i == 1000) {
            String time_string;
            //距离米
//            String distance =  distanceResult.getDistanceResults().get(0).getDistance() / 1000 + "";
            long second = (long) distanceResult.getDistanceResults().get(0).getDuration();
            long days = second / 86400;            //转换天数
            second = second % 86400;            //剩余秒数
            long hours = second / 3600;            //转换小时
            second = second % 3600;                //剩余秒数
            long minutes = second / 60;            //转换分钟
            second = second % 60;

            if (days > 0) {
                time_string = days + "天" + hours + "小时" + minutes + "分钟";
            } else if (hours > 0) {
                time_string = hours + "小时" + minutes + "分钟";
            } else {
                time_string = minutes + "分钟";
            }
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String dis = fnum.format(distanceResult.getDistanceResults().get(0).getDistance() / 1000);
            distance_tv.setText("距您" + dis + "公里");
            SpannableString spannableString = new SpannableString(distance_tv.getText());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5781c1")),
                    2, spannableString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (isShowPublicService) { //如果是公共服务类
                public_service_distance.setText(spannableString);
            } else {
                distance_tv.setText(spannableString);
            }
            timeconsuming.setText("预计耗时:" + time_string);
            SpannableString spannableTimeString = new SpannableString(timeconsuming.getText());
            spannableTimeString.setSpan(new ForegroundColorSpan(Color.parseColor("#5781c1")),
                    5, spannableTimeString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (isShowPublicService) { //如果是公共服务类
                public_service_timeconsuming.setText(spannableTimeString);
            } else {
                timeconsuming.setText(spannableTimeString);
            }

        } else {
            distance_tv.setText("暂无定位信息");
        }
    }

    /**
     * marker点击事件
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!"厕所".equals(marker.getTitle()) && !"加油站".equals(marker.getTitle())) {
            return true;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.map_custom_click_marker, null);
        ImageView type_image_marker = view.findViewById(R.id.type_image_marker);
        type_image_marker.setImageResource("厕所".equals(marker.getTitle()) ?
                R.mipmap.marker_click_wc : R.mipmap.marker_click_jiayou);
        marker.setIcon(BitmapDescriptorFactory.fromView(view));
        isShowPublicService = true;
        LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(marker.getPosition().latitude), Double.valueOf(marker.getPosition().longitude));
        navEndPoint = latLonPoint;
        getAddress(marker.getPosition());
        mMapView.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), zoomTo));
        //点击当前marker展示自定义窗体
//        marker.showInfoWindow();
        //返回true 表示接口已响应事,无需继续传递
        return true;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //解析result获取POI信息
        LogUtil.e(poiResult.getPois().size() + "");
        GasstationMarker.clear();
        for (int i1 = 0; i1 < poiResult.getPois().size(); i1++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(poiResult.getPois().get(i1).getLatLonPoint().getLatitude(),
                    poiResult.getPois().get(i1).getLatLonPoint().getLongitude()));
            markerOptions.visible(true);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
                    isShowWc ? R.mipmap.marker_wc : R.mipmap.marker_jiayou));
            markerOptions.icon(bitmapDescriptor);
            markerOptions.title(isShowWc ? "厕所" : "加油站");
            Marker marker = mMapView.getMap().addMarker(markerOptions);
            GasstationMarker.add(marker);
            //设置marker点击事件监听
            mMapView.getMap().setOnMarkerClickListener(this);
            LogUtil.e(poiResult.getPois().get(i1).getLatLonPoint() + "" + poiResult.getPois().get(i1).getTitle());
        }
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {
        showToast("失败了");
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 帐户冻结事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountFreezeEvent(AccountFreezeEvent event) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - mSaveTime) > 1000) {
            mSaveTime = currentTime;
            // 退出至主界面
            gotoActivity(MainActivity.class);
            gotoActivity(LoginActivity.class);
            // 退出登录
            UserBiz.exitLogin(mContext);
        }
    }


    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void index(ContactBean bean) {
        titleBar.setRightRedYuan(bean.getMessageCount() > 0 ? View.VISIBLE : View.GONE);
        sumWater.setText(bean.getSumWater());
        sumOrder.setText(bean.getSumOrder());
    }

    LocationManager locationManager;

    private void initGPS() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //判断是否开启了GPS
        boolean ok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //若开启，申请权限
        if (ok) {
        } else {
            //若未开启，弹框让用户选择去开
            PromptDialog dialog = new PromptDialog(MainActivity.this,
                    "未开启位置信息，是否前往开启");
            dialog.setTitle("获取位置失败");
            dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
                @Override
                public void onClick() {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 1);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

}