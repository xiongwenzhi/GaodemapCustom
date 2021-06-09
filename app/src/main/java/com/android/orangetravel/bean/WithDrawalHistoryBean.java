package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/21
 */

public class WithDrawalHistoryBean extends ErrorMsgBean {


    /**
     * id : da212fm1bhMa1|YunQue|FE59hJRKSeg8255I/PmQ3XcV9tb3tw
     * name : 中国工商银行(1452)
     * add_time : 2021-03-21 14:59
     * amount : 382.7
     * status : -1
     * tips : 未通过
     */

    private String id;
    private String name;
    private String add_time;
    private double amount;
    private int status;
    private String tips;
    private String time;
    private String type;
    private String logo;
    private String code;
    private String background;
    private String url;
    private String subTitle;
    private String image;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBg() {
        return background;
    }

    public void setBg(String bg) {
        this.background = bg;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
