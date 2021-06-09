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
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2020/12/23
 * 常见问题
 */

public class CommonProblemActivity extends BaseActivity {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.problem_list)
    RecyclerView problem_list;
    private CommonAdapter<String> mRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_problem;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setTitleBar("常见问题");
        setmYangStatusBar(ContextCompat.getColor(CommonProblemActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(CommonProblemActivity.this, R.color.title_bar_black));
        initRv();
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<String>(mContext,
                R.layout.item_problem) {
            @Override
            protected void convert(ViewHolder mHolder, final String item,
                                   final int position) {
            }
        };
        problem_list.setLayoutManager(new LinearLayoutManager(mContext));
        problem_list.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        problem_list.setAdapter(mRvAdapter);
        mRvAdapter.addData("");
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
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
