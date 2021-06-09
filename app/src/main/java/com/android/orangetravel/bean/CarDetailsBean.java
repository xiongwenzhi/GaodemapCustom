package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/16
 */

public class CarDetailsBean extends ErrorMsgBean {

    /**
     * id : 700478J4FXRbmP6IRR9UHZCLJxrc6LpMA2mkAEGt1E
     * name : 奔驰威霆
     * owner : 施文
     * color : 黑色
     * plateNumber : 赣A99W44
     * city : 南昌市
     * status : 1
     * lessee : 江西伟海汽车服务有限公司
     * register_time : 2017-04-12
     * hint :
     */

    private String id;
    private String name;
    private String owner;
    private String color;
    private String plateNumber;
    private String city;
    private String status;
    private String lessee;
    private String register_time;
    private String hint;
    private String appearance;
    private String driving_front;
    private String driving_reverse;
    private String car_type;
    private String vin;

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getDriving_front() {
        return driving_front;
    }

    public void setDriving_front(String driving_front) {
        this.driving_front = driving_front;
    }

    public String getDriving_reverse() {
        return driving_reverse;
    }

    public void setDriving_reverse(String driving_reverse) {
        this.driving_reverse = driving_reverse;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLessee() {
        return lessee;
    }

    public void setLessee(String lessee) {
        this.lessee = lessee;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
