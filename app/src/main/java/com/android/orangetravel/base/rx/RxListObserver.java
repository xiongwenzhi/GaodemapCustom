package com.android.orangetravel.base.rx;

import com.android.orangetravel.base.base.BaseApp;
import com.android.orangetravel.base.utils.NetWorkUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 观察者
 *
 * @author yangfei
 */
public abstract class RxListObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        _onSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        String message = e.getMessage();
        LogUtil.eSuper(e.getClass() + "\u3000Message:" + message);
        /*网络不可用*/
        if (!NetWorkUtil.isNetConnected(BaseApp.getAppContext())) {// e instanceof UnknownHostException
            message = "网络不可用,请检查你的网络";

            /*请求超时*/
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            message = "请求超时,请稍后再试";

            /*服务器异常*/
        } else if (e instanceof retrofit2.HttpException || e instanceof retrofit2.adapter.rxjava2.HttpException) {
            message = "服务器异常,请稍后再试";

            /*数据报错(返回的数据中的报错)*/
        } else if (e instanceof ApiErrorException) {
            if (StringUtil.isEmpty(message)) {
                message = "接口报错";
            }
            // 登录提示：账户已被冻结，请与客服联系
            if (message.contains("账号被冻结或被删除")) {
                EventBus.getDefault().post(new AccountFreezeEvent(message));// 帐户冻结事件
            } else if (message.contains("该账号已在其他设备上登录")) {
//                EventBus.getDefault().post(new AccountFreezeEvent(message));// 帐户冻结事件
            } else if (message.contains("请登录")) {
                EventBus.getDefault().post(new AccountFreezeEvent(message));// 帐户冻结事件
            } else if (message.contains("登录已过期，请重新登录")) {
                EventBus.getDefault().post(new AccountFreezeEvent(message));// 帐户冻结事件
            } else if (message.contains("登录状态有误，请重新登录")) {
                EventBus.getDefault().post(new AccountFreezeEvent(message));// 帐户冻结事件
            }

        } else if (e instanceof JsonSyntaxException) {
            message = "数据解析错误";

            /*其它*/
        } else {
            message = "网络访问错误,请稍后再试";
        }
        _onError(message);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void _onError(String msg);

    protected abstract void _onNext(T t);

    protected abstract void _onSubscribe(Disposable d);

}