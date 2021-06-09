package com.android.orangetravel.base.widgets.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.orangetravel.base.adapter.baseadapter.BaseCommonAdapter;
import com.android.orangetravel.base.adapter.baseadapter.BaseViewHolder;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.base.widgets.ListViewScroll;
import com.yang.base.R;

/**
 * 时间：2018/7/20 0020
 * 描述：公共的弹框
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author WangZhiHui
 */

public class ListDialog extends BaseDialog {

    private TextView dialog_common_cancel;
    private TextView dialog_common_title;
    private ListViewScroll dialog_common_listview;
    private BaseCommonAdapter<String> mAdapter;
    /*选中的*/
    private int selectPosition = -1;
    /*显示隐藏*/
    private int showHidden = View.GONE;
    private OnClickListener onClickListener;
    private Context mContext;

    /**
     * @param context 上下文对象
     * @param titles  选项数组
     */
    public ListDialog(Context context, String[] titles, String title) {
        super(context);
        this.mContext = context;
        // 弹出框位置
        setGravity(Gravity.BOTTOM);
        // 设置动画
        setAnimation(R.style.DialogBottomAnim);

        // 初始化控件
        initView(titles, title);
    }

    /**
     * 初始化控件
     */
    private void initView(String[] titles, final String title) {
        dialog_common_cancel = (TextView) findViewById(R.id.dialog_common_cancel);
        dialog_common_title = (TextView) findViewById(R.id.dialog_common_title);
        dialog_common_title.setText(title);
        dialog_common_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    if (selectPosition != -1) {
                        onClickListener.onItemClick(selectPosition);
                    } else {
                        ToastUitl.showLong(mContext, "请选择" + title);
                        return;
                    }
                    dismiss();
                }
            }
        });
        dialog_common_listview = (ListViewScroll) findViewById(R.id.dialog_common_listview);
        mAdapter = new BaseCommonAdapter<String>(getContext(), R.layout.dialog_list_item) {
            @Override
            protected void convert(BaseViewHolder mHolder, String item, int position) {
                mHolder.setText(R.id.dialog_common_item_title, item);
                CheckBox dialog_common_item_checkbox = mHolder.getView(R.id.dialog_common_item_checkbox);
                if (getShowHidden() == View.GONE) {
                    dialog_common_item_checkbox.setVisibility(View.GONE);
                } else {
                    dialog_common_item_checkbox.setVisibility(View.VISIBLE);
                }
                if (position == getSelectPosition()) {
                    dialog_common_item_checkbox.setChecked(true);
                } else {
                    dialog_common_item_checkbox.setChecked(false);
                }
                if (position == (mAdapter.getAllData().size() - 1)) {
                    mHolder.setVisible(R.id.dialog_common_item_line, false);
                } else {
                    mHolder.setVisible(R.id.dialog_common_item_line, true);
                }
            }
        };
        dialog_common_listview.setAdapter(mAdapter);
        dialog_common_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                mAdapter.notifyDataSetChanged();
            }
        });

        for (int i = 0; i < titles.length; i++) {
            mAdapter.addData(titles[i]);
        }
    }

    /**
     * 得到显示隐藏
     */
    private int getShowHidden() {
        return showHidden;
    }

    /**
     * 得到选中项
     *
     * @return
     */
    private int getSelectPosition() {
        return selectPosition;
    }

    /**
     * 设置选中项
     *
     * @param selectPosition
     */
    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    /**
     * 设置显示隐藏
     *
     * @param showHidden
     */
    public void setShowHidden(int showHidden) {
        this.showHidden = showHidden;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_list;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 接口回调
     */
    public interface OnClickListener {
        void onItemClick(int position);
    }
}
