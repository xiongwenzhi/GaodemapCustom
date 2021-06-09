package com.android.orangetravel.api;

import android.text.TextUtils;

import com.android.orangetravel.application.Constant;
import com.android.orangetravel.base.logger.LoggingInterceptor;
import com.android.orangetravel.base.utils.ConstantUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api
 */
public class Api {

    private static volatile Api mApi;
    private OkHttpClient mClient;
    private Retrofit mRetrofit;
    public ApiService mApiService;

    /**
     * 获取单例
     */
    public static Api getInstance() {
        if (null == mApi) {
            synchronized (Api.class) {
                if (null == mApi) {
                    mApi = new Api();
                }
            }
        }
        return mApi;
    }

    /**
     * 构造方法
     */
    private Api() {

        mClient = new OkHttpClient.Builder()
                // 设置进行连接失败重试
                .retryOnConnectionFailure(true)
                // 写入超时
                .writeTimeout(20, TimeUnit.SECONDS)
                // 读取超时
                .readTimeout(20, TimeUnit.SECONDS)
                // 连接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                // 拦截并打印数据的拦截器
                .addInterceptor(headInterceptor)
                .addInterceptor(new LoggingInterceptor.Builder()
                        .isDebug(LogUtil.DEBUG)
                        // 请求
                        .setRequestTag("Request")
                        // 响应
                        .setResponseTag("Response")
                        // application/json;charset=UTF-8
                        // application/x-www-form-urlencoded;charset=UTF-8
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Content-Encoding", "gzip")
                        .build())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlParam.HOST)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

//    /**
//     * Request和Response拦截器
//     */
//    private Interceptor mLogInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            LogUtil.eNormal("Request", "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//            LogUtil.eNormal("Request", "┃ " + request.url() + "\u3000" + request.method());
//            LogUtil.eNormal("Request", "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//            long startTime = System.currentTimeMillis();
//            Response response = chain.proceed(chain.request());
//            long duration = System.currentTimeMillis() - startTime;
//            MediaType mediaType = response.body().contentType();
//            String content = response.body().string();
//            LogUtil.eNormal("Response", "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//            LogUtil.eNormal("Response", "┃ " + request.url() + "\u3000" + response.isSuccessful() + "\u3000" + response.code() + "\u3000" + duration + "ms");
//            LogUtil.eNormal("Response", "┃ " + content);
//            LogUtil.eNormal("Response", "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//            LogUtil.eSuper(content);
//            return response.newBuilder()
//                    .body(ResponseBody.create(mediaType, content))
//                    .build();
//        }
//    };

    /**
     * Request和Response拦截器
     */
    private Interceptor headInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = SPUtil.get(ConstantUtil.USER_TOKEN, "").toString();
            LogUtil.e(token);
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("authorization", token)
                    .addHeader("X-CSRF-TOKEN", Constant.token)
                    .build();
            return chain.proceed(request);
        }
    };

}