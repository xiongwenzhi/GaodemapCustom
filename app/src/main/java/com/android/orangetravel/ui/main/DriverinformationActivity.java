package com.android.orangetravel.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.amap.api.services.core.PoiItem;
import com.android.orangetravel.R;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
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
import com.android.orangetravel.ui.widgets.dialog.PhotoPickerDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.xiaofeidev.round.RoundImageView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/27
 * 网约车驾驶证资料补齐
 */

public class DriverinformationActivity extends BaseActivity<CommonPresenter> implements View.OnClickListener, CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.city_tv)
    TextView city_tv;
    @BindView(R.id.id_number)
    EditText id_number;
    @BindView(R.id.car_test)
    RoundImageView car_test;
    @BindView(R.id.effective_time)
    TextView meffective_time;
    @BindView(R.id.certificate_time)
    TextView mcertificate_time;
    private String city;
    private String image;
    private String effective_time;
    private String certificate_time;
    /*照片选择弹出框*/
    private PhotoPickerDialog mPhotoDialog;
    private TimePickerView pvTime;
    private boolean isStartTime = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_information;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(DriverinformationActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(DriverinformationActivity.this, R.color.title_bar_black));
        setTitleBar("网约车驾驶证资料补齐");
        initTimePicker();
    }

    //选择图片
    @OnClick(R.id.choose_photo)
    void choose_photo() {
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(DriverinformationActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }

    //选择有效日期
    @OnClick(R.id.choose_time_start)
    void choose_time_start() {
        isStartTime = true;
        pvTime.show(meffective_time);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
    }

    //选择发证日期
    @OnClick(R.id.choose_time_stop)
    void choose_time_stop() {
        isStartTime = false;
        pvTime.show(mcertificate_time);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(DriverinformationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String value = DateUtil.date2Strtime(date, "yyyy年MM月dd日");
                if (isStartTime) {
                    meffective_time.setText(value);
                    effective_time = value;
                } else {
                    mcertificate_time.setText(value);
                    certificate_time = value;
                }

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
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

    @OnClick(R.id.complianceEdit)
    void complianceEdit() {
        if (isNull()) {
            Map map = new HashMap();
            map.put("city", city);
            map.put("image", image);
            map.put("image", image);
            map.put("effective_time", effective_time);
            map.put("certificate_time", certificate_time);
            map.put("id_number", id_number.getText().toString().trim());
            map.put("type", "driving");//driving：驾驶证 transport：运输证
            getPresenter().complianceEdit(map);
        }
    }

    //判断为空
    private boolean isNull() {
        if (TextUtils.isEmpty(city)) {
            showToast("请选择发证城市");
            return false;
        } else if (TextUtils.isEmpty(image)) {
            showToast("请上传网约车驾驶证");
            return false;
        } else if (TextUtils.isEmpty(id_number.getText().toString())) {
            showToast("请输入驾驶证号");
            return false;
        } else if (TextUtils.isEmpty(effective_time)) {
            showToast("请选择驾驶员证有效日期");
            return false;
        } else if (TextUtils.isEmpty(certificate_time)) {
            showToast("请选择驾驶员证发证日期");
            return false;
        }
        return true;
    }


    @OnClick(R.id.choose_city)
    void choose_city() {
        CityPicker.from((FragmentActivity) mContext)
                .enableAnimation(true)
                .setType(1)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(new LocatedCity(Constant.LocationCity, "", ""))
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        city_tv.setText(data.getName());
                        city = data.getName();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onClickList(PoiItem poiItem) {
                    }

                    @Override
                    public void onClickTop(String mcity) {
                        city_tv.setText(mcity);
                        city = mcity;
                    }

                    @Override
                    public void onLocate() {
                    }
                })
                .show();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void contact(ContactBean bean) {
        GlideUtil.loadImg(mContext, bean.getUrl(), car_test);
        image = bean.getUrl();
    }

    @Override
    public void balance(BalanceBean bean) {

    }

    @Override
    public void loginOut(ErrorMsgBean bean) {
        showToast("资料提交成功，请等待审核！");
        finish();
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选头像
        if (null != mPhotoDialog) {
            List<LocalMedia> selectList = mPhotoDialog.handleResult(requestCode, resultCode, data);
            if (StringUtil.isListNotEmpty(selectList)) {
                LogUtil.eSuper(selectList);
                LocalMedia media = selectList.get(0);
                getPresenter().UploadImage(new File(media.getCompressPath()));

            }
        }

    }

}
