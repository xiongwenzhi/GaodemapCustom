package com.android.orangetravel.bean;


import java.io.Serializable;
import java.util.List;

/**
 * @author Mr Xiong
 * @date 2020/12/19
 */

public  class NaviIntentBean  implements Serializable {

//    LatLonPoint start;
//    LatLonPoint stop;
    double lat;
    double lon;
//    List<NaviIntentBean> start;
//    List<NaviIntentBean> stop;


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

//    public List<NaviIntentBean> getStart() {
//        return start;
//    }
//
//    public void setStart(List<NaviIntentBean> start) {
//        this.start = start;
//    }
//
//    public List<NaviIntentBean> getStop() {
//        return stop;
//    }
//
//    public void setStop(List<NaviIntentBean> stop) {
//        this.stop = stop;
//    }
}
