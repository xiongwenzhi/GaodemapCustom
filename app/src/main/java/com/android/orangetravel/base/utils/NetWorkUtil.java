package com.android.orangetravel.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络管理工具
 */
public final class NetWorkUtil {

    private NetWorkUtil() {
        // 这个类不能实例化
    }

    /**
     * 检查网络是否可用
     */
    public static boolean isNetConnected(Context mContext) {
        NetworkInfo mNetworkInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (null != mNetworkInfo && mNetworkInfo.isAvailable()) {
            return true;// 可用
        }
        return false;// 不可用
    }

//    /**
//     * 检测WiFi是否连接
//     */
//    public static boolean isWifiConnected(Context mContext) {
//        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm != null) {
//            NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
//            if (null != mNetworkInfo && mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                return true;// 已连接WiFi
//            }
//        }
//        return false;// 未连接WiFi
//    }

//    /**
//     * 检测3G是否连接
//     */
//    public static boolean is3gConnected(Context mContext) {
//        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm != null) {
//            NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
//            if (null != mNetworkInfo && mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                return true;// 已连接3G
//            }
//        }
//        return false;// 未连接3G
//    }

//    /*0:没有网络*/
//    public static final int NETTYPE_NO = 0;
//    /*1:WIFI网络*/
//    public static final int NETTYPE_WIFI = 0x01;
//    /*2:WAP网络*/
//    public static final int NETTYPE_CMWAP = 0x02;
//    /*3:NET网络*/
//    public static final int NETTYPE_CMNET = 0x03;
//    /**
//     * 获取当前网络类型 0:没有网络 1:WIFI网络 2:WAP网络 3:NET网络
//     */
//    public int getNetworkType(Context mContext) {
//        int netType = NETTYPE_NO;
//        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//        if (null == networkInfo) {
//            return netType;
//        }
//        int nType = networkInfo.getType();
//        if (nType == ConnectivityManager.TYPE_MOBILE) {
//            String extraInfo = networkInfo.getExtraInfo();
//            if (null != extraInfo) {
//                if ("cmnet".equals(extraInfo.toLowerCase())) {
//                    netType = NETTYPE_CMNET;
//                } else {
//                    netType = NETTYPE_CMWAP;
//                }
//            }
//        } else if (nType == ConnectivityManager.TYPE_WIFI) {
//            netType = NETTYPE_WIFI;
//        }
//        return netType;
//    }

}