package com.android.orangetravel.ui.main;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.android.orangetravel.base.utils.log.LogUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;
import com.android.orangetravel.ui.widgets.pop.ListTestPopup;
import com.android.orangetravel.ui.widgets.view.RadarView;
import com.lxj.xpopup.XPopup;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Mr Xiong
 * @date 2020/12/21
 * 听单检测
 */

public class TripTestActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    @BindView(R.id.radar)
    RadarView radarView;
    /*要检测的布局*/
    @BindView(R.id.test_list)
    RecyclerView test_list;
    /*检测结果布局*/
    @BindView(R.id.test_result_layout)
    LinearLayout test_result_layout;
    /*檢測結果*/
    @BindView(R.id.test_result)
    RecyclerView test_result;
    /*检测完成*/
    @BindView(R.id.test_finish)
    TextView test_finish;
    /*检测顶部布局*/
    @BindView(R.id.test_top_layout)
    LinearLayout test_top_layout;
    private CommonAdapter<String> mRvAdapter;
    private CommonAdapter<BalanceBean> mRvTestResultAdapter;
    private ListTestPopup listTestPopup;
    int position = 0;
    int finalI;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trip_test;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setTitleStyle();
        initRv();
        initTestResultRv();
    }

    private void setTitleStyle() {
        setmYangStatusBar(ContextCompat.getColor(TripTestActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(TripTestActivity.this, R.color.title_bar_black));
        setTitleBar("听单检测");
        id_title_bar.setTitleBottomLineGone();
        id_title_bar.setRightText("我要反馈");
        id_title_bar.setRightTextVisible(true);
        id_title_bar.setRightTextColor(ContextCompat.getColor(mContext, R.color.white));
        radarView.start();
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<String>(mContext,
                R.layout.item_test_trip) {
            @Override
            protected void convert(ViewHolder mHolder, final String item,
                                   final int mposition) {
                mHolder.setText(R.id.test_value, item);
                if (position == mposition) {
                    mHolder.setVisible(R.id.test_success, true);
                    mHolder.setVisible(R.id.tobetest, false);
                    mHolder.setTextColor(R.id.test_value, ContextCompat.getColor(mContext, R.color.black));
                } else {
                    mHolder.setVisible(R.id.test_success, false);
                    mHolder.setVisible(R.id.tobetest, true);
                    mHolder.setTextColor(R.id.test_value, ContextCompat.getColor(mContext, R.color.colorIcon));
                }
            }
        };
        test_list.setLayoutManager(new LinearLayoutManager(mContext));
        test_list.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        test_list.setAdapter(mRvAdapter);
        mRvAdapter.addData("网络连接");
        mRvAdapter.addData("定位服务");
        mRvAdapter.addData("账号状态");
        mRvAdapter.addData("听单检测");
//        for (int i = 0; i < mRvAdapter.getAllData().size(); i++) {
//            finalI = i;
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        sleep((finalI + 1) * 800);
//                        handler.sendEmptyMessage(finalI);
//                    } catch (Exception e) {
//                    }
//                }
//            }.start();
//        }
        handle(0);
        handle(1);
        handle(2);
        handle(3);
    }

    private void handle(int finalI) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep((finalI + 1) * 800);
                    handler.sendEmptyMessage(finalI);
                } catch (Exception e) {
                }
            }
        }.start();
    }


    /**
     * 检测结果
     */
    private void initTestResultRv() {
        mRvTestResultAdapter = new CommonAdapter<BalanceBean>(mContext,
                R.layout.item_test_result) {
            @Override
            protected void convert(ViewHolder mHolder, final BalanceBean item,
                                   final int mposition) {
                mHolder.setText(R.id.name, item.getName())
                        .setText(R.id.tips, item.getTips()).setVisible(R.id.kefu, item.isShowtBtn());
            }
        };
        test_result.setLayoutManager(new LinearLayoutManager(mContext));
        test_result.addItemDecoration(new ListItemDecoration(mContext, 1f, R.color.layout_gray_bg, true));
        test_result.setAdapter(mRvTestResultAdapter);
        test_result.setNestedScrollingEnabled(false);
        mRvTestResultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                if (listTestPopup == null) {
                    listTestPopup = new ListTestPopup(TripTestActivity.this, "停止为你提供派单服务",
                            "原因：账号出借他人", "查看详情", "我知道了");
                }
                new XPopup.Builder(TripTestActivity.this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(listTestPopup)
                        .show();
            }
        });


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            position = msg.what;
            mRvAdapter.notifyItemChanged(msg.what);
            LogUtil.e(msg.what + "");
            if (msg.what == 3) {
                if (radarView != null) {
                    radarView.stop();
                }
                if (test_finish != null) {
                    test_finish.setVisibility(View.VISIBLE);
                }
                if (radarView != null) {
                    radarView.setFinish();
                }
//                test_top_layout.setVisibility(View.GONE);
//                test_list.setVisibility(View.GONE);
                if (test_result_layout != null) {
                    test_result_layout.setVisibility(View.VISIBLE);
                    test_result_layout.setAnimation(moveToViewLocation());
                }
            }
        }
    };

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    private TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE,
                500.0f, Animation.ABSOLUTE, 0.0f);
        mHiddenAction.setDuration(980);
        return mHiddenAction;
    }


    @Override
    public void requestData() {
        getPresenter().detection("account");
        getPresenter().detection("rest");
        checkNetWork();//检查网络是否正常
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (radarView != null) {
            radarView.stop();
        }
    }

    @Override
    public void contact(ContactBean bean) {

    }

    @Override
    public void balance(BalanceBean bean) {
        if ("0".equals(bean.getStatus())) { //如果有异常
            if (bean.getName().contains("账号") || bean.getName().contains("听单")) {
                bean.setShowtBtn(true);
                mRvTestResultAdapter.addData(bean);
            }

        }
    }

    //检查网络
    private void checkNetWork() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setName("网络连接异常");
                balanceBean.setTips("请检查您的网络");
                balanceBean.setShowtBtn(false);
                mRvTestResultAdapter.addData(balanceBean);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

        });
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
        showToast(msg);
    }
}
