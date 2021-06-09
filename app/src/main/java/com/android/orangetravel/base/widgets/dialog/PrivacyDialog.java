package com.android.orangetravel.base.widgets.dialog;

import android.app.Dialog;
import android.content.Context;

import com.android.orangetravel.R;


/**
 * 用户第一进来弹用户协议
 */
public class PrivacyDialog extends Dialog {

    public PrivacyDialog(Context context) {
        super(context, R.style.custom_dialog2);
        setContentView(R.layout.dialog_privacy);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
