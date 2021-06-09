package com.android.orangetravel.ui.main;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;
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
import com.android.orangetravel.ui.widgets.ChooseBankListPopup;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author Mr Xiong
 * @date 2020/12/21
 * 添加银行卡
 */

public class AddBankActivity extends BaseActivity<BillPresenter>
        implements View.OnClickListener, BillView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.phone_addback)
    TextView phone_addback;
    @BindView(R.id.bank_name)
    TextView bank_name;
    /*姓名*/
    @BindView(R.id.name)
    TextView name;
    /*身份证号码*/
    @BindView(R.id.id_card)
    TextView id_card;
    /*银行卡卡号*/
    @BindView(R.id.input_card_num)
    TextView input_card_num;
    /*开户支行*/
    @BindView(R.id.branch)
    EditText branch;
    /*手机号*/
    @BindView(R.id.phone)
    EditText phone;
    /*验证码*/
    @BindView(R.id.msg_code)
    EditText msg_code;
    @BindView(R.id.send_msg)
    TextView send_msg;
    /*选择银行卡底部弹出框*/
    private ChooseBankListPopup chooseCardModelPopup;
    List<WithDrawalHistoryBean> withDrawalHistoryBeans = new ArrayList<>();
    private WithDrawalHistoryBean withDrawalHistoryBean = new WithDrawalHistoryBean();
    /*倒计时*/
    private Disposable mCountDown;
    private int mTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_addbank;
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
        setmYangStatusBar(ContextCompat.getColor(AddBankActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(AddBankActivity.this, R.color.title_bar_black));
        setTitleBar("添加银行卡");
        phone_addback.setOnClickListener(this);
    }


    @OnClick(R.id.choose_bank)
    void choose_bank() {
        if (chooseCardModelPopup == null) {
            chooseCardModelPopup = new ChooseBankListPopup(AddBankActivity.this, withDrawalHistoryBeans);
        }
        new XPopup.Builder(AddBankActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .asCustom(chooseCardModelPopup/*.enableDrag(false)*/)
                .show();
        chooseCardModelPopup.setOnItemClick(new ChooseBankListPopup.OnItemMenuClick() {
            @Override
            public void chooseCard(WithDrawalHistoryBean bankListDefault) {
                chooseCardModelPopup.dismiss();
                withDrawalHistoryBean = bankListDefault;
                bank_name.setText(bankListDefault.getName());
            }
        });
    }


    @OnClick(R.id.emergency_btn)
    void emergency_btn() {
        if (TextUtils.isEmpty(withDrawalHistoryBean.getName())) {
            showToast("请选择开户行");
            return;
        }
        if (TextUtils.isEmpty(input_card_num.getText().toString())) {
            showToast("请输入银行卡号");
            return;
        }
        if (TextUtils.isEmpty(branch.getText().toString())) {
            showToast("请输入开户支行");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            showToast("请输入手机号");
            return;
        }
        //   phone => 银行logo
//        name => 银行logo
//        car_number => 银行logo
//        logo => 银行logo
//        code => 联行号
//        bg => 背景图
//        bank => 开户行
//        branch => 开户支行
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone.getText().toString().trim());
        map.put("name", name.getText().toString());
        map.put("card_number", input_card_num.getText().toString());
        map.put("logo", withDrawalHistoryBean.getLogo());
        map.put("code", withDrawalHistoryBean.getCode());
        map.put("bg", withDrawalHistoryBean.getBg());
        map.put("bank", withDrawalHistoryBean.getName());
        map.put("branch", branch.getText().toString());
        //获取验证码
        getPresenter().bankEdit(map);
    }

    @OnClick(R.id.send_msg)
    void send_msg() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            showToast("请输入手机号");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone.getText().toString().trim());
        map.put("type", "driver_bank_code");
        //获取验证码
        getPresenter().verify(map);
    }

    @OnClick(R.id.support_card)
    void support_card() {
        gotoActivity(SystomSetCardActivity.class);
    }

    @Override
    public void requestData() {
        getPresenter().billTips();
        getPresenter().bankSystom();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_addback:
                PromptDialog dialog = new PromptDialog(AddBankActivity.this,
                        "银行预留的手机号是办理该银行卡是所填写的手机号码\n没有预、手机号忘记或者已停用，请联系银行客服更新处理");
                dialog.setTitle("手机号说明");
                dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
                    @Override
                    public void onClick() {
                    }
                });
                dialog.show();
                break;
        }
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
        withDrawalHistoryBeans.addAll(bean);
    }

    @Override
    public void verify(String bean) {
        showToast("验证码发送成功");
        mTime = 60;
        send_msg.setClickable(false);
        send_msg.setText(String.format("%s秒", String.valueOf(mTime)));
        mCountDown = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mTime == 1) {
                            send_msg.setClickable(true);
                            send_msg.setText("获取验证码");
                            mCountDown.dispose();
                            return;
                        }
                        mTime = mTime - 1;
                        send_msg.setText(String.format("%s秒", String.valueOf(mTime)));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCountDown.dispose();
                    }
                });
    }


    @Override
    public void showErrorMsg(String msg) {

    }
}
