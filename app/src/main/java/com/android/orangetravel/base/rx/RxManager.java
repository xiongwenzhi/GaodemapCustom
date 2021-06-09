package com.android.orangetravel.base.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 用于管理单个Persenter的RxJava订阅管理
 */
public class RxManager {

    // ListCompositeDisposable
    // ArrayCompositeDisposable
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 添加
     */
    public void add(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    /**
     * 处理
     */
    public void clear() {
        mCompositeDisposable.clear();
        mCompositeDisposable = null;
    }

}