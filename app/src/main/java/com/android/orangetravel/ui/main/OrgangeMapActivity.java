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
 * ????????????
 */

public class OrgangeMapActivity extends BaseActivity<IndexPresenter> implements View.OnClickListener, LocationSource, AMapLocationListener, AMapNaviListener, GeocodeSearch.OnGeocodeSearchListener, DistanceSearch.OnDistanceSearchListener, IndexView {
    @BindView(R.id.orange_map)
    MapView mMapView;
    @BindView(R.id.search_map)
    LinearLayout search_map;
    /*??????*/
    @BindView(R.id.lukuang_img)
    ImageView lukuang_img;
    /*????????????*/
    @BindView(R.id.luxian_list)
    RecyclerView luxian_list;
    /*??????????????????*/
    @BindView(R.id.nva_layouot)
    LinearLayout nva_layouot;
    /*??????*/
    @BindView(R.id.address_title)
    TextView address_title;
    /*????????????*/
    @BindView(R.id.address_details)
    TextView address_details;
    @BindView(R.id.address_distance)
    TextView address_distance;
    /*?????????*/
    @BindView(R.id.search_text)
    TextView search_text;
    /*??????????????????*/
    @BindView(R.id.public_service_layout)
    CardView public_service_layout;
    /*????????????*/
    @BindView(R.id.nav_layout)
    LinearLayout nav_layout;
    /*??????????????????*/
    private int zoomTo = 14;
    /*????????????*/
    OnLocationChangedListener mListener;
    /*??????*/
    AMapLocationClient mlocationClient;
    /*????????????*/
    AMapLocationClientOption mLocationOption;
    //??????????????????
    LatLonPoint startPoint, navEndPoint;
    /*map*/
    private AMap mAmap;
    /*????????????*/
    private Marker mStartMarker;
    /*????????????*/
    private Marker mEndMarker;
    /* ????????????(??????)*/
    private AMapNavi mAMapNavi;
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /* ?????????????????????????????????????????????*/
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    /*???????????????????????????*/
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();
    /*?????????????????????*/
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    private NaviLatLng startLatlng;
    private NaviLatLng endLatlng;
    private boolean congestion, cost, hightspeed, avoidhightspeed;
    /*?????????????????????????????????????????????????????????*/
    private int routeIndex;
    /* ???????????????????????????????????????????????????????????????????????????????????????*/
    private int zindex = 1;
    private boolean setTrafficEnabled = true; //??????????????????
    private CommonAdapter<TrafficListBean> mRvAdapter;
    private List<TrafficListBean> trafficListBeans = new ArrayList<>();
    private int selectRouterPostion = 0;
    private TrafficReportPopup trafficReportPopup;
    /*??????marker*/
    private Marker clickMaker;
    /*???????????????*/
    private GeocodeSearch geocoderSearch;

    /*???????????????id*/
    private int selectRoteId = 0;
    /*??????????????????????????? */
    private boolean isStartNav = false;

    //??????
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
        UiSettings mUiSettings;//????????????UiSettings??????
        mUiSettings = mMapView.getMap().getUiSettings();//?????????UiSettings?????????
        mUiSettings.setZoomControlsEnabled(false);
        mMapView.getMap().setTrafficEnabled(setTrafficEnabled); //????????????
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
     * ?????????Rv
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

    //??????
    @OnClick(R.id.lukuang_img)
    void lukuang_img() {
        mMapView.getMap().setTrafficEnabled(!setTrafficEnabled); //????????????
        lukuang_img.setImageResource(setTrafficEnabled ? R.mipmap.lukuang_nocheck : R.mipmap.lukuang_check);
        setTrafficEnabled = !setTrafficEnabled;
    }


    /*??????*/
    @OnClick(R.id.navi_btn)
    void navi_btn() {
        isStartNav = true;
        selectRouterPostion = 0;
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
    }

