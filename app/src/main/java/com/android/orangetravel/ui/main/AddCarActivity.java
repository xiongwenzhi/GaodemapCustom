package com.android.orangetravel.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.DateUtil;
import com.android.orangetravel.base.utils.EventBusUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.android.orangetravel.base.widgets.RoundImageView;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.event.DeleteCarEvent;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.mvp.MyCarPresenter;
import com.android.orangetravel.ui.mvp.MyCarView;
import com.android.orangetravel.ui.widgets.dialog.PhotoPickerDialog;
import com.android.orangetravel.ui.widgets.view.PopupWindowRight;
import com.android.orangetravel.ui.widgets.view.XCRoundRectImageView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
 * @date 2021/1/23
 * 新增车辆
 */

public class AddCarActivity extends BaseActivity<MyCarPresenter> implements View.OnClickListener, MyCarView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.car_lic_one)
    View car_lic_one;
    @BindView(R.id.car_lic_two)
    View car_lic_two;
    @BindView(R.id.car_lic_three)
    View car_lic_three;
    @BindView(R.id.car_test)
    ImageView car_test;
    /*车辆注册日期*/
    @BindView(R.id.add_car_time)
    TextView add_car_time;
    /*行驶证正面*/
    @BindView(R.id.image_one)
    ImageView image_one;
    /*行驶证反面*/
    @BindView(R.id.image_two)
    ImageView image_two;
    /*服务公司*/
    @BindView(R.id.czr)
    EditText czr;
    /*车主姓名*/
    @BindView(R.id.car_Phople_name)
    EditText car_Phople_name;
    /*车辆所在地*/
    @BindView(R.id.car_city)
    EditText car_city;
    /*爱车名称*/
    @BindView(R.id.car_Carname)
    EditText car_Carname;
    /*车辆品牌*/
    @BindView(R.id.car_type)
    EditText car_type;
    /*车牌号*/
    @BindView(R.id.plateNumber)
    EditText plateNumber;
    /*车辆vin*/
    @BindView(R.id.car_vin)
    EditText car_vin;
    /*车辆颜色*/
    @BindView(R.id.car_color)
    TextView car_color;
    PopupWindowRight popupWindowRight;
    private TimePickerView pvTime;
    /*照片选择弹出框*/
    private PhotoPickerDialog mPhotoDialog;
    private int imageType = 0;
    private String driving_front;//行驶证正面
    private String driving_reverse;//行驶证反面
    private String appearance;//车辆外观
    private String register_time;//车辆注册日期
    private String color;//车辆颜色
    private String id;
    private CarDetailsBean detailsBean;
    private String title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    public MyCarPresenter initPresenter() {
        return new MyCarPresenter(this);
    }

    @Override
    public void initView() {
        GlideUtil.loadImg(mContext, "https://car2.autoimg.cn/cardfs/product/g3/M01/8B/C1/240x180_0_q95_c42_autohomecar__ChsEkVw_8kiAXIv_AARB_CH-RAA695.jpg", car_test);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("id")) {
                id = bundle.getString("id");
            }
            if (bundle.containsKey("carDetais")) {
                detailsBean = (CarDetailsBean) bundle.getSerializable("carDetais");
                setData();
            }
        }
        setmYangStatusBar(ContextCompat.getColor(AddCarActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(AddCarActivity.this, R.color.title_bar_black));
        setTitleBar(TextUtils.isEmpty(id) ? "新增车辆信息" : "编辑车辆信息");
        car_lic_one.getBackground().setAlpha(180);
        car_lic_two.getBackground().setAlpha(180);
        car_lic_three.getBackground().setAlpha(180);
        initTimePicker();
//        if (bundle.containsKey("title")) {
//            title = bundle.getString("title");
//            setTitleBar(title);
//        }
    }


    private void setData() {
        GlideUtil.loadImg(mContext, detailsBean.getDriving_front(), image_one);
        driving_front = detailsBean.getDriving_front();
        GlideUtil.loadImg(mContext, detailsBean.getDriving_reverse(), image_two);
        driving_reverse = detailsBean.getDriving_reverse();
        GlideUtil.loadImg(mContext, detailsBean.getAppearance(), car_test);
        appearance = detailsBean.getAppearance();
        color = detailsBean.getColor();
        car_color.setText(detailsBean.getColor());
        czr.setText(detailsBean.getLessee());
        car_Phople_name.setText(detailsBean.getOwner());
        car_city.setText(detailsBean.getCity());
        car_Carname.setText(detailsBean.getName());
        car_type.setText(detailsBean.getCar_type());
        plateNumber.setText(detailsBean.getPlateNumber());
        car_vin.setText(detailsBean.getVin());
        add_car_time.setText(detailsBean.getRegister_time());
        register_time = detailsBean.getRegister_time();


    }

    //行驶证正面
    @OnClick(R.id.driving_front)
    void driving_front() {
        imageType = 0;
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(AddCarActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }

    //行驶证反面
    @OnClick(R.id.driving_reverse)
    void driving_reverse() {
        imageType = 1;
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(AddCarActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }


    @OnClick(R.id.image_three)
    void image_three() {
        imageType = 2;
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(AddCarActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }

    @OnClick(R.id.addCar_choose_date)
    void addCar_choose_date() {
        pvTime.show(add_car_time);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
    }

    @OnClick(R.id.choose_car_color)
    void choose_car_color() {
        showRightDialog();
    }

    //添加车辆
    @OnClick(R.id.add_car)
    void add_car() {
        if (isNull()) {
            Map map = new HashMap();
            if (!TextUtils.isEmpty(id)) { //如果id不为空 则是编辑
                map.put("id", id);
            }
            map.put("driving_front", driving_front);
            map.put("driving_reverse", driving_reverse);
            map.put("register_time", register_time);
            map.put("appearance", appearance);
            map.put("color", color);
            map.put("lessee", czr.getText().toString());
            map.put("owner", car_Phople_name.getText().toString());
            map.put("city", car_city.getText().toString());
            map.put("name", car_Carname.getText().toString());
            map.put("car_type", car_type.getText().toString());
            map.put("plateNumber", plateNumber.getText().toString());
            map.put("vin", car_vin.getText().toString());
            getPresenter().carEdit(map);
        }
    }

    @Override
    public void showLoadingDialog() {
        super.showLoadingDialog();
    }

    private boolean isNull() {
        if (TextUtils.isEmpty(driving_front)) {
            showToast("请上传行驶证正面图片");
            return false;
        } else if (TextUtils.isEmpty(driving_reverse)) {
            showToast("请上传行驶证反面图片");
            return false;
        } else if (TextUtils.isEmpty(appearance)) {
            showToast("请上传车辆外观图片");
            return false;
        } else if (TextUtils.isEmpty(color)) {
            showToast("请选择车辆颜色");
            return false;
        } else if (TextUtils.isEmpty(czr.getText().toString())) {
            showToast("请输入服务公司或承租人");
            return false;
        } else if (TextUtils.isEmpty(car_Phople_name.getText().toString())) {
            showToast("请输入车主姓名");
            return false;
        } else if (TextUtils.isEmpty(car_city.getText().toString())) {
            showToast("请输入车辆所在地");
            return false;
        } else if (TextUtils.isEmpty(car_Carname.getText().toString())) {
            showToast("请输入爱车名称");
            return false;
        } else if (TextUtils.isEmpty(car_type.getText().toString())) {
            showToast("请输入车辆品牌");
            return false;
        } else if (TextUtils.isEmpty(plateNumber.getText().toString())) {
            showToast("请输入车牌号");
            return false;
        } else if (TextUtils.isEmpty(car_vin.getText().toString())) {
            showToast("请输入车辆VIN");
            return false;
        } else if (TextUtils.isEmpty(register_time)) {
            showToast("请选择注册日期");
            return false;

        }
        return true;
    }

    private void showRightDialog() {
        if (popupWindowRight == null) {
            popupWindowRight = new PopupWindowRight(this);
        }
        popupWindowRight.setOnChooseColor(new PopupWindowRight.OnColorChoose() {
            @Override
            public void getColor(String value) {
                popupWindowRight.dismiss();
                car_color.setText(value);
                color = value;
            }
        });
        if (popupWindowRight.isShowing()) {
            popupWindowRight.dismiss();
        } else {
            popupWindowRight.showAtLocation(getWindow().getDecorView(), Gravity.RIGHT
                    | Gravity.BOTTOM, 0, 0);
        }
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(AddCarActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String value = DateUtil.date2Strtime(date, "yyyy年MM月");
                add_car_time.setText(value);
                register_time = value;
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
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

    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View v) {

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
                new CommonPresenter(new CommonView() {
                    @Override
                    public void contact(ContactBean bean) {
                        if (imageType == 0) {
                            GlideUtil.loadImg(mContext, bean.getUrl(), image_one);
                            driving_front = bean.getUrl();
                        } else if (imageType == 1) {
                            GlideUtil.loadImg(mContext, bean.getUrl(), image_two);
                            driving_reverse = bean.getUrl();
                        } else if (imageType == 2) {
                            GlideUtil.loadImg(mContext, bean.getUrl(), car_test);
                            appearance = bean.getUrl();
                        }
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

                    }

                    @Override
                    public void showErrorMsg(String msg) {

                    }

                    @Override
                    public void showLoadingDialog() {

                    }

                    @Override
                    public void dismissLoadingDialog() {

                    }
                }).UploadImage(new File(media.getCompressPath()));
            }
        }

    }

    @Override
    public void carList(List<MyCarListBean> bean) {

    }

    @Override
    public void carDetails(CarDetailsBean bean) {

    }

    @Override
    public void setDefault(ErrorMsgBean bean) {
        showToast(TextUtils.isEmpty(id) ? "新增成功！" : "编辑成功！");
        EventBusUtil.post(new DeleteCarEvent());// 车辆删除
        finish();
    }

    @Override
    public void perectInfo(PerfectInfoBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
