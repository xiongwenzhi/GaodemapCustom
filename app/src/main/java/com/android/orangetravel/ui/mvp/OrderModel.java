package com.android.orangetravel.ui.mvp;

import com.android.orangetravel.api.Api;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseModel;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.base.rx.RxResult;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

/**
 * 时间：2019/6/25 0010
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author xiongwenzhi
 */
public class OrderModel extends BaseModel {


    /**
     * 我的行程
     *
     * @param observer
     */
    public void orderList(RxListObserver<List<OrderListBean>> observer,String trip) {
        Api.getInstance()
                .mApiService
                .orderList(trip)
                .compose(RxResult.<OrderListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 行程详情
     *
     * @param observer
     */
    public void orderDetails(RxObserver<OrderListBean.DataBean> observer,String id) {
        Api.getInstance()
                .mApiService
                .orderDetails(id)
                .compose(RxResult.<OrderListBean.DataBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 流水明细
     *
     * @param observer
     */
    public void billDetails(RxListObserver<List<OrderListBean>> observer) {
        Api.getInstance()
                .mApiService
                .billDetails()
                .compose(RxResult.<OrderListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 流水分析
     *
     * @param observer
     */
    public void billAnalyze(RxObserver<FlowAnalyBean> observer) {
        Api.getInstance()
                .mApiService
                .billAnalyze()
                .compose(RxResult.<FlowAnalyBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 费用明细
     *
     * @param observer
     */
    public void orderAmountDetail(RxObserver<ChargeDetailsBean> observer, String id) {
        Api.getInstance()
                .mApiService
                .orderAmountDetail(id)
                .compose(RxResult.<ChargeDetailsBean>handleResultCode())
                .subscribe(observer);
    }
}