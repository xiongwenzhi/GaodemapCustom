package com.android.orangetravel.base.bean;

import com.android.orangetravel.base.utils.StringUtil;

import java.io.Serializable;

/**
 * 报错信息
 *
 * @author yangfei
 */
public class ErrorMsgBean implements Serializable {

    private String ERROR_Param_Format;// 参数值格式错误
    private String ERROR_Param_Empty;// 参数值不能为空
    private String errMsg;

    public String getMsg() {
        return errMsg;
    }

    public void setMsg(String msg) {
        this.errMsg = msg;
    }

    public String getErrorMessage() {
        String msgStr = "";
        if (StringUtil.isNotEmpty(ERROR_Param_Format)) {
            msgStr = ERROR_Param_Format;
        } else if (StringUtil.isNotEmpty(ERROR_Param_Empty)) {
            msgStr = ERROR_Param_Empty;
        } else if (StringUtil.isNotEmpty(errMsg)) {
            msgStr = errMsg;
        }
        /*----------------------------------------------------------*/
        switch (msgStr) {
            case "confirm_password_empty":
                msgStr = "确认密码不能为空";
                break;
            case "password_no_same":
                msgStr = "两次输入的密码不一致";
                break;
            case "mobile_empty":
                msgStr = "手机号不能为空";
                break;
            case "password_empty":
                msgStr = "密码不能为空";
                break;
            case "mobile_error":
                msgStr = "手机号错误";
                break;
            case "password_error":
                msgStr = "密码错误";
                break;
            case "account_error":
                msgStr = "账户已被冻结,请与客服联系";
                break;
            case "noedit":
                msgStr = "未修改任何信息";
                break;
            case "mobile":
                msgStr = "手机号格式错误";
                break;
            case "similarpwd":
                msgStr = "不能和之前密码重复";
                break;
            case "inventory_short":
                msgStr = "库存不足";
                break;
            case "nosetzfpassword":
                msgStr = "支付密码未设置";
                break;
            case "zferror":
                msgStr = "支付密码输入错误";
                break;
            case "blancelace":
                msgStr = "余额不足";
                break;
            case "blance_lace":
                msgStr = "余额不足";
                break;
            case "code_error":
                msgStr = "验证码错误";
                break;
            case "user_id":
                msgStr = "您尚未登录";
                break;
            default:
                break;
        }
        /*----------------------------------------------------------*/
        return msgStr;
    }

}