package com.android.orangetravel.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.android.orangetravel.base.base.BaseActivityRefresh;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.ui.mvp.UserCenterPresenter;
import com.android.orangetravel.ui.mvp.UsercenterView;
import com.android.orangetravel.ui.widgets.view.TipView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2021/1/23
 * 我的消息
 */

public class MyMessageActivity extends BaseActivity<UserCenterPresenter> implements View.OnClickListener, UsercenterView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*服务通知*/
    @BindView(R.id.service_layout)
    LinearLayout service_layout;
    /**/
    @BindView(R.id.service_tv)
    TextView service_tv;
    @BindView(R.id.service_noti)
    View service_noti;
    /*其他*/
    @BindView(R.id.other_layout)
    LinearLayout other_layout;
    @BindView(R.id.other_tv)
    TextView other_tv;
    @BindView(R.id.other_line)
    View other_line;
    /*消息列表*/
    @BindView(R.id.message_list)
    RecyclerView message_list;
    /*服务通知消息数*/
    @BindView(R.id.msgNum)
    TipView msgNum;
    /*其他消息消息数*/
    @BindView(R.id.other_count)
    TipView other_count;
    private CommonAdapter<MessageNotciList> mRvAdapter;
    private String messageType = "1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    public UserCenterPresenter initPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(MyMessageActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(MyMessageActivity.this, R.color.title_bar_black));
        setTitleBar("消息");
        initListen();
        initRv();
    }

    private void initListen() {
        service_layout.setOnClickListener(this);
        other_layout.setOnClickListener(this);
    }

    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<MessageNotciList>(mContext,
                R.layout.item_message) {
            @Override
            protected void convert(ViewHolder mHolder, final MessageNotciList item,
                                   final int position) {
                mHolder.setVisible(R.id.msg_is_read, "1".equals(item.getIsRead()) ? false : true)
                        .setText(R.id.title_msg, item.getTitle()).setText(R.id.desc_msg, item.getDesc());
            }
        };
        message_list.setLayoutManager(new LinearLayoutManager(mContext));
        message_list.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                Map map = new HashMap();
                map.put("id", mRvAdapter.getAllData().get(position).getId());
                getPresenter().noticeRead(map);
                mRvAdapter.getAllData().get(position).setIsRead("1");
                mRvAdapter.notifyDataSetChanged();
                Bundle bundle = new Bundle();
                bundle.putString("url", mRvAdapter.getAllData().get(position).getData());
                bundle.putString("title", mRvAdapter.getAllData().get(position).getTitle());
                gotoActivity(WebviewActivity.class, bundle);
            }
        });
    }


    @Override
    public void requestData() {
        getPresenter().noReadCount();
        getPresenter().notice("1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_layout:
                service_tv.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
                service_noti.setVisibility(View.VISIBLE);
                other_tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                other_line.setVisibility(View.GONE);
                getPresenter().notice("1");
                messageType = "1";
                break;
            case R.id.other_layout:
                other_tv.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color));
                other_line.setVisibility(View.VISIBLE);
                service_tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                service_noti.setVisibility(View.GONE);
                getPresenter().notice("0");
                messageType = "0";
                break;
        }
    }

    @Override
    public void verify(String bean) {

    }

    @Override
    public void verify(SendCodeBean bean) {

    }

    @Override
    public void verifyLogin(LoginBean bean) {

    }

    @Override
    public void resetPwd(ErrorMsgBean bean) {
        getPresenter().noReadCount();
    }

    @Override
    public void userInfo(UserInfoBean bean) {
        msgNum.setText(bean.getNotification());
        other_count.setText(bean.getOther());
        msgNum.setVisibility(bean.getNotification() > 0 ? View.VISIBLE : View.GONE);
        other_count.setVisibility(bean.getOther() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void toolList(List<ToolListBean> bean) {

    }

    @Override
    public void actiity(List<ToolListBean> bean) {

    }

    @Override
    public void noticeNew(List<MessageNotciList> bean) {
        mRvAdapter.replaceData(bean);
        message_list.setVisibility(bean.size() > 0 ? View.VISIBLE : View.GONE);
        setEmpty(bean.size());
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
