package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/27
 */

public  class OrderListBean extends ErrorMsgBean {

    /**
     * date : 10月-21日
     * data : [{"id":"fb88c7Lyfyxfo0cBjWlr|YunQue|FEvjvUO9x0h7VqWN5XJgojQLb","num":0,"car_brand":0,"way":"包车","day":4,"origin":"九州上郡-西南门","destination":"南昌昌北国际机场","user_price":"168.88","driver_price":"236.19","paid":1,"add_time":"22:01:41","refund_status":0,"driver_status":2,"send_type":"own","invoice":0,"coordinate":null,"via_address":null,"hint":"平台垫付","price":"236.19","typeName":"考斯特","sendTime":"10月-18日 13:23:52 抢单"},{"id":"e258e1/A57ePtwl6Q0QkIVreRE24MzU7e38rfzRxULS0Ki","num":0,"car_brand":0,"way":"包车","day":4,"origin":"九州上郡-西南门","destination":"南昌昌北国际机场","user_price":"168.88","driver_price":"386.54","paid":1,"add_time":"22:01:41","refund_status":0,"driver_status":1,"send_type":"system","invoice":0,"coordinate":null,"via_address":null,"hint":"平台垫付","price":"386.54","typeName":"大巴","sendTime":""}]
     */

    private String date;
    private List<DataBean> data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  extends ErrorMsgBean{
        /**
         * id : fb88c7Lyfyxfo0cBjWlr|YunQue|FEvjvUO9x0h7VqWN5XJgojQLb
         * num : 0
         * car_brand : 0
         * way : 包车
         * day : 4
         * origin : 九州上郡-西南门
         * destination : 南昌昌北国际机场
         * user_price : 168.88
         * driver_price : 236.19
         * paid : 1
         * add_time : 22:01:41
         * refund_status : 0
         * driver_status : 2
         * send_type : own
         * invoice : 0
         * coordinate : null
         * via_address : null
         * hint : 平台垫付
         * price : 236.19
         * typeName : 考斯特
         * sendTime : 10月-18日 13:23:52 抢单
         */

        private String id;
        private int num;
        private int car_brand;
        private String way;
        private int day;
        private String origin;
        private String destination;
        private String user_price;
        private String driver_price;
        private int paid;
        private String add_time;
        private int refund_status;
        private int driver_status;
        private String send_type;
        private int invoice;
        private Object coordinate;
        private Object via_address;
        private String hint;
        private String price;
        private String typeName;
        private String sendTime;
        private String tailNumber;
        private String serving;


        public String getServing() {
            return serving;
        }

        public String getTailNumber() {
            return tailNumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(int car_brand) {
            this.car_brand = car_brand;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getUser_price() {
            return user_price;
        }

        public void setUser_price(String user_price) {
            this.user_price = user_price;
        }

        public String getDriver_price() {
            return driver_price;
        }

        public void setDriver_price(String driver_price) {
            this.driver_price = driver_price;
        }

        public int getPaid() {
            return paid;
        }

        public void setPaid(int paid) {
            this.paid = paid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public int getDriver_status() {
            return driver_status;
        }

        public void setDriver_status(int driver_status) {
            this.driver_status = driver_status;
        }

        public String getSend_type() {
            return send_type;
        }

        public void setSend_type(String send_type) {
            this.send_type = send_type;
        }

        public int getInvoice() {
            return invoice;
        }

        public void setInvoice(int invoice) {
            this.invoice = invoice;
        }

        public Object getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(Object coordinate) {
            this.coordinate = coordinate;
        }

        public Object getVia_address() {
            return via_address;
        }

        public void setVia_address(Object via_address) {
            this.via_address = via_address;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }
    }
}
