package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.android.orangetravel.R;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.widgets.citypicker.CityPicker;
import com.android.orangetravel.ui.widgets.citypicker.adapter.OnPickListener;
import com.android.orangetravel.ui.widgets.citypicker.model.City;
import com.android.orangetravel.ui.widgets.citypicker.model.LocatedCity;
import com.android.orangetravel.ui.widgets.pop.ListTestPopup;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/27
 * 合规认证
 */

public class CompliancecertificationActivity extends BaseActivity<CommonPresenter> implements View.OnClickListener, CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*合规认证*/
    @BindView(R.id.qualificationList)
    RecyclerView qualificationList;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.field)
    TextView field;
    @BindView(R.id.tips)
    TextView tips;
    /*城市*/
    @BindView(R.id.city_tv)
    TextView city_tv;
    private CommonAdapter<ComplianceBean.QualificationBean> mnotQualifiedAdapter;
    private ComplianceBean complianceBean;
    private ListTestPopup listTestPopup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_compliance_certification;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(CompliancecertificationActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(CompliancecertificationActivity.this, R.color.title_bar_black));
        setTitleBar("合规认证");
        initnotQualifiedRv();

    }

    /*选择城市*/
    @OnClick(R.id.choose_city)
    void choose_city() {
        if (listTestPopup == null) {
            listTestPopup = new ListTestPopup(CompliancecertificationActivity.this, "确认切换发证城市",
                    "您已在南昌市办理合规证件，切换发证城市将使当前合规状态失效",
                    "暂不切换", "确认切换");
        }
        new XPopup.Builder(CompliancecertificationActivity.this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .asCustom(listTestPopup)
                .show();
        listTestPopup.setOnItemClick(new ListTestPopup.OnItemMenuClick() {
            @Override
            public void cancle() {

            }

            @Override
            public void confim() {
                CityPicker.from((FragmentActivity) mContext)
                        .enableAnimation(true)
                        .setType(1)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setLocatedCity(new LocatedCity(Constant.LocationCity, "", ""))
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                city_tv.setText(data.getName());
                            }

                            @Override
                            public void onCancel() {
                            }

                            @Override
                            public void onClickList(PoiItem poiItem) {
                            }

                            @Override
                            public void onClickTop(String city) {

                            }

                            @Override
                            public void onLocate() {
                            }
                        })
                        .show();
            }
        });

    }

    /**
     * 引导办理列表
     */
    private void initnotQualifiedRv() {
        mnotQualifiedAdapter = new CommonAdapter<ComplianceBean.QualificationBean>(mContext,
                R.layout.item_qualification_list) {
            @Override
            protected void convert(ViewHolder mHolder, final ComplianceBean.QualificationBean item,
                                   final int position) {
                mHolder.setText(R.id.name, item.getName()).setText(R.id.tip,
                        item.getTip()).setText(R.id.guide, item.getGuide()).setVisible(R.id.image_right,
                        TextUtils.isEmpty(item.getGuide().trim()) ? false : true);
                mHolder.setOnClickListener(R.id.guide_layout, position, new CommonOnClickListener() {
                    @Override
                    public void clickListener(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", item.getGuideUrl());
                        bundle.putString("title", item.getGuide());
                        gotoActivity(WebviewActivity.class, bundle);
                    }
                });

            }
        };
        qualificationList.setLayoutManager(new LinearLayoutManager(mContext));
        qualificationList.setAdapter(mnotQualifiedAdapter);
        qualificationList.addItemDecoration(new ListItemDecoration(mContext, 10f,
                R.color.activity_bg, false));
        mnotQualifiedAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                if ("driving".equals(mnotQualifiedAdapter.getAllData().get(position).getType())) {
                    gotoActivity(DriverinformationActivity.class);
                } else {
                    gotoActivity(TransportformationActivity.class);
                }

            }
        });
    }


    @OnClick(R.id.question)
    void question() {
        if (TextUtils.isEmpty(complianceBean.getQuestion())) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", complianceBean.getQuestion());
        bundle.putString("title", "常见问题");
        gotoActivity(WebviewActivity.class, bundle);
    }

//    @OnClick(R.id.upload_driver)
//    void upload_driver() {
//        gotoActivity(DriverinformationActivity.class);
//    }


    @Override
    public void requestData() {
        getPresenter().compliance("南昌市");
    }

    @Override
    public void onClick(View v) {

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
        complianceBean = bean;
        name.setText(bean.getName());
        field.setText(bean.getField());
        tips.setText(bean.getTips());
        mnotQualifiedAdapter.clearData();
        mnotQualifiedAdapter.addAllData(bean.getQualification());
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
