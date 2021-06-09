package com.android.orangetravel.ui.widgets.pop;

import android.content.Context;
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
import com.android.orangetravel.ui.widgets.citypicker.CityPicker;
import com.android.orangetravel.ui.widgets.citypicker.adapter.OnPickListener;
import com.android.orangetravel.ui.widgets.citypicker.model.City;
import com.android.orangetravel.ui.widgets.citypicker.model.LocatedCity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/**
 * 听单检测 查看原因pop
 */
public class ListTestPopup extends BottomPopupView implements View.OnClickListener {

    /*查看详情*/
    private TextView look_details;
    /*我知道了*/
    private TextView user_know;

    private TextView list_test_title;
    private TextView content_test;

    private String title;
    private String content;
    private String confimBtn;
    private String cancleBtn;


    public ListTestPopup(@NonNull Context context, String title, String content, String confimBtn, String cancleBtn) {
        super(context);
        this.title = title;
        this.content = content;
        this.confimBtn = confimBtn;
        this.cancleBtn = cancleBtn;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_list_test_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        look_details = findViewById(R.id.look_details);
        user_know = findViewById(R.id.user_know);
        list_test_title = findViewById(R.id.list_test_title);
        content_test = findViewById(R.id.content_test);
        look_details.setOnClickListener(this);
        user_know.setOnClickListener(this);
        list_test_title.setText(title);
        content_test.setText(content);
        look_details.setText(confimBtn);
        user_know.setText(cancleBtn);
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
        return (int) (XPopupUtils.getAppHeight(getContext()) * .4f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.look_details:
                dismiss();
                if (onItemMenuClick != null) {
                    onItemMenuClick.cancle();
                }
                break;
            case R.id.user_know:
                dismiss();
                if (onItemMenuClick != null) {
                    onItemMenuClick.confim();
                }
                break;
        }
    }


    public interface OnItemMenuClick {
        void cancle();

        void confim();

    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}