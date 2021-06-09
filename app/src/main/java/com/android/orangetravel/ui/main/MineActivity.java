package com.android.orangetravel.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.baseadapter.BaseCommonAdapter;
import com.android.orangetravel.base.adapter.baseadapter.BaseViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.DisplayUtil;
import com.android.orangetravel.base.widgets.CircleImageView;
import com.android.orangetravel.base.widgets.GridViewScroll;
import com.android.orangetravel.base.widgets.ListViewScroll;
import com.android.orangetravel.bean.CenterMenuDataModel;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.ui.mvp.UserCenterPresenter;
import com.android.orangetravel.ui.mvp.UsercenterView;
import com.android.orangetravel.ui.widgets.view.CustomScrollView;
import com.superluo.textbannerlibrary.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Mr Xiong
 * @date 2020/12/20
 * 个人中心
 */

public class MineActivity extends BaseActivity<UserCenterPresenter> implements UsercenterView {

    /*滚动条*/
    @BindView(R.id.scrollView)
    CustomScrollView mscrollView;
    /*顶部topbar*/
    @BindView(R.id.top_bar)
    LinearLayout top_bar;
    /*顶部标题栏*/
    @BindView(R.id.toolbar)
    FrameLayout mtoolbar;
    /*个人中心标题*/
    @BindView(R.id.integral_title)
    TextView mintegral_title;
    /*标题栏*/
    @BindView(R.id.layout_title)
    RelativeLayout layout_title;
    /*我的行程等菜单*/
    @BindView(R.id.menu_top)
    GridViewScroll menugridViewScroll;
    /*我的工具菜单*/
    @BindView(R.id.CommonlyList)
    GridViewScroll CommonlyList;
    /*文字跑马灯*/
    @BindView(R.id.tv_banner)
    TextBannerView marqueeView;
    /*用户名*/
    @BindView(R.id.username)
    TextView username;
    /*用户头像*/
    @BindView(R.id.frg_mine_header)
    CircleImageView frg_mine_header;
    /*推荐活动*/
    @BindView(R.id.activity_list)
    ListViewScroll activity_list;
    /*常用功能适配器*/
    BaseCommonAdapter<CenterMenuDataModel> mcommonAdapter;
    /*我的工具适配器*/
    BaseCommonAdapter<ToolListBean> mCommonlyListAdapter;
    private BaseCommonAdapter<ToolListBean> mRvAdapter;
    private UserInfoBean userInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public UserCenterPresenter initPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void initView() {
        setTitleStyle();
        titleView();
        initCommonFun();
        initCommonlyList();
        initRv();
        setSimpleMarqueeView();
    }

    private void setSimpleMarqueeView() {

    }

