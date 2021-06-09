package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.CarDetailsBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 完善信息
 */

public class PerfectInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.top_tips)
    TextView top_tips;
    @BindView(R.id.center_tips)
    TextView center_tips;
    private CommonAdapter<String> mRvAdapter;
    private String id;
    private CarDetailsBean detailsBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_perfect_info;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(PerfectInfoActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(PerfectInfoActivity.this, R.color.title_bar_black));
        setTitleBar("完善车辆信息");
        String str1 = "请确保身边已准备好以下材料，需<font color= \"#ff9600\">一次性</font>完成上传";
        top_tips.setText(Html.fromHtml(str1));
        String centenr = "注册时，需要对车辆<font color= \"#ff9600\">实时拍摄</font>，请您合理安排已确保照片可在规定时间内上传";
        center_tips.setText(Html.fromHtml(centenr));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("id")) {
                id = bundle.getString("id");
            }
            if (bundle.containsKey("carDetais")) {
                detailsBean = (CarDetailsBean) bundle.getSerializable("carDetais");
            }
        }
    }


    @Override
    public void requestData() {

    }

    @OnClick(R.id.readly)
    void readly() {
        if (detailsBean == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putSerializable("carDetais", detailsBean);
        gotoActivity(PerfectInfoFinalActivity.class, bundle);
    }

    @Override
    public void onClick(View v) {

    }
}
