package com.android.orangetravel.ui.widgets;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.orangetravel.R;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.ui.widgets.utils.tts.TTSController;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.Date;

/**
 * 设置模式pop
 */
public class SettingModelPopup extends BottomPopupView implements View.OnClickListener {
    private ImageView dismiss_pop;
    private TextView start_time, end_time;
    private TimePickerView pvTime;
    private boolean isStart = true;
    protected TTSController mTtsManager;
    /*保存设置*/
    private TextView save_setting;

    public SettingModelPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_set_model_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getContext());
        mTtsManager.init();
        mTtsManager.setTTSType(TTSController.TTSType.SYSTEMTTS);
        dismiss_pop = findViewById(R.id.dismiss_pop);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        save_setting = findViewById(R.id.save_setting);
        save_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTtsManager.playText("完成设置");
                dismiss();
            }
        });
        end_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = false;
                initTimePicker();
                pvTime.show(v);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
            }
        });
        start_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                initTimePicker();
                pvTime.show(v);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
            }
        });
        dismiss_pop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String value = DateUtil.date2Strtime(date, "dd日HH时mm分");
                if (isStart) {
                    start_time.setText(value);
                } else {
                    end_time.setText(value);
                }
                mTtsManager.playText("听" + start_time.getText().toString() + "到" + end_time.getText().toString() + "的订单");


            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{false, false, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 50;
            params.rightMargin = 50;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_scale_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.CENTER);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
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
        return (int) (XPopupUtils.getAppHeight(getContext()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    public interface OnItemMenuClick {
    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}