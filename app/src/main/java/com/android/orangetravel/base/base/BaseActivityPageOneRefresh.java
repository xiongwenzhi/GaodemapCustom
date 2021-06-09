package com.android.orangetravel.base.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.ConstantUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yang.base.R;

/**
 * Activity基类（下拉刷新page从0开始）
 *
 * @author yangfei
 */
public abstract class BaseActivityPageOneRefresh<P extends BasePresenter, T> extends SuperActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取布局Id
        super.setLayoutResId(this.getLayoutId());
        // MVP
        /*mPresenter = TUtil.getPresenter(this.getClass());
        if (null != mPresenter) {
            mPresenter.attachView((BaseView) this);
        }*/
        mPresenter = initPresenter();
        // 初始化刷新
        this.initRefresh();
        // 初始化控件
        this.initView();
        // 请求数据
        // this.requestData();
        this.startRefresh();
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

    // ---------------------------------------------------------------------------------------------
    // 下拉刷新,上拉加载

    // 刷新布局
    protected RefreshLayout mRefreshLayout;
    // 刷新控件
    protected T mRefreshView;
    // 无数据显示布局
    private RelativeLayout mDataEmptyLayout;
    private ImageView pull_empty_img;
    private TextView pull_empty_tv;
    // 记录当前操作,刷新还是加载
    private int what = ConstantUtil.REFRESH;
    // 一页的数据数
    private int pageSize = ConstantUtil.DEFAULT_PAGESIZE;
    // 页数
    private int page = 0;
    // 数据长度
    private int dataLength = 0;

    public String getPageSize() {
        return String.valueOf(pageSize);
    }

    public String getPage() {
        return String.valueOf(page);
    }

    protected boolean isRefresh() {
        return what == ConstantUtil.REFRESH;
    }

    /**
     * 开始刷新
     */
    protected void startRefresh() {
        this.what = ConstantUtil.REFRESH;
        this.page = 0;
        this.requestData();
    }

    /**
     * 开始加载
     */
    protected void startLoadMore() {
        this.what = ConstantUtil.LOADING;
        this.page++;
        this.requestData();
    }

    /**
     * 初始化刷新---
     */
    protected void initRefresh() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.mSmartRefreshLayout);
        mRefreshView = (T) findViewById(R.id.mRefreshView);
        mDataEmptyLayout = (RelativeLayout) findViewById(R.id.pull_empty_layout);
        pull_empty_img = (ImageView) findViewById(R.id.pull_empty_img);
        pull_empty_tv = (TextView) findViewById(R.id.pull_empty_tv);
        if (mRefreshLayout == null) {
            return;
        }
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 开始加载
                startLoadMore();
            }


            @Override
            public void onRefresh(RefreshLayout mRefreshLayout) {
                // 开始刷新
                startRefresh();
            }
        });
        // mRefreshLayout.autoRefresh(50);// 自动刷新
        // mRefreshLayout.setEnableLoadmore(true);// 是否启用上拉加载更多(默认启用)
        // mRefreshLayout.setEnableRefresh(true);// 是否启用下拉刷新(默认启用)
        // mRefreshLayout.setEnableOverScrollBounce(true);// 是否启用越界回弹(默认启用)
    }

    /**
     * 数据请求成功后执行
     *
     * @param newLength 响应后数据源的长度
     */
    protected void successAfter(int newLength) {
        if (null != mRefreshLayout) {
            if (what == ConstantUtil.LOADING) {
                mRefreshLayout.finishLoadMore(true);// 加载成功
                if (((newLength - this.dataLength) < pageSize) || newLength < pageSize) {
                    // 加载完成，不能上拉
                    mRefreshLayout.setEnableLoadMore(true);
                }
            } else {
                mRefreshLayout.finishRefresh(true);// 刷新成功
                if (newLength < pageSize) {
                    // 加载完成，不能上拉
                    mRefreshLayout.setEnableLoadMore(true);
                } else {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        }
        this.dataLength = newLength;
        setEmpty();
    }

    /**
     * 数据请求失败后执行
     *
     * @param newLength 响应后数据源的长度
     */
    protected void failureAfter(int newLength) {
        if (null != mRefreshLayout) {
            if (what == ConstantUtil.LOADING) {
                mRefreshLayout.setEnableLoadMore(false);// 加载失败
                page--;
            } else {
                mRefreshLayout.finishRefresh(false);// 刷新失败
            }
        }
        this.dataLength = newLength;
        setEmpty();
    }

    /**
     * 判断数据源长度,决定是否显示布局
     */
    private void setEmpty() {
        if (null != mDataEmptyLayout) {
            if (this.dataLength > 0) {
                mDataEmptyLayout.setVisibility(View.GONE);
            } else {
                mDataEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置空数据布局-背景
     */
    protected void setEmptyLayoutBg(@ColorRes int colorRes) {
        mDataEmptyLayout.setBackgroundResource(colorRes);
    }

    /**
     * 设置空数据布局-图片和文字
     */
    protected void setEmptyLayoutImgText(@DrawableRes int imgRes,
                                         @StringRes int strRes) {
        pull_empty_img.setBackgroundResource(imgRes);
        pull_empty_tv.setText(strRes);
    }

}