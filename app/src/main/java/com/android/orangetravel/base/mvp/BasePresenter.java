package com.android.orangetravel.base.mvp;

import com.android.orangetravel.base.rx.RxManager;
import com.android.orangetravel.base.utils.StringUtil;

import io.reactivex.disposables.Disposable;


public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected V mView = null;
    protected M mModel = null;
    private RxManager mRxManager = null;

    // ---------------------------------------------------------
    // 供BaseActivity(4个基类)调用

    /**
     * 绑定View
     */
    /*public void attachView(V v) {
        this.mView = v;
    }*/

    /**
     * 分离View
     */
    /*public void detachView() {
        this.mView = null;
    }*/

    /**
     * 销毁前的操作
     */
    public void onDestroy() {
        this.mView = null;
        this.mModel = null;
        if (null != this.mRxManager) {
            this.mRxManager.clear();
            this.mRxManager = null;
        }
    }

    // ---------------------------------------------------------
    // 供BasePresenter子类调用

    /**
     * 设置VM
     */
    protected void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }

    /**
     * 获取View
     */
    /*protected V getView() {
        return this.mView;
    }*/

    /**
     * 获取Model
     */
    /*protected M getModel() {
        return this.mModel;
    }*/

    /**
     * 传入订阅结果进行统一管理
     */
    protected void addRxManager(Disposable d) {
        if (null == mRxManager) {
            mRxManager = new RxManager();
        }
        mRxManager.add(d);
    }

    /**
     * View和Model不为空
     */
    protected boolean isVMNotNull() {
        return (null != mView) && (null != mModel);
    }

    // ---------------------------------------------------------
    // BasePresenter子类中调用View的基础方法

    /**
     * 显示错误信息
     */
    protected void showErrorMsg(String msg) {
        if (null != mView && StringUtil.isNotEmpty(msg)) {
            mView.showErrorMsg(msg);
        }
    }

    /**
     * 显示加载弹出框
     */
    protected void showDialog() {
        if (null != mView) {
            mView.showLoadingDialog();
        }
    }

    /**
     * 关闭加载弹出框
     */
    protected void dismissDialog() {
        if (null != mView) {
            mView.dismissLoadingDialog();
        }
    }

}