    /*个人主页*/
    @OnClick(R.id.personal_homePage)
    void personal_homePage() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userBean", userInfoBean);
        gotoActivity(PersonalHomepageActivity.class, bundle);
    }

    //关闭当前页面
    @OnClick({R.id.mine_back_black, R.id.mine_back})
    void mine_back_black() {
        finish();
    }

    //关闭当前页面
    @OnClick({R.id.setting, R.id.setting_white})
    void setting() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userBean", userInfoBean);
        gotoActivity(SettingActivity.class, bundle);
    }

    @Override
    public void requestData() {
        getPresenter().info();
        getPresenter().tools();
        getPresenter().activity();
        getPresenter().noticeNew();
    }


    private void titleView() {
        top_bar.setPadding(0, DisplayUtil.getStatusBarHeight(mContext), 0, 0);
        //获取dimen属性中 标题和头部图片的高度
        final float title_height = getResources().getDimension(R.dimen.space_40dp);
        final float head_height = getResources().getDimension(R.dimen.space_100dp);
        //滑动事件回调监听（一次滑动的过程一般会连续触发多次）
        mscrollView.setOnScrollListener(new CustomScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {//手指往上滑,距离未超过200dp
//                    标题栏逐渐从透明变成不透明
                    mtoolbar.setBackgroundColor(ContextCompat.
                            getColor(MineActivity.this,
                                    R.color.c424a5e));
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                } else if (!isUp && dy > move_distance) {//手指往上滑,距离超过200dp
                    TitleAlphaChange(1, 1);//设置不透明百分比为100%，防止因滑动速度过快，导致距离超过200dp,而标题栏透明度却还没变成完全不透的情况。
//                    mintegral_title.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    layout_title.setVisibility(View.VISIBLE);
                } else if (isUp && dy <= move_distance) {//返回顶部，但距离头部位置小于200dp
//                    标题栏逐渐从不透明变成透明
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    layout_title.setVisibility(View.GONE);
//                    mintegral_title.setTextColor(ContextCompat.getColor(mContext, R.color.trans));
                }
            }
        });
    }

    private void setTitleStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams totalBarParams = new FrameLayout.LayoutParams
                    (FrameLayout.LayoutParams.MATCH_PARENT,
                            DisplayUtil.dip2px(40)
                                    + DisplayUtil.getStatusBarHeight(mContext));
            mtoolbar.setLayoutParams(totalBarParams);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    DisplayUtil.dip2px(40));
            layoutParams.setMargins(0,
                    DisplayUtil.getStatusBarHeight(mContext)
                    , 0, 0);
            layout_title.setLayoutParams(layoutParams);
            layout_title.setGravity(Gravity.CENTER);
        }
    }

    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {//设置标题栏透明度变化
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        //如果是设置背景透明度，则传入的参数是int类型，取值范围0-255
        //如果是设置控件透明度，传入的参数是float类型，取值范围0.0-1.0
        //这里我们是设置背景透明度就好，因为设置控件透明度的话，返回ICON等也会变成透明的。
        //alpha 值越小越透明
        int alpha = (int) (percent * 255);
        if (mtoolbar != null) {
            mtoolbar.getBackground().setAlpha(alpha);//设置控件背景的透明度，传入int类型的参数（范围0~255）
        }
    }


    /**
     * 初始化Rv
     */
    private void initRv() {
        mRvAdapter = new BaseCommonAdapter<ToolListBean>(mContext, R.layout.item_activity) {
            @Override
            protected void convert(BaseViewHolder mHolder, ToolListBean item, int position) {
                mHolder.setText(R.id.name, item.getName()).setText(R.id.desc, item.getData()).loadImage(mContext,
                        item.getImage(), R.id.image);
            }
        };
        activity_list.setAdapter(mRvAdapter);
    }

    /**
     * 常用功能
     */
    private void initCommonFun() {
        mcommonAdapter = new BaseCommonAdapter<CenterMenuDataModel>(
                mContext, R.layout.item_commonfun_layouot) {
            @Override
            protected void convert(BaseViewHolder mHolder, CenterMenuDataModel item, int position) {
                mHolder.loadImage(mContext, item.getImage(), R.id.menu_image).
                        setText(R.id.menu_text, item.getTitle());
            }
        };
        menugridViewScroll.setAdapter(mcommonAdapter);
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/e17c4a43aef42b595164489059538f73.png", "行程", "trip"));
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/064bbde3b71067e630ee6e09db4a2f6b.png", "钱包", "wallet"));
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/97a21f3f7d519d96acbea4483c558d7e.png", "流水", "water"));
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/2df337a4c53d942ac5ee081c81a1924a.png", "资质", "certification"));
        menugridViewScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        gotoActivity(MyTripActivity.class);
                        break;
                    case 1:
                        gotoActivity(WalletActivity.class);
                        break;
                    case 2:
                        gotoActivity(FlowingWaterActivity.class);
                        break;
                    case 3:
                        gotoActivity(MyQualificationsActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * 我的工具
     */
    private void initCommonlyList() {
        mCommonlyListAdapter = new BaseCommonAdapter<ToolListBean>(
                mContext, R.layout.item_commonfun_layouot) {
            @Override
            protected void convert(BaseViewHolder mHolder, ToolListBean item, int position) {
                mHolder.loadImage(mContext, item.getIcon(), R.id.menu_image).
                        setText(R.id.menu_text, item.getName());
                ImageView menu_image = mHolder.getView(R.id.menu_image);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) menu_image.getLayoutParams();
                layoutParams.width = DisplayUtil.dip2px(20);
                layoutParams.height = DisplayUtil.dip2px(20);
                menu_image.setLayoutParams(layoutParams);

            }
        };
        CommonlyList.setAdapter(mCommonlyListAdapter);
        CommonlyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mCommonlyListAdapter.getAllData().get(position).getName()) {
                    case "听单检测":/*听单检测*/
                        gotoActivity(TripTestActivity.class);
                        break;
                    case "导航设置":/*导航设置*/
                        gotoActivity(NavigationSettingActivity.class);
                        break;
                    case "接单手机号":/*接单手机号*/
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userBean", userInfoBean);
                        gotoActivity(ChangePhoneActivity.class, bundle);
                        break;
                    case "车辆管理":/*车辆管理*/
                        gotoActivity(MyCarListActivity.class);
                        break;
                    case "安橙出行":/*安橙出行*/
                        gotoActivity(OrgangeMapActivity.class);
                        break;
                    /*二维码*/
                    case "二维码":
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("userBean", userInfoBean);
                        gotoActivity(MyQrCodeActivity.class, bundle1);
                        break;

                }
            }
        });
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

    }

    @Override
    public void userInfo(UserInfoBean bean) {
        userInfoBean = bean;
        username.setText(bean.getName());
        GlideUtil.loadImg(MineActivity.this, bean.getAvatar(), frg_mine_header);
    }

    @Override
    public void toolList(List<ToolListBean> bean) {
        mCommonlyListAdapter.addAllData(bean);
    }

    @Override
    public void actiity(List<ToolListBean> bean) {
        mRvAdapter.addAllData(bean);
    }

    @Override
    public void noticeNew(List<MessageNotciList> bean) {
        //设置数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bean.size(); i++) {
            list.add(bean.get(i).getTitle());
        }
        marqueeView.setDatas(list);
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
