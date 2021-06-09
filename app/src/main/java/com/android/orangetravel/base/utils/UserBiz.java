package com.android.orangetravel.base.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.orangetravel.bean.UserBean;
import com.android.orangetravel.ui.widgets.utils.CommonUtil;


/**
 * 登录成功
 * 退出登录
 * 获取登录状态
 * 更新用户数据
 * 获取用户数据
 * 清除用户数据
 * 我是个人
 * 我是商家
 * 获取商家ID
 * 修改头像
 * 修改昵称
 * 修改性别
 *
 * @author yangfei
 * @date 2018/6/12
 */
public class UserBiz {

    /**
     * 登录成功
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @param shopId   商家ID
     */
    public static void loginSuccess(String userId, String userType, @Nullable String shopId) {
        SPUtil.put(ConstantUtil.LOGIN_STATE, true);
        SPUtil.put(ConstantUtil.USER_ID, userId);
        EventBusUtil.post(new LoginSucEvent());// 登录成功事件
    }

    /**
     * 退出登录
     * 注意：先清除用户数据，再清除登录状态
     */
    public static void exitLogin(Context context) {

        // 清除用户数据
        UserBiz.clearUserData();

        SPUtil.remove(ConstantUtil.LOGIN_STATE);
        SPUtil.remove(ConstantUtil.USER_ID);
        SPUtil.remove(ConstantUtil.USER_TOKEN);
//        // 退出登录删除别名
//        CommonUtil.setAlias(context, "-1");
    }

    /**
     * 清除用户数据
     */
    private static void clearUserData() {
        if (UserBiz.getLoginState()) {
            SPSaveClass.removeClass(UserBean.class);
        }
    }

    /**
     * 获取登录状态
     *
     * @return true:已登录
     */
    public static boolean getLoginState() {
        return (boolean) SPUtil.get(ConstantUtil.LOGIN_STATE, false);
    }

    /**
     * 获取用户ID
     */
    @Nullable
    public static String getUserId() {
        UserBean data = UserBiz.getUserData();
        if (null == data) {
            return null;
        } else {
            return data.getId();
        }
    }

    /**
     * 获取用户数据
     */
    @Nullable
    public static UserBean getUserData() {
        if (UserBiz.getLoginState()) {
            return SPSaveClass.getClass(UserBean.class);
        }
        return null;
    }


    /**
     * 修改头像
     */
    public static void updateAvatar(String head) {
        if (StringUtil.isNotEmpty(head)) {
            // 获取用户数据
            UserBean data = UserBiz.getUserData();
            if (null != data) {
                data.setHead(head);
                // 更新用户数据
                UserBiz.updateUserData(data);
            }
        }
    }

    /**
     * 修改生日
     */
    public static void updateBrithday(String brithday) {
        if (StringUtil.isNotEmpty(brithday)) {
            // 获取用户数据
            UserBean data = UserBiz.getUserData();
            if (null != data) {
                data.setBirthday(brithday);
                // 更新用户数据
                UserBiz.updateUserData(data);
            }
        }
    }

    /**
     * 更新用户数据
     */
    public static void updateUserData(UserBean bean) {
        SPSaveClass.saveClass(bean);
    }

    /**
     * 修改昵称
     */
    public static void updateNickname(String nickname) {
        if (StringUtil.isNotEmpty(nickname)) {
            // 获取用户数据
            UserBean data = UserBiz.getUserData();
            if (null != data) {
                data.setNickname(nickname);
                // 更新用户数据
                UserBiz.updateUserData(data);
            }
        }
    }

    /**
     * 修改QQ
     */
    public static void updateQQ(String qq) {
        if (StringUtil.isNotEmpty(qq)) {
            // 获取用户数据
            UserBean data = UserBiz.getUserData();
            if (null != data) {
                data.setQq(qq);
                // 更新用户数据
                UserBiz.updateUserData(data);
            }
        }
    }

    /**
     * 修改性别
     */
    public static void updateSex(String sex) {
        if (StringUtil.isNotEmpty(sex)) {
            // 获取用户数据
            UserBean data = UserBiz.getUserData();
            if (null != data) {
                data.setSex(sex);
                // 更新用户数据
                UserBiz.updateUserData(data);
            }
        }
    }

}