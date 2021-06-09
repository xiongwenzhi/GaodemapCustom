package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/19
 */

public class PerfectInfoBean extends ErrorMsgBean {


    /**
     * reason : [{"why":"行驶证:就是不让你过,咋滴啦","type":"front"},{"why":"车辆外观:车辆外观照片不清楚","type":"appearance"}]
     * why : {"front":0,"reverse":1,"appearance":0}
     */

    private WhyBean why;
    private List<ReasonBean> reason;

    public WhyBean getWhy() {
        return why;
    }

    public void setWhy(WhyBean why) {
        this.why = why;
    }

    public List<ReasonBean> getReason() {
        return reason;
    }

    public void setReason(List<ReasonBean> reason) {
        this.reason = reason;
    }

    public static class WhyBean {
        /**
         * front : 0
         * reverse : 1
         * appearance : 0
         */

        private int front;
        private int reverse;
        private int appearance;

        public int getFront() {
            return front;
        }

        public void setFront(int front) {
            this.front = front;
        }

        public int getReverse() {
            return reverse;
        }

        public void setReverse(int reverse) {
            this.reverse = reverse;
        }

        public int getAppearance() {
            return appearance;
        }

        public void setAppearance(int appearance) {
            this.appearance = appearance;
        }
    }

    public static class ReasonBean {
        /**
         * why : 行驶证:就是不让你过,咋滴啦
         * type : front
         */

        private String why;
        private String type;

        public String getWhy() {
            return why;
        }

        public void setWhy(String why) {
            this.why = why;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
