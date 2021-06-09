package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.CommonDialog;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.ui.mvp.OrderPresenter;
import com.android.orangetravel.ui.mvp.OrderView;
import com.android.orangetravel.ui.widgets.utils.ConsultUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 行程详情
 */

public class LtineraryDetailsActivity extends BaseActivity<OrderPresenter> implements View.OnClickListener, OrderView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tailNumber)
    TextView tailNumber;
    @BindView(R.id.origin)
    TextView origin;
    @BindView(R.id.destination)
    TextView destination;
    private String id;
    private OrderListBean.DataBean orderListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_itinerary_details;
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(LtineraryDetailsActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(LtineraryDetailsActivity.this, R.color.title_bar_black));
        setTitleBar("行程详情");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("id")) {
                id = bundle.getString("id");
            }
        }
    }

    @OnClick(R.id.charge_details)
    void charge_details() {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        gotoActivity(ChargeDetailsActivity.class, bundle);
    }


    @Override
    public void requestData() {
        getPresenter().orderDetails(id);
    }


    @OnClick(R.id.serving)
    void serving() {
        if (TextUtils.isEmpty(orderListBean.getServing())) {
            return;
        }
        final String[] titles = {"呼叫" + orderListBean.getServing()};
        CommonDialog dialog = new CommonDialog(mContext, titles);
        dialog.setOnClickListener(new CommonDialog.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    /*呼叫客服*/
                    case 0:
                        ConsultUtil.phoneConsult(LtineraryDetailsActivity.this, orderListBean.getServing());
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void orderList(List<OrderListBean> bean) {

    }

    @Override
    public void orderDetails(OrderListBean.DataBean bean) {
        orderListBean = bean;
        hint.setText(bean.getHint());
        price.setText(bean.getPrice());
        tailNumber.setText(bean.getTailNumber());
        origin.setText(bean.getOrigin());
        destination.setText(bean.getDestination());
    }

    @Override
    public void FlowAnaly(FlowAnalyBean bean) {

    }

    @Override
    public void chargeDetails(ChargeDetailsBean bean) {

    }


    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
