package com.android.orangetravel.ui.main;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.android.orangetravel.R;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.base.adapter.baseadapter.BaseCommonAdapter;
import com.android.orangetravel.base.adapter.baseadapter.BaseViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.GridItemDecoration;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.DisplayUtil;
import com.android.orangetravel.base.utils.MapUtils;
import com.android.orangetravel.base.utils.SystemUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.android.orangetravel.base.widgets.GridViewScroll;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.TrafficListBean;
import com.android.orangetravel.ui.mvp.IndexPresenter;
import com.android.orangetravel.ui.mvp.IndexView;
import com.android.orangetravel.ui.widgets.TrafficReportPopup;
import com.android.orangetravel.ui.widgets.citypicker.CityPicker;
import com.android.orangetravel.ui.widgets.citypicker.adapter.OnPickListener;
import com.android.orangetravel.ui.widgets.citypicker.model.City;
import com.android.orangetravel.ui.widgets.citypicker.model.LocatedCity;
import com.android.orangetravel.ui.widgets.utils.navi.NaviUtil;
import com.lxj.xpopup.XPopup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/20
 * 安橙出行
 */

public class OrgangeMapActivity extends BaseActivity<IndexPresenter> implements View.OnClickListener, LocationSource, AMapLocationListener, AMapNaviListener, GeocodeSearch.OnGeocodeSearchListener, DistanceSearch.OnDistanceSearchListener, IndexView {
    @BindView(R.id.orange_map)
    MapView mMapView;
    @BindView(R.id.search_map)
    LinearLayout search_map;
    /*路况*/
    @BindView(R.id.lukuang_img)
    ImageView lukuang_img;
    /*导航分类*/
    @BindView(R.id.luxian_list)
    RecyclerView luxian_list;
    /*导航路线布局*/
    @BindView(R.id.nva_layouot)
    LinearLayout nva_layouot;
    /*地址*/
    @BindView(R.id.address_title)
    TextView address_title;
    /*详细地址*/
    @BindView(R.id.address_details)
    TextView address_details;
    @BindView(R.id.address_distance)
    TextView address_distance;
    /*目的地*/
    @BindView(R.id.search_text)
    TextView search_text;
    /*显示导航布局*/
    @BindView(R.id.public_service_layout)
    CardView public_service_layout;
    /*导航布局*/
    @BindView(R.id.nav_layout)
    LinearLayout nav_layout;
    /*地图放大比例*/
    private int zoomTo = 14;
    /*定位监听*/
    OnLocationChangedListener mListener;
    /*定位*/
    AMapLocationClient mlocationClient;
    /*定位参数*/
    AMapLocationClientOption mLocationOption;
    //获取当前定位
    LatLonPoint startPoint, navEndPoint;
    /*map*/
    private AMap mAmap;
    /*起点标注*/
    private Marker mStartMarker;
    /*终点标注*/
    private Marker mEndMarker;
    /* 导航对象(单例)*/
    private AMapNavi mAMapNavi;
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /* 终点坐标集合［建议就一个终点］*/
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    /*保存当前算好的路线*/
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();
    /*途径点坐标集合*/
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    private NaviLatLng startLatlng;
    private NaviLatLng endLatlng;
    private boolean congestion, cost, hightspeed, avoidhightspeed;
    /*当前用户选中的路线，在下个页面进行导航*/
    private int routeIndex;
    /* 路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线*/
    private int zindex = 1;
    private boolean setTrafficEnabled = true; //路况是否显示
    private CommonAdapter<TrafficListBean> mRvAdapter;
    private List<TrafficListBean> trafficListBeans = new ArrayList<>();
    private int selectRouterPostion = 0;
    private TrafficReportPopup trafficReportPopup;
    /*长按marker*/
    private Marker clickMaker;
    /*你编码对象*/
    private GeocodeSearch geocoderSearch;

    /*选中的线路id*/
    private int selectRoteId = 0;
    /*是否是直接开始导航 */
    private boolean isStartNav = false;

    //地址
    private String address;


    @Override
    public int getLayoutId() {
        return R.layout.activity_orange_map;
    }

    @Override
    public IndexPresenter initPresenter() {
        return new IndexPresenter(this);
    }

