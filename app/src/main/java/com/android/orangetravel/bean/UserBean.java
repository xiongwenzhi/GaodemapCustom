package com.android.orangetravel.bean;

/**
 * 用户
 *
 * @author yangfei
 * @date 2018/6/12
 */
public class UserBean {

    /**
     * id : 5
     * head : /data/image/nohead.jpg
     * mobile : 18379190283
     * nickname : DL18379190283
     * sex : 男
     * type : 2 (1个人2商家)
     * shop_id : 57 (商家ID)
     * unread_message : 未读消息
     */

    private String id;
    private String nickname;
    private String mobile;
    private String head;
    private String sex;
    private String balance;
    private String birthday;
    private int integral;
    private int status;
    private int sign_num;
    private String token;
    private int group_id;
    private String group_name;
    private String type;
    private String shop_id;
    private String unread_message;
    private String qq;
    private String is_agent;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSign_num() {
        return sign_num;
    }

    public String getIs_agent() {
        return is_agent;
    }

    public String getQq() {
        return qq == null ? "" : qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setIs_agent(String is_agent) {
        this.is_agent = is_agent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getUnread_message() {
        return unread_message;
    }

    public void setUnread_message(String unread_message) {
        this.unread_message = unread_message;
    }

}