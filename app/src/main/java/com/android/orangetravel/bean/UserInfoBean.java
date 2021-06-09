package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/8
 */

public class UserInfoBean extends ErrorMsgBean {

    /**
     * account : 18970881148
     * name : 罗盼
     * sex : 1
     * avatar : https://driver.igormret.com/uploads/store/driver/20200904/3241530f4ef317c614d23d03d6f10ba2.png
     * medal : [{"id":312,"title":"见义勇为","image":"http://igor.igormret.com/uploads/attach/2020/09/20200908/9fe09769db00ce4255a3ee3474ec8ee6.png","type":"jyyw","selected":1},{"id":311,"title":"公益大使","image":"http://igor.igormret.com/uploads/attach/2020/09/20200908/9fe09769db00ce4255a3ee3474ec8ee6.png","type":"charity","selected":0},{"id":310,"title":"拾金不昧","image":"http://igor.igormret.com/uploads/attach/2020/09/20200908/9fe09769db00ce4255a3ee3474ec8ee6.png","type":"credit","selected":0},{"id":309,"title":"惊喜服务","image":"http://igor.igormret.com/uploads/attach/2020/09/20200908/9fe09769db00ce4255a3ee3474ec8ee6.png","type":"service","selected":0},{"id":308,"title":"态度好服务棒","image":"http://igor.igormret.com/uploads/attach/2020/09/20200908/9fe09769db00ce4255a3ee3474ec8ee6.png","type":"attitude","selected":1},{"id":307,"title":"注册4周年","image":"http://igor.igormret.com/uploads/attach/2020/08/20200822/9967d1f4f888083cd3416a01937502a2.png","type":"register","selected":0}]
     * id_number : 360*******7257
     * city : 南昌市
     * is_rest : 0
     * is_account : 0
     * card_time : 1996-06-29
     * carCheck : 车证验真通过
     * serviceDay : 216
     * sumOrder : 3
     * sumWater : 819.17
     * score : 3.4
     * medalCount : 2
     */

    private String account;
    private String name;
    private int sex;
    private String avatar;
    private String id_number;
    private String city;
    private int is_rest;
    private int is_account;
    private String card_time;
    private String carCheck;
    private int serviceDay;
    private int sumOrder;
    private double sumWater;
    private double score;
    private int medalCount;
    private int notification;
    private int other;
    private List<MedalBean> medal;


    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIs_rest() {
        return is_rest;
    }

    public void setIs_rest(int is_rest) {
        this.is_rest = is_rest;
    }

    public int getIs_account() {
        return is_account;
    }

    public void setIs_account(int is_account) {
        this.is_account = is_account;
    }

    public String getCard_time() {
        return card_time;
    }

    public void setCard_time(String card_time) {
        this.card_time = card_time;
    }

    public String getCarCheck() {
        return carCheck;
    }

    public void setCarCheck(String carCheck) {
        this.carCheck = carCheck;
    }

    public int getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(int serviceDay) {
        this.serviceDay = serviceDay;
    }

    public int getSumOrder() {
        return sumOrder;
    }

    public void setSumOrder(int sumOrder) {
        this.sumOrder = sumOrder;
    }

    public double getSumWater() {
        return sumWater;
    }

    public void setSumWater(double sumWater) {
        this.sumWater = sumWater;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getMedalCount() {
        return medalCount;
    }

    public void setMedalCount(int medalCount) {
        this.medalCount = medalCount;
    }

    public List<MedalBean> getMedal() {
        return medal;
    }

    public void setMedal(List<MedalBean> medal) {
        this.medal = medal;
    }

    public static class MedalBean  implements Serializable {
        /**
         * id : 312
         * title : 见义勇为
         * image : http://igor.igormret.com/uploads/attach/2020/09/20200908/9fe09769db00ce4255a3ee3474ec8ee6.png
         * type : jyyw
         * selected : 1
         */

        private int id;
        private String title;
        private String image;
        private String type;
        private int selected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }
    }
}
