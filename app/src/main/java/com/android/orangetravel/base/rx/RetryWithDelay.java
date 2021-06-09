package com.android.orangetravel.base.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 重试和延迟
 *
 * @author yangfei
 */
public class RetryWithDelay implements Function<Observable<? extends Throwable>, ObservableSource<?>> {

    /**
     * // 总共重试3次,重试间隔3000毫秒
     * .retryWhen(new RetryWithDelay(3, 3000))
     */

    // 最大重试次数
    private final int maxRetries;
    // 间隔时间(毫秒)
    private final int retryDelayMillis;
    // 重复次数
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public ObservableSource<?> apply(Observable<? extends Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {
                if (++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    // LogUtil.e("get error, it will try after " + retryDelayMillis + " millisecond, retry count " + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error((Throwable) o);
            }
        });
    }

}