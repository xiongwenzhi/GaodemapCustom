package com.android.orangetravel.base.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

/**
 * @author Mr Xiong
 * @date 2020/11/6
 */
public class MapUtils {
    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    /**********************************************************移动地图**************************************/
    public static void animMove(AMap aMap, LatLng latLng) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public static void animMove(AMap aMap, LatLonPoint point) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(point), 15));
    }

    public static void animMove(AMap aMap, AMapLocation location) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
    }
}