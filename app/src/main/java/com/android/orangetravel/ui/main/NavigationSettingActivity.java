package com.android.orangetravel.ui.main;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.NavigationSettingBean;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/28
 * 导航设置
 */

public class NavigationSettingActivity extends BaseActivity {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*跟随车头*/
    @BindView(R.id.gs_chetou)
    TextView gs_chetou;
    /*始终向北*/
    @BindView(R.id.sz_xb)
    TextView sz_xb;
    /*自动切换*/
    @BindView(R.id.auto_qie)
    TextView auto_qie;
    /*始终日间*/
    @BindView(R.id.sz_rj)
    TextView sz_rj;
    /*始终夜间*/
    @BindView(R.id.sz_yj)
    TextView sz_yj;
    /*路况*/
    @BindView(R.id.traffic_check)
    SwitchButton traffic_check;
    /*导航声音*/
    @BindView(R.id.voice_check)
    SwitchButton voice_check;
    /*牵引线*/
    @BindView(R.id.line_check)
    SwitchButton line_check;
    /*路况条*/
    @BindView(R.id.lukuang_check)
    SwitchButton lukuang_check;
    public static String SAVENAVAGI_KET = "navigationSetting";
    private NavigationSettingBean settingBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_navigation_settting;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setmYangStatusBar(ContextCompat.getColor(NavigationSettingActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.title_bar_black));
        setTitleBar("导航设置");
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
        if ("chetou".equals(settingBean.getFangxiang())) {
            gs_chetou.setBackgroundResource(R.drawable.border_theme_1);
            gs_chetou.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
            sz_xb.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
            sz_xb.setBackgroundResource(R.drawable.border_grey_1);
        } else {
            sz_xb.setBackgroundResource(R.drawable.border_theme_1);
            gs_chetou.setBackgroundResource(R.drawable.border_grey_1);
            sz_xb.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
            gs_chetou.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
        }
        if ("auto".equals(settingBean.getDayModel())) {
            setDefalut();
            auto_qie.setBackgroundResource(R.drawable.border_theme_1);
            auto_qie.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
        } else if ("day".equals(settingBean.getDayModel())) {
            setDefalut();
            sz_rj.setBackgroundResource(R.drawable.border_theme_1);
            sz_rj.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
        } else {
            setDefalut();
            sz_yj.setBackgroundResource(R.drawable.border_theme_1);
            sz_yj.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
        }
        traffic_check.setChecked(settingBean.getTraffic());
        voice_check.setChecked(settingBean.getVocie());
        line_check.setChecked(settingBean.getQianyinLine());
        lukuang_check.setChecked(settingBean.getLukuang());
        setCheckBtn();

    }

    //选中框的保存
    private void setCheckBtn() {
        traffic_check.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settingBean.setTraffic(isChecked);
                String json = new Gson().toJson(settingBean);
                SPUtil.put(SAVENAVAGI_KET, json);
            }
        });
        voice_check.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settingBean.setVocie(isChecked);
                String json = new Gson().toJson(settingBean);
                SPUtil.put(SAVENAVAGI_KET, json);
            }
        });
        line_check.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settingBean.setQianyinLine(isChecked);
                String json = new Gson().toJson(settingBean);
                SPUtil.put(SAVENAVAGI_KET, json);
            }
        });
        lukuang_check.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settingBean.setLukuang(isChecked);
                String json = new Gson().toJson(settingBean);
                SPUtil.put(SAVENAVAGI_KET, json);
            }
        });
    }

    //跟随车头
    @OnClick(R.id.gs_chetou)
    void gs_chetou() {
        gs_chetou.setBackgroundResource(R.drawable.border_theme_1);
        gs_chetou.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
        sz_xb.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
        sz_xb.setBackgroundResource(R.drawable.border_grey_1);
        settingBean.setFangxiang("chetou");
        String json = new Gson().toJson(settingBean);
        SPUtil.put(SAVENAVAGI_KET, json);

    }

    //始终向北
    @OnClick(R.id.sz_xb)
    void sz_xb() {
        settingBean.setFangxiang("xiangbei");
        String json = new Gson().toJson(settingBean);
        SPUtil.put(SAVENAVAGI_KET, json);
        sz_xb.setBackgroundResource(R.drawable.border_theme_1);
        gs_chetou.setBackgroundResource(R.drawable.border_grey_1);
        sz_xb.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
        gs_chetou.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
    }

    //自动切换
    @OnClick(R.id.auto_qie)
    void auto_qie() {
        setDefalut();
        auto_qie.setBackgroundResource(R.drawable.border_theme_1);
        auto_qie.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
        settingBean.setDayModel("auto");
        String json = new Gson().toJson(settingBean);
        SPUtil.put(SAVENAVAGI_KET, json);
    }

    //始终日间
    @OnClick(R.id.sz_rj)
    void sz_rj() {
        settingBean.setDayModel("day");
        String json = new Gson().toJson(settingBean);
        SPUtil.put(SAVENAVAGI_KET, json);
        setDefalut();
        sz_rj.setBackgroundResource(R.drawable.border_theme_1);
        sz_rj.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
    }

    //始终夜间
    @OnClick(R.id.sz_yj)
    void sz_yj() {
        setDefalut();
        settingBean.setDayModel("night");
        String json = new Gson().toJson(settingBean);
        SPUtil.put(SAVENAVAGI_KET, json);
        sz_yj.setBackgroundResource(R.drawable.border_theme_1);
        sz_yj.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.theme_color));
    }

    //设置默认
    private void setDefalut() {
        sz_rj.setBackgroundResource(R.drawable.border_grey_1);
        sz_yj.setBackgroundResource(R.drawable.border_grey_1);
        auto_qie.setBackgroundResource(R.drawable.border_grey_1);

        sz_rj.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
        sz_yj.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
        auto_qie.setTextColor(ContextCompat.getColor(NavigationSettingActivity.this, R.color.black));
    }


    @Override
    public void requestData() {

    }
}
