package com.android.orangetravel.bean;

/**
 * @author Mr Xiong
 * @date 2020/12/21
 */

public class CenterMenuDataModel {
    String image;
    String title;
    String type;

    public CenterMenuDataModel(String image, String title, String type) {
        this.image = image;
        this.title = title;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
