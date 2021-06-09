package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.base.BaseActivityRefresh;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.ui.mvp.OrderPresenter;
import com.android.orangetravel.ui.mvp.OrderView;

import java.util.List;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 我的行程
 */

public class MyTripActivity extends BaseActivityRefresh<OrderPresenter,RecyclerView> implements View.OnClickListener, OrderView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*全部订单*/
    @BindView(R.id.all_order)
    LinearLayout all_order;
    /**/
    @BindView(R.id.all_order_tv)
    TextView all_order_tv;
    @BindView(R.id.all_order_line)
    View all_order_line;
    /*已支付*/
    @BindView(R.id.payed_layout)
    LinearLayout payed_layout;
    @BindView(R.id.payed_tv)
    TextView payed_tv;
    @BindView(R.id.payed_tv_line)
    View payed_tv_line;
    /*未支付*/
    @BindView(R.id.Unpaid_layout)
    LinearLayout Unpaid_layout;
    @BindView(R.id.Unpaid_tv)
    TextView Unpaid_tv;
    @BindView(R.id.Unpaid_line)
    View Unpaid_line;
    /*已取消*/
    @BindView(R.id.cancle_layout)
    LinearLayout cancle_layout;
    @BindView(R.id.cancle_tv)
    TextView cancle_tv;
    @BindView(R.id.cancle_line)
    View cancle_line;
//    @BindView(R.id.trip_list)
//    RecyclerView trip_list;
    private CommonAdapter<OrderListBean> mRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_trip;
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(MyTripActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(MyTripActivity.this, R.color.title_bar_black));
        setTitleBar("我的行程");
        initListen();
        initRv();
    }

    private void initListen() {
        all_order.setOnClickListener(this);
        payed_layout.setOnClickListener(this);
        Unpaid_layout.setOnClickListener(this);
        cancle_layout.setOnClickListener(this);
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<OrderListBean>(mContext,
                R.layout.item_trip) {
            @Override
            protected void convert(ViewHolder mHolder, final OrderListBean item,
                                   final int position) {
                RecyclerView tripList = mHolder.getView(R.id.trip_details_list);
                initRvDetails(tripList, item.getData());
                mHolder.setText(R.id.date, item.getDate());
            }
        };
        mRefreshView.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshView.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.baidu.com");
                gotoActivity(WebviewActivity.class, bundle);
            }
        });
    }

    /**
     * 初始化Rv
     */
    private void initRvDetails(RecyclerView tripList, List<OrderListBean.DataBean> dataBeans) {
        CommonAdapter<OrderListBean.DataBean> mRvAdapter = new CommonAdapter<OrderListBean.DataBean>(mContext,
                R.layout.item_trip_details) {
            @Override
            protected void convert(ViewHolder mHolder, final OrderListBean.DataBean item,
                                   final int position) {
                mHolder.setText(R.id.way, item.getTypeName())
                        .setText(R.id.add_time, item.getAdd_time())
                        .setText(R.id.hint, item.getHint()).setText(R.id.sendTime, item.getSendTime())
                        .setText(R.id.origin, item.getOrigin())
                        .setText(R.id.type_name, item.getWay())
                        .setText(R.id.destination, item.getDestination()).
                        setVisible(R.id.sendTime, TextUtils.isEmpty(item.getSendTime()) ? false : true);
            }
        };
        tripList.setLayoutManager(new LinearLayoutManager(mContext));
        tripList.setAdapter(mRvAdapter);
        tripList.setNestedScrollingEnabled(false);
        tripList.addItemDecoration(new ListItemDecoration(mContext, 10f,
                R.color.activity_bg, false));
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mRvAdapter.getAllData().get(position).getId());
                gotoActivity(LtineraryDetailsActivity.class, bundle);
            }
        });
        mRvAdapter.addAllData(dataBeans);
    }

    @Override
    public void requestData() {
        getPresenter().orderList("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_order:
                setDefault();
                all_order_tv.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
                all_order_line.setVisibility(View.VISIBLE);
                getPresenter().orderList("");
                break;
            case R.id.payed_layout:
                setDefault();
                payed_tv.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
                payed_tv_line.setVisibility(View.VISIBLE);
                getPresenter().orderList("1");
                break;
            case R.id.Unpaid_layout:
                setDefault();
                Unpaid_tv.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
                Unpaid_line.setVisibility(View.VISIBLE);
                getPresenter().orderList("0");
                break;
            case R.id.cancle_layout:
                setDefault();
                cancle_tv.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
                cancle_line.setVisibility(View.VISIBLE);
                getPresenter().orderList("2");
                break;
        }
    }

    private void setDefault() {
        all_order_tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        payed_tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        Unpaid_tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        cancle_tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));

        all_order_line.setVisibility(View.GONE);
        payed_tv_line.setVisibility(View.GONE);
        Unpaid_line.setVisibility(View.GONE);
        cancle_line.setVisibility(View.GONE);
    }

    @Override
    public void orderList(List<OrderListBean> bean) {
        mRvAdapter.clearData();
        mRvAdapter.addAllData(bean);
        successAfter(bean.size());
//        mRefreshView.setVisibility(bean.size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void orderDetails(OrderListBean.DataBean bean) {

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
