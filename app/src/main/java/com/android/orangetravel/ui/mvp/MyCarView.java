package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseView;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
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
public interface MyCarView extends BaseView {

    /**
     * 我的车里-成功
     */
    void carList(List<MyCarListBean> bean);

    /**
     * 轮播消息-成功
     */
    void carDetails(CarDetailsBean bean);
    /**
     * 设置默认车辆-成功
     */
    void setDefault(ErrorMsgBean bean);
    /**
     * 设置默认车辆-成功
     */
    void perectInfo(PerfectInfoBean bean);
}