package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseView;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;

import java.util.List;

/**
 * 时间：2019/5/15 0010
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author xiongwenzhi
 */
public interface UsercenterView extends BaseView {

    /**
     * 获取验证码-成功
     */
    void verify(String bean);
    /**
     * 获取验证码-成功
     */
    void verify(SendCodeBean bean);

    /**
     * 登录-成功
     */
    void verifyLogin(LoginBean bean);
    /**
     * 修改密码-成功
     */
    void resetPwd(ErrorMsgBean bean);
    /**
     * 登录-成功
     */
    void userInfo(UserInfoBean bean);

    /**
     * 我的工具-成功
     */
    void toolList(List<ToolListBean> bean);


    /**
     * 推荐活动-成功
     */
    void actiity(List<ToolListBean> bean);

    /**
     * 轮播消息-成功
     */
    void noticeNew(List<MessageNotciList> bean);
}