package com.android.orangetravel.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.orangetravel.MainActivity;
import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.permission.PermissionConstant;
import com.android.orangetravel.base.permission.PermissionPresenter;
import com.android.orangetravel.base.permission.PermissionView;
import com.android.orangetravel.base.utils.ConstantUtil;
import com.android.orangetravel.base.utils.DisplayUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.widgets.dialog.BaseUIDialog;
import com.android.orangetravel.base.widgets.dialog.MessageDialog;
import com.android.orangetravel.base.widgets.dialog.PrivacyDialog;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;


/**
 * 欢迎页
 *
 * @author yangfei
 */
public class WelcomeActivity extends BaseActivity implements PermissionView {

    /*倒计时*/
    private CountDownTimer mCountDownTimer;
    /*提示弹出框*/
    private PromptDialog mPromptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 欢迎页设置了WelcomeTheme这个主题，
        // Activity显示后把Window背景设置成透明
        super.getWindow().setBackgroundDrawableResource(R.color.trans);
        // 欢迎页设置全屏
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_welcome;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
    }

    @Override
    public void requestData() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isFirse = (boolean) SPUtil.get("isFirst", true);
        if (isFirse) { //如果是第一次 则要弹一个用户协议的对话框
            showPrivacy();
        } else {
            // 请求权限
            PermissionPresenter.getInstance()
                    .init(this)
                    .addPermission(PermissionConstant.READ_PHONE_STATE)
                    .addPermission(PermissionConstant.READ_EXTERNAL_STORAGE)
                    .addPermission(PermissionConstant.CAMERA)
                    .addPermission(PermissionConstant.WRITE_EXTERNAL_STORAGE)
                    .addPermission(PermissionConstant.ACCESS_FINE_LOCATION)
                    .addPermission(PermissionConstant.ACCESS_COARSE_LOCATION)
                    .requestPermissions(this);
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        // 销毁
        PermissionPresenter.getInstance().destroy();
        // 倒计时
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionPresenter.getInstance().onRequestPermissionsResult(requestCode,
                permissions, grantResults);
    }

    @Override
    public void onAuthSuccess() {
        // 倒计时
        mCountDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if ((boolean) SPUtil.get(ConstantUtil.WELCOME_STATE, false)) {
                    gotoActivity(MainActivity.class);// 主界面
                    finish();
                    overridePendingTransition(R.anim.act_main_enter_anim, 0);
                } else {
                    gotoActivity(MainActivity.class);// 引导页
                    finish();
                }
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onAuthFailure() {
        // 消息对话框
        new MessageDialog.Builder(this)
                // 标题可以不用填写
                .setTitle("温馨提示")
                // 内容必须要填写
                .setMessage("权限请求失败，要正常使用需，前往设置同意权限?")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                .setCanceledOnTouchOutside(false)
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseUIDialog dialog) {
                        PermissionPresenter.getInstance().gotoPermissionSettingIntent(mContext);
                    }

                    @Override
                    public void onCancel(BaseUIDialog dialog) {
                        finish();
                    }
                })
                .show();
//        if (null == mPromptDialog) {
//            mPromptDialog = new PromptDialog(mContext, "权限请求失败，要正常使用需，前往设置同意权限?");
//            mPromptDialog.setOnClickCancelListener(new PromptDialog.OnClickCancelListener() {
//                @Override
//                public void onClick() {
//                    finish();
//                }
//            });
//            mPromptDialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
//                @Override
//                public void onClick() {
//                    PermissionPresenter.getInstance().gotoPermissionSettingIntent(mContext);
//                }
//            });
//        }
//        mPromptDialog.show();
    }

    /*
     * Activity创建并显示
     * onCreate -> onStart -> onResume
     *
     * 调finish()方法
     * onPause -> onStop-> onDestroy
     *
     * 按HOME键，退到后台运行
     * onPause -> onStop
     *
     * 后台转到前台
     * onRestart -> onStart -> onResume
     */


    /**
     * 显示用户协议和隐私政策
     */
    private void showPrivacy() {
        final PrivacyDialog dialog = new PrivacyDialog(mContext);
        TextView tv_privacy_tips = dialog.findViewById(R.id.tv_privacy_tips);
        TextView btn_exit = dialog.findViewById(R.id.btn_exit);
        TextView btn_enter = dialog.findViewById(R.id.btn_enter);
        dialog.show();
        String string = mContext.getResources().getString(R.string.privacy_tips);
        String key1 = mContext.getResources().getString(R.string.privacy_tips_key1);
        String key2 = mContext.getResources().getString(R.string.privacy_tips_key2);
        int index1 = string.indexOf(key1);
        int index2 = string.indexOf(key2);
        SpannableString spannedString = new SpannableString(string);
        //设置点击字体颜色
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(mContext.getResources().getColor(R.color.theme_color));
        spannedString.setSpan(colorSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(mContext.getResources().getColor(R.color.theme_color));
        spannedString.setSpan(colorSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击字体大小
        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan(16, true);
        spannedString.setSpan(sizeSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(16, true);
        spannedString.setSpan(sizeSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                gotoActivity(RegisterProtocolActivity.class);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //点击事件去掉下划线
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                gotoActivity(PrivacypolicyActivity.class);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //点击事件去掉下划线
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击后的颜色为透明，否则会一直出现高亮
        tv_privacy_tips.setHighlightColor(Color.TRANSPARENT);
        //开始响应点击事件
        tv_privacy_tips.setMovementMethod(LinkMovementMethod.getInstance());
        tv_privacy_tips.setText(spannedString);
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (DisplayUtil.getScreenWidth(mContext) * 0.75);
        dialog.getWindow().setAttributes(params);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SPUtil.put("isFirst", true);
                finish();
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SPUtil.put("isFirst", false);
                // 请求权限
                PermissionPresenter.getInstance()
                        .init(WelcomeActivity.this)
                        .addPermission(PermissionConstant.READ_PHONE_STATE)
                        .addPermission(PermissionConstant.READ_EXTERNAL_STORAGE)
                        .addPermission(PermissionConstant.CAMERA)
                        .addPermission(PermissionConstant.ACCESS_FINE_LOCATION)
                        .addPermission(PermissionConstant.ACCESS_COARSE_LOCATION)
                        .requestPermissions(WelcomeActivity.this);
            }
        });

    }
}