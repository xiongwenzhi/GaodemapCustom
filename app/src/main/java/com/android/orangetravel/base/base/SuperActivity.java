package com.android.orangetravel.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.orangetravel.base.annotation.BindEventBus;
import com.android.orangetravel.base.statusBar.YangStatusBar;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.yang.base.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 超级Activity
 *
 * @author yangfei
 */
public class SuperActivity extends AppCompatActivity {

    // 上下文
    protected Context mContext;
    // 注解
    private Unbinder mUnbinder;
    // 沉浸式状态栏
    // private ImmersionBar mImmersionBar;
    protected YangStatusBar mYangStatusBar;
    // 软键盘管理
    private InputMethodManager imm;
    protected String start_time;
    protected String end_time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 上下文
        mContext = this;
    }

    /**
     * 设置布局资源Id
     */
    protected void setLayoutResId(@LayoutRes int layoutResId) {
        // 设置布局之前的配置
        // 关闭标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 竖屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置布局文件Id
        this.setContentView(layoutResId);
        // 注解
        mUnbinder = ButterKnife.bind(this);
        // 沉浸式状态栏
        mYangStatusBar = YangStatusBar.with(this).setDarkColor(true);
        if (null != mYangStatusBar) {
            mYangStatusBar.init();
        }
        /*mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.navigationBarColor(R.color.theme_color);// 导航栏颜色
        // mImmersionBar.statusBarDarkFont(true, 0.2f);// 设置深色文字颜色(解决白色状态栏)
        mImmersionBar.init();*/
        // EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
    }

    public void setmYangStatusBar(int color) {
        mYangStatusBar.setStatusBarColor(color);
        mYangStatusBar.setDarkColor(true);
        mYangStatusBar.init();
    }


    @Override
    protected void onResume() {
        start_time = DateUtil.currentTimeStamp2();
        super.onResume();

    }


    @Override
    protected void onPause() {
        end_time = DateUtil.currentTimeStamp2();
        super.onPause();

    }

    /**
     * 执行销毁前的操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭加载弹出框
        dismissLoadingDialog();
        // 上下文
        mContext = null;
        mYangTitleBar = null;
        // 销毁软键盘
        imm = null;
        // 注解
        mUnbinder.unbind();
        // 沉浸式状态栏
        /*if (null != mImmersionBar) {mImmersionBar.destroy();}*/
        if (null != mYangStatusBar) {
            mYangStatusBar.destroy();
            mYangStatusBar = null;
        }
        // EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);// 取消订阅
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((null != localView) && (null != this.imm)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 提供给子类调用的方法

    /**
     * 跳转Activity
     */
    protected void gotoActivity(Class<?> cls, @Nullable Bundle mBundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (null != mBundle) {
            intent.putExtras(mBundle);
        }
        if (requestCode < 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
        overridePendingTransition(R.anim.act_enter_right, R.anim.act_exit_left);
    }

    protected void gotoActivity(Class<?> cls, @Nullable Bundle mBundle) {
        gotoActivity(cls, mBundle, -1);
    }

    protected void gotoActivity(Class<?> cls, int requestCode) {
        gotoActivity(cls, null, requestCode);
    }

    protected void gotoActivity(Class<?> cls) {
        gotoActivity(cls, null, -1);
    }

    /**
     * Activity退出动画
     */
    @Override
    public void finish() {
        // 隐藏软键盘
        hideSoftKeyBoard();
        // 结束
        super.finish();
        // 过渡动画
        super.overridePendingTransition(R.anim.act_enter_left, R.anim.act_exit_right);
    }

    /**
     * 显示Toast
     */
    protected void showToast(String message) {
        if (StringUtil.isNotEmpty(message)) {
            ToastUitl.showShort(mContext, message);
        }
    }

//    /**
//     * 软键盘适配底部输入框
//     */
//    protected void softKeyboardAdaptive() {
//        if (null != mImmersionBar) {
//            mImmersionBar.keyboardEnable(true).init();
//            // WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
//        } else {
//            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        }
//    }

    /**
     * 自定义标题栏
     */
    protected YangTitleBar mYangTitleBar;

    protected void setTitleBar(String title) {
        mYangTitleBar = (YangTitleBar) findViewById(R.id.id_title_bar);
        if (null != mYangTitleBar) {
            if (StringUtil.isNotEmpty(title))
                mYangTitleBar.setTitle(title);
            mYangTitleBar.setTitle("");
            mYangTitleBar.setBackClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    // ---------------------------------------------------------------------------------------------
    // 加载弹出框

    private LoadingDialogInter mLoadingDialogInter = new LoadingDialogInterImpl();

    // 显示加载弹出框
    public void showLoadingDialog(String msg) {
        mLoadingDialogInter.showLoadingDialog(mContext, msg);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        YangStatusBar.translucentStatusBar((Activity) mContext, false);
    }

    // 显示加载弹出框
    public void showLoadingDialog() {
        mLoadingDialogInter.showLoadingDialog(mContext);
    }

    // 关闭加载弹出框
    public void dismissLoadingDialog() {
        mLoadingDialogInter.dismissLoadingDialog();
    }

}