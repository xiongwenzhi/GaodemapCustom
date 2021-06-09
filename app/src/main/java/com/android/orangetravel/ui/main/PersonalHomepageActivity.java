package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.CircleImageView;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.ui.widgets.pop.ListTestPopup;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/2/25
 * 个人主页
 */

public class PersonalHomepageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.frg_mine_header)
    CircleImageView header;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.serviceDay)
    TextView serviceDay;
    @BindView(R.id.sumOrder)
    TextView sumOrder;
    @BindView(R.id.sumWater)
    TextView sumWater;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.id_number)
    TextView id_number;
    @BindView(R.id.card_time)
    TextView card_time;
    private ListTestPopup listTestPopup;
    private UserInfoBean userBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("userBean")) {
            userBean = (UserInfoBean) bundle.getSerializable("userBean");
            setData();
        }
        setmYangStatusBar(ContextCompat.getColor(PersonalHomepageActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(PersonalHomepageActivity.this, R.color.title_bar_black));
        setTitleBar("个人主页");
    }


    private void setData() {
        GlideUtil.loadImg(mContext, userBean.getAvatar(), header);
        name.setText(userBean.getName());
        serviceDay.setText(userBean.getServiceDay() + "");
        sumOrder.setText(userBean.getSumOrder() + "");
        sumWater.setText(userBean.getSumWater() + "");
        city.setText(userBean.getCity() + "");
        user_name.setText(userBean.getName() + "");
        id_number.setText(userBean.getId_number() + "");
        card_time.setText(userBean.getCard_time() + "");
    }

    @OnClick(R.id.look_details_personal)
    void look_details_personal() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "证件异常");
        gotoActivity(AddCarActivity.class, bundle);
    }

    @OnClick(R.id.change_city)
    void change_city() {
        if (listTestPopup == null) {
            listTestPopup = new ListTestPopup(PersonalHomepageActivity.this, "注意事项",
                    "更改接单城市需满足目标城市的准入条件，存在接单类型变更或无法接单、不可改回原城市的风险，请谨慎选择",
                    "确定", "取消");
        }
        new XPopup.Builder(PersonalHomepageActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .asCustom(listTestPopup)
                .show();
    }

    @OnClick(R.id.real_name)
    void real_name() {
        gotoActivity(RealNamepageActivity.class);
    }


    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View v) {

    }
}
