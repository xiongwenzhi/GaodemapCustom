package com.android.orangetravel.ui.widgets.citypicker.adapter;

import com.android.orangetravel.ui.widgets.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
