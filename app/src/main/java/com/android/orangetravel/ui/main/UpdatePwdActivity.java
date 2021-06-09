package com.android.orangetravel.ui.main;

import android.text.TextUtils;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.ui.mvp.UserCenterPresenter;
import com.android.orangetravel.ui.mvp.UsercenterView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/23
 * 修改密码
 */

public class UpdatePwdActivity extends BaseActivity<UserCenterPresenter> implements UsercenterView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.input_old)
    EditText input_old;
    @BindView(R.id.new_pwd)
    EditText new_pwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_updatepwd;
    }

    @Override
    public UserCenterPresenter initPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(UpdatePwdActivity.this, R.color.white));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(UpdatePwdActivity.this, R.color.white));
        id_title_bar.setLeftIcon(R.mipmap.left_back);
        id_title_bar.setTitleBottomLineGone();
        setTitleBar("修改密码");
    }

    @OnClick(R.id.forget_pwd)
    void update_phone() {
        gotoActivity(GetMsgCodeActivity.class);
    }

    @OnClick(R.id.update_pwd)
    void update_pwd() {
        if (TextUtils.isEmpty(input_old.getText().toString())) {
            showToast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(new_pwd.getText().toString())) {
            showToast("请输入新密码");
            return;
        }
        Map map = new HashMap();
        map.put("old_password", input_old.getText().toString().trim());
        map.put("new_password", new_pwd.getText().toString().trim());
        getPresenter().resetPwd(map);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void verify(String bean) {

    }

    @Override
    public void verify(SendCodeBean bean) {

    }

    @Override
    public void verifyLogin(LoginBean bean) {

    }

    @Override
    public void resetPwd(ErrorMsgBean bean) {
        showToast("密码修改成功！");
        finish();
    }

    @Override
    public void userInfo(UserInfoBean bean) {

    }

    @Override
    public void toolList(List<ToolListBean> bean) {

    }

    @Override
    public void actiity(List<ToolListBean> bean) {

    }

    @Override
    public void noticeNew(List<MessageNotciList> bean) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
