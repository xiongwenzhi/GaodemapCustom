package com.android.orangetravel.ui.widgets.pop;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.orangetravel.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/**
 * 隐私政策用户协议
 */
public class PrivacyPopup extends BottomPopupView implements View.OnClickListener {

    /*查看详情*/
    private TextView look_details;
    /*我知道了*/
    private TextView user_know;

    private TextView list_test_title;
    private TextView content_test;
    private TextView center_tips;

    private String title;
    private String content;
    private String confimBtn;
    private String cancleBtn;
    private boolean ShowCenterTips = true;


    public PrivacyPopup(@NonNull Context context, String title, String content,
                        String confimBtn, String cancleBtn, boolean ShowCenterTips) {
        super(context);
        this.title = title;
        this.content = content;
        this.confimBtn = confimBtn;
        this.cancleBtn = cancleBtn;
        this.ShowCenterTips = ShowCenterTips;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_privacy_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        look_details = findViewById(R.id.look_details);
        center_tips = findViewById(R.id.center_tips);
        user_know = findViewById(R.id.user_know);
        list_test_title = findViewById(R.id.list_test_title);
        content_test = findViewById(R.id.content_test);
        look_details.setOnClickListener(this);
        user_know.setOnClickListener(this);
        list_test_title.setText(title);
        content_test.setText(content);
        look_details.setText(confimBtn);
        user_know.setText(cancleBtn);
        center_tips.setVisibility(ShowCenterTips ? VISIBLE : GONE);
        look_details.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemMenuClick != null) {
                    onItemMenuClick.confim();
                }
            }
        });
        user_know.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemMenuClick != null) {
                    onItemMenuClick.cancle();
                }
            }
        });
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

    public void setCenter_tips(int Visible) {

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getAppHeight(getContext()) * .6f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.look_details:
                dismiss();
                break;
            case R.id.user_know:
                dismiss();
                break;
        }
    }


    public interface OnItemMenuClick {
        void confim();

        void cancle();


    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}