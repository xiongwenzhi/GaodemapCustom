package com.android.orangetravel.ui.main;

import android.view.View;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2021/2/26
 * 实名认证
 */

public class RealNamepageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_real_namepage;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(RealNamepageActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(RealNamepageActivity.this, R.color.title_bar_black));
        setTitleBar("统一实名认证");
    }




    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View v) {

    }
}
