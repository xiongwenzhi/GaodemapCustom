package com.android.orangetravel.ui.fragment;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseFragment;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.ui.mvp.OrderPresenter;
import com.android.orangetravel.ui.mvp.OrderView;
import com.android.orangetravel.ui.widgets.utils.chat.DayAxisValueFormatter;
import com.android.orangetravel.ui.widgets.utils.chat.XYMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Fill;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 流水分析
 *
 * @author xiongwenzhi
 * @date 2021/2/7
 */
public class FlowAnalysisFragment extends BaseFragment<OrderPresenter> implements OnChartValueSelectedListener, OrderView {
    @BindView(R.id.chart1)
    PieChart chart;
    @BindView(R.id.barchart)
    BarChart barchart;
    @BindView(R.id.water)
    TextView water;
    @BindView(R.id.m_liushui)
    TextView m_liushui;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.water_qs)
    TextView water_qs;

    public static FlowAnalysisFragment newInstance(String status) {
        Bundle bundle = new Bundle();
        FlowAnalysisFragment fragment = new FlowAnalysisFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_flowingwater_anslysis;
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }


    @Override
    public void initView() {
        // 初始化Rv

        initBarChat();
    }

    private void initChat(String water) {
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterText(generateCenterSpannableText(water));
        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        // add a selection listener
        chart.setOnChartValueSelectedListener(this);
        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

    }

    private void initBarChat() {
        barchart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barchart.setMaxVisibleValueCount(1);
        //Y轴左侧隐藏
        barchart.getAxisRight().setEnabled(false);
        //Y轴右侧隐藏
        barchart.getAxisLeft().setEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        barchart.setPinchZoom(true);


        barchart.setDrawBarShadow(false);
        barchart.setDrawGridBackground(false);

        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(15);
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + 1 + "";
            }
        });
        barchart.getAxisLeft().setDrawGridLines(false);
        Legend l = barchart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(16f);
        l.setTextSize(11f);
        l.setXEntrySpace(20f);
        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(barchart); // For bounds control
        barchart.setMarker(mv); // Set the marker to the chart
        // setting data
        // add a nice and smooth animation
        barchart.animateY(1500);

        barchart.getLegend().setEnabled(false);


    }

    private SpannableString generateCenterSpannableText(String water) {
        SpannableString s = new SpannableString("￥" + water);
        s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length(), 0);
        return s;
    }


    private void setData(List<FlowAnalyBean.RateBean> rateBeans) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < rateBeans.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(rateBeans.get(i).getAmount()),
                    rateBeans.get(i).getName()));
        }
//        entries.add(new PieEntry(2128, "奖励"));
//        entries.add(new PieEntry(445, "其他"));
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0xff4a94f7);
        colors.add(0xff44e3cf);
        colors.add(0xfff8bb60);
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();
    }


    @Override
    public void requestData() {
        // 每次显示Fragment的时候都刷新
        getPresenter().billAnalyze();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void orderList(List<OrderListBean> bean) {

    }

    @Override
    public void orderDetails(OrderListBean.DataBean bean) {

    }

    @Override
    public void FlowAnaly(FlowAnalyBean bean) {
        water.setText("￥" + bean.getWater());
        m_liushui.setText(bean.getM() + "总流水");
        order.setText("共" + bean.getOrder() + "单");
        month.setText(bean.getM() + "流水占比");
        water_qs.setText(bean.getM() + "流水趋势");
        initChat(bean.getWater());
        setData(bean.getRate());
        setBarchartData(bean.getTrend());
    }

    @Override
    public void chargeDetails(ChargeDetailsBean bean) {

    }

    private void setBarchartData(List<FlowAnalyBean.TrendBean> trendBeans) {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < trendBeans.size(); i++) {
//            float multi = (30 + 1);
//            float val = (float) (Math.random() * multi) + multi / 3;
            values.add(new BarEntry(i, Float.parseFloat(trendBeans.get(i).getAmount())));
        }
        BarDataSet set1;
        if (barchart.getData() != null &&
                barchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barchart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barchart.getData().notifyDataChanged();
            barchart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setDrawValues(true);
            List<Fill> gradientFills = new ArrayList<>();
            int startColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);
            int endColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);
            gradientFills.add(new Fill(startColor1, endColor1));
            set1.setFills(gradientFills);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            barchart.setData(data);
            barchart.setFitBars(true);
        }
        Matrix m = new Matrix();
        m.postScale(3f, 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        barchart.getViewPortHandler().refresh(m, barchart, false);//将图表动画显示之前进行缩
        barchart.invalidate();
    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);

    }
}