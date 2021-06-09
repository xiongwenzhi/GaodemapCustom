package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
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
public class MyCarPresenter extends BasePresenter<MyCarView, MyCarModel> {

    public MyCarPresenter(MyCarView view) {
        setVM(view, new MyCarModel());
    }


    /**
     * 我的车辆列表
     */
    public void car() {
        if (isVMNotNull()) {
            showDialog();
            mModel.car(new RxListObserver<List<MyCarListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<MyCarListBean> bean) {
                    dismissDialog();
                    mView.carList(bean);
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
     * 获取是否有新消息
     */
    public void carDetail(String id) {
        if (isVMNotNull()) {
            showDialog();
            mModel.carDetail(new RxObserver<CarDetailsBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(CarDetailsBean bean) {
                    dismissDialog();
                    mView.carDetails(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, id);
        }
    }


    /**
     * 设置默认车辆
     */
    public void setDefault(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.setDefault(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.setDefault(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },map);
        }
    }

    /**
     * 车辆新增编辑
     */
    public void carEdit(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.carEdit(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.setDefault(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },map);
        }
    }

    /**
     * 车辆删除
     */
    public void carDel(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.carDel(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.setDefault(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },map);
        }
    }

    /**
     * 车辆驳回原因
     */
    public void carReason(String map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.carReason(new RxObserver<PerfectInfoBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(PerfectInfoBean bean) {
                    dismissDialog();
                    mView.perectInfo(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },map);
        }
    }

    /**
     * 完善车辆信息
     */
    public void carperfect(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.carperfect(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.setDefault(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            },map);
        }
    }
}