package com.android.orangetravel.application;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.android.orangetravel.base.base.BaseApp;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.tencent.smtt.sdk.QbSdk;

import update.UpdateAppUtils;


/**
 * 应用程序
 *
 * @author xiongwenzhi
 */
public class MyApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化友盟
//        initUM();
        /**
         * 极光推送
         */
//        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.e("开启TBS===X5加速成功");
            }

            @Override
            public void onCoreInitFinished() {
                LogUtil.e("开启TBS===X5加速失败");

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        UpdateAppUtils.init(getAppContext());
    }


    /**
     * 64K
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}