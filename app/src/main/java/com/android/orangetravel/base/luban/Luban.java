package com.android.orangetravel.base.luban;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.android.orangetravel.base.utils.ConstantUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.ThreadPoolUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Luban implements Handler.Callback {

    /*文件名*/
    private static final String DEFAULT_DISK_CACHE_DIR = ConstantUtil.APP_NAME;// "compress"

    private static final int MSG_COMPRESS_SUCCESS = 0;
    private static final int MSG_COMPRESS_START = 1;
    private static final int MSG_COMPRESS_ERROR = 2;

    private List<String> mPaths;
    private OnCompressListener onCompressListener;
    private Handler mHandler;

    // 加的
    private Map<String, String> results;
    private int count;
    private List<String> parameters;

    private Luban(Builder builder) {
        this.mPaths = builder.mPaths;
        this.parameters = builder.parameters;
        this.results = new HashMap<>();
        this.count = this.mPaths.size();
        this.onCompressListener = builder.onCompressListener;
        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    public static Builder with(Context mContext) {
        return new Builder(mContext);
    }

    /**
     * Returns a mFile with a cache audio name in the private cache directory.
     *
     * @param mContext A context.
     */
    private File getImageCacheFile(Context mContext, String suffix) {
        suffix = StringUtil.isEmpty(suffix) ? ".jpg" : suffix;
        if (getImageCacheDir(mContext) != null) {
            return new File(getImageCacheDir(mContext) + "/" + System.currentTimeMillis() + (int) (Math.random() * 1000) + suffix);
        }
        return null;
    }

    /**
     * Returns a directory with a default name in the private cache directory of the application to
     * use to store retrieved audio.
     *
     * @param mContext A context.
     * @see #getImageCacheDir(Context, String)
     */
    @Nullable
    private File getImageCacheDir(Context mContext) {
        return getImageCacheDir(mContext, DEFAULT_DISK_CACHE_DIR);
    }

    /**
     * Returns a directory with the given name in the private cache directory of the application to
     * use to store retrieved media and thumbnails.
     *
     * @param mContext  A context.
     * @param cacheName The name of the subdirectory in which to store the cache.
     * @see #getImageCacheDir(Context)
     */
    @Nullable
    private File getImageCacheDir(Context mContext, String cacheName) {
        File cacheDir = mContext.getExternalCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, cacheName);
            if (!result.mkdirs() && (!result.exists() || !result.isDirectory())) {
                // File wasn't able to create a directory, or the result exists but not a directory
                return null;
            }
            return result;
        }
        return null;
    }

    /**
     * start asynchronous compress thread
     */
    @UiThread
    private void launch(final Context mContext) {
        if (mPaths == null || mPaths.size() == 0 && onCompressListener != null) {
            onCompressListener.onError(new NullPointerException("image file cannot be null"));
        }
        mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_START));
        Iterator<String> iterator = mPaths.iterator();
        while (iterator.hasNext()) {
            final String pathName = iterator.next();
            if (Checker.isImage(pathName)) {
                ThreadPoolUtil.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File mFile = new Engine(pathName, getImageCacheFile(mContext, Checker.checkSuffix(pathName))).compress();
                            results.put(parameters.get(results.size()), mFile.getAbsolutePath());
                            if (count == results.size()) {
                                parameters.clear();
                                mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_SUCCESS, results));
                            }
                        } catch (IOException e) {
                            mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_ERROR, e));
                        }
                    }
                });
                /*AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });*/
            }
            iterator.remove();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (null == onCompressListener) {
            return false;
        }
        switch (msg.what) {
            case MSG_COMPRESS_START: {
                onCompressListener.onStart();
                break;
            }
            case MSG_COMPRESS_SUCCESS: {
                onCompressListener.onSuccess((Map<String, String>) msg.obj);
                break;
            }
            case MSG_COMPRESS_ERROR: {
                onCompressListener.onError((Throwable) msg.obj);
                break;
            }
        }
        return false;
    }

    public static class Builder {

        private Context mContext;
        private List<String> mPaths;
        private List<String> parameters;
        private OnCompressListener onCompressListener;

        Builder(Context mContext) {
            this.mContext = mContext;
            this.mPaths = new ArrayList<>();
            this.parameters = new ArrayList<>();
        }

        private Luban build() {
            return new Luban(this);
        }

        /*public Builder load(File file) {
            mPaths.add(file.getAbsolutePath());
            return this;
        }
        public Builder load(String string) {
            mPaths.add(string);
            return this;
        }*/

        public Builder load(List<String> list, List<String> parameters) {
            this.mPaths.addAll(list);
            this.parameters.addAll(parameters);
            return this;
        }

        public Builder setCompressListener(OnCompressListener listener) {
            this.onCompressListener = listener;
            return this;
        }

        public void launch() {
            build().launch(mContext);
        }
    }

}