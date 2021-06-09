package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/20
 */

public  class BankDefault extends ErrorMsgBean {

    /**
     * isData : 1
     * count : 2
     * id : 329447esVMcio59t7FzmkzLzsk4f6KoscXVooQKwxUj2Y
     * bank : 中国农业银行
     * logo : http://igor.igormret.com/uploads/attach/2020/07/20200731/83c6f9f98bf2d5a8ea5931034853cd65.png
     * name : *盼
     * card : 6217 0021 0001 0285 237
     */

    private int isData;
    private int count;
    private String id;
    private String bank;
    private String logo;
    private String name;
    private String card;

    public int getIsData() {
        return isData;
    }

    public void setIsData(int isData) {
        this.isData = isData;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
