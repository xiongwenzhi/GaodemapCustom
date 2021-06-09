package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
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

import io.reactivex.disposables.Disposable;

/**
 * 时间：2020/3/4 0010
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author Xiongwenzhi
 */
public class OrderPresenter extends BasePresenter<OrderView, OrderModel> {

    public OrderPresenter(OrderView view) {
        setVM(view, new OrderModel());
    }


    /**
     * 我的行程
     */
    public void orderList(String trip) {
        if (isVMNotNull()) {
            showDialog();
            mModel.orderList(new RxListObserver<List<OrderListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<OrderListBean> bean) {
                    dismissDialog();
                    mView.orderList(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },trip);
        }
    }

    /**
     * 行程详情
     */
    public void orderDetails(String id) {
        if (isVMNotNull()) {
            showDialog();
            mModel.orderDetails(new RxObserver<OrderListBean.DataBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(OrderListBean.DataBean bean) {
                    dismissDialog();
                    mView.orderDetails(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },id);
        }
    }

    /**
     * 流水明细
     */
    public void billDetails() {
        if (isVMNotNull()) {
            showDialog();
            mModel.billDetails(new RxListObserver<List<OrderListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<OrderListBean> bean) {
                    dismissDialog();
                    mView.orderList(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            });
        }
    }

    /**
     * 流水分析
     */
    public void billAnalyze() {
        if (isVMNotNull()) {
            showDialog();
            mModel.billAnalyze(new RxObserver<FlowAnalyBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(FlowAnalyBean bean) {
                    dismissDialog();
                    mView.FlowAnaly(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            });
        }
    }

    /**
     * 费用明细
     */
    public void orderAmountDetail(String id) {
        if (isVMNotNull()) {
            showDialog();
            mModel.orderAmountDetail(new RxObserver<ChargeDetailsBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ChargeDetailsBean bean) {
                    dismissDialog();
                    mView.chargeDetails(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },id);
        }
    }

}