package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;

import java.io.File;
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
public class CommonPresenter extends BasePresenter<CommonView, CommonModel> {

    public CommonPresenter(CommonView view) {
        setVM(view, new CommonModel());
    }

    /**
     * 联系我们
     */
    public void contact() {
        if (isVMNotNull()) {
            showDialog();
            mModel.contact(new RxObserver<ContactBean>() {
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
     * 司机钱包
     */
    public void balance() {
        if (isVMNotNull()) {
            showDialog();
            mModel.balance(new RxObserver<BalanceBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(BalanceBean bean) {
                    dismissDialog();
                    mView.balance(bean);
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
     * 账单列表
     */
    public void bill(String type) {
        if (isVMNotNull()) {
            showDialog();
            mModel.bill(new RxListObserver<List<BillListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<BillListBean> bean) {
                    dismissDialog();
                    mView.billList(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, type);
        }
    }

    /**
     * 二维码
     */
    public void qrCode() {
        if (isVMNotNull()) {
            showDialog();
            mModel.qrCode(new RxObserver<BalanceBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(BalanceBean bean) {
                    dismissDialog();
                    mView.balance(bean);
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
     * 听单检测
     */
    public void detection(String type) {
        if (isVMNotNull()) {
            showDialog();
            mModel.detection(new RxObserver<BalanceBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(BalanceBean bean) {
                    dismissDialog();
                    mView.balance(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, type);
        }
    }

    /**
     * 退出登录
     */
    public void logout() {
        if (isVMNotNull()) {
            showDialog();
            mModel.logout(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.loginOut(bean);
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
     * 图片上传
     */
    public void UploadImage(File image) {
        if (isVMNotNull()) {
            showDialog();
            mModel.UploadImage(new RxObserver<ContactBean>() {
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
            }, image);
        }
    }

    /**
     * 设备管理
     */
    public void equipment() {
        if (isVMNotNull()) {
            showDialog();
            mModel.equipment(new RxListObserver<List<WithDrawalHistoryBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<WithDrawalHistoryBean> bean) {
                    dismissDialog();
                    mView.DiveList(bean);
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
     * 设备删除
     */
    public void equipmentDel(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.equipmentDel(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.loginOut(bean);
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
     * 我的资质
     */
    public void qualification() {
        if (isVMNotNull()) {
            showDialog();
            mModel.qualification(new RxListObserver<List<WithDrawalHistoryBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<WithDrawalHistoryBean> bean) {
                    dismissDialog();
                    mView.DiveList(bean);
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
     * 未获得资质
     */
    public void notQualified() {
        if (isVMNotNull()) {
            showDialog();
            mModel.notQualified(new RxListObserver<List<BillListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<BillListBean> bean) {
                    dismissDialog();
                    mView.billList(bean);
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
     * 合规认证
     */
    public void compliance(String city) {
        if (isVMNotNull()) {
            showDialog();
            mModel.compliance(new RxObserver<ComplianceBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ComplianceBean bean) {
                    dismissDialog();
                    mView.Compliance(bean);
                }

                @Override
                protected void _onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            }, city);
        }
    }

    /**
     * 上传合规资质
     */
    public void complianceEdit(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.complianceEdit(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.loginOut(bean);
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
     * 注销账号
     */
    public void accountCancel() {
        if (isVMNotNull()) {
            showDialog();
            mModel.accountCancel(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.loginOut(bean);
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
     * 修改手机号
     */
    public void modifyPhone(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.modifyPhone(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.loginOut(bean);
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
     * 更新版本
     */
    public void versionUpdate(String version, String equipment) {
        if (isVMNotNull()) {
            showDialog();
            mModel.versionUpdate(new RxObserver<ContactBean>() {
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
            }, version, equipment);
        }
    }

}