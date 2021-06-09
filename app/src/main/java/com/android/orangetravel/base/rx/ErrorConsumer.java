package com.android.orangetravel.base.rx;//package com.yang.base.rx;
//
//import com.yang.base.base.BaseApp;
//import com.yang.base.utils.NetWorkUtil;
//import com.yang.base.utils.StringUtil;
//import com.yang.base.utils.log.LogUtil;
//
//import java.net.ConnectException;
//import java.net.SocketTimeoutException;
//
//import io.reactivex.functions.Consumer;
//import retrofit2.HttpException;
//
///**
// * Class
// *
// * @author yangfei
// */
//public abstract class ErrorConsumer implements Consumer<Throwable> {
//
//    @Override
//    public void accept(Throwable e) {
//        String message = e.getMessage();
//        LogUtil.eSuper(e.getClass() + "\u3000Message:" + message);
//        /*网络不可用*/
//        if (!NetWorkUtil.isNetConnected(BaseApp.getAppContext())) {// e instanceof UnknownHostException
//            message = "网络不可用,请检查你的网络";
//
//        /*请求超时*/
//        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
//            message = "请求超时,请稍后再试";
//
//        /*服务器异常*/
//        } else if (e instanceof HttpException) {
//            message = "服务器异常,请稍后再试";
//
//        /*数据报错(返回的数据中的报错)*/
//        } else if (e instanceof ApiErrorException) {
//            if (StringUtil.isEmpty(message))
//                message = "接口报错";
//
//        /*其它*/
//        } else {
//            message = "网络访问错误,请稍后再试";
//        }
//        _onError(message);
//    }
//
//    protected abstract void _onError(String msg);
//
//}