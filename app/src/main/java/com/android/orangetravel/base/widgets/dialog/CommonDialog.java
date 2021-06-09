package com.android.orangetravel.base.widgets.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.baseadapter.BaseCommonAdapter;
import com.android.orangetravel.base.adapter.baseadapter.BaseViewHolder;
import com.android.orangetravel.base.widgets.ListViewScroll;

/**
 * 公共弹窗框
 *
 * @author yangfei
 */
public class CommonDialog extends BaseDialog {

    public CommonDialog(Context mContext, String[] titles) {
        super(mContext);
        // 弹出框位置
        setGravity(Gravity.BOTTOM);
        // 设置动画
        setAnimation(R.style.DialogBottomAnim);

        // 初始化控件
        initView(titles);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_common;
    }

    private TextView dialog_common_cancel;
    private ListViewScroll dialog_common_listview;
    private BaseCommonAdapter<String> mAdapter;

    /**
     * 初始化控件
     */
    private void initView(String[] titles) {
        dialog_common_cancel = (TextView) findViewById(R.id.dialog_common_cancel);
        dialog_common_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog_common_listview = (ListViewScroll) findViewById(R.id.dialog_common_listview);
        mAdapter = new BaseCommonAdapter<String>(getContext(), R.layout.dialog_common_item) {
            @Override
            protected void convert(BaseViewHolder mHolder, String item, int position) {
                mHolder.setText(R.id.dialog_common_item_title, item);
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
                if (null != onClickListener) {
                    dismiss();
                    onClickListener.onItemClick(position);
                }
            }
        });

        for (int i = 0; i < titles.length; i++) {
            mAdapter.addData(titles[i]);
        }
    }

    /**
     * 接口回调
     */
    public interface OnClickListener {
        void onItemClick(int position);
    }
    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
//    // 公共弹窗框
//    String[] titles = {"添加", "编辑", "删除"};
//    CommonDialog mCommonDialog = new CommonDialog(mContext, titles);
//    mCommonDialog.setOnClickListener(new CommonDialog.OnClickListener() {
//    @Override
//    public void onItemClick(int position) {
//            showShortToast(position + "");
//        }
//    });
//    mCommonDialog.show();