package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.CommonOnClickListener;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.annotation.BindEventBus;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.ui.event.DeleteCarEvent;
import com.android.orangetravel.ui.mvp.MyCarPresenter;
import com.android.orangetravel.ui.mvp.MyCarView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 我的车辆
 */
@BindEventBus
public class MyCarListActivity extends BaseActivity<MyCarPresenter> implements
        View.OnClickListener, MyCarView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.my_car_list)
    RecyclerView my_car_list;
    private CommonAdapter<MyCarListBean> mRvAdapter;
    private int setDefalutPosition = -1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_car_list;
    }

    @Override
    public MyCarPresenter initPresenter() {
        return new MyCarPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(MyCarListActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(MyCarListActivity.this, R.color.title_bar_black));
        setTitleBar("我的车辆");
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<MyCarListBean>(mContext,
                R.layout.item_car_list) {
            @Override
            protected void convert(ViewHolder mHolder, final MyCarListBean item,
                                   final int position) {
                mHolder.loadImage(mContext, item.getStatusImage(), R.id.examine_status)
                        .loadImage(mContext, item.getAppearance(), R.id.appearance).
                        setText(R.id.name_car, item.getName())
                        .setText(R.id.plateNumber, item.getPlateNumber()).
                        setText(R.id.tips, item.getTips()).
                        setVisible(R.id.tips_layout, TextUtils.isEmpty(item.getTips()) ? false : true
                        ).setImageResource(R.id.is_defalut, item.getIs_default() == 1 ? R.mipmap.check_theme : R.mipmap.check_grey);

                mHolder.setOnClickListener(R.id.setDefalut_car, position, new CommonOnClickListener() {
                    @Override
                    public void clickListener(View view, int position) {
                        setDefalutPosition = position;
                        Map map = new HashMap();
                        map.put("id", mRvAdapter.getAllData().get(position).getId());
                        getPresenter().setDefault(map);
                    }
                });
            }
        };
        my_car_list.setLayoutManager(new LinearLayoutManager(mContext));
        my_car_list.setAdapter(mRvAdapter);
        my_car_list.addItemDecoration(new ListItemDecoration(mContext, 10f,
                R.color.activity_bg, false));
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mRvAdapter.getAllData().get(position).getId());
                gotoActivity(CarDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public void requestData() {
        getPresenter().car();
    }

    @OnClick(R.id.add_car)
    void add_car() {
        gotoActivity(AddCarActivity.class);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 车辆删除
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSucEvent(DeleteCarEvent event) {
        getPresenter().car();
    }

    @Override
    public void carList(List<MyCarListBean> bean) {
        mRvAdapter.clearData();
        mRvAdapter.addAllData(bean);
        my_car_list.setVisibility(bean.size() > 0 ? View.VISIBLE : View.GONE);
        setEmpty(bean.size());
    }

    @Override
    public void carDetails(CarDetailsBean bean) {

    }

    @Override
    public void setDefault(ErrorMsgBean bean) {
        showToast("设置成功");
        for (int i = 0; i < mRvAdapter.getAllData().size(); i++) {
            mRvAdapter.getAllData().get(i).setIs_default(0);
        }
        mRvAdapter.getAllData().get(setDefalutPosition).setIs_default(1);
    }

    @Override
    public void perectInfo(PerfectInfoBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }
}
