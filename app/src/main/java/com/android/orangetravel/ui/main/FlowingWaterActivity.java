package com.android.orangetravel.ui.main;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.BaseFragmentPagerAdapter;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.tablayout.SlidingTabLayout;
import com.android.orangetravel.ui.fragment.FlowAnalysisDetailsFragment;
import com.android.orangetravel.ui.fragment.FlowAnalysisFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2021/2/5
 * 流水
 */

public class FlowingWaterActivity extends BaseActivity {
    @BindView(R.id.type_viewpage)
    ViewPager type_viewpage;
    @BindView(R.id.act_type_list_tab)
    SlidingTabLayout act_type_list_tab;
    /*适配器和数据源*/
    private BaseFragmentPagerAdapter mVpAdapter;
    private String[] mTitles;
    private List<Fragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flowingwater;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(FlowingWaterActivity.this, R.color.title_bar_black));
        act_type_list_tab.setBackgroundColor(ContextCompat.getColor(FlowingWaterActivity.this, R.color.title_bar_black));
        initVP();
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    private void initVP() {
        mTitles = new String[]{"流水明细", "流水分析"};
        mFragments = new ArrayList<>(mTitles.length);
        mFragments.add(FlowAnalysisDetailsFragment.newInstance(null));// -----------流水明细
        mFragments.add(FlowAnalysisFragment.newInstance(null));// ----------流水分析
        // Tab宽度不相等
//        act_order_list_tab.setTabSpaceEqual(false);
        mVpAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                mTitles, mFragments);
        type_viewpage.setAdapter(mVpAdapter);
        act_type_list_tab.setViewPager(type_viewpage);
        act_type_list_tab.setUnderlineColor(ContextCompat.getColor(mContext, R.color.white));
    }


    @Override
    public void requestData() {

    }
}
