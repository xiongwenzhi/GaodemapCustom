package com.android.orangetravel.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.mvp.MyCarPresenter;
import com.android.orangetravel.ui.mvp.MyCarView;
import com.android.orangetravel.ui.widgets.dialog.PhotoPickerDialog;
import com.android.orangetravel.ui.widgets.view.XCRoundRectImageView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 完善信息
 */

public class PerfectInfoFinalActivity extends BaseActivity<MyCarPresenter> implements View.OnClickListener, MyCarView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.car_test)
    ImageView car_test;
    @BindView(R.id.car_lic_one)
    View car_lic_one;
    @BindView(R.id.car_lic_two)
    View car_lic_two;
    /*行驶证正面*/
    @BindView(R.id.image_one)
    ImageView image_one;
    /*行驶证反面*/
    @BindView(R.id.image_two)
    ImageView image_two;
    @BindView(R.id.yuanyin_list)
    RecyclerView yuanyin_list;
    @BindView(R.id.info_size)
    TextView info_size;
    private CommonAdapter<PerfectInfoBean.ReasonBean> mRvAdapter;
    private String id;
    private CarDetailsBean detailsBean;
    private String driving_front;//行驶证正面
    private String driving_reverse;//行驶证反面
    private String appearance;//车辆外观
    private int imageType = 0;
    /*照片选择弹出框*/
    private PhotoPickerDialog mPhotoDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_perfect_info_final;
    }

    @Override
    public MyCarPresenter initPresenter() {
        return new MyCarPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(PerfectInfoFinalActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(PerfectInfoFinalActivity.this, R.color.title_bar_black));
        setTitleBar("完善车辆信息");
        car_lic_one.getBackground().setAlpha(180);
        car_lic_two.getBackground().setAlpha(180);
        initRv();
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
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<PerfectInfoBean.ReasonBean>(mContext,
                R.layout.item_perfect_list) {
            @Override
            protected void convert(ViewHolder mHolder, final PerfectInfoBean.ReasonBean item,
                                   final int position) {
                mHolder.setText(R.id.perfect_tv, item.getWhy());
            }
        };
        yuanyin_list.setLayoutManager(new LinearLayoutManager(mContext));
        yuanyin_list.setAdapter(mRvAdapter);
        yuanyin_list.addItemDecoration(new ListItemDecoration(mContext, 10f,
                R.color.trans, false));
    }

    @OnClick(R.id.save_info)
    void save_info() {
        if (isNull()) {
            Map map = new HashMap();
            map.put("id", id);
            map.put("front", driving_front);
            map.put("reverse", driving_reverse);
            map.put("appearance", appearance);
            getPresenter().carperfect(map);
        }
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
        }
        return true;
    }

    //行驶证正面
    @OnClick(R.id.driving_front)
    void driving_front() {
        imageType = 0;
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(PerfectInfoFinalActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }

    //行驶证反面
    @OnClick(R.id.driving_reverse)
    void driving_reverse() {
        imageType = 1;
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(PerfectInfoFinalActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }


    @OnClick(R.id.image_three)
    void image_three() {
        imageType = 2;
        if (null == mPhotoDialog) {
            mPhotoDialog = new PhotoPickerDialog(PerfectInfoFinalActivity.this);
        }
        mPhotoDialog.selectionMode = PictureConfig.SINGLE;
        mPhotoDialog.show();
    }

    private void setData() {
        GlideUtil.loadImg(mContext, detailsBean.getDriving_front(), image_one);
        GlideUtil.loadImg(mContext, detailsBean.getDriving_reverse(), image_two);
        GlideUtil.loadImg(mContext, detailsBean.getAppearance(), car_test);
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
    public void requestData() {
        getPresenter().carReason(id);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void carList(List<MyCarListBean> bean) {

    }

    @Override
    public void carDetails(CarDetailsBean bean) {

    }

    @Override
    public void setDefault(ErrorMsgBean bean) {
        showToast("修改成功！");
        finish();
    }

    @Override
    public void perectInfo(PerfectInfoBean bean) {
        mRvAdapter.replaceData(bean.getReason());
        info_size.setText("审核失败原因(" + bean.getReason().size() + "处信息有误，请修改)");
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
