package com.android.orangetravel.ui.main;

import android.view.View;
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
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;

import java.util.List;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2020/12/21
 * 我的钱包
 */

public class WalletActivity extends BaseActivity<CommonPresenter> implements View.OnClickListener, CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*钱包列表*/
    @BindView(R.id.wallet_list)
    RecyclerView wallet_list;
    /*全部*/
    @BindView(R.id.wallet_all)
    TextView wallet_all;
    /*仅收入*/
    @BindView(R.id.waller_income)
    TextView waller_income;
    /*仅支出*/
    @BindView(R.id.wallet_expenditure)
    TextView wallet_expenditure;
    /*提现*/
    @BindView(R.id.withdrawal)
    TextView withdrawal;
    /*余额*/
    @BindView(R.id.balance)
    TextView balance;
    /*可用余额*/
    @BindView(R.id.actual)
    TextView actual;
    private CommonAdapter<BillListBean> mRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setTitleStyle();
        initRv();
    }

    private void setTitleStyle() {
        setmYangStatusBar(ContextCompat.getColor(WalletActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(WalletActivity.this, R.color.title_bar_black));
        setTitleBar("钱包");
        id_title_bar.setTitleBottomLineGone();
        id_title_bar.setRightTextVisible(true);
        id_title_bar.setRightTextColor(ContextCompat.getColor(mContext, R.color.white));
        wallet_all.setOnClickListener(this);
        waller_income.setOnClickListener(this);
        wallet_expenditure.setOnClickListener(this);
        withdrawal.setOnClickListener(this);
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<BillListBean>(mContext,
                R.layout.item_wallet_list) {
            @Override
            protected void convert(ViewHolder mHolder, final BillListBean item,
                                   final int mposition) {
                RecyclerView walletDeitals = mHolder.getView(R.id.wallet__details_list);
                initDetailsRv(walletDeitals, item.getData());
                mHolder.setText(R.id.date_wallet, item.getDate()).
                        setText(R.id.all_money_wallet, item.getTotal());
            }
        };
        wallet_list.setLayoutManager(new LinearLayoutManager(mContext));
        wallet_list.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        wallet_list.setAdapter(mRvAdapter);


    }

    /**
     * 初始化明细列表
     */
    private void initDetailsRv(RecyclerView recyclerView, List<BillListBean.DataBean> dataBeanList) {
        CommonAdapter<BillListBean.DataBean> mRvAdapter = new CommonAdapter<BillListBean.DataBean>(mContext,
                R.layout.item_wallet_details_item) {
            @Override
            protected void convert(ViewHolder mHolder, final BillListBean.DataBean item,
                                   final int mposition) {
                mHolder.setText(R.id.title_wallet, item.getTitle()).
                        setText(R.id.only_money_wallet, item.getMoney());
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        recyclerView.setAdapter(mRvAdapter);
        mRvAdapter.addAllData(dataBeanList);


    }

    @Override
    public void requestData() {
        getPresenter().balance();
        getPresenter().bill("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_all:
                wallet_all.setTextColor(ContextCompat.getColor(mContext, R.color.title_bar_black));
                waller_income.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                wallet_expenditure.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                getPresenter().bill("");
                break;
            case R.id.waller_income: //仅收入
                waller_income.setTextColor(ContextCompat.getColor(mContext, R.color.title_bar_black));
                wallet_all.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                wallet_expenditure.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                getPresenter().bill("1");
                break;
            case R.id.wallet_expenditure://仅支出
                wallet_expenditure.setTextColor(ContextCompat.getColor(mContext, R.color.title_bar_black));
                wallet_all.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                waller_income.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                getPresenter().bill("0");
                break;
            case R.id.withdrawal:
                gotoActivity(WithdrawalActivity.class);
                break;
        }
    }

    @Override
    public void contact(ContactBean bean) {
    }

    @Override
    public void balance(BalanceBean bean) {
//        showToast(bean.getOther().getQuestion());
        balance.setText(bean.getDriverInfo().getBalance());
        actual.setText("可用余额:"+bean.getDriverInfo().getActual());
    }

    @Override
    public void loginOut(ErrorMsgBean bean) {

    }

    @Override
    public void billList(List<BillListBean> bean) {
        mRvAdapter.clearData();
        mRvAdapter.addAllData(bean);
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
