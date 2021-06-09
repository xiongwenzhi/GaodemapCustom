package com.android.orangetravel.base.rx;

/**
 * 帐户冻结事件
 *
 * @author yangfei
 * @date 2018/8/28 15:15
 */
public class AccountFreezeEvent {

    private String msg;

    public AccountFreezeEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}