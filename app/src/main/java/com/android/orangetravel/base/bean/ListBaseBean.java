package com.android.orangetravel.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 解析请求到的数据
 *
 * @author yangfei
 */
public class ListBaseBean<E extends ErrorMsgBean> implements Serializable {

    /**
     * data : {}
     * pack_no : 10003
     * status : {"code":"000","message":"成功"}
     */
    private List<E> data;
    private String status;
    private String errMsg;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}