package com.android.orangetravel.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.android.orangetravel.MainActivity;
import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.swipeBack.ActivityLifecycleManage;
import com.android.orangetravel.base.utils.ConstantUtil;
import com.android.orangetravel.base.utils.OSUtils;
import com.android.orangetravel.base.utils.PacketUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.ui.mvp.UserCenterPresenter;
import com.android.orangetravel.ui.mvp.UsercenterView;
import com.android.orangetravel.ui.widgets.pop.PrivacyPopup;
import com.android.orangetravel.ui.widgets.view.SeparatorPhoneEditView;
import com.google.gson.JsonObject;
import com.lxj.xpopup.XPopup;

import org.json.JSONException;
import org.json.JSONObject;

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
 * @date 2021/3/3
 * 登录
 */

public class LoginActivity extends BaseActivity<UserCenterPresenter> implements View.OnClickListener, UsercenterView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*验证码登录*/
    @BindView(R.id.login_type)
    TextView login_type;
    /*获取验证码*/
    @BindView(R.id.get_code)
    TextView get_code;
    @BindView(R.id.right_send)
    LinearLayout right_send;
    @BindView(R.id.input_phone_login)
    SeparatorPhoneEditView input_phone_login;
    /*密码或验证码*/
    @BindView(R.id.input_pwd)
    AppCompatEditText input_pwd;
    @BindView(R.id.show_pwd)
    ImageView show_pwd;
    /*手机号*/
    private PrivacyPopup privacyPopup;
    private PrivacyPopup confimPop;
    boolean isCode = false;//是否是发送验证码
    /*倒计时*/
    private Disposable mCountDown;
    private int mTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public UserCenterPresenter initPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(LoginActivity.this, R.color.white));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
        setTitleBar("");
        id_title_bar.setTitleBottomLineGone();
        showTips();

    }

    @OnClick(R.id.show_pwd)
    void show_pwd() {
        if (input_pwd.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {//如果现在是显示密码模式
            input_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            show_pwd.setImageResource(R.mipmap.show_pwd);
        } else {
            input_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            show_pwd.setImageResource(R.mipmap.unshow_pwd);
        }

    }

    //登录类型切换
    @OnClick(R.id.login_type)
    void login_type() {
        if ("短信验证码登录".equals(login_type.getText().toString())) {
            login_type.setText("账号密码登录");
            right_send.setVisibility(View.GONE);
            get_code.setVisibility(View.VISIBLE);
            input_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_pwd.setSelection(input_pwd.getText().length());
            isCode = true;
        } else {
            isCode = false;
            login_type.setText("短信验证码登录");
            get_code.setVisibility(View.GONE);
            right_send.setVisibility(View.VISIBLE);
            input_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input_pwd.setSelection(input_pwd.getText().length());
        }

    }


    @OnClick(R.id.legalprovisions)
    void legalprovisions() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.baidu.com");
        bundle.putString("title", "法律条款与平台规则");
        gotoActivity(WebviewActivity.class, bundle);
    }

    @OnClick(R.id.login_btn)
    void login_btn() {
        if (TextUtils.isEmpty(input_phone_login.getText().toString())) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(input_pwd.getText().toString())) {
            if (isCode) {
                showToast("请输入验证码");
            } else {
                showToast("请输入密码");
            }
            return;
        }

        Map<String, Object> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("name", Build.MODEL);
            object.put("system", android.os.Build.BRAND);
            object.put("version", Build.VERSION.RELEASE);
            object.put("model", "Android");
            object.put("uuid", PacketUtil.getUUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("account", input_phone_login.getText().toString().replaceAll(" ", ""));
        map.put("equipment", object);// 登录设备信息 JSON格式
        map.put(isCode ? "captcha" : "password", input_pwd.getText().toString());
        //获取验证码
        getPresenter().Login(map);


    }

    @OnClick(R.id.get_code)
    void get_code() {
        if (TextUtils.isEmpty(input_phone_login.getText().toString())) {
            showToast("请输入手机号");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", input_phone_login.getText().toString().trim());
        //获取验证码
        getPresenter().verify(map);
    }


    private void showTips() {
        if (privacyPopup == null) {
            privacyPopup = new PrivacyPopup(LoginActivity.this, "安橙法律条款及隐私政策",
                    "更改接单城市需满足目标城市的准入条件，更改接单城市需满足目标城市的准入条件更改接单城市需满足目标城市的准入条件更改接单城市需满足目标城市的准入条件更改接单城市需满足目标城市的准入条件存在接单类型变更或无法接单、不可改回原城市的风险，请谨慎选择",
                    "同意", "不同意", true);
        }
        new XPopup.Builder(LoginActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(false)
                .dismissOnTouchOutside(false)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .asCustom(privacyPopup)
                .show();
        privacyPopup.setOnItemClick(new PrivacyPopup.OnItemMenuClick() {
            @Override
            public void confim() {
                privacyPopup.dismiss();
                if (confimPop != null) {
                    confimPop.dismiss();
                }
            }

            @Override
            public void cancle() {
                if (confimPop == null) {
                    confimPop = new PrivacyPopup(LoginActivity.this, "温馨提示",
                            "我们非常重视对您隔热信息的保护，承诺严格按照" +
                                    "安橙车主隐私政策保护及处理您的信息，如不同意" +
                                    "该政策，很遗憾我们将无法提供服务",
                            "再次查看", "退出应用", false);
                }
                new XPopup.Builder(LoginActivity.this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(false)
                        .dismissOnTouchOutside(false)
                        .isThreeDrag(false)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(confimPop)
                        .show();
                privacyPopup.dismiss();
                confimPop.setOnItemClick(new PrivacyPopup.OnItemMenuClick() {
                    @Override
                    public void confim() {
                        if (privacyPopup == null) {
                            privacyPopup = new PrivacyPopup(LoginActivity.this, "安橙法律条款及隐私政策",
                                    "更改接单城市需满足目标城市的准入条件，更改接单城市需满足目标城市的准入条件更改接单城市需满足目标城市的准入条件更改接单城市需满足目标城市的准入条件更改接单城市需满足目标城市的准入条件存在接单类型变更或无法接单、不可改回原城市的风险，请谨慎选择",
                                    "同意", "不同意", true);
                        }
                        new XPopup.Builder(LoginActivity.this)
                                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                                .enableDrag(false)
                                .dismissOnTouchOutside(false)
                                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                                .asCustom(privacyPopup)
                                .show();
                    }

                    @Override
                    public void cancle() {
//                        finish();
                        ActivityLifecycleManage.getInstance().AppExit(LoginActivity.this);
                    }
                });
            }
        });
    }


    @Override
    public void requestData() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("account", "15279168829");
//        map.put("captcha", "15279168829");
//        //获取验证码
//        getPresenter().verifyLogin(map);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }


    @Override
    public void verify(String bean) {
        showToast("验证码发送成功");
        mTime = 60;
        get_code.setClickable(false);
        get_code.setText(String.format("%s秒", String.valueOf(mTime)));
        mCountDown = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mTime == 1) {
                            get_code.setClickable(true);
                            get_code.setText("获取验证码");
                            mCountDown.dispose();
                            return;
                        }
                        mTime = mTime - 1;
                        get_code.setText(String.format("%s秒", String.valueOf(mTime)));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCountDown.dispose();
                    }
                });
    }

    @Override
    public void verify(SendCodeBean bean) {

    }

    @Override
    public void verifyLogin(LoginBean bean) {
        SPUtil.put(ConstantUtil.LOGIN_STATE, true);
        SPUtil.put(ConstantUtil.USER_TOKEN, bean.getToken());
        gotoActivity(MainActivity.class);
    }

    @Override
    public void resetPwd(ErrorMsgBean bean) {

    }

    @Override
    public void userInfo(UserInfoBean bean) {

    }

    @Override
    public void toolList(List<ToolListBean> bean) {

    }

    @Override
    public void actiity(List<ToolListBean> bean) {

    }

    @Override
    public void noticeNew(List<MessageNotciList> bean) {

    }

    @Override
    protected void onDestroy() {
        // 倒计时
        if (null != mCountDown && !mCountDown.isDisposed()) {
            mCountDown.dispose();
        }
        super.onDestroy();
    }
}
