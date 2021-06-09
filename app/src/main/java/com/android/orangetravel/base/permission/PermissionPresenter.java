package com.android.orangetravel.base.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.orangetravel.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 权限请求处理
 *
 * @author yangfei
 */
public class PermissionPresenter {

    /**
     * 单例模式
     */
    private static PermissionPresenter mPresenter;

    public static PermissionPresenter getInstance() {
        if (null == mPresenter) {
            synchronized (PermissionPresenter.class) {
                if (null == mPresenter) {
                    mPresenter = new PermissionPresenter();
                }
            }
        }
        return mPresenter;
    }

    // 回调
    private PermissionView mView;
    // 请求码
    private int requestCode;
    // 权限集合
    private List<String> permissionList;

    /**
     * 初始化
     */
    public PermissionPresenter init(PermissionView mView) {
        this.mView = mView;
        this.requestCode = new Random().nextInt(100);
        if (null == permissionList) {
            permissionList = new ArrayList<>();
        } else {
            permissionList.clear();
        }
        return this;
    }

    /**
     * 添加权限
     */
    public PermissionPresenter addPermission(@NonNull String permission) {
        if (StringUtil.isNotEmpty(permission) && (!permissionList.contains(permission))) {
            permissionList.add(permission);
        }
        return this;
    }

    /**
     * 请求权限
     */
    public void requestPermissions(Activity activity) {
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) ||
                StringUtil.isListEmpty(permissionList)) {
            mView.onAuthSuccess();
        } else {
            List<String> mLists = new ArrayList<>();
            for (String str : permissionList) {
                if (ContextCompat.checkSelfPermission(activity, str) != PackageManager.PERMISSION_GRANTED) {
                    mLists.add(str);
                }
            }
            if (mLists.size() == 0) {
                mView.onAuthSuccess();
            } else {
                ActivityCompat.requestPermissions(activity, mLists.toArray(new String[mLists.size()]), requestCode);
            }
        }
    }

    /**
     * 请求回调判断
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == this.requestCode) {
            for (int ret : grantResults) {
                if (ret == PackageManager.PERMISSION_GRANTED) {
                    continue;
                } else {
                    mView.onAuthFailure();
                    return;
                }
            }
            mView.onAuthSuccess();
        }
    }

    /**
     * 销毁
     */
    public void destroy() {
        this.mView = null;
    }

    /**
     * 跳转到权限设置界面
     */
    public void gotoPermissionSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

}