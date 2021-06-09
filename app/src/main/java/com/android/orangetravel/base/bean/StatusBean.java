package com.android.orangetravel.base.bean;

import com.android.orangetravel.base.utils.ConstantUtil;

import java.io.Serializable;

/**
 * 状态信息
 *
 * @author yangfei
 */
public class StatusBean implements Serializable {

    /**
     * code : 000
     * message : 成功
     */
    private String code;
    private String message;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }



}