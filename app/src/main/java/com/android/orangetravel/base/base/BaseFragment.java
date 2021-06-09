package com.android.orangetravel.base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.orangetravel.base.annotation.BindEventBus;
import com.android.orangetravel.base.mvp.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @author yangfei
 */
public abstract class BaseFragment<P extends BasePresenter> extends SuperFragment {

    /*获取布局Id*/
    public abstract int getLayoutId();

    /*初始化Presenter*/
    public abstract P initPresenter();

    /*初始化控件*/
    public abstract void initView();

    /*请求数据*/
    public abstract void requestData();

    private P mPresenter;
    protected boolean isVisible = false;
    protected boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getActivity();
            /*获取布局Id*/
            rootView = inflater.inflate(this.getLayoutId(), container, false);
            /*ButterKnife注解*/
            mUnbinder = ButterKnife.bind(this, rootView);
            /*MVP*/
            /*mPresenter = TUtil.getPresenter(this.getClass());
            if (null != mPresenter) {
                mPresenter.attachView((BaseView) this);
            }*/
            mPresenter = initPresenter();
            /*初始化控件*/
            this.initView();
            // 是否加载
            isLoad();
            /*EventBus*/
            if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
                EventBus.getDefault().register(this);
            }
        }
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (null != group) {
            group.removeView(rootView);
        }
        return rootView;
    }

    /**
     * 是否加载
     */
    private void isLoad() {
        if (isVisible) {
            isFirst = false;
            /*请求数据*/
            this.requestData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isFirst && null != rootView) {
            // 是否加载
            isLoad();
        }
    }

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        if (isVisible && isFirst && null != rootView) {
            // 是否加载
            isLoad();
        }
    }*/

    /**
     * 执行销毁前的操作
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        /*MVP*/
        if (null != mPresenter) {
            mPresenter.onDestroy();
        }
        /*EventBus*/
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 提供给子类调用的方法

    public P getPresenter() {
        if (null == mPresenter) {
            throw new NullPointerException("抽象方法(initPresenter)的实现中，未初始化 Presenter");
        }
        return mPresenter;
    }

}