package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/9
 */

public  class BalanceBean extends ErrorMsgBean {


    /**
     * driverInfo : {"balance":"0.00","blocked":"0.00","actual":"0.00"}
     * other : {"question":"为了降低您的账户风险,仅2天前的收入可用于消费"}
     */

    private DriverInfoBean driverInfo;
    private OtherBean other;
    private String link;
    private String qrCode;
    private String status;
    private String name;
    private String tips;
    private boolean showtBtn;

    public boolean isShowtBtn() {
        return showtBtn;
    }

    public void setShowtBtn(boolean showtBtn) {
        this.showtBtn = showtBtn;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getTips() {
        return tips;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public DriverInfoBean getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfoBean driverInfo) {
        this.driverInfo = driverInfo;
    }

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public static class DriverInfoBean {
        /**
         * balance : 0.00
         * blocked : 0.00
         * actual : 0.00
         */

        private String balance;
        private String blocked;
        private String actual;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBlocked() {
            return blocked;
        }

        public void setBlocked(String blocked) {
            this.blocked = blocked;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }
    }

    public static class OtherBean {
        /**
         * question : 为了降低您的账户风险,仅2天前的收入可用于消费
         */

        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }
}
