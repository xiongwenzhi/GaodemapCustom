package com.android.orangetravel.base.rx;

/**
 * 返回的数据中的报错
 *
 * @author yangfei
 */
public class ApiErrorException extends Exception {

    String message;
    String status;

    public ApiErrorException(String message) {
        super(message);
    }

    public ApiErrorException(String message, String status) {
        this.message = message;
        this.status = status;
    }
}