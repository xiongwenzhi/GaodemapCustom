package com.android.orangetravel.bean;

/**
 * @author Mr Xiong
 * @date 2021/1/14
 */

public class TrafficReportListBean {
    private String menu;
    private String type;
    private int image;

    public TrafficReportListBean(String menu, String type, int image) {
        this.menu = menu;
        this.type = type;
        this.image = image;
    }

    public TrafficReportListBean(String menu, int image) {
        this.menu = menu;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
