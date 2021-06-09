package com.android.orangetravel.ui.main;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.MainActivity;
import com.android.orangetravel.R;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.BillPresenter;
import com.android.orangetravel.ui.mvp.BillView;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.widgets.ChooseBankCardPopup;
import com.android.orangetravel.ui.widgets.SettingModelPopup;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2020/12/21
 * 提现
 */

public class WithdrawalActivity extends BaseActivity<BillPresenter> implements View.OnClickListener,
        BillView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*提现布局*/
    @BindView(R.id.tips_layout)
    LinearLayout tips_layout;
    /*删除提示*/
    @BindView(R.id.delete_tips)
    ImageView delete_tips;
    /*添加银行卡*/
    @BindView(R.id.choose_card)
    LinearLayout choose_card;
    @BindView(R.id.bank_image_default)
    ImageView bank_image_default;
    @BindView(R.id.bank_card_default)
    TextView bank_card_default;
    @BindView(R.id.bank_name_default)
    TextView bank_name_default;
    @BindView(R.id.count_bank)
    TextView count_bank;
    @BindView(R.id.withdrawal_history)
    TextView withdrawal_history;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.emergency_btn)
    TextView emergency_btn;
    @BindView(R.id.input_money)
    EditText input_money;
    /*选择银行卡底部弹出框*/
    private ChooseBankCardPopup chooseCardModelPopup;
    List<BankListDefault> bankListDefaults = new ArrayList<>();
    private BankDefault bankDefault;
    private String bank_id = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawalt;
    }

    @Override
    public BillPresenter initPresenter() {
        return new BillPresenter(this);
    }

    @Override
    public void initView() {
        setTitleStyle();
    }

    private void setTitleStyle() {
        setmYangStatusBar(ContextCompat.getColor(WithdrawalActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(WithdrawalActivity.this, R.color.title_bar_black));
        setTitleBar("提现");
        id_title_bar.setTitleBottomLineGone();
        id_title_bar.setRightTextVisible(true);
        delete_tips.setOnClickListener(this);
        choose_card.setOnClickListener(this);
        emergency_btn.setOnClickListener(this);
        withdrawal_history.setOnClickListener(this);
    }


    @Override
    public void requestData() {
        getPresenter().billTips();
        getPresenter().bankDefalut();
        getPresenter().bankList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_tips:
                tips_layout.setVisibility(View.GONE);
                break;
            case R.id.withdrawal_history:
                gotoActivity(WithdrawalHistoryActivity.class);
                break;
            case R.id.choose_card:
                if (chooseCardModelPopup == null) {
                    chooseCardModelPopup = new ChooseBankCardPopup(WithdrawalActivity.this, bankListDefaults);
                }
                new XPopup.Builder(WithdrawalActivity.this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(chooseCardModelPopup/*.enableDrag(false)*/)
                        .show();
                chooseCardModelPopup.setOnItemClick(new ChooseBankCardPopup.OnItemMenuClick() {
                    @Override
                    public void chooseCard(BankListDefault bankListDefault) {
                        GlideUtil.loadImg(mContext, bankListDefault.getLogo(), bank_image_default);
                        bank_card_default.setText(bankListDefault.getCard());
                        chooseCardModelPopup.dismiss();
                        bank_id = bankListDefault.getId();
                        bank_name_default.setText(bankListDefault.getBank() + " " + bankDefault.getName());
                    }
                });
                break;
            case R.id.emergency_btn:
                if (TextUtils.isEmpty(input_money.getText().toString())) {
                    showToast("请输入提现金额");
                    return;
                }
                Map map = new HashMap();
                map.put("bank_id", bank_id);
                map.put("type", "bank");
                map.put("money", input_money.getText().toString());
                getPresenter().extractCash(map);
                break;
        }
    }

    @Override
    public void contact(ContactBean bean) {
        Constant.token = bean.getToken();
        Object object = bean.getTips();
        if (object instanceof String) {
            tips.setText(bean.getTips().toString());
        }
        if (object instanceof List) {

        }


    }

    @Override
    public void bankDefalut(BankDefault bean) {
        bankDefault = bean;
        GlideUtil.loadImg(mContext, bean.getLogo(), bank_image_default);
        bank_card_default.setText(bean.getCard());
        bank_name_default.setText(bean.getBank() + " " + bean.getName());
        count_bank.setText(bean.getCount() + "张");
        bank_id = bean.getId();
    }

    @Override
    public void bankList(List<BankListDefault> bean) {
        bankListDefaults.clear();
        bankListDefaults.addAll(bean);
    }

    @Override
    public void WithdrawHistoryList(List<WithDrawalHistoryBean> bean) {

    }

    @Override
    public void verify(String bean) {

    }


    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
