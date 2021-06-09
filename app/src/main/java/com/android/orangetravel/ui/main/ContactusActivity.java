package com.android.orangetravel.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.CommonDialog;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/22
 * 联系我们
 */

public class ContactusActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.service)
    TextView service;
    @BindView(R.id.url)
    TextView url;
    @BindView(R.id.mini)
    TextView mini;
    @BindView(R.id.address)
    TextView address;
    ContactBean contactBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contactus;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(ContactusActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(ContactusActivity.this, R.color.title_bar_black));
        setTitleBar("联系我们");
    }

    @Override
    public void requestData() {
        getPresenter().contact();
    }

    @OnClick(R.id.call_phone)
    void call_phone() {
        if (contactBean == null) {
            return;
        }
        final String[] titles = {"呼叫" + contactBean.getService()};
        CommonDialog dialog = new CommonDialog(mContext, titles);
        dialog.setOnClickListener(new CommonDialog.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    /*呼叫客服*/
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactBean.getService()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @OnClick(R.id.call_url)
    void call_url() {
        if (contactBean == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", contactBean.getUrl());
        gotoActivity(WebviewActivity.class, bundle);
    }

    @Override
    public void contact(ContactBean bean) {
        contactBean = bean;
        service.setText(bean.getService());
        url.setText(bean.getUrl());
        mini.setText(bean.getMini());
        address.setText(bean.getAddress());
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
