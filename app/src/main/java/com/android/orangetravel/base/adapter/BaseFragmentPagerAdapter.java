package com.android.orangetravel.base.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * FragmentPagerAdapter的公共适配器
 *
 * @author yangfei
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = null;
    private List<Fragment> mFragments = null;

    public BaseFragmentPagerAdapter(FragmentManager fm, @Nullable String[] mTitles,
                                    List<Fragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null == mTitles) {
            return String.valueOf(position);// super.getPageTitle(position);
        } else {
            return mTitles[position];
        }
    }

    @Override
    public int getCount() {
        return mFragments.size() > 0 ? mFragments.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}