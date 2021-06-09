package com.android.orangetravel.base.versionUpdate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * 作者：Andy on 2017/3/1 09:34
 * <p>
 * 邮箱：a1993628470@gmail.com
 */
public abstract class LocalDialog extends Dialog {

    protected LocalDialog(Context context) {
        super(context);
        // 去除dialog 头上的横线 ,try catch 避免非Holo 主题异常问题
        try {
            int dividerID = context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initalize();

    }

    //初始化View
    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(getdialogLayoutId(), null);
        setContentView(view);
        ButterKnife.bind(this);
        initWindow();
        initdata();

    }

    //获取dialog布局文件
    public abstract int getdialogLayoutId();

//    //加载数据
//    public abstract void initdata();

    public void initdata() {

    }

    /**
     * 添加黑色半透明背景
     */
    public void initWindow() {
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));//设置window背景
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//设置输入法显示模式
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();//获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.8); //宽度为屏幕80%
        lp.gravity = Gravity.CENTER;     //中央居中
        dialogWindow.setAttributes(lp);
    }
}
