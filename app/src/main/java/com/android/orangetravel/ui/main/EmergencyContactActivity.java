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

import com.android.orangetravel.MainActivity;
import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.annotation.BindEventBus;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.rx.AccountFreezeEvent;
import com.android.orangetravel.base.utils.UserBiz;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.ui.mvp.UserCenterPresenter;
import com.android.orangetravel.ui.mvp.UsercenterView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/24
 * 紧急联系人
 */

@BindEventBus
public class EmergencyContactActivity extends BaseActivity<UserCenterPresenter> implements UsercenterView {
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
    /*紧急联系人列表*/
    @BindView(R.id.EmergencyList)
    RecyclerView EmergencyList;
    /*添加新 紧急联系人列表*/
    @BindView(R.id.add_emergency)
    TextView add_emergency;
    /*输入框布局*/
    @BindView(R.id.input_layout)
    LinearLayout input_layout;

    private CommonAdapter<ToolListBean> mRvAdapter;
    private int deletePostion = -1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_emergency_contact;
    }

    @Override
    public UserCenterPresenter initPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void initView() {
        setTitleBar("紧急联系人");
        setmYangStatusBar(ContextCompat.getColor(EmergencyContactActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(EmergencyContactActivity.this, R.color.title_bar_black));
//        inputListen();
        initRv();
    }

    @OnClick(R.id.emergency_btn)
    void emergency_btn() {
        gotoActivity(AddContactActivity.class);
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<ToolListBean>(mContext,
                R.layout.item_emergencycontact) {
            @Override
            protected void convert(ViewHolder mHolder, final ToolListBean item,
                                   final int position) {
                mHolder.setText(R.id.emergency_name, item.getName() + "(" + item.getTail() + ")");
                mHolder.setOnClickListener(R.id.delete_contact, position, new CommonOnClickListener() {
                    @Override
                    public void clickListener(View view, int position) {
                        deletePostion = position;
                        PromptDialog dialog = new PromptDialog(EmergencyContactActivity.this,
                                "确定删除吗？");
                        dialog.setTitle("温馨提示");
                        dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
                            @Override
                            public void onClick() {
                                Map map = new HashMap();
                                map.put("id", item.getId());
                                getPresenter().contactDel(map);
                            }
                        });
                        dialog.show();

                    }
                });
            }
        };
        EmergencyList.setLayoutManager(new LinearLayoutManager(mContext));
        EmergencyList.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        EmergencyList.setAdapter(mRvAdapter);

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
        getPresenter().contactList();
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
        showToast("删除成功！");
        mRvAdapter.removeData(deletePostion);

    }

    @Override
    public void userInfo(UserInfoBean bean) {

    }

    @Override
    public void toolList(List<ToolListBean> bean) {
        mRvAdapter.replaceData(bean);
    }

    @Override
    public void actiity(List<ToolListBean> bean) {

    }

    @Override
    public void noticeNew(List<MessageNotciList> bean) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    /**
     * 添加成功联系人
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountFreezeEvent(ToolListBean event) {
        getPresenter().contactList();
    }
}
