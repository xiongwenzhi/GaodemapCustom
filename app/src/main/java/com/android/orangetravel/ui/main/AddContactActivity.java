package com.android.orangetravel.ui.main;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.utils.EventBusUtil;
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
 * @date 2020/12/24
 * 添加紧急联系人
 */

public class AddContactActivity extends BaseActivity<UserCenterPresenter> implements UsercenterView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*联系人*/
    @BindView(R.id.input_emergency_name)
    EditText input_emergency_name;
    /*电话号码*/
    @BindView(R.id.input_emergency_phone)
    EditText input_emergency_phone;
    /*添加按钮*/
    @BindView(R.id.emergency_btn)
    TextView emergency_btn;
    /*输入框布局*/
    @BindView(R.id.input_layout)
    LinearLayout input_layout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public UserCenterPresenter initPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void initView() {
        setTitleBar("添加紧急联系人");
        setmYangStatusBar(ContextCompat.getColor(AddContactActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(AddContactActivity.this, R.color.title_bar_black));
        inputListen();
    }

    @OnClick(R.id.emergency_btn)
    void emergency_btn() {
        if (TextUtils.isEmpty(input_emergency_name.getText().toString())) {
            showToast("请输入紧急联系人姓名");
            return;
        }
        if (TextUtils.isEmpty(input_emergency_phone.getText().toString())) {
            showToast("请输入紧急联系人电话");
            return;
        }

        Map map = new HashMap();
        map.put("name", input_emergency_name.getText().toString());
        map.put("phone", input_emergency_phone.getText().toString());
        getPresenter().contactEdit(map);
    }


    private void inputListen() {
        input_emergency_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setBtnbg();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_emergency_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setBtnbg();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBtnbg() {
        if (TextUtils.isEmpty(input_emergency_phone.getText()) ||
                TextUtils.isEmpty(input_emergency_name.getText())) {
            emergency_btn.setBackgroundResource(R.drawable.shape_ellipse_grey_10dp);
        } else {
            emergency_btn.setBackgroundResource(R.drawable.shape_ellipse_green_10dp);
        }
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
        showToast("添加成功！");
        EventBusUtil.post(new ToolListBean());
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