    /*????????????*/
    @OnClick(R.id.shangbao_traff)
    void shangbao_traff() {
        trafficReportPopup = new TrafficReportPopup(OrgangeMapActivity.this);
        trafficReportPopup.setNightModel(true);
        new XPopup.Builder(OrgangeMapActivity.this)
                .moveUpToKeyboard(false) //????????????????????????????????????????????????????????????
                .enableDrag(true)
                .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
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

    //????????????
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

    //????????????
    @OnClick(R.id.refresh_line)
    void refresh_line() {
        routeIndex = 0;
        selectRouterPostion = 0;
        setRouter(navEndPoint.getLatitude(), navEndPoint.getLongitude());
    }


    /*????????????*/
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
        //????????????????????????
        //???activity??????onCreate?????????mMapView.onCreate(savedInstanceState)???????????????
        mMapView.onCreate(savedInstanceState);
        // ?????????Marker???????????????
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
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //???activity??????onSaveInstanceState?????????mMapView.onSaveInstanceState (outState)??????????????????????????????
        mMapView.onSaveInstanceState(outState);

    }

    //????????????
    private void showLoacation() {
        // ??????????????????
        mMapView.getMap().setLocationSource(this);
//        startLocation();
        mMapView.getMap().setMyLocationEnabled(true);
        UiSettings mUiSettings;//????????????UiSettings??????
        mMapView.getMap().setTrafficEnabled(true); //????????????
        mUiSettings = mMapView.getMap().getUiSettings();//?????????UiSettings?????????
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setLogoBottomMargin(-50);//??????logo
        //???????????????????????????????????????
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(zoomTo);
        mMapView.getMap().moveCamera(mCameraUpdate);
        mMapView.getMap().setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //????????????????????? ??????????????????marker??????
                public_service_layout.setVisibility(View.GONE);
                nav_layout.setVisibility(View.GONE);
                clearRoute();//????????????
                clickMaker.remove();
            }
        });
        mMapView.getMap().setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            //????????????????????????????????????
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
        // ???Marker????????????????????????????????????????????????????????????
