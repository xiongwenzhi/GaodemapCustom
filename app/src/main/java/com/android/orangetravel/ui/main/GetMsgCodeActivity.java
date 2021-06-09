package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.BillPresenter;
import com.android.orangetravel.ui.mvp.BillView;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.widgets.view.VerificationCodeInput;

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
 * @date 2020/12/22
 * 获取验证码
 */

public class GetMsgCodeActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.verificationCodeInput)
    VerificationCodeInput verificationCodeInput;
    @BindView(R.id.phone_num)
    TextView phone_num;
    @BindView(R.id.send_msg)
    TextView send_msg;
    private String phone;
    /*倒计时*/
    private Disposable mCountDown;
    private int mTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_getmsgcode;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(GetMsgCodeActivity.this, R.color.white));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(GetMsgCodeActivity.this, R.color.white));
        id_title_bar.setLeftIcon(R.mipmap.left_back);
        id_title_bar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id_title_bar.setTitleBottomLineGone();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("phone")) {
                phone = bundle.getString("phone");
                phone_num.setText("验证码已经发送到您的手机\n+86 " + phone);
                getCode();
            }
        }
        verificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone.trim());
                map.put("captcha", content);
                getPresenter().modifyPhone(map);
            }
        });
    }

    @OnClick(R.id.send_msg)
    void send_msg() {
        getCode();
    }


    private void getCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone.trim());
        map.put("type", "driver_modify_code");
        new BillPresenter(new BillView() {
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

            }

            @Override
            public void verify(String bean) {
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

            @Override
            public void showLoadingDialog() {

            }

            @Override
            public void dismissLoadingDialog() {

            }
        }).verify(map);
    }

    @Override
    public void requestData() {

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

    }

    @Override
    public void DiveList(List<WithDrawalHistoryBean> bean) {

    }

    @Override
    public void Compliance(ComplianceBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
