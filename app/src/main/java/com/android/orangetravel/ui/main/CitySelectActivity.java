package com.android.orangetravel.ui.main;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2020/12/30
 * 城市选择
 */

public class CitySelectActivity extends BaseActivity {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    private CommonAdapter<String> mRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_city_select;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setTitleBar("城市选择");
        setmYangStatusBar(ContextCompat.getColor(CitySelectActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(CitySelectActivity.this, R.color.title_bar_black));
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<String>(mContext,
                R.layout.item_device_manage) {
            @Override
            protected void convert(ViewHolder mHolder, final String item,
                                   final int position) {
            }
        };
//        device_list.setLayoutManager(new LinearLayoutManager(mContext));
//        device_list.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
//        device_list.setAdapter(mRvAdapter);
        mRvAdapter.addData("");
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                new XPopup.Builder(CitySelectActivity.this).
                        asConfirm("删除当前设备", "删除后再次使用该设备登录需要重新验证",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                    }
                                }).setCancelText("取消").setConfirmText("确认")
                        .show();
            }
        });
        mRvAdapter.addData("");
        mRvAdapter.addData("");
        mRvAdapter.addData("");
    }

    @Override
    public void requestData() {

    }
}
