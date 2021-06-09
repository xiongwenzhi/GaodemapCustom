package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/29
 */

public  class FlowAnalyBean extends ErrorMsgBean {

    /**
     * m : 3月
     * water : 272.53
     * order : 2
     * rate : [{"name":"基础车费","amount":180.46},{"name":"奖励","amount":6.34},{"name":"其他","amount":30.43}]
     * trend : [{"amount":0,"day":1},{"amount":272.53,"day":2},{"amount":0,"day":3},{"amount":0,"day":4},{"amount":0,"day":5},{"amount":0,"day":6},{"amount":0,"day":7},{"amount":0,"day":8},{"amount":0,"day":9},{"amount":0,"day":10},{"amount":0,"day":11},{"amount":0,"day":12},{"amount":0,"day":13},{"amount":0,"day":14},{"amount":0,"day":15},{"amount":0,"day":16},{"amount":0,"day":17},{"amount":0,"day":18},{"amount":0,"day":19},{"amount":0,"day":20},{"amount":0,"day":21},{"amount":0,"day":22},{"amount":0,"day":23},{"amount":0,"day":24},{"amount":0,"day":25},{"amount":0,"day":26},{"amount":0,"day":27},{"amount":0,"day":28},{"amount":0,"day":29},{"amount":0,"day":30},{"amount":0,"day":31}]
     */

    private String m;
    private String water;
    private int order;
    private List<RateBean> rate;
    private List<TrendBean> trend;

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<RateBean> getRate() {
        return rate;
    }

    public void setRate(List<RateBean> rate) {
        this.rate = rate;
    }

    public List<TrendBean> getTrend() {
        return trend;
    }

    public void setTrend(List<TrendBean> trend) {
        this.trend = trend;
    }

    public static class RateBean {
        /**
         * name : 基础车费
         * amount : 180.46
         */

        private String name;
        private String amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public static class TrendBean {
        /**
         * amount : 0
         * day : 1
         */

        private String amount;
        private int day;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
}
