package com.android.orangetravel.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.amap.api.services.core.PoiItem;
import com.android.orangetravel.R;
import com.android.orangetravel.application.Constant;
import com.android.orangetravel.ui.main.PickUppassengersActivity;
import com.android.orangetravel.ui.widgets.citypicker.CityPicker;
import com.android.orangetravel.ui.widgets.citypicker.adapter.OnPickListener;
import com.android.orangetravel.ui.widgets.citypicker.model.City;
import com.android.orangetravel.ui.widgets.citypicker.model.LocatedCity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/**
 * Description: 接单
 */
public class ReceivingOrdersPopup extends BottomPopupView implements View.OnClickListener {

    private ImageView dismiss_order;
    private TextView jiedan;

    public ReceivingOrdersPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_receiving_order_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        dismiss_order = findViewById(R.id.dismiss_order);
        jiedan = findViewById(R.id.jiedan);
        jiedan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), PickUppassengersActivity.class));
            }
        });
        dismiss_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "知乎评论 onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getAppHeight(getContext()));
    }

    @Override
    public void onClick(View v) {
    }


    public interface OnItemMenuClick {
    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}