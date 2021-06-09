package com.android.orangetravel.ui.widgets.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


import com.android.orangetravel.application.MyApp;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.base.widgets.dialog.PromptDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 咨询工具类
 *
 * @author yangfei
 * @date 2018/7/19
 */
public final class ConsultUtil {

    private ConsultUtil() {
        // no instance
    }


    /**
     * 电话咨询
     */
    public static void phoneConsult(final Context context, final String shopMobile) {
        if (StringUtil.isEmpty(shopMobile)) {
            ToastUitl.showShort(MyApp.getAppContext(), "商家未开通");
            return;
        }
        PromptDialog dialog = new PromptDialog(context, String.format("电话：%s", shopMobile));
        dialog.setBtnText("取消", "拨打");
        dialog.setOnClickConfirmListener(new PromptDialog.OnClickConfirmListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shopMobile));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        dialog.show();
    }

    /**
     * 通过图片url生成Bitmap对象
     *
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}