package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseView;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.OrderListBean;
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
public interface OrderView extends BaseView {


    /**
     * 我的行程-成功
     */
    void orderList(List<OrderListBean> bean);
    /**
     * 行程详情-成功
     */
    void orderDetails(OrderListBean.DataBean bean);
    /**
     * 流水分析-成功
     */
    void FlowAnaly(FlowAnalyBean bean);

    /**
     * 费用明细-成功
     */
    void chargeDetails(ChargeDetailsBean bean);
}