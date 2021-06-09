package com.android.orangetravel.bean;

/**
 * @author Mr Xiong
 * @date 2021/1/20
 */

public class TrafficListBean {
    String time;
    String title;
    String lightSize;
    String length;

    public TrafficListBean(String time, String title, String lightSize, String length) {
        this.time = time;
        this.title = title;
        this.lightSize = lightSize;
        this.length = length;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLightSize() {
        return lightSize;
    }

    public void setLightSize(String lightSize) {
        this.lightSize = lightSize;
    }
}
