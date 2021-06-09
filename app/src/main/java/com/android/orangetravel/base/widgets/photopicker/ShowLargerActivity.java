package com.android.orangetravel.base.widgets.photopicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.yang.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示大图
 */
public class ShowLargerActivity extends BaseActivity {

    private TextView act_show_larger_count;
    private ViewPager mViewPager;

    private ArrayList<String> mLists;
    private List<View> mViews;

    public static final String TOUCHIMAGELIST = "TouchImageList";
    public static final String TOUCHIMAGEPOSITION = "TouchImagePosition";

    public static void start(Context context, ArrayList<String> arrayList, int position) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ShowLargerActivity.TOUCHIMAGELIST, arrayList);
        bundle.putInt(ShowLargerActivity.TOUCHIMAGEPOSITION, position);

        Intent intent = new Intent(context, ShowLargerActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.act_enter_right, R.anim.act_exit_left);
        }
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_larger);
    }*/

    @Override
    public int getLayoutId() {
        return R.layout.act_show_larger;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        act_show_larger_count = (TextView) findViewById(R.id.act_show_larger_count);
        mViewPager = (ViewPager) findViewById(R.id.act_show_larger_vp);

        Bundle mBundle = getIntent().getExtras();
        mLists = new ArrayList<>();
        mLists = mBundle.getStringArrayList(TOUCHIMAGELIST);
        int mCurPosition = mBundle.getInt(TOUCHIMAGEPOSITION, 0);

        mViews = new ArrayList<>();
        for (int i = 0; i < mLists.size(); i++) {
            View view = ShowLargerActivity.this.getLayoutInflater().inflate(R.layout.act_show_larger_item, null);
            mViews.add(view);
        }

        GalleryImageViewPagerAdapter mAdapter = new GalleryImageViewPagerAdapter(this, mViews, mLists);
        mViewPager.setOffscreenPageLimit(mViews.size());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurPosition);

        act_show_larger_count.setText((mCurPosition + 1) + "/" + mLists.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                act_show_larger_count.setText((position + 1) + "/" + mLists.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mAdapter.setOnViewPagerItemClick(new GalleryImageViewPagerAdapter.OnViewPagerItemClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void requestData() {
    }

}