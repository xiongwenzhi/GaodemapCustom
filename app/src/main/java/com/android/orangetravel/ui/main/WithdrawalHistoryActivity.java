package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.annotation.BindEventBus;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.event.DeleteCarEvent;
import com.android.orangetravel.ui.mvp.BillPresenter;
import com.android.orangetravel.ui.mvp.BillView;
import com.android.orangetravel.ui.mvp.MyCarPresenter;
import com.android.orangetravel.ui.mvp.MyCarView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 提现记录
 */
public class WithdrawalHistoryActivity extends BaseActivity<BillPresenter> implements
        View.OnClickListener, BillView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.my_history_list)
    RecyclerView my_history_list;
    private CommonAdapter<WithDrawalHistoryBean> mRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawalhistory;
    }

    @Override
    public BillPresenter initPresenter() {
        return new BillPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(WithdrawalHistoryActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(WithdrawalHistoryActivity.this, R.color.title_bar_black));
        setTitleBar("提现记录");
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<WithDrawalHistoryBean>(mContext,
                R.layout.item_withdraw_history) {
            @Override
            protected void convert(ViewHolder mHolder, final WithDrawalHistoryBean item,
                                   final int position) {
                mHolder.setText(R.id.bank_name, item.getName())
                        .setText(R.id.add_time, item.getAdd_time()).
                        setText(R.id.amount, item.getAmount() + "元").
                        setText(R.id.tips, item.getTips());

            }
        };
        my_history_list.setLayoutManager(new LinearLayoutManager(mContext));
        my_history_list.setAdapter(mRvAdapter);
        my_history_list.addItemDecoration(new ListItemDecoration(mContext, 1f,
                R.color.activity_bg, false));
    }

    @Override
    public void requestData() {
        getPresenter().extract();
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void contact(ContactBean bean) {

    }

    @Override
    public void bankDefalut(BankDefault bean) {

    }

    @Override
    public void bankList(List<BankListDefault> bean) {

    }

    @Override
    public void WithdrawHistoryList(List<WithDrawalHistoryBean> bean) {
        mRvAdapter.addAllData(bean);
    }

    @Override
    public void verify(String bean) {

    }
}
