package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mr Xiong
 * @date 2021/3/20
 */

public  class BankListDefault extends ErrorMsgBean {


    /**
     * id : 6c0db1cBEsr7wvhAONFM7j|YunQue|Wll6jPyhHb/QwxinSREtdA
     * bank : 中国建设银行
     * card_number : **** **** **** 1452
     * card : 6222 0214 0202 2851 452
     * logo : http://igor.igormret.com/uploads/attach/2020/07/20200731/83c6f9f98bf2d5a8ea5931034853cd65.png
     * bg : http://admin.jxmssc.com/uploads/attach/2020/11/20201127/46259e9affe6002bd83a3ab3d545bcf1.png
     * is_default : 0
     * short : (1452)
     */

    private String id;
    private String bank;
    private String card_number;
    private String card;
    private String logo;
    private String bg;
    private int is_default;
    @SerializedName("short")
    private String shortX;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getShortX() {
        return shortX;
    }

    public void setShortX(String shortX) {
        this.shortX = shortX;
    }
}
