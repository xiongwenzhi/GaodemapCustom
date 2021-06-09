package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;

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
public class BillPresenter extends BasePresenter<BillView, BillModel> {

    public BillPresenter(BillView view) {
        setVM(view, new BillModel());
    }

    /**
     * 提现提示语
     */
    public void billTips() {
        if (isVMNotNull()) {
            showDialog();
            mModel.billTips(new RxObserver<ContactBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ContactBean bean) {
                    dismissDialog();
                    mView.contact(bean);
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
     * 获取默认银行卡
     */
    public void bankDefalut() {
        if (isVMNotNull()) {
            showDialog();
            mModel.bankDefalut(new RxObserver<BankDefault>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(BankDefault bean) {
                    dismissDialog();
                    mView.bankDefalut(bean);
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
     * 银行卡列表
     */
    public void bankList() {
        if (isVMNotNull()) {
            showDialog();
            mModel.bankList(new RxListObserver<List<BankListDefault>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<BankListDefault> bean) {
                    dismissDialog();
                    mView.bankList(bean);
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
     * 提现记录
     */
    public void extract() {
        if (isVMNotNull()) {
            showDialog();
            mModel.extract(new RxListObserver<List<WithDrawalHistoryBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<WithDrawalHistoryBean> bean) {
                    dismissDialog();
                    mView.WithdrawHistoryList(bean);
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
     * 申请提现
     */
    public void extractCash(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.extractCash(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
//                    mView.setDefault(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, map);
        }
    }

    /**
     * 提现记录
     */
    public void bankSystom() {
        if (isVMNotNull()) {
            showDialog();
            mModel.bankSystom(new RxListObserver<List<WithDrawalHistoryBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<WithDrawalHistoryBean> bean) {
                    dismissDialog();
                    mView.WithdrawHistoryList(bean);
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
     * 发送验证码
     */
    public void verify(Map<String, Object> map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.verify(new RxObserver<Object>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(Object bean) {
                    dismissDialog();
                    mView.verify(bean.toString());
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, map);
        }
    }

    /**
     * 申请提现
     */
    public void bankEdit(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.bankEdit(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
//                    mView.setDefault(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, map);
        }
    }
}