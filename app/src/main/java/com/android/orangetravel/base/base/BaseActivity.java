package com.android.orangetravel.base.base;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.ConstantUtil;

/**
 * Activity基类
 *
 * @author xiongwenzhi
 */
public abstract class BaseActivity<P extends BasePresenter> extends SuperActivity {

    /*获取布局Id*/
    public abstract int getLayoutId();

    /*初始化Presenter*/
    public abstract P initPresenter();

    /*初始化控件*/
    public abstract void initView();

    /*请求数据*/
    public abstract void requestData();

    // MVP
    private P mPresenter;
    // 无数据显示布局
    private RelativeLayout mDataEmptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取布局Id
        super.setLayoutResId(this.getLayoutId());
        findView();
        // MVP
        /*mPresenter = TUtil.getPresenter(this.getClass());
        if (null != mPresenter) {
            mPresenter.attachView((BaseView) this);
        }*/
        mPresenter = initPresenter();
        // 初始化控件
        this.initView();
        // 请求数据
        this.requestData();
    }

    private void findView() {
        mDataEmptyLayout = (RelativeLayout) findViewById(com.yang.base.R.id.pull_empty_layout);
    }

    /**
     * 执行销毁前的操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MVP
        if (null != mPresenter) {
            mPresenter.onDestroy();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 提供给子类调用的方法

    protected P getPresenter() {
        if (null == mPresenter) {
            throw new NullPointerException("抽象方法(initPresenter)的实现中，未初始化 Presenter");
        }
        return mPresenter;
    }

    /**
     * 判断数据源长度,决定是否显示布局
     */
    public void setEmpty(int dataLength) {
        if (null != mDataEmptyLayout) {
            if (dataLength > 0) {
                mDataEmptyLayout.setVisibility(View.GONE);
            } else {
                mDataEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

}