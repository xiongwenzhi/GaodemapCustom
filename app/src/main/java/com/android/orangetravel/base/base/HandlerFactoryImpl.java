package com.android.orangetravel.base.base;//package com.yang.base.base;
//
//import android.os.Handler;
//
///**
// * Handler工厂的实现
// *
// * @author yangfei
// */
//public class HandlerFactoryImpl implements HandlerFactory {
//
//    // Handler管理
//    private Handler mHandler;
//
//    /**
//     * 获取Handler
//     */
//    @Override
//    public Handler getHandler(Handler.Callback callback) {
//        if (null == mHandler) {
//            mHandler = new Handler(callback);
//        } else {
//            throw new RuntimeException("getHandler()只能调用一次");
//        }
//        return mHandler;
//    }
//
//    /**
//     * 清除Handler
//     */
//    @Override
//    public void clearHandler() {
//        if (null != mHandler) {
//            mHandler.removeCallbacksAndMessages(null);
//            mHandler = null;
//        }
//    }
//
//}