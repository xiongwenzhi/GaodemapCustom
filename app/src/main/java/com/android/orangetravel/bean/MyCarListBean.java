package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/15
 */

public class MyCarListBean extends ErrorMsgBean {

    /**
     * id : 77da6cfZ9I567SxrJHspaj1j0K|YunQue|NefFj1l33MdgVsb
     * name : 奔驰威霆(黑色)
     * color : 黑色
     * plateNumber : 赣A99W44
     * appearance : http://admin.jxllhb.com/uploads/attach/2021/02/20210222/28b90d70df26434e942e84b53a2353c6.png
     * status : 1
     * is_default : 1
     * statusImage : http://admin.jxllhb.com/uploads/attach/2021/02/20210222/68a3ed811e1cf0cffd9fa4c34318656d.png
     * tips :
     */

    private String id;
    private String name;
    private String color;
    private String plateNumber;
    private String appearance;
    private int status;
    private int is_default;
    private String statusImage;
    private String tips;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getStatusImage() {
        return statusImage;
    }

    public void setStatusImage(String statusImage) {
        this.statusImage = statusImage;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
