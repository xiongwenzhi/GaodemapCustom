package com.android.orangetravel.ui.widgets.citypicker.adapter;

import com.amap.api.services.core.PoiItem;
import com.android.orangetravel.ui.widgets.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
    void onClickList(PoiItem poiItem);
    void onClickTop(String city);
}
