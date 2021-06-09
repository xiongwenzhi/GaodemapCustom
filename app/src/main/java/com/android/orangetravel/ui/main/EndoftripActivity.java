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
 * @date 2021/2/23
 * 行程结束
 */

public class EndoftripActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_end_trip;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(EndoftripActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(EndoftripActivity.this, R.color.title_bar_black));
        setTitleBar("行程结束");
    }



    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View v) {

    }
}
