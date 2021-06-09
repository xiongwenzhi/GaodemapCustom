package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/27
 * 我的资质
 */

public class MyQualificationsActivity extends BaseActivity<CommonPresenter> implements View.OnClickListener, CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
//    @BindView(R.id.driver_card)
//    ImageView driver_card;
//    @BindView(R.id.car_card)
//    ImageView car_card;
//    @BindView(R.id.driver_card_no)
//    ImageView driver_card_no;
//    @BindView(R.id.car_card_no)
//    ImageView car_card_no;
    /*已获得资质*/
    @BindView(R.id.qualificationList)
    RecyclerView qualificationList;
    /*未获得资质*/
    @BindView(R.id.notQualifiedList)
    RecyclerView notQualifiedList;
    private CommonAdapter<WithDrawalHistoryBean> mRvAdapter;
    private CommonAdapter<BillListBean> mnotQualifiedAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qualifications;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(MyQualificationsActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(MyQualificationsActivity.this, R.color.title_bar_black));
        setTitleBar("我的资质");
        initRv();
        initnotQualifiedRv();
    }

    /**
     * 未获得资质
     */
    private void initnotQualifiedRv() {
        mnotQualifiedAdapter = new CommonAdapter<BillListBean>(mContext,
                R.layout.item_notqualified) {
            @Override
            protected void convert(ViewHolder mHolder, final BillListBean item,
                                   final int position) {
                mHolder.loadImage(mContext, item.getImage(), R.id.driver_card_no)
                        .setText(R.id.name, item.getName()).setText(R.id.subTitle,
                        item.getSubTitle());

            }
        };
        notQualifiedList.setLayoutManager(new LinearLayoutManager(mContext));
        notQualifiedList.setAdapter(mnotQualifiedAdapter);
        notQualifiedList.addItemDecoration(new ListItemDecoration(mContext, 10f,
                R.color.activity_bg, false));
        mnotQualifiedAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.baidu.com");
                gotoActivity(WebviewActivity.class, bundle);
            }
        });
    }


//    @OnClick(R.id.jsy_car)
//    void jsy_car(){
//        gotoActivity(CompliancecertificationActivity.class);
//    }


    /**
     * 已获得资质
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<WithDrawalHistoryBean>(mContext,
                R.layout.item_qualification) {
            @Override
            protected void convert(ViewHolder mHolder, final WithDrawalHistoryBean item,
                                   final int position) {
                mHolder.loadImage(mContext, item.getImage(), R.id.driver_card)
                        .setText(R.id.name, item.getName()).setText(R.id.subTitle,
                        item.getSubTitle());

            }
        };
        qualificationList.setLayoutManager(new LinearLayoutManager(mContext));
        qualificationList.setAdapter(mRvAdapter);
        qualificationList.addItemDecoration(new ListItemDecoration(mContext, 10f,
                R.color.activity_bg, false));
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                gotoActivity(CompliancecertificationActivity.class);
            }
        });
    }

    @Override
    public void requestData() {
        getPresenter().qualification();
        getPresenter().notQualified();
    }

    @Override
    public void onClick(View v) {

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
        mnotQualifiedAdapter.clearData();
        mnotQualifiedAdapter.addAllData(bean);
    }

    @Override
    public void DiveList(List<WithDrawalHistoryBean> bean) {
        mRvAdapter.clearData();
        mRvAdapter.addAllData(bean);
    }

    @Override
    public void Compliance(ComplianceBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
