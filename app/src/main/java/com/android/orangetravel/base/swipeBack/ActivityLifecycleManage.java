package com.android.orangetravel.base.swipeBack;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * Activity生命周期管理
 *
 * @author yangfei
 */
public class ActivityLifecycleManage implements Application.ActivityLifecycleCallbacks {

    // 锁
    private static final Object lockObj = new Object();
    // 管理对象单例
    private volatile static ActivityLifecycleManage mLifecycleManage;

    /**
     * 获取单例
     */
    public static ActivityLifecycleManage getInstance() {
        if (null == mLifecycleManage) {
            synchronized (lockObj) {
                if (null == mLifecycleManage) {
                    mLifecycleManage = new ActivityLifecycleManage();
                }
            }
        }
        return mLifecycleManage;
    }

    // 保存Activity
    private LinkedList<Activity> actList;

    /**
     * 构造方法
     */
    private ActivityLifecycleManage() {
        actList = new LinkedList<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // 添加Activity到集合
        if (null == actList) {
            actList = new LinkedList<>();
        }
        actList.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (actList.contains(activity)) {
            actList.remove(activity);
        }
        if (actList.size() == 0) {
            actList = null;
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 获取当前Activity
     */
    public Activity getLatestActivity() {
        if (null != actList && actList.size() > 0) {
            return actList.getLast();
        }
        return null;
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity getPreviousActivity() {
        if (null != actList && actList.size() >= 2) {
            return actList.get(actList.size() - 2);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------

//    /**
//     * 结束当前Activity
//     */
//    public void finishActivity() {
//        if (null != actList && actList.size() > 0) {
//            finishActivity(actList.getLast());
//        }
//    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity) {
            actList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

//    /**
//     * 移除指定的Activity
//     */
//    public void removeActivity(Activity activity) {
//        if (null != activity) {
//            actList.remove(activity);
//            activity = null;
//        }
//    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> clazz) {
        if (null != clazz) {
            try {
                for (Activity activity : actList) {
                    if (clazz.equals(activity.getClass())) {
                        // 结束指定的Activity
                        finishActivity(activity);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, count = actList.size(); i < count; i++) {
            if (null != actList.get(i)) {
                actList.get(i).finish();
            }
        }
        actList.clear();
    }

    /**
     * 返回到指定的Activity
     */
    public void returnToActivity(Class<?> clazz) {
        while (actList.size() != 0) {
            Activity activity = actList.getLast();
            if (clazz.equals(activity.getClass())) {
                break;
            } else {
                // 结束指定的Activity
                finishActivity(activity);
            }
        }
    }

//    /**
//     * 是否已经打开指定的Activity
//     */
//    public boolean isOpenActivity(Class<?> clazz) {
//        if (null != actList) {
//            for (int i = 0, size = actList.size(); i < size; i++) {
//                if (clazz.equals(actList.getLast().getClass())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context mContext) {
        try {
            // 结束所有Activity
//            finishAllActivity();
            // 杀死后台进程
//            ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//            activityManager.killBackgroundProcesses(mContext.getPackageName());
            // 终止状态。按照惯例,一个非零状态码表示异常终止。
            System.exit(0);
            // 杀死当前进程Process.killProcess(Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}