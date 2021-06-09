package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.OrderPresenter;
import com.android.orangetravel.ui.mvp.OrderView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/3/29
 */

public class ChargeDetailsActivity extends BaseActivity<OrderPresenter> implements OrderView {
    @BindView(R.id.charge_list)
    RecyclerView charge_list;
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.driver_price)
    TextView driver_price;
    private CommonAdapter<ChargeDetailsBean.AmountDetailBean> mRvAdapter;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_charge_details;
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(ChargeDetailsActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(ChargeDetailsActivity.this, R.color.title_bar_black));
        setTitleBar("收费明细");
        initRv();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("id")) {
                id = bundle.getString("id");
                getPresenter().orderAmountDetail(id);
            }
        }
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<ChargeDetailsBean.AmountDetailBean>(mContext,
                R.layout.item_charge_details) {
            @Override
            protected void convert(ViewHolder mHolder, final ChargeDetailsBean.AmountDetailBean item,
                                   final int position) {
                RecyclerView recyclerView = mHolder.getView(R.id.charge_details_list);
                initRvDetails(recyclerView);
                mHolder.setText(R.id.title, item.getName()).setText(R.id.price, item.getAmount());
            }
        };
        charge_list.setLayoutManager(new LinearLayoutManager(mContext));
        charge_list.setAdapter(mRvAdapter);
        charge_list.addItemDecoration(new ListItemDecoration(mContext, 5f,
                R.color.white, false));
    }

    /**
     * 初始化Rv
     */
    private void initRvDetails(RecyclerView recyclerView) {
        CommonAdapter<WithDrawalHistoryBean> mRvAdapter = new CommonAdapter<WithDrawalHistoryBean>(mContext,
                R.layout.item_charge_details_small) {
            @Override
            protected void convert(ViewHolder mHolder, final WithDrawalHistoryBean item,
                                   final int position) {

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mRvAdapter);
        mRvAdapter.addData(new WithDrawalHistoryBean());
        recyclerView.addItemDecoration(new ListItemDecoration(mContext, 5f,
                R.color.white, false));
    }


    @OnClick(R.id.jijia_guize)
    void jijia_guize() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.baidu.com");
        gotoActivity(WebviewActivity.class, bundle);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void orderList(List<OrderListBean> bean) {

    }

    @Override
    public void orderDetails(OrderListBean.DataBean bean) {

    }

    @Override
    public void FlowAnaly(FlowAnalyBean bean) {

    }

    @Override
    public void chargeDetails(ChargeDetailsBean bean) {
        driver_price.setText(bean.getDriver_price() + "元");
        mRvAdapter.addAllData(bean.getAmountDetail());
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
