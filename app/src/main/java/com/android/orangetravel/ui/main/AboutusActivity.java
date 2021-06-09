package com.android.orangetravel.ui.main;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/22
 * 关于我们
 */

public class AboutusActivity extends BaseActivity {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(AboutusActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(AboutusActivity.this, R.color.title_bar_black));
        setTitleBar("联系我们");
    }

    @OnClick(R.id.sm)
    void sm() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.baidu.com");
        gotoActivity(WebviewActivity.class, bundle);
    }

    @OnClick(R.id.sm_tb)
    void sm_tb() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.baidu.com");
        gotoActivity(WebviewActivity.class, bundle);
    }

    @OnClick(R.id.ys_zc)
    void ys_zc() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.baidu.com");
        gotoActivity(WebviewActivity.class, bundle);
    }

    @Override
    public void requestData() {

    }
}
