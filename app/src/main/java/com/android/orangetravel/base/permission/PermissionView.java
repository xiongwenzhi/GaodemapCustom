package com.android.orangetravel.base.permission;

/**
 * 权限请求结果回调
 *
 * @author yangfei
 */
public interface PermissionView {

    /**
     * 授权成功
     */
    void onAuthSuccess();

    /**
     * 授权失败
     */
    void onAuthFailure();

}