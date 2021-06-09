package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.widgets.view.SeparatorPhoneEditView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/22
 * 修改手机号 第一步
 */

public class SetNewPhoneActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.input_phone_set_newphone)
    SeparatorPhoneEditView input_phone_set_newPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setnewphone;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(SetNewPhoneActivity.this, R.color.white));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(SetNewPhoneActivity.this, R.color.white));
        id_title_bar.setLeftIcon(R.mipmap.left_back);
        id_title_bar.setTitleBottomLineGone();
        setTitleBar("联系我们");
    }

    @OnClick(R.id.nex_set_phone)
    void nex_set_phone() {
        if (TextUtils.isEmpty(input_phone_set_newPhone.getText().toString().trim())) {
            showToast("请输入手机号");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("phone", input_phone_set_newPhone.getText().toString());
        gotoActivity(GetMsgCodeActivity.class, bundle);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void contact(ContactBean bean) {

    }

    @Override
    public void balance(BalanceBean bean) {

    }

    @Override
    public void loginOut(ErrorMsgBean bean) {

    }

    @Override
    public void billList(List<BillListBean> bean) {

    }

    @Override
    public void DiveList(List<WithDrawalHistoryBean> bean) {

    }

    @Override
    public void Compliance(ComplianceBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
