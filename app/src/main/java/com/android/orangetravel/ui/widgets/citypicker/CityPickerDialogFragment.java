package com.android.orangetravel.ui.widgets.citypicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.MultiItemTypeAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.LoadingDialogInter;
import com.android.orangetravel.base.base.LoadingDialogInterImpl;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.ui.widgets.citypicker.adapter.CityListAdapter;
import com.android.orangetravel.ui.widgets.citypicker.adapter.InnerListener;
import com.android.orangetravel.ui.widgets.citypicker.adapter.OnPickListener;
import com.android.orangetravel.ui.widgets.citypicker.adapter.decoration.DividerItemDecoration;
import com.android.orangetravel.ui.widgets.citypicker.adapter.decoration.SectionItemDecoration;
import com.android.orangetravel.ui.widgets.citypicker.db.DBManager;
import com.android.orangetravel.ui.widgets.citypicker.model.City;
import com.android.orangetravel.ui.widgets.citypicker.model.HotCity;
import com.android.orangetravel.ui.widgets.citypicker.model.LocatedCity;
import com.android.orangetravel.ui.widgets.citypicker.util.ScreenUtil;
import com.android.orangetravel.ui.widgets.citypicker.view.SideIndexBar;
import com.android.orangetravel.ui.widgets.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Bro0cL
 * @Date: 2018/2/6 20:50
 */
public class CityPickerDialogFragment extends DialogFragment implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener, PoiSearch.OnPoiSearchListener {
    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;
    private EditText mSearchBox;
    private TextView mCancelBtn;
    private ImageView mClearAllBtn;
    private RecyclerView search_result; //????????????

    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;

    private DBManager dbManager;

    private int height;
    private int width;

    private boolean enableAnim = false;
    private int mAnimStyle = com.zaaach.citypicker.R.style.DefaultCityPickerAnimation;
    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;
    private TextView choose_city;
    private TextView location_city;
    private PoiSearch.Query query;// Poi???????????????;
    private PoiSearch poiSearch;// POI??????
    private int currentPage = 0;// ??????????????????0????????????
    private ProgressDialog progDialog = null;// ??????????????????
    PoiResult poiResult;
    private LoadingDialogInter mLoadingDialogInter = new LoadingDialogInterImpl();
    private CommonAdapter<PoiItem> mRvAdapter;
    private int type = 0;
    private View v_ui_line;

    /**
     * ????????????
     *
     * @param enable ????????????????????????
     * @return
     */
    public static CityPickerDialogFragment newInstance(boolean enable) {
        final CityPickerDialogFragment fragment = new CityPickerDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, com.zaaach.citypicker.R.style.CityPickerStyle);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setLocatedCity(LocatedCity location) {
        mLocatedCity = location;
    }

    public void setHotCities(List<HotCity> data) {
        if (data != null && !data.isEmpty()) {
            this.mHotCities = data;
        }
    }

    @SuppressLint("ResourceType")
    public void setAnimationStyle(@StyleRes int resId) {
        this.mAnimStyle = resId <= 0 ? mAnimStyle : resId;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(com.zaaach.citypicker.R.layout.cp_dialog_city_picker, container, false);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initViews();
    }

