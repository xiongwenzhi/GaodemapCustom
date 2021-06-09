package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * @author Mr Xiong
 * @date 2021/3/8
 */

public class LoginBean extends ErrorMsgBean {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
