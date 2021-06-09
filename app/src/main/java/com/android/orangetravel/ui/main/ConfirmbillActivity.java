package com.android.orangetravel.ui.main;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.ui.widgets.view.CustomSlideToUnlockView;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2021/2/23
 * 确认账单
 */

public class ConfirmbillActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.confirm_tips)
    TextView confirm_tips;
    @BindView(R.id.slide_to_unlock)
    CustomSlideToUnlockView slide_to_unlock;

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_bill;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(ConfirmbillActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(ConfirmbillActivity.this, R.color.title_bar_black));
        setTitleBar("确认账单");
        slide_to_unlock.setHintText("发起收款");
        setTips();
    }

    private void setTips() {
        SpannableStringBuilder span1 = new SpannableStringBuilder("感谢您将");
        SpannableString spannableString = new SpannableString("尾号1148");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fb5e29")), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.append(spannableString);
        SpannableString spannableString2 = new SpannableString("送到目的地");
        span1.append(spannableString2);
        confirm_tips.setText(span1);
        slide_to_unlock.setmCallBack(new CustomSlideToUnlockView.CallBack() {
            @Override
            public void onSlide(int distance) {

            }

            @Override
            public void onUnlocked() {
                gotoActivity(EndoftripActivity.class);
            }
        });
    }


    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View v) {

    }
}
