package com.android.orangetravel.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.bean.TrafficReportListBean;
import com.android.orangetravel.ui.main.NavigationSettingActivity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 交通上报Pop
 */
public class TrafficReportPopup extends BottomPopupView implements View.OnClickListener {
    private TextView tv_dismiss;
    private RecyclerView traffic_report_list;
    private CommonAdapter<TrafficReportListBean> mRvAdapter;
    List<TrafficReportListBean> trafficReportListBeans = new ArrayList<>();
    boolean isDay;
    private LinearLayout trafficreport_layout;

    public TrafficReportPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_trafficreport_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_dismiss = findViewById(R.id.tv_dismiss);
        traffic_report_list = findViewById(R.id.traffic_report_list);
        trafficreport_layout = findViewById(R.id.trafficreport_layout);
        tv_dismiss.setOnClickListener(this);
        setData();
        initRv();
    }

    public void setNightModel(boolean isDay) {
        this.isDay = isDay;
    }

    private void setData() {
        trafficReportListBeans.add(new TrafficReportListBean("拥堵", "congestion", isDay ? R.mipmap.yongdu_day : R.mipmap.yongdu_night));
        trafficReportListBeans.add(new TrafficReportListBean("事故", "accident", isDay ? R.mipmap.shigu_day : R.mipmap.shigu_night));
        trafficReportListBeans.add(new TrafficReportListBean("不可通行", "impassable", isDay ? R.mipmap.buketong_day : R.mipmap.buketong_night));
        trafficReportListBeans.add(new TrafficReportListBean("占到施工", "presence", isDay ? R.mipmap.shigu_day : R.mipmap.shigu_night));
        trafficReportListBeans.add(new TrafficReportListBean("管制", "controls", isDay ? R.mipmap.guanzhi_day : R.mipmap.guanzhi_night));
        trafficReportListBeans.add(new TrafficReportListBean("交规问题", "trafficRules", isDay ? R.mipmap.jiaogui_day : R.mipmap.jiaogui_night));
        trafficReportListBeans.add(new TrafficReportListBean("导航绕路", "detours", isDay ? R.mipmap.raolu_day : R.mipmap.raolu_night));
        trafficReportListBeans.add(new TrafficReportListBean("起终点问题", "startEnd", isDay ? R.mipmap.qizhongdian_day : R.mipmap.qizhongdian_night));
        trafficReportListBeans.add(new TrafficReportListBean("停车违章", "violation", isDay ? R.mipmap.tingche_day : R.mipmap.tingche_night));
        if (isDay) {
            trafficreport_layout.setBackgroundResource(R.drawable.bg_round);
            tv_dismiss.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        } else {
            trafficreport_layout.setBackgroundResource(R.drawable.bg_round_night);
            tv_dismiss.setTextColor(ContextCompat.getColor(getContext(), R.color.ca4));
        }
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<TrafficReportListBean>(getContext(),
                R.layout.item_trafficreport) {
            @Override
            protected void convert(ViewHolder mHolder, final TrafficReportListBean item,
                                   final int position) {
                mHolder.setImageResource(R.id.traffic_image, item.getImage()).setText(R.id.traffic_title, item.getMenu());
                mHolder.setTextColor(R.id.traffic_title, ContextCompat.getColor(getContext(), isDay ? R.color.black : R.color.ca4));
            }
        };
        traffic_report_list.setLayoutManager(new GridLayoutManager(getContext(), 3));
        traffic_report_list.setAdapter(mRvAdapter);
        mRvAdapter.addAllData(trafficReportListBeans);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                if (onItemMenuClick != null) {
                    onItemMenuClick.trafficClick(mRvAdapter.getAllData().get(position).getType());
                }
            }
        });
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getAppHeight(getContext()) * .9f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dismiss:
                dismiss();
                break;
            case R.id.navi_setting:
                getContext().startActivity(new Intent(getContext(), NavigationSettingActivity.class));
                break;
        }
    }

    //设置默认
    private void setDefault(boolean isCheckceSuo) {
    }

    public interface OnItemMenuClick {
        void trafficClick(String type);

    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}