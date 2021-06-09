package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.utils.EventBusUtil;
import com.android.orangetravel.base.utils.LoginSucEvent;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.ui.event.DeleteCarEvent;
import com.android.orangetravel.ui.mvp.MyCarPresenter;
import com.android.orangetravel.ui.mvp.MyCarView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/3/15
 * 车辆详情
 */

public class CarDetailsActivity extends BaseActivity<MyCarPresenter> implements
        View.OnClickListener, MyCarView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.lessee)
    TextView lessee;
    @BindView(R.id.owner)
    TextView owner;
    @BindView(R.id.register_time)
    TextView register_time;
    @BindView(R.id.plateNumber)
    TextView plateNumber;
    @BindView(R.id.name)
    TextView name;
    /*车辆审核状态*/
    @BindView(R.id.status_car)
    TextView status_car;
    @BindView(R.id.hint)
    TextView hint;
    /*完善资料*/
    @BindView(R.id.edit_car)
    TextView edit_car;
    String id;
    private CarDetailsBean carDetailsBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_car_details;
    }

    @Override
    public MyCarPresenter initPresenter() {
        return new MyCarPresenter(this);
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("id")) {
            id = bundle.getString("id");
        }
        setmYangStatusBar(ContextCompat.getColor(CarDetailsActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(CarDetailsActivity.this, R.color.title_bar_black));
        setTitleBar("车辆详情");
    }


    @OnClick(R.id.delete_car)
    void delete_car() {
        PromptDialog dialog = new PromptDialog(CarDetailsActivity.this,
                "确定删除吗？");
        dialog.setTitle("温馨提示");
        dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
            @Override
            public void onClick() {
                Map map = new HashMap();
                map.put("id", id);
                getPresenter().carDel(map);
            }
        });
        dialog.show();
    }

    @OnClick(R.id.edit_car)
    void edit_car() {
        if ("2".equals(carDetailsBean.getStatus())) { //待完善
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putSerializable("carDetais", carDetailsBean);
            gotoActivity(PerfectInfoActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putSerializable("carDetais", carDetailsBean);
            gotoActivity(AddCarActivity.class, bundle);
        }
    }

    @Override
    public void requestData() {
        getPresenter().carDetail(id);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void carList(List<MyCarListBean> bean) {
    }

    @Override
    public void carDetails(CarDetailsBean bean) {
        carDetailsBean = bean;
        city.setText(bean.getCity());
        lessee.setText(bean.getLessee());
        owner.setText(bean.getOwner());
        register_time.setText(bean.getRegister_time());
        plateNumber.setText(bean.getPlateNumber());
        hint.setText(bean.getHint());
        name.setText(bean.getName() + "(" + bean.getColor() + ")");
        if ("1".equals(bean.getStatus())) {
            status_car.setText("审核成功");
            edit_car.setText("修改车辆信息");
            status_car.setTextColor(ContextCompat.getColor(mContext, R.color.title_bar_black));
        }
        if ("0".equals(bean.getStatus())) {
            status_car.setText("审核中");
            edit_car.setText("修改车辆信息");
            status_car.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
        }
        if ("2".equals(bean.getStatus())) {
            status_car.setText("待完善");
            edit_car.setText("完善资料");
            status_car.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
    }

    @Override
    public void setDefault(ErrorMsgBean bean) {
        showToast("删除成功！");
        EventBusUtil.post(new DeleteCarEvent());// 车辆删除
        finish();
    }

    @Override
    public void perectInfo(PerfectInfoBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
