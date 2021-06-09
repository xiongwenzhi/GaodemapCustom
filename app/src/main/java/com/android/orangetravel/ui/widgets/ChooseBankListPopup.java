package com.android.orangetravel.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.main.AddBankActivity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择银行卡
 */
public class ChooseBankListPopup extends BottomPopupView implements View.OnClickListener {
    private ImageView dismiss_pop;
    private RecyclerView bank_list;
    private CommonAdapter<WithDrawalHistoryBean> mRvAdapter;
    private List<WithDrawalHistoryBean> bankListDefaults = new ArrayList<>();
    private int mposition = 0;

    public ChooseBankListPopup(@NonNull Context context, List<WithDrawalHistoryBean> bankListDefaults) {
        super(context);
        this.bankListDefaults = bankListDefaults;
    }

    public ChooseBankListPopup(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_choose_card_pop_list;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        dismiss_pop = findViewById(R.id.dismiss_pop);
        bank_list = findViewById(R.id.bank_list);
        dismiss_pop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<WithDrawalHistoryBean>(getContext(),
                R.layout.item_bank) {
            @Override
            protected void convert(ViewHolder mHolder, final WithDrawalHistoryBean item,
                                   final int position) {
                mHolder.loadImage(mContext, item.getLogo(), R.id.bank_logo).
                        setText(R.id.bank_name, item.getName()).
                        setVisible(R.id.bank_check, mposition == position ? true
                                : false);
            }
        };
        bank_list.setLayoutManager(new LinearLayoutManager(getContext()));
        bank_list.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                mposition = position;
                mRvAdapter.notifyDataSetChanged();
                if (onItemMenuClick != null) {
                    onItemMenuClick.chooseCard(mRvAdapter.getAllData().get(position));
                }
            }
        });
        mRvAdapter.addAllData(bankListDefaults);
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "知乎评论 onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getAppHeight(getContext()) * 0.4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    public interface OnItemMenuClick {
        void chooseCard(WithDrawalHistoryBean bankListDefault);
    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}