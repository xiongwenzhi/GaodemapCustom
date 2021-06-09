package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/9
 */

public  class ContactBean extends ErrorMsgBean {


    /**
     * service : 4000091822
     * url : admin.jxllhb.com
     * mini : 安橙出行
     * address : 南昌市青山湖区青山湖北大道2199号10层1007号
     */

    private String service;
    private String url;
    private String mini;
    private String address;
    private String name;
    private Object tips;
    private String token;
    private String id;
    private String android;
    private String content;
    private String download;
    private String version_big;
    private String apk;
    private String client;
    private String ios;
    private String sumWater;
    private String sumOrder;
    private int messageCount;
    private String session_id;

    public String getSumWater() {
        return sumWater;
    }

    public String getSumOrder() {
        return sumOrder;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getId() {
        return id;
    }

    public String getAndroid() {
        return android;
    }

    public String getContent() {
        return content;
    }

    public String getDownload() {
        return download;
    }

    public String getVersion_big() {
        return version_big;
    }

    public String getApk() {
        return apk;
    }

    public String getClient() {
        return client;
    }

    public String getIos() {
        return ios;
    }

    public Object getTips() {
        return tips;
    }

    public void setTips(Object tips) {
        this.tips = tips;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMini() {
        return mini;
    }

    public void setMini(String mini) {
        this.mini = mini;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
