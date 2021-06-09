package com.android.orangetravel.base.utils;

import com.android.orangetravel.base.base.BaseApp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类
 *
 * @author yangfei
 */
public class ThreadPoolUtil {

    /*线程池*/
    private volatile static ExecutorService threadPool;

    /**
     * 获取线程池单例
     */
    public static ExecutorService getInstance() {
        if (null == threadPool) {
            synchronized (BaseApp.class) {
                if (null == threadPool) {
                    threadPool = Executors.newCachedThreadPool();
                }
            }
        }
        return threadPool;
    }

}