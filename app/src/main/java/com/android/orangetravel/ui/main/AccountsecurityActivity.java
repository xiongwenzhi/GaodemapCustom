package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.MainActivity;
import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.widgets.ReceivingOrdersPopup;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/22
 * 账号与安全
 */

public class AccountsecurityActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    private UserInfoBean userInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_accountsecurity;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(AccountsecurityActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(AccountsecurityActivity.this, R.color.title_bar_black));
        setTitleBar("账号与安全");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("userBean")) {
                userInfoBean = (UserInfoBean) bundle.getSerializable("userBean");
            }
        }
    }

    @OnClick(R.id.change_phone)
    void change_phone() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userBean", userInfoBean);
        gotoActivity(ChangePhoneActivity.class, bundle);
    }

    @OnClick(R.id.update_pwd)
    void update_pwd() {
        gotoActivity(UpdatePwdActivity.class);
    }

    @OnClick(R.id.device_manage)
    void device_manage() {
        gotoActivity(DeviceManageActivity.class);
//        gotoActivity(CommonProblemActivity.class);
    }

    @OnClick(R.id.accountcancel)
    void accountcancel() {
        PromptDialog dialog = new PromptDialog(AccountsecurityActivity.this,
                "确定注销账号吗？");
        dialog.setTitle("温馨提示");
        dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
            @Override
            public void onClick() {
                getPresenter().accountCancel();
            }
        });
        dialog.show();
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
        showToast(msg);
    }
}