    private void initViews() {
        mRecyclerView = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_city_recyclerview);
        search_result = mContentView.findViewById(R.id.search_result);
        v_ui_line = mContentView.findViewById(R.id.v_ui_line);
        choose_city = mContentView.findViewById(com.zaaach.citypicker.R.id.choose_city);
        choose_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_result.setVisibility(View.GONE);
            }
        });

        location_city = mContentView.findViewById(com.zaaach.citypicker.R.id.location_city);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SectionItemDecoration(getActivity(), mAllCities), 0);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()), 1);
        mAdapter = new CityListAdapter(getActivity(), mAllCities, mHotCities, locateState);
        mAdapter.autoLocate(true);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //?????????????????????????????????
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.refreshLocationItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        mEmptyView = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_empty_view);
        mOverlayTextView = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_overlay);

        mIndexBar = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_side_index_bar);
        mIndexBar.setNavigationBarHeight(ScreenUtil.getNavigationBarHeight(getActivity()));
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);

        mSearchBox = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_search_box);
        mSearchBox.addTextChangedListener(this);

        mCancelBtn = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_cancel);
        mClearAllBtn = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_clear_all);
        mCancelBtn.setOnClickListener(this);
        mClearAllBtn.setOnClickListener(this);
        location_city.setText("?????????????????????" + mLocatedCity.getName());
        location_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPickListener != null) {
                    mOnPickListener.onClickTop(mLocatedCity.getName());
                    dismiss();
                }
            }
        });
        choose_city.setText(mLocatedCity.getName());
        initRv();
        if (CommonUtil.getValue(getContext(), "addressSelect", "history").size() > 0) {
            search_result.setVisibility(View.VISIBLE);
            mRvAdapter.clearData();
            mRvAdapter.addAllData(CommonUtil.getValue(getContext(), "addressSelect", "history"));
        }
        choose_city.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
        v_ui_line.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
        mSearchBox.setHint(type == 1 ? "?????????????????????" : "???????????????");
        search_result.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
    }

    private void initData() {
        Bundle args = getArguments();
        if (args != null) {
            enableAnim = args.getBoolean("cp_enable_anim");
        }
        //?????????????????????
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("??????", "??????", "101010100"));
            mHotCities.add(new HotCity("??????", "??????", "101020100"));
            mHotCities.add(new HotCity("??????", "??????", "101280101"));
            mHotCities.add(new HotCity("??????", "??????", "101280601"));
            mHotCities.add(new HotCity("??????", "??????", "101030100"));
            mHotCities.add(new HotCity("??????", "??????", "101210101"));
            mHotCities.add(new HotCity("??????", "??????", "101190101"));
            mHotCities.add(new HotCity("??????", "??????", "101270101"));
            mHotCities.add(new HotCity("??????", "??????", "101200101"));
        }
        //????????????????????????????????????????????????????????????
//        if (mLocatedCity == null) {
//            mLocatedCity = new LocatedCity(getString(R.string.cp_locating), "??????", "0");
//            locateState = LocateState.LOCATING;
//        } else {
//            locateState = LocateState.SUCCESS;
//        }

        dbManager = new DBManager(getActivity());
        mAllCities = dbManager.getAllCities();
//        mAllCities.add(0, mLocatedCity);
//        mAllCities.add(1, new HotCity("????????????", "??????", "0"));
        mResults = mAllCities;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mOnPickListener != null) {
                        mOnPickListener.onCancel();
                    }
                }
                return false;
            }
        });

        measure();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            window.setGravity(Gravity.BOTTOM);
