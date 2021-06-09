package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
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
public class IndexPresenter extends BasePresenter<IndexView, IndexModel> {

    public IndexPresenter(IndexView view) {
        setVM(view, new IndexModel());
    }

    /**
     * 首页
     */
    public void index() {
        if (isVMNotNull()) {
            showDialog();
            mModel.index(new RxObserver<ContactBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ContactBean bean) {
                    dismissDialog();
                    mView.index(bean);
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
     * 交通上报
     */
    public void traffic(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.traffic(new RxObserver<ContactBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ContactBean bean) {
                    dismissDialog();
                    mView.index(bean);
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
     * 司机日报
     */
    public void daily(String area, String date) {
        if (isVMNotNull()) {
            showDialog();
            mModel.daily(new RxObserver<ContactBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ContactBean bean) {
                    dismissDialog();
//                    mView.index(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, area, date);
        }
    }
}