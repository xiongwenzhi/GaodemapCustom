package com.android.orangetravel.base.tablayout;


import androidx.annotation.Nullable;

import com.android.orangetravel.base.tablayout.listener.CustomTabEntity;

/**
 * Class
 *
 * @author yangfei
 * @date 2018/6/20
 */
public class TabEntity implements CustomTabEntity {

    private String title;

    public TabEntity(@Nullable String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }

}