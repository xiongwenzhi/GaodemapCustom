package com.android.orangetravel.ui.main;

import android.view.View;

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
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2020/12/23
 * 设置管理
 */

public class DeviceManageActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.device_list)
    RecyclerView device_list;
    private CommonAdapter<WithDrawalHistoryBean> mRvAdapter;
    private int Deleteposition = -1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_manage;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setTitleBar("设备管理");
        setmYangStatusBar(ContextCompat.getColor(DeviceManageActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(DeviceManageActivity.this, R.color.title_bar_black));
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<WithDrawalHistoryBean>(mContext,
                R.layout.item_device_manage) {
            @Override
            protected void convert(ViewHolder mHolder,
                                   final WithDrawalHistoryBean item,
                                   final int position) {
                mHolder.setText(R.id.name_phone, item.getName()).setText(R.id.login_time, item.getTime());
            }
        };
        device_list.setLayoutManager(new LinearLayoutManager(mContext));
        device_list.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        device_list.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                Deleteposition = position;
                new XPopup.Builder(DeviceManageActivity.this).
                        asConfirm("删除当前设备", "删除后再次使用该设备登录需要重新验证",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        Map map = new HashMap();
                                        map.put("id", mRvAdapter.getAllData().get(position).getId());
                                        getPresenter().equipmentDel(map);
                                    }
                                }).setCancelText("取消").setConfirmText("确认")
                        .show();
            }
        });
    }

    @Override
    public void requestData() {
        getPresenter().equipment();
    }

    @Override
    public void contact(ContactBean bean) {

    }

    @Override
    public void balance(BalanceBean bean) {

    }

    @Override
    public void loginOut(ErrorMsgBean bean) {
        showToast("删除成功!");
        mRvAdapter.getAllData().remove(Deleteposition);
        mRvAdapter.notifyDataSetChanged();
    }

    @Override
    public void billList(List<BillListBean> bean) {

    }

    @Override
    public void DiveList(List<WithDrawalHistoryBean> bean) {
        mRvAdapter.addAllData(bean);
    }

    @Override
    public void Compliance(ComplianceBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