    @Override
    public void initView() {
        setSearchlayout();
        setMap();
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    private void setMap() {
        UiSettings mUiSettings;//定义一个UiSettings对象
        mUiSettings = mMapView.getMap().getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mMapView.getMap().setTrafficEnabled(setTrafficEnabled); //显示路况
    }

    private void setSearchlayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                DisplayUtil.dip2px(55));
        params.setMargins(0, SystemUtil.getStatusBarHeight() +
                DisplayUtil.dip2px(10), 0, 0);
        search_map.setLayoutParams(params);
        search_map.setOnClickListener(this);
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<TrafficListBean>(mContext,
                R.layout.item_luxian) {
            @Override
            protected void convert(ViewHolder mHolder, final TrafficListBean item,
                                   final int position) {

                mHolder.setText(R.id.nat_time, DateUtil.getDate(Integer.parseInt(item.getTime())))
                        .setText(R.id.juli, (NaviUtil.formatKM(Integer.parseInt(item.getLength())))).
                        setText(R.id.light, item.getLightSize()).setText(R.id.tips_nav, item.getTitle());
                if (selectRouterPostion == position) {
                    mHolder.setTextColor(R.id.nat_time, ContextCompat.getColor(mContext, R.color.theme_color))
                            .setTextColor(R.id.juli, ContextCompat.getColor(mContext, R.color.theme_color)).
                            setTextColor(R.id.light, ContextCompat.getColor(mContext, R.color.theme_color))
                            .setTextColor(R.id.tips_nav, ContextCompat.getColor(mContext, R.color.theme_color)).setImageResource(R.id.light_image, R.mipmap.dmk_multiroute_trafficlight_press);
                } else {
                    mHolder.setTextColor(R.id.nat_time, ContextCompat.getColor(mContext, R.color.black))
                            .setTextColor(R.id.juli, ContextCompat.getColor(mContext, R.color.gray2)).
                            setTextColor(R.id.light, ContextCompat.getColor(mContext, R.color.gray2))
                            .setTextColor(R.id.tips_nav, ContextCompat.getColor(mContext, R.color.gray1)).setImageResource(R.id.light_image, R.mipmap.dph_fullscreen_trafficlight_day);
                }
            }
        };
        luxian_list.setLayoutManager(new GridLayoutManager(mContext, trafficListBeans.size() > 2 ? 3 : trafficListBeans.size()));
        luxian_list.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                selectRouterPostion = position;
                selectPostion(position);
                mRvAdapter.notifyDataSetChanged();
            }
        });
        selectRouterPostion = 0;
        mRvAdapter.addAllData(trafficListBeans);
    }

    @OnClick(R.id.finish)
    void mapfinish() {
        finish();
    }

    //路况
    @OnClick(R.id.lukuang_img)
    void lukuang_img() {
        mMapView.getMap().setTrafficEnabled(!setTrafficEnabled); //显示路况
        lukuang_img.setImageResource(setTrafficEnabled ? R.mipmap.lukuang_nocheck : R.mipmap.lukuang_check);
        setTrafficEnabled = !setTrafficEnabled;
    }


    /*导航*/
    @OnClick(R.id.navi_btn)
    void navi_btn() {
        isStartNav = true;
        selectRouterPostion = 0;
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
    }

    /*交通上报*/
    @OnClick(R.id.shangbao_traff)
    void shangbao_traff() {
        trafficReportPopup = new TrafficReportPopup(OrgangeMapActivity.this);
        trafficReportPopup.setNightModel(true);
        new XPopup.Builder(OrgangeMapActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .asCustom(trafficReportPopup)
                .show();
        trafficReportPopup.setOnItemClick(new TrafficReportPopup.OnItemMenuClick() {
            @Override
            public void trafficClick(String type) {
                Map map = new HashMap();
                map.put("address", address);
                map.put("type", type);
                map.put("longitude", startPoint.getLatitude());
                map.put("latitude", startPoint.getLongitude());
                getPresenter().traffic(map);
            }
        });
    }

    //查看路线
    @OnClick(R.id.look_line)
    void look_line() {
        if (navEndPoint == null) {
            return;
        }
        isStartNav = false;
        nav_layout.setVisibility(View.VISIBLE);
        routeIndex = 0;
        mEndMarker.remove();
        public_service_layout.setVisibility(View.GONE);
        selectRouterPostion = 0;
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
        search_text.setText(address_title.getText());
    }

    //刷新路线
    @OnClick(R.id.refresh_line)
    void refresh_line() {
        routeIndex = 0;
        selectRouterPostion = 0;
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
    }


    /*开始导航*/
    @OnClick(R.id.start_nav)
    void start_nav() {
        mAMapNavi.selectRouteId(selectRoteId);
        Bundle bundle = new Bundle();
        bundle.putString("type", "GPS");
        gotoActivity(GPSNaviActivity.class, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取地图控件引用
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        // 初始化Marker添加到地图
        mAmap = mMapView.getMap();
        mStartMarker = mAmap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.navi_marker_location))));
        mEndMarker = mAmap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dht_navi_end_point))));
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        showLoacation();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);

    }

    //显示地图
    private void showLoacation() {
        // 设置定位监听
        mMapView.getMap().setLocationSource(this);
//        startLocation();
        mMapView.getMap().setMyLocationEnabled(true);
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
                //点击地图的时候 把长按出现的marker移除
                public_service_layout.setVisibility(View.GONE);
                nav_layout.setVisibility(View.GONE);
                clearRoute();//清除路线
                clickMaker.remove();
            }
        });
        mMapView.getMap().setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            //长按地图显示距离导航过去
            @Override
            public void onMapLongClick(LatLng latLng) {
                mAmap.clear();
                showNaviLayout(latLng);
            }
        });
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

    @Override
    public void requestData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_map:
                chooseCity();
                break;
        }
    }


    private void chooseCity() {
        CityPicker.from((FragmentActivity) mContext)
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(new LocatedCity(Constant.LocationCity, "", ""))
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onClickList(PoiItem poiItem) {
                        routeIndex = 0;
                        selectRouterPostion = 0;
                        LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(poiItem.getLatLonPoint().
                                getLatitude()), Double.valueOf(poiItem.getLatLonPoint().getLongitude()));
                        navEndPoint = latLonPoint;
                        setRouter(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                        search_text.setText(poiItem.getTitle());
                    }

                    @Override
                    public void onClickTop(String city) {

                    }

                    @Override
                    public void onLocate() {
                    }
                })
                .show();
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
                mlocationClient.stopLocation();
                address = amapLocation.getAddress();//获取详细地址
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
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
        mMapView.getMap().setMyLocationEnabled(false);

    }

    private void calculate() {
        showLoadingDialog("正在规划路线");
        clearRoute();
        int strategyFlag = 0;
        try {
//            avoidCongestion - 是否躲避拥堵
//            avoidHighway - 是否不走高速
//            avoidCost - 是否避免收费
//            prioritiseHighway - 是否高速优先
            strategyFlag = mAMapNavi.strategyConvert(congestion, avoidhightspeed,
                    cost, hightspeed, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            mAMapNavi.calculateDriveRoute(endList, wayList, strategyFlag);
        }
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        //清空上次计算的路径列表。
        dismissLoadingDialog();
        LatLngBounds.Builder newbounds = new LatLngBounds.Builder();
        newbounds.include(mStartMarker.getPosition());//通过for循环将所有的轨迹点添加进去.
        newbounds.include(mEndMarker.getPosition());//通过for循环将所有的轨迹点添加进去.
//        mAmap.animateCamera(CameraUpdateFactory.newLatLngBounds(newbounds.build(),
//                DisplayUtil.dip2px(400)));//第二个参数为四周留空宽度.
//        CameraUpdate mCameraUpdate = CameraUpdateFactory.changeLatLng(mStartMarker.getPosition());
        if (!isStartNav) {
            routeOverlays.clear();
            int[] routeIds = aMapCalcRouteResult.getRouteid();
            HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
            for (int i = 0; i < routeIds.length; i++) {
                AMapNaviPath path = paths.get(routeIds[i]);
                for (int i1 = 0; i1 < path.getCoordList().size(); i1++) {
                    newbounds.include(new LatLng(path.getCoordList().get(i1).getLatitude(),
                            path.getCoordList().get(i1).getLongitude()));
                }
                if (path != null) {
                    drawRoutes(routeIds[i], path);
                }
            }
            nva_layouot.setVisibility(View.VISIBLE);
            mAmap.addMarker(new MarkerOptions().position(new LatLng(startPoint.getLatitude(),
                    startPoint.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.navi_marker_location))));
            mAmap.addMarker(new MarkerOptions().position(new LatLng(endLatlng.getLatitude(),
                    endLatlng.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.
                    decodeResource(getResources(), R.mipmap.dht_navi_end_point))));
            if (clickMaker != null) {
                clickMaker.remove();

            }
        }
        changeRoute(); //选择线路
        mMapView.getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(newbounds.build(),
                DisplayUtil.dip2px(100)));
    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
        RouteOverLay routeOverLay = new RouteOverLay(mAmap, path, this);
        routeOverLay.setTrafficLine(true);
        routeOverLay.setLightsVisible(false);
        routeOverLay.showStartMarker(false);
        routeOverLay.showEndMarker(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
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
        dismissLoadingDialog();
        showToast("路线规划失败" + i);

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
//        showToast("路线规划失败");
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

    private void selectPostion(int postion) {
        int routeID = routeOverlays.keyAt(postion);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.3f);
        }
        RouteOverLay routeOverlay = routeOverlays.get(routeID);
        if (routeOverlay != null) {
            routeOverlay.setTransparency(1);
            /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
            routeOverlay.setZindex(postion);
        }
        selectRoteId = routeID;
    }

    public void changeRoute() {
        trafficListBeans.clear();
//         计算出来的路径只有一条
        if (routeOverlays.size() == 1) {
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
            if (isStartNav) { //如果是导航则直接跳转导航
                Bundle bundle = new Bundle();
                bundle.putString("type", "GPS");
                gotoActivity(GPSNaviActivity.class, bundle);
                return;
            }
            trafficListBeans.add(new TrafficListBean((mAMapNavi.getNaviPath()).getAllTime()
                    + "", (mAMapNavi.getNaviPath()).getLabels() + "",
                    (mAMapNavi.getNaviPath().getLightList().size() + ""), (mAMapNavi.getNaviPath().getAllLength() + "")));
            initRv();
            return;
        }

        if (routeIndex >= routeOverlays.size()) {
            routeIndex = 0;
        }
        int routeID = routeOverlays.keyAt(routeIndex);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.3f);
        }
        RouteOverLay routeOverlay = routeOverlays.get(routeID);
        if (routeOverlay != null) {
            routeOverlay.setTransparency(1f);
            /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
            routeOverlay.setZindex(zindex++);
        }
        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
        if (isStartNav) { //如果是导航则直接跳转导航
            Bundle bundle = new Bundle();
            bundle.putString("type", "GPS");
            gotoActivity(GPSNaviActivity.class, bundle);
            return;
        }
        routeIndex++;
        trafficListBeans.clear();
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay mrouteOverlay = routeOverlays.get(routeOverlays.keyAt(i));
            AMapNaviPath path = mrouteOverlay.getAMapNaviPath();
            trafficListBeans.add(new TrafficListBean((path).getAllTime()
                    + "", (path).getLabels() + "",
                    (path.getLightList().size() + ""), (path).getAllLength() + ""));