//                markerOption.setFlat(true);//??????marker??????????????????
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
     * ???????????????????????????
     */
    public void getAddress(final LatLng latLonPoint) {
        showLoadingDialog();
        // ???????????????????????????Latlng????????????????????????????????????????????????????????????????????????????????????GPS???????????????
        RegeocodeQuery query = new RegeocodeQuery(MapUtils.convertToLatLonPoint(latLonPoint), 500, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// ?????????????????????????????????
        //?????????????????????????????????????????? ????????????????????????
        DistanceSearch.DistanceQuery distanceQuery = new DistanceSearch.DistanceQuery();
        DistanceSearch distanceSearch = new DistanceSearch(this);
        LatLonPoint dest = new LatLonPoint(Double.valueOf(latLonPoint.latitude), Double.valueOf(latLonPoint.longitude));
        List<LatLonPoint> latLonPoints = new ArrayList<LatLonPoint>();
        latLonPoints.add(startPoint);
        distanceQuery.setOrigins(latLonPoints);
        distanceQuery.setDestination(dest);
//        ??????????????????????????????????????????
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
            //???????????????
            mlocationClient = new AMapLocationClient(this);
            //?????????????????????
            mLocationOption = new AMapLocationClientOption();
            //????????????????????????
            mlocationClient.setLocationListener(this);
            //??????????????????????????????
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //??????????????????
            mlocationClient.setLocationOption(mLocationOption);
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
            // ??????????????????????????????????????????????????????????????????2000ms?????????????????????????????????stopLocation()???????????????????????????
            // ???????????????????????????????????????????????????onDestroy()??????
            // ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
            mlocationClient.startLocation();//????????????
            LogUtil.e("????????????");

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
                mListener.onLocationChanged(amapLocation);// ?????????????????????
                Constant.LocationCity = amapLocation.getCity();
                Constant.locationLatLonPoint = startPoint;
                mlocationClient.stopLocation();
                address = amapLocation.getAddress();//??????????????????
            } else {
                String errText = "????????????," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
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
        calculate(); //????????????
        mMapView.getMap().setMyLocationEnabled(false);

    }

    private void calculate() {
        showLoadingDialog("??????????????????");
        clearRoute();
        int strategyFlag = 0;
        try {
//            avoidCongestion - ??????????????????
//            avoidHighway - ??????????????????
//            avoidCost - ??????????????????
//            prioritiseHighway - ??????????????????
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
        //????????????????????????????????????
        dismissLoadingDialog();
        LatLngBounds.Builder newbounds = new LatLngBounds.Builder();
        newbounds.include(mStartMarker.getPosition());//??????for???????????????????????????????????????.
        newbounds.include(mEndMarker.getPosition());//??????for???????????????????????????????????????.
//        mAmap.animateCamera(CameraUpdateFactory.newLatLngBounds(newbounds.build(),
//                DisplayUtil.dip2px(400)));//????????????????????????????????????.
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
        changeRoute(); //????????????
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
        showToast("??????????????????" + i);

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
//        showToast("??????????????????");
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
        //????????????????????????
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.3f);
        }
        RouteOverLay routeOverlay = routeOverlays.get(routeID);
        if (routeOverlay != null) {
            routeOverlay.setTransparency(1);
            /**????????????????????????????????????????????????????????????????????????????????????????????????????????????**/
            routeOverlay.setZindex(postion);
        }
        selectRoteId = routeID;
    }

    public void changeRoute() {
        trafficListBeans.clear();
//         ?????????????????????????????????
        if (routeOverlays.size() == 1) {
            //????????????AMapNavi ???????????????????????????
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
            if (isStartNav) { //????????????????????????????????????
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
        //????????????????????????
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.3f);
        }
        RouteOverLay routeOverlay = routeOverlays.get(routeID);
        if (routeOverlay != null) {
            routeOverlay.setTransparency(1f);
            /**????????????????????????????????????????????????????????????????????????????????????????????????????????????**/
            routeOverlay.setZindex(zindex++);
        }
        //????????????AMapNavi ???????????????????????????
        mAMapNavi.selectRouteId(routeID);
        if (isStartNav) { //????????????????????????????????????
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
//            Log.e("nxt", "????????????:" + path.getLabels()
//                    + "????????????:" + (path).getAllLength() + "m" + "\n" + "????????????:" + (path).getAllTime()
//                    + "?????????" + (path).getLightList().size());
        }
        initRv();
    }


    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), "??????????????????" + aMapCalcRouteResult.getErrorCode(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }

    /**
     * ????????????????????????????????????
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }


    /**
     * ??????????????????
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
         * ?????????????????????????????????activity??????????????????????????????????????????
         */
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.destroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) { //??????????????????????????????
            dismissLoadingDialog();
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getNeighborhood());
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getBuilding());
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getDistrict());
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getBusinessAreas());
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getPois());
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getRoads());
                LogUtil.e("?????????????????????  ??????????????????" + result.getRegeocodeAddress().getTownship());
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
        Log.d("??????", "onDistanceSearched: " + i);
        if (i == 1000) {
            String time_string;
            //?????????
//            String distance =  distanceResult.getDistanceResults().get(0).getDistance() / 1000 + "";
            long second = (long) distanceResult.getDistanceResults().get(0).getDuration();
            long days = second / 86400;            //????????????
            second = second % 86400;            //????????????
            long hours = second / 3600;            //????????????
            second = second % 3600;                //????????????
            long minutes = second / 60;            //????????????
            second = second % 60;

            if (days > 0) {
                time_string = days + "???" + hours + "??????" + minutes + "??????";
            } else if (hours > 0) {
                time_string = hours + "??????" + minutes + "??????";
            } else {
                time_string = minutes + "??????";
            }
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String dis = fnum.format(distanceResult.getDistanceResults().get(0).getDistance() / 1000);
            address_distance.setText(dis + "??????");
        }
    }

    @Override
    public void index(ContactBean bean) {
        trafficReportPopup.dismiss();
        showToast("????????????,??????????????????");
    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
