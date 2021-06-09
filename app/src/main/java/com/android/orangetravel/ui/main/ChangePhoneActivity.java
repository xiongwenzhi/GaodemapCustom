package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.UserInfoBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/22
 * 修改手机号 第一步
 */

public class ChangePhoneActivity extends BaseActivity {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.user_phone)
    TextView user_phone;
    private UserInfoBean userInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_changephone_one;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(ChangePhoneActivity.this, R.color.white));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(ChangePhoneActivity.this, R.color.white));
        id_title_bar.setLeftIcon(R.mipmap.left_back);
        id_title_bar.setTitleBottomLineGone();
        id_title_bar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("userBean")) {
                userInfoBean = (UserInfoBean) bundle.getSerializable("userBean");
                user_phone.setText("您当前手机号为" + userInfoBean.getAccount());
            }
        }
    }

    @OnClick(R.id.update_phone)
    void update_phone() {
        gotoActivity(SetNewPhoneActivity.class);
    }

    @Override
    public void requestData() {

    }
}
