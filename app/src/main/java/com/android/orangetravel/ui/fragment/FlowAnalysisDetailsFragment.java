package com.android.orangetravel.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseFragment;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.ui.mvp.OrderPresenter;
import com.android.orangetravel.ui.mvp.OrderView;

import java.util.List;

import butterknife.BindView;

/**
 * 流水明细
 *
 * @author xiongwenzhi
 * @date 2021/2/7
 */
public class FlowAnalysisDetailsFragment extends BaseFragment<OrderPresenter> implements OrderView {
    @BindView(R.id.flowing_water_list)
    RecyclerView flowing_water_list;
    private CommonAdapter<String> mRvAdapter;

    public static FlowAnalysisDetailsFragment newInstance(String status) {
        Bundle bundle = new Bundle();
        FlowAnalysisDetailsFragment fragment = new FlowAnalysisDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_flowingwater_details;
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }


    @Override
    public void initView() {
        // 初始化Rv
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<String>(mContext,
                R.layout.item_flowingwater_details) {
            @Override
            protected void convert(ViewHolder mHolder, final String item,
                                   final int position) {
            }
        };
        flowing_water_list.setLayoutManager(new LinearLayoutManager(mContext));
        flowing_water_list.setAdapter(mRvAdapter);
        flowing_water_list.addItemDecoration(new ListItemDecoration(mContext, 1f,
                R.color.line_light_color, false));
        mRvAdapter.addData("");
        mRvAdapter.addData("");
        mRvAdapter.addData("");
        mRvAdapter.addData("");
        mRvAdapter.addData("");
    }

    @Override
    public void requestData() {
        // 每次显示Fragment的时候都刷新
        getPresenter().billDetails();
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

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}