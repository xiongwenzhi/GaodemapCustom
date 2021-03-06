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
 * ????????????
 */

public class MineActivity extends BaseActivity<UserCenterPresenter> implements UsercenterView {

    /*?????????*/
    @BindView(R.id.scrollView)
    CustomScrollView mscrollView;
    /*??????topbar*/
    @BindView(R.id.top_bar)
    LinearLayout top_bar;
    /*???????????????*/
    @BindView(R.id.toolbar)
    FrameLayout mtoolbar;
    /*??????????????????*/
    @BindView(R.id.integral_title)
    TextView mintegral_title;
    /*?????????*/
    @BindView(R.id.layout_title)
    RelativeLayout layout_title;
    /*?????????????????????*/
    @BindView(R.id.menu_top)
    GridViewScroll menugridViewScroll;
    /*??????????????????*/
    @BindView(R.id.CommonlyList)
    GridViewScroll CommonlyList;
    /*???????????????*/
    @BindView(R.id.tv_banner)
    TextBannerView marqueeView;
    /*?????????*/
    @BindView(R.id.username)
    TextView username;
    /*????????????*/
    @BindView(R.id.frg_mine_header)
    CircleImageView frg_mine_header;
    /*????????????*/
    @BindView(R.id.activity_list)
    ListViewScroll activity_list;
    /*?????????????????????*/
    BaseCommonAdapter<CenterMenuDataModel> mcommonAdapter;
    /*?????????????????????*/
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

    /*????????????*/
    @OnClick(R.id.personal_homePage)
    void personal_homePage() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userBean", userInfoBean);
        gotoActivity(PersonalHomepageActivity.class, bundle);
    }

    //??????????????????
    @OnClick({R.id.mine_back_black, R.id.mine_back})
    void mine_back_black() {
        finish();
    }

    //??????????????????
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
        //??????dimen????????? ??????????????????????????????
        final float title_height = getResources().getDimension(R.dimen.space_40dp);
        final float head_height = getResources().getDimension(R.dimen.space_100dp);
        //??????????????????????????????????????????????????????????????????????????????
        mscrollView.setOnScrollListener(new CustomScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {//???????????????,???????????????200dp
//                    ???????????????????????????????????????
                    mtoolbar.setBackgroundColor(ContextCompat.
                            getColor(MineActivity.this,
                                    R.color.c424a5e));
                    TitleAlphaChange(dy, move_distance);//???????????????
                } else if (!isUp && dy > move_distance) {//???????????????,????????????200dp
                    TitleAlphaChange(1, 1);//???????????????????????????100%???????????????????????????????????????????????????200dp,????????????????????????????????????????????????????????????
//                    mintegral_title.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    layout_title.setVisibility(View.VISIBLE);
                } else if (isUp && dy <= move_distance) {//??????????????????????????????????????????200dp
//                    ???????????????????????????????????????
                    TitleAlphaChange(dy, move_distance);//???????????????
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

    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {//??????????????????????????????
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        //??????????????????????????????????????????????????????int?????????????????????0-255
        //???????????????????????????????????????????????????float?????????????????????0.0-1.0
        //???????????????????????????????????????????????????????????????????????????????????????ICON???????????????????????????
        //alpha ??????????????????
        int alpha = (int) (percent * 255);
        if (mtoolbar != null) {
            mtoolbar.getBackground().setAlpha(alpha);//???????????????????????????????????????int????????????????????????0~255???
        }
    }


    /**
     * ?????????Rv
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
     * ????????????
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
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/e17c4a43aef42b595164489059538f73.png", "??????", "trip"));
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/064bbde3b71067e630ee6e09db4a2f6b.png", "??????", "wallet"));
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/97a21f3f7d519d96acbea4483c558d7e.png", "??????", "water"));
        mcommonAdapter.addData(new CenterMenuDataModel("http://admin.jxsmzx.com/uploads/attach/2020/12/20201209/2df337a4c53d942ac5ee081c81a1924a.png", "??????", "certification"));
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
     * ????????????
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
                    case "????????????":/*????????????*/
                        gotoActivity(TripTestActivity.class);
                        break;
                    case "????????????":/*????????????*/
                        gotoActivity(NavigationSettingActivity.class);
                        break;
                    case "???????????????":/*???????????????*/
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userBean", userInfoBean);
                        gotoActivity(ChangePhoneActivity.class, bundle);
                        break;
                    case "????????????":/*????????????*/
                        gotoActivity(MyCarListActivity.class);
                        break;
                    case "????????????":/*????????????*/
                        gotoActivity(OrgangeMapActivity.class);
                        break;
                    /*?????????*/
                    case "?????????":
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
        //????????????
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
