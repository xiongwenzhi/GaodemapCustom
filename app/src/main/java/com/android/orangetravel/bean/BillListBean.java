package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/20
 */

public class BillListBean extends ErrorMsgBean {

    /**
     * date : 2021年-03月-02日
     * total : 合计：207.63元
     * data : [{"time":"17:16","title":"基础车费","money":"110.99"},{"time":"17:16","title":"用户奖励","money":"96.64"}]
     */

    private String date;
    private String total;
    private List<DataBean> data;
    private String url;
    private String subTitle;
    private String image;
    private String id;
    private String type;
    private String name;
    private String phone;
    private String tail;

    public String getPhone() {
        return phone;
    }

    public String getTail() {
        return tail;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 17:16
         * title : 基础车费
         * money : 110.99
         */

        private String time;
        private String title;
        private String money;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
