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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * @author Mr Xiong
 * @date 2020/12/21
 * 设置
 */

public class SettingActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    private UserInfoBean userInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(SettingActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(SettingActivity.this, R.color.title_bar_black));
        setTitleBar("设置");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("userBean")) {
                userInfoBean = (UserInfoBean) bundle.getSerializable("userBean");
            }
        }
    }

    //退出登录
    @OnClick(R.id.loginOut)
    void loginOut() {
        PromptDialog dialog = new PromptDialog(SettingActivity.this,
                "确定退出登录吗？");
        dialog.setTitle("温馨提示");
        dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
            @Override
            public void onClick() {
                getPresenter().logout();
                finish();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.update_version)
    void update_version() {
        getPresenter().versionUpdate("1.1", "android");
    }

    //联系我们
    @OnClick(R.id.contactus)
    void contactus() {
        gotoActivity(ContactusActivity.class);
    }

    //关于我们
    @OnClick(R.id.about_us)
    void about_us() {
        gotoActivity(AboutusActivity.class);
    }

    //紧急联系人
    @OnClick(R.id.emergency_contact)
    void emergency_contact() {
        gotoActivity(EmergencyContactActivity.class);
    }


    //导航设置
    @OnClick(R.id.navigation_setting)
    void navigation_setting() {
        gotoActivity(NavigationSettingActivity.class);
    }

    //账号与安全
    @OnClick(R.id.accountsecurity)
    void accountsecurity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userBean", userInfoBean);
        gotoActivity(AccountsecurityActivity.class, bundle);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void contact(ContactBean bean) {
        if (bean == null) {
            return;
        }
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.setNeedCheckMd5(true);
        updateConfig.setNotifyImgRes(R.mipmap.app_logo);
        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.PLENTIFUL);
        UpdateAppUtils
                .getInstance()
                .apkUrl(bean.getApk())
                .uiConfig(uiConfig)
                .updateTitle("发现新版本V" + bean.getAndroid())
                .updateConfig(updateConfig)
                .updateContent(bean.getContent())
                .update();
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