//            window.setLayout(width, height - ScreenUtil.getStatusBarHeight(getActivity()));
            window.setLayout(width, height);
            if (enableAnim) {
                window.setWindowAnimations(mAnimStyle);
            }
        }
    }

    //????????????
    private void measure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            height = dm.heightPixels;
            width = dm.widthPixels;
        } else {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            height = dm.heightPixels;
            width = dm.widthPixels;
        }
    }

    /**
     * ???????????????
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (type == 1) { //????????????????????????
            return;
        }
        if (s.toString().length() > 0) {
            doSearchQuery();
        } else {
            mRvAdapter.clearData();
            mRvAdapter.addAllData(CommonUtil.getValue(getContext(), "addressSelect", "history"));
        }
    }


    /**
     * ?????????Rv
     */
    private void initRv() {
        mRvAdapter = new CommonAdapter<PoiItem>(getContext(),
                R.layout.item_search_result) {
            @Override
            protected void convert(ViewHolder mHolder, final PoiItem item,
                                   final int position) {
                mHolder.setText(R.id.location_name, item.getTitle()).
                        setText(R.id.location_details, item.getProvinceName()
                                + item.getCityName() + item.getAdName() + item.getSnippet());
//                        setText(R.id.distance, item.getDistance() + "");

            }
        };
        search_result.setLayoutManager(new LinearLayoutManager(getContext()));
        search_result.addItemDecoration(new ListItemDecoration(getContext(), 1f, R.color.layout_gray_bg, true));
        search_result.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                CommonUtil.SetSearchHistory("addressSelect",
                        getContext(), mRvAdapter.getAllData().get(position), "history");
                if (mOnPickListener != null) {
                    mOnPickListener.onClickList(mRvAdapter.getAllData().get(position));
                    dismiss();
                }
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (type == 0) {
            return;
        }
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            mClearAllBtn.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mResults = mAllCities;
            ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        } else {
            mClearAllBtn.setVisibility(View.VISIBLE);
            //?????????????????????
            mResults = dbManager.searchCity(keyword);
            ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        search_result.setVisibility(View.GONE);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.zaaach.citypicker.R.id.cp_cancel) {
            dismiss();
            if (mOnPickListener != null) {
                mOnPickListener.onCancel();
            }
        } else if (id == com.zaaach.citypicker.R.id.cp_clear_all) {
            mSearchBox.setText("");
        }
    }

    /**
     * ????????????poi??????
     */
    protected void doSearchQuery() {
        showLoadingDialog("?????????");// ???????????????
        currentPage = 0;
        query = new PoiSearch.Query(TextUtils.isEmpty(mSearchBox.getText().toString().trim())
                ? choose_city.getText().toString() : mSearchBox.getText().toString(),
                "",
                choose_city.getText().toString());// ????????????????????????????????????????????????????????????poi????????????????????????????????????poi??????????????????????????????????????????
        query.setPageSize(20);// ?????????????????????????????????poiitem
        query.setPageNum(currentPage);// ??????????????????
        poiSearch = new PoiSearch(getContext(), query);
//        poiSearch.setBound(new PoiSearch.SearchBound(Constant.locationLatLonPoint, 1000000000, true));//
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }


    /**
     * ???????????????
     */
    protected void showLoadingDialog(String msg) {
        mLoadingDialogInter.showLoadingDialog(getContext(), msg);
    }

    /**
     * ???????????????
     */
    private void dissmissProgressDialog() {
        mLoadingDialogInter.dismissLoadingDialog();
    }

    @Override
    public void onIndexChanged(String index, int position) {
        //??????RecyclerView???????????????
        mAdapter.scrollToSection(index);
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
//        dismiss();
        choose_city.setText(data.getName());
        if (type == 0) { //????????????????????????????????????
            doSearchQuery();
            return;
        }
        if (mOnPickListener != null) {
            mOnPickListener.onPick(position, data);
            dismiss();
        }
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }

    /**
     * POI????????????????????????
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// ???????????????
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// ??????poi?????????
                if (result.getQuery().equals(query)) {// ??????????????????
                    poiResult = result;
                    // ??????????????????poiitems????????????
                    List<PoiItem> poiItems = poiResult.getPois();// ??????????????????poiitem????????????????????????0??????
                    mRvAdapter.clearData();
                    mRvAdapter.addAllData(poiItems);
                    Log.e("mtgetAdCode", poiItems.get(0).getAdCode());
                    Log.e("mtgetAdName", poiItems.get(0).getAdName());
                    Log.e("mtgetBusinessArea", poiItems.get(0).getBusinessArea());
                    Log.e("mtgetCityName", poiItems.get(0).getCityName());
                    Log.e("mtgetDirection", poiItems.get(0).getDirection());
                    Log.e("mtgetParkingType", poiItems.get(0).getParkingType());
                    Log.e("mt", poiItems.get(0).getProvinceName());
                    Log.e("mtdis", poiItems.get(0).getDistance() + "");
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// ???????????????poiitem?????????????????????????????????????????????????????????
                    if (poiItems != null && poiItems.size() > 0) {
//                        ToastUitl.showShort(getContext(), poiItems.size() + "");
                        search_result.setVisibility(View.VISIBLE);
                    } else if (suggestionCities != null && suggestionCities.size() > 0) {

                    } else {
                        ToastUitl.showShort(getContext(), "?????????");
                    }
                }
            } else {
                ToastUitl.showShort(getContext(), "?????????");
            }
        } else {
            ToastUitl.showShort(getContext(), "??????????????????");
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
