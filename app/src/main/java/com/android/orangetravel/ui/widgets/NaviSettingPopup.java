package com.android.orangetravel.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.bean.NavigationSettingBean;
import com.android.orangetravel.ui.main.NavigationSettingActivity;
import com.google.gson.Gson;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import static com.android.orangetravel.ui.main.NavigationSettingActivity.SAVENAVAGI_KET;

/**
 * 导航设置Pop
 */
public class NaviSettingPopup extends BottomPopupView implements View.OnClickListener {
    private TextView tv_dismiss;
    private TextView navi_setting;
    private LinearLayout navi_setting_layout;
    boolean isDay;
    private ImageView lukuang_img;//路况
    private ImageView nav_vocie;//导航声音
    private ImageView fangxiang;//方向
    private TextView lukuang_tv;//路况文字
    private TextView voice_tv;//导航声音
    private TextView fangxiang_tv;//方向文字
    NavigationSettingBean settingBean;

    public NaviSettingPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_navisetting_pop;
    }

    public void setNightModel(boolean isDay) {
        this.isDay = isDay;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        tv_dismiss = findViewById(R.id.tv_dismiss);
        navi_setting = findViewById(R.id.navi_setting);
        navi_setting_layout = findViewById(R.id.navi_setting_layout);
        lukuang_img = findViewById(R.id.lukuang_img);
        lukuang_img.setOnClickListener(this);
        fangxiang = findViewById(R.id.fangxiang);
        fangxiang.setOnClickListener(this);
        nav_vocie = findViewById(R.id.nav_vocie);
        lukuang_tv = findViewById(R.id.lukuang_tv);
        voice_tv = findViewById(R.id.voice_tv);
        fangxiang_tv = findViewById(R.id.fangxiang_tv);
        navi_setting_layout = findViewById(R.id.navi_setting_layout);
        tv_dismiss.setOnClickListener(this);
        navi_setting.setOnClickListener(this);
        nav_vocie.setOnClickListener(this);
        setNavaiSetting();

    }


    //导航设置
    private void setNavaiSetting() {
        String setting = SPUtil.get(SAVENAVAGI_KET, "").toString();
        if (TextUtils.isEmpty(setting)) {  //如果没有设置过
            settingBean = new NavigationSettingBean();
            settingBean.setFangxiang("chetou");
            settingBean.setDayModel("auto");
            settingBean.setTraffic(true);
            settingBean.setVocie(true);
            settingBean.setQianyinLine(true);
            settingBean.setLukuang(true);
            String json = new Gson().toJson(settingBean);
            SPUtil.put(SAVENAVAGI_KET, json);
        } else {
            settingBean = new Gson().fromJson(setting, NavigationSettingBean.class);
        }
        if (isDay) {//白天模式
            if ("chetou".equals(settingBean.getFangxiang())) { //车头放心
                fangxiang.setImageResource(R.mipmap.dht_navisetting_north);
                fangxiang_tv.setText("始终向北");
            } else {
                fangxiang.setImageResource(R.mipmap.dht_navisetting_follow_head);
                fangxiang_tv.setText("跟随车头");
            }
            if (settingBean.getTraffic()) { //路况
                lukuang_img.setImageResource(R.mipmap.lk_check_day);
                lukuang_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            } else {
                lukuang_img.setImageResource(R.mipmap.lk_nockech_day);
                lukuang_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.gray1));
            }
            if (settingBean.getVocie()) { //声音
                nav_vocie.setImageResource(R.mipmap.voice_check_day);
                voice_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            } else {
                nav_vocie.setImageResource(R.mipmap.voice_nocheck_day);
                voice_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.gray1));
            }
            navi_setting_layout.setBackgroundResource(R.drawable.bg_round);
            tv_dismiss.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            navi_setting.setBackgroundResource(R.drawable.grey_shape_8);
            navi_setting.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        } else {//晚上模式
            if ("chetou".equals(settingBean.getFangxiang())) { //车头方向
                fangxiang.setImageResource(R.mipmap.xiangbei_ngiht);
                fangxiang_tv.setText("始终向北");
                fangxiang_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            } else {
                fangxiang.setImageResource(R.mipmap.chetou_night);
                fangxiang_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
                fangxiang_tv.setText("跟随车头");
            }
            if (settingBean.getTraffic()) { //路况
                lukuang_img.setImageResource(R.mipmap.lukuang_night);
                lukuang_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            } else {
                lukuang_img.setImageResource(R.mipmap.lu_nocheck_night);
                lukuang_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.ca4));
            }
            if (settingBean.getVocie()) { //声音
                nav_vocie.setImageResource(R.mipmap.daohang_voice_night);
                voice_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            } else {
                nav_vocie.setImageResource(R.mipmap.daohang_voice_nocheck_night);
                voice_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.ca4));
            }
            navi_setting_layout.setBackgroundResource(R.drawable.bg_round_night);
            tv_dismiss.setTextColor(ContextCompat.getColor(getContext(), R.color.ca4));
            navi_setting.setBackgroundResource(R.drawable.grey_shape_night);
            navi_setting.setTextColor(ContextCompat.getColor(getContext(), R.color.ca4));
        }

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
                dismiss();
                break;
            case R.id.fangxiang:
                if (onItemMenuClick != null) {
                    onItemMenuClick.xiangbei(settingBean.getFangxiang());
                }
                if (isDay) {
                    if ("chetou".equals(settingBean.getFangxiang())) { //车头方向
                        fangxiang.setImageResource(R.mipmap.dht_navisetting_north);
                        fangxiang_tv.setText("始终向北");
                        settingBean.setFangxiang("xiangbei");
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    } else {
                        fangxiang.setImageResource(R.mipmap.dht_navisetting_follow_head);
                        fangxiang_tv.setText("跟随车头");
                        settingBean.setFangxiang("chetou");
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    }
                } else {
                    if ("chetou".equals(settingBean.getFangxiang())) { //车头方向
                        fangxiang.setImageResource(R.mipmap.xiangbei_ngiht);
                        fangxiang_tv.setText("始终向北");
                        settingBean.setFangxiang("xiangbei");
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    } else {
                        fangxiang.setImageResource(R.mipmap.chetou_night);
                        fangxiang_tv.setText("跟随车头");
                        settingBean.setFangxiang("chetou");
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    }
                }

                dismiss();
                break;
            case R.id.lukuang_img:
                if (isDay) {
                    if (settingBean.getTraffic()) { //路况
                        lukuang_img.setImageResource(R.mipmap.lk_check_day);
                        settingBean.setTraffic(false);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    } else {
                        lukuang_img.setImageResource(R.mipmap.lk_nockech_day);
                        settingBean.setTraffic(true);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    }
                } else {
                    if (settingBean.getTraffic()) { //路况
                        lukuang_img.setImageResource(R.mipmap.lk_check_day);
                        settingBean.setTraffic(false);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    } else {
                        lukuang_img.setImageResource(R.mipmap.lk_check_day);
                        settingBean.setTraffic(true);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    }
                }
                if (onItemMenuClick != null) {
                    onItemMenuClick.trafficClick(settingBean.getTraffic());
                }
                dismiss();
                break;
            case R.id.nav_vocie:
                if (isDay) {
                    if (settingBean.getVocie()) { //声音
                        nav_vocie.setImageResource(R.mipmap.voice_check_day);
                        settingBean.setVocie(false);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    } else {
                        nav_vocie.setImageResource(R.mipmap.voice_nocheck_day);
                        settingBean.setVocie(true);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    }
                } else {
                    if (settingBean.getVocie()) { //路况
                        nav_vocie.setImageResource(R.mipmap.daohang_voice_night);
                        settingBean.setVocie(false);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    } else {
                        nav_vocie.setImageResource(R.mipmap.daohang_voice_nocheck_night);
                        settingBean.setVocie(true);
                        String json = new Gson().toJson(settingBean);
                        SPUtil.put(SAVENAVAGI_KET, json);
                    }
                }
                if (onItemMenuClick != null) {
                    onItemMenuClick.voice(settingBean.getVocie());
                }
                dismiss();
                break;
        }
    }


    public interface OnItemMenuClick {
        void trafficClick(boolean isCheck);

        void voice(boolean isCheck);

        void xiangbei(String fangxiang);

    }

    OnItemMenuClick onItemMenuClick;

    public void setOnItemClick(OnItemMenuClick onItemClick) {
        this.onItemMenuClick = onItemClick;

    }
}