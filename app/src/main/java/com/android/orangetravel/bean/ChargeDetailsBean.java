package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/31
 */

public class ChargeDetailsBean extends ErrorMsgBean {

    /**
     * car_brand : 0
     * user_price : 168.88
     * driver_price : 236.19
     * refund_status : 0
     * invoice : 0
     * coordinate : null
     * via_address : null
     * typeName : 考斯特
     * amountDetail : [{"amount":"96.64","name":"里程费(82.3公里)","type":"mileage","list":[]},{"amount":"13.34","name":"远途费(64.9公里)","type":"longFee","list":[]},{"amount":"20.00","name":"高速费(不参与优惠券)","type":"highFee","list":[]},{"amount":"3.65","name":"路桥费","type":"tollFee","list":[]},{"amount":"6.78","name":"停车费","type":"parking","list":[]},{"amount":"6.34","name":"奖励费用","type":"reward","list":[]},{"amount":"-0.5","name":"基础信息服务费","type":"service","list":[]}]
     * rules : []
     */

    private int car_brand;
    private String user_price;
    private String driver_price;
    private int refund_status;
    private int invoice;
    private Object coordinate;
    private Object via_address;
    private String typeName;
    private List<AmountDetailBean> amountDetail;
    private List<?> rules;

    public int getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(int car_brand) {
        this.car_brand = car_brand;
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

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<AmountDetailBean> getAmountDetail() {
        return amountDetail;
    }

    public void setAmountDetail(List<AmountDetailBean> amountDetail) {
        this.amountDetail = amountDetail;
    }

    public List<?> getRules() {
        return rules;
    }

    public void setRules(List<?> rules) {
        this.rules = rules;
    }

    public static class AmountDetailBean {
        /**
         * amount : 96.64
         * name : 里程费(82.3公里)
         * type : mileage
         * list : []
         */

        private String amount;
        private String name;
        private String type;
        private List<?> list;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
    }
}
