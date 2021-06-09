package com.android.orangetravel.base.versionUpdate;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yang.base.R;


/**
 * Created by Administrator on 2015/11/2.
 */
public class CustomDialogProGress extends LocalDialog {
    private Context context;
    private TextView titleTv, contentTv;
    TextView mokBtn;
    TextView mtitle_name;
    TextView mbtn_cancel;
    TextView mtext_view;
    ProgressBar pro;


    protected CustomDialogProGress(Context context) {
        super(context);
    }

    public void setProgress(int progress) {
        pro.setProgress(progress);
    }

    public void setText(String text) {
        mokBtn.setText(text);
    }

    public void setVersion(String version) {
        mbtn_cancel.setText(version);
    }

    public void settitleName(String titlename) {
        mtitle_name.setText(titlename);
    }

    public void setcotent(String content) {
        mtext_view.setText(content);
    }

    @Override
    public int getdialogLayoutId() {
        return R.layout.customdialogprogress_layout;
    }

    @Override
    public void initdata() {
        mokBtn = findViewById(R.id.btn_ok);
        mtitle_name = findViewById(R.id.title_name);
        mbtn_cancel = findViewById(R.id.btn_cancel);
        mtext_view = findViewById(R.id.text_view);
        pro = findViewById(R.id.pro);
    }


}
