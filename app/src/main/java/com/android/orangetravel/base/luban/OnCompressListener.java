package com.android.orangetravel.base.luban;

import java.util.Map;

public interface OnCompressListener {

    /**
     * 开始压缩
     */
    void onStart();

    /**
     * 压缩成功
     */
    void onSuccess(Map<String, String> newFiles);

    /**
     * 压缩失败
     */
    void onError(Throwable e);

}