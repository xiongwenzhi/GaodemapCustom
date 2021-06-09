package com.android.orangetravel.base.widgets.photopicker.beans;

import java.io.Serializable;

/**
 * PhotoBean
 * <p/>
 * 照片实体
 */
public class PhotoBean implements Serializable {

    private int id;
    private String path;// 路径
    private boolean isCamera;

    public PhotoBean(String path) {
        this.path = path;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public boolean isCamera() {
        return isCamera;
    }
    public void setIsCamera(boolean isCamera) {
        this.isCamera = isCamera;
    }

}
