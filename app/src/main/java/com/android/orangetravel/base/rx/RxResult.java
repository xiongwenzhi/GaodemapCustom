package com.android.orangetravel.base.rx;

import com.android.orangetravel.base.bean.BaseBean;
import com.android.orangetravel.base.bean.BaseBeanforCode;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.bean.ListBaseBean;
import com.android.orangetravel.bean.SendCodeBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理返回的数据
 */
public class RxResult {


    /**
     * 处理结果  没有data返回 才用这个
     */
    public static <Object> ObservableTransformer<BaseBean<Object>, Object>
    handleResult() {
        return new ObservableTransformer<BaseBean<Object>, Object>() {
            @Override
            public ObservableSource<Object> apply(Observable<BaseBean<Object>> upstream) {
                return upstream.flatMap(
                        new Function<BaseBean<Object>, ObservableSource<Object>>() {
                            @Override
                            public ObservableSource<Object> apply(BaseBean<Object> tBaseBean)
                                    throws Exception {
                                if ("200".equals(tBaseBean.getStatus())) {
                                    return createObservableNoT(tBaseBean.getMsg());
                                } else {
                                    String msg;
                                    try {
                                        msg = tBaseBean.getErrMsg();
                                    } catch (Exception e) {
                                        msg = "";
                                    }
                                    return Observable.error(new ApiErrorException(msg));
                                }
                            }
                        }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 处理结果（判断status为200也是成功）
     */
    public static <T extends ErrorMsgBean> ObservableTransformer<BaseBeanforCode<T>, T>
    handleResultCode() {
        return new ObservableTransformer<BaseBeanforCode<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBeanforCode<T>> upstream) {
                return upstream.flatMap(
                        new Function<BaseBeanforCode<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(BaseBeanforCode<T> tBaseBean) throws Exception {
                                if ("200".equals(tBaseBean.getStatus())) {
                                    if (tBaseBean.getData() != null) {
                                        return createObservable(tBaseBean.getData());
                                    } else {
                                        ErrorMsgBean errorMsgBean = new ErrorMsgBean();
                                        return createObservable((T) errorMsgBean);
                                    }
                                } else {
                                    String msg;
                                    try {
                                        msg = tBaseBean.getErrMsg();
                                    } catch (Exception e) {
                                        msg = "";
                                    }
                                    return Observable.error(new ApiErrorException(msg));
                                }
                            }
                        }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 处理结果(第二种数据处理方式code为0也是成功)
     */
    public static <T extends ErrorMsgBean> ObservableTransformer<ListBaseBean<T>, List<T>> handleListResult() {
        return new ObservableTransformer<ListBaseBean<T>, List<T>>() {
            @Override
            public ObservableSource<List<T>> apply(Observable<ListBaseBean<T>> upstream) {
                return upstream.flatMap(
                        new Function<ListBaseBean<T>, ObservableSource<List<T>>>() {
                            @Override
                            public ObservableSource<List<T>> apply(ListBaseBean<T> tBaseBean) throws Exception {
                                if ("200".equals(tBaseBean.getStatus())) {
                                    return createListObservable(tBaseBean.getData());
                                } else {
                                    String msg;
                                    try {
                                        msg = tBaseBean.getErrMsg();
                                    } catch (Exception e) {
                                        msg = "";
                                    }
                                    return Observable.error(new ApiErrorException(msg));
                                }
                            }
                        }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建被观察者
     */
    private static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    // e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }


    /**
     * 创建被观察者
     *
     * @param t
     */
    private static <Object> Observable<Object> createObservableNoT(final String t) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    emitter.onNext((Object) t);
                    emitter.onComplete();
                } catch (Exception e) {
                    // e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 创建被观察者
     */
    private static <E> Observable<List<E>> createListObservable(final List<E> t) {
        return Observable.create(new ObservableOnSubscribe<List<E>>() {
            @Override
            public void subscribe(ObservableEmitter<List<E>> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    // e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

}