package com.android.orangetravel.base.versionUpdate;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import androidx.core.app.NotificationCompat;

import com.android.orangetravel.base.utils.ConstantUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.SystemUtil;
import com.yang.base.BuildConfig;
import com.yang.base.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * 版本更新
 */
public class AppUpdate {

    public static final String savePath = Environment.getExternalStorageDirectory().getPath() + "/" + ConstantUtil.APP_NAME + "/";
    public static final String saveFileName = ConstantUtil.APP_NAME + ".apk";
    private WeakReference<Context> mWeakReference = null;
    private CustomDialogProGress dialogProGress;
    private String updateInfo;

    public AppUpdate(Context mContext) {
        this.mWeakReference = new WeakReference<>(mContext);
    }

    public void updateApp(String apkUrl) {
        File mFile = new File(savePath);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
        File mFile2 = new File(savePath + saveFileName);
        if (mFile2.exists()) {
            mFile2.delete();
        }
        if (StringUtil.isNotEmpty(apkUrl)) {
            downloadNewVersionApk(apkUrl);
            return;
        }
    }

    public void updateApp(String apkUrl, String updateInfo) {
        this.updateInfo = updateInfo;
        File mFile = new File(savePath);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
        File mFile2 = new File(savePath + saveFileName);
        if (mFile2.exists()) {
            mFile2.delete();
        }
        if (StringUtil.isNotEmpty(apkUrl)) {
            downloadNewVersionApk(apkUrl);
            return;
        }
    }


    /**
     * 下载apk
     */
    void downloadNewVersionApk(String apkUrl) {
        dialogProGress = new CustomDialogProGress(mWeakReference.get());
        dialogProGress.settitleName("正在更新");
        dialogProGress.setcotent(updateInfo);
        dialogProGress.setVersion("V" + BuildConfig.VERSION_NAME);
        dialogProGress.setCancelable(false);
        dialogProGress.show();
        DownloadUtil.getInstance().download(apkUrl, ConstantUtil.APP_NAME, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                hideNotification(download_notify_id);
                showDownLoadSucceseDialog(savePath + saveFileName);
                if (dialogProGress != null) {
                    dialogProGress.dismiss();
                }
            }

            @Override
            public void onDownloading(int progress) {
//                showNotice(progress);
                Message message = new Message();
                message.arg1 = progress;
                message.what = 123;
                handler.sendMessage(message);

            }

            @Override
            public void onDownloadFailed() {
                hideNotification(download_notify_id);
                if (dialogProGress != null) {
                    dialogProGress.dismiss();
                }
            }
        });
        /*OkHttpUtils.get().url(apkUrl).build().execute(new FileCallBack(savePath, saveFileName) {
            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                showNotice((int) (progress * 100));
            }
            @Override
            public void onError(Call call, Exception e, int i) {
                hideNotification(download_notify_id);
            }
            @Override
            public void onResponse(File file, int i) {
                hideNotification(download_notify_id);
                showDownLoadSucceseDialog(savePath + saveFileName);
            }
        });*/
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialogProGress.setProgress(msg.arg1);
            dialogProGress.setText("已下载" + msg.arg1 + "%");
//            if (dialogProGress != null) {
//                if (!dialogProGress.isShowing()) {
//                    dialogProGress.show();
//                }
//            }
        }
    };

    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder mBuilder = null;
    private final int download_notify_id = 0x0816;// 下载ID
    private final int install_notify_id = 0x1123;// 安装ID
    private int lastProgress = 0;
    private final int minProgress = 10;// 设置最新更新进度,减少通知消耗

    public void showNotice(int progress) {
        if (progress < lastProgress * minProgress) {
            return;
        }
        lastProgress++;
        mNotificationManager = getNotificationManager();
        mBuilder = buildDownloadNotification(progress);
        mNotificationManager.notify(download_notify_id, mBuilder.build());
    }

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) mWeakReference.get().getSystemService(Context.NOTIFICATION_SERVICE);
        return mNotificationManager;
    }

    // 创建下载通知
    private NotificationCompat.Builder buildDownloadNotification(int progress) {
        if (null != mBuilder) {
            mBuilder.setProgress(100, progress, false);
            return mBuilder;
        }
        long when = System.currentTimeMillis();
        mBuilder = new NotificationCompat.Builder(mWeakReference.get());
        mBuilder.setContentTitle(mWeakReference.get().getString(R.string.app_name) + " 正在下载新版本")// 设置通知栏标题
                .setWhen(when)// 设置通知栏时间
                .setSmallIcon(R.mipmap.app_logo)// 设置通知小ICON
                .setOngoing(true).setProgress(100, 0, false);
        return mBuilder;
    }

    public void hideNotification(int id) {
        mNotificationManager = getNotificationManager();
        mNotificationManager.cancel(id);
    }

    void showDownLoadSucceseDialog(final String filePath) {
        // showInstallNotifycation(filePath);
        EventBus.getDefault().post(new DownloadCompleteEvent(filePath));// 下载完成事件
    }

    public void showInstallNotifycation(String filePath) {
        mNotificationManager = getNotificationManager();
        mBuilder = buildInstallNotification(filePath);
        mNotificationManager.notify(install_notify_id, mBuilder.build());
    }

    // 创建安装通知
    private NotificationCompat.Builder buildInstallNotification(String filePath) {
        PendingIntent mPendingIntent = PendingIntent.getActivity(mWeakReference.get(), 0, installIntent(filePath), PendingIntent.FLAG_ONE_SHOT);
        long when = System.currentTimeMillis();
        mBuilder = new NotificationCompat.Builder(mWeakReference.get());
        mBuilder.setContentTitle(mWeakReference.get().getString(R.string.app_name) + "新版本已下载完成")// 设置通知栏标题
                .setContentText("点击进行安装")// 设置通知栏显示内容
                .setWhen(when)// 设置通知栏时间
                .setSmallIcon(R.mipmap.app_logo)// 设置通知小ICON
                .setAutoCancel(true).setOngoing(false).setContentIntent(mPendingIntent)
                .setTicker(mWeakReference.get().getString(R.string.app_name) + "新版本已下载完成")
                .setDefaults(Notification.DEFAULT_VIBRATE);
        return mBuilder;
    }

    // 安装Intent
    private Intent installIntent(String apkPath) {
        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        Uri mUri = SystemUtil.getFileUri(mWeakReference.get(), apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // mUri = FileProvider.getUriForFile(mWeakReference.get(), ConstantUtil.FILE_PROVIDER_AUTHORITIES, new File(apkPath));
            mIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            // mUri = Uri.fromFile(new File(apkPath));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mIntent.setDataAndType(mUri, "application/vnd.android.package-archive");
        return mIntent;
    }

    public static void installApk(Activity activity, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri apkUri = SystemUtil.getFileUri(activity, apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

}