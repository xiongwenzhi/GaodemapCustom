package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
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
public class UserCenterPresenter extends BasePresenter<UsercenterView, UserCenterModel> {

    public UserCenterPresenter(UsercenterView view) {
        setVM(view, new UserCenterModel());
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
     * 登录
     */
    public void verifyLogin(Map<String, Object> map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.verifyLogin(new RxObserver<LoginBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(LoginBean bean) {
                    dismissDialog();
                    mView.verifyLogin(bean);
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
     * 新增、修改紧急联系人
     */
    public void contactEdit(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.contactEdit(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.resetPwd(bean);
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
     * 删除联系人
     */
    public void contactDel(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.contactDel(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.resetPwd(bean);
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
     * 账号密码登录
     */
    public void Login(Map<String, Object> map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.Login(new RxObserver<LoginBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(LoginBean bean) {
                    dismissDialog();
                    mView.verifyLogin(bean);
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
     * 修改密码
     */
    public void resetPwd(Map<String, Object> map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.resetPwd(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.resetPwd(bean);
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
     * 个人信息
     */
    public void info() {
        if (isVMNotNull()) {
            showDialog();
            mModel.info(new RxObserver<UserInfoBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(UserInfoBean bean) {
                    dismissDialog();
                    mView.userInfo(bean);
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
     * 我的工具
     */
    public void tools() {
        if (isVMNotNull()) {
            showDialog();
            mModel.tools(new RxListObserver<List<ToolListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<ToolListBean> bean) {
                    dismissDialog();
                    mView.toolList(bean);
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
     * 推荐活动
     */
    public void activity() {
        if (isVMNotNull()) {
            showDialog();
            mModel.activity(new RxListObserver<List<ToolListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<ToolListBean> bean) {
                    dismissDialog();
                    mView.actiity(bean);
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
     * 推荐活动
     */
    public void noticeNew() {
        if (isVMNotNull()) {
            showDialog();
            mModel.noticeNew(new RxListObserver<List<MessageNotciList>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<MessageNotciList> bean) {
                    dismissDialog();
                    mView.noticeNew(bean);
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
    public void noReadCount() {
        if (isVMNotNull()) {
            showDialog();
            mModel.noReadCount(new RxObserver<UserInfoBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(UserInfoBean bean) {
                    dismissDialog();
                    mView.userInfo(bean);
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
     * 消息列表
     */
    public void notice(String type) {
        if (isVMNotNull()) {
            showDialog();
            mModel.notice(new RxListObserver<List<MessageNotciList>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<MessageNotciList> bean) {
                    dismissDialog();
                    mView.noticeNew(bean);
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
     * 紧急联系人列表
     */
    public void contactList() {
        if (isVMNotNull()) {
            showDialog();
            mModel.contactList(new RxListObserver<List<ToolListBean>>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(List<ToolListBean> bean) {
                    dismissDialog();
                    mView.toolList(bean);
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
     * 设置消息阅读状态
     */
    public void noticeRead(Map map) {
        if (isVMNotNull()) {
            showDialog();
            mModel.noticeRead(new RxObserver<ErrorMsgBean>() {
                @Override
                protected void _onSubscribe(Disposable d) {
                    addRxManager(d);
                }

                @Override
                protected void _onNext(ErrorMsgBean bean) {
                    dismissDialog();
                    mView.resetPwd(bean);
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