//            Log.e("nxt", "路线标签:" + path.getLabels()
//                    + "导航距离:" + (path).getAllLength() + "m" + "\n" + "导航时间:" + (path).getAllTime()
//                    + "红绿灯" + (path).getLightList().size());
        }
        initRv();
    }


    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), "路线规划失败" + aMapCalcRouteResult.getErrorCode(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }

    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        startList.clear();
//        wayList.clear();
//        endList.clear();
//        routeOverlays.clear();
//        mRouteMapView.onDestroy();
        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) { //通过经纬度得到的地址
            dismissLoadingDialog();
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getNeighborhood());
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getBuilding());
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getDistrict());
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getBusinessAreas());
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getPois());
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getRoads());
                LogUtil.e("逆地理编码回调  得到的地址：" + result.getRegeocodeAddress().getTownship());
                address_details.setText(result.getRegeocodeAddress().getFormatAddress());
                public_service_layout.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(result.getRegeocodeAddress().getTownship())) {
                    try {
                        String[] title = result.getRegeocodeAddress().getFormatAddress().split(result.getRegeocodeAddress().getTownship());
                        if (title.length > 0) {
                            address_title.setText(title[1]);
                        }
                    } catch (Exception e) {

                    }

                }


            }
        }
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
            address_distance.setText(dis + "公里");
        }
    }

    @Override
    public void index(ContactBean bean) {
        trafficReportPopup.dismiss();
        showToast("上报成功,感谢您的反馈");
    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
