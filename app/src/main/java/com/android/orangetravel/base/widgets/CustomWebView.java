package com.android.orangetravel.base.widgets;//package com.yang.base.widgets;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.net.http.SslError;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.ViewGroup;
//import android.webkit.SslErrorHandler;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
///**
// * 自定义WebView
// *
// * @author yangfei
// */
//public class CustomWebView extends WebView {
//
//    public CustomWebView(Context context) {
//        super(context);
//    }
//    public CustomWebView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    // ---------------------------------------------------------------------------------------------
//
//    private WebSettings mSettings = this.getSettings();
//
//    /*是否支持js*/
//    public void setJsEnabled(boolean flag) {
//        mSettings.setJavaScriptEnabled(flag);
//    }
//
//    /*支持缩放操作*/
//    public void setSupportZoom() {
//        mSettings.setSupportZoom(true);// 支持缩放,默认为true(是下面那个的前提)
//        mSettings.setBuiltInZoomControls(true);// 设置内置的缩放控件.若为false,则该WebView不可缩放
//        mSettings.setDisplayZoomControls(false);// 隐藏原生的缩放控件
//    }
//
//    /*普通适配*/
//    public void normalAdaptive() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            mSettings.setLoadWithOverviewMode(true);
//        } else {
//            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        }
//    }
//
//    public void setClient(OnClientListener listener) {
//        this.onClientListener = listener;
//        this.setWebViewClient(new WebViewClient() {
//            // Url重定向
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (null != onClientListener) {
//                    onClientListener.shouldOverrideUrlLoading(view, url);
//                }
//                return true;
//            }
//
//            // 开始
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                if (null != onClientListener) {
//                    onClientListener.onStart();
//                }
//            }
//
//            // 完成
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (null != onClientListener) {
//                    onClientListener.onFinished();
//                }
//            }
//
//            /*@Override
//            public void onLoadResource(WebView view, String url) {
//                super.onLoadResource(view, url);
//                // 设定加载资源的操作
//            }*/
//            // 错误
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                if (null != onClientListener) {
//                    onClientListener.onError(view, errorCode, description, failingUrl);
//                }
//            }
//
//            // 处理Https请求
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                // super.onReceivedSslError(view, handler, error);
//                /**
//                 * 处理Https请求
//                 * WebView默认是不处理Https请求的,页面显示空白,需要进行如下设置:
//                 * ____handler.proceed();// 表示等待证书响应
//                 * ____handler.cancel();// 表示挂起连接,为默认方式
//                 * ____handler.handleMessage(null);// 可做其他处理
//                 */
//                handler.proceed();// 表示等待证书响应
//            }
//        });
//        this.setWebChromeClient(new WebChromeClient() {
//            // 获取加载进度
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (null != onClientListener) {
//                    onClientListener.onProgressChanged(newProgress);
//                }
//            }
//
//            // 获取网站标题
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                if (null != onClientListener) {
//                    onClientListener.onTitle(title);
//                }
//            }
//            // 支持javascript的警告框
//            /*@Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                // return super.onJsAlert(view, url, message, result);
//                new AlertDialog.Builder(getContext())
//                        .setTitle("JsAlert")
//                        .setMessage(message)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm();
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();
//                return true;
//            }*/
//            // 支持javascript的确认框
//            /*@Override
//            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//                new AlertDialog.Builder(getContext())
//                        .setTitle("JsConfirm")
//                        .setMessage(message)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.cancel();
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();
//                return true;
//            }*/
//            // 支持javascript输入框
//            /*@Override
//            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
//                final EditText et = new EditText(getContext());
//                et.setText(defaultValue);
//                new AlertDialog.Builder(getContext())
//                        .setTitle(message)
//                        .setView(et)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm(et.getText().toString());
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.cancel();
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();
//                return true;
//            }*/
//        });
//    }
//
//    /**
//     * 接口回调
//     */
//    public interface OnClientListener {
//        void shouldOverrideUrlLoading(WebView view, String url);
//        void onStart();
//        void onFinished();
//        void onError(WebView view, int errorCode, String description, String failingUrl);
//        void onTitle(String title);
//        void onProgressChanged(int newProgress);
//    }
//    private OnClientListener onClientListener;
//
//    /**
//     * 显示拼接的HTML代码
//     */
//    public void loadDataWithBaseURL(String baseUrl, String data) {
//        super.loadDataWithBaseURL(baseUrl, data, "text/html", "utf-8", null);
//    }
//
//    // 其他细节操作
//    private void other() {
//        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 关闭WebView中缓存
//        mSettings.setAllowFileAccess(true);// 设置可以访问文件
//        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 支持通过JS打开新窗口
//        mSettings.setLoadsImagesAutomatically(true);// 支持自动加载图片
//        mSettings.setDefaultTextEncodingName("utf-8");// 设置编码格式
//        //
//        // 是否可以后退
//        this.canGoBack();
//        // 后退网页
//        this.goBack();
//        // 是否可以前进
//        this.canGoForward();
//        // 前进网页
//        this.goForward();
//        // 以当前的index为起始点前进或者后退到历史记录中指定的steps
//        // 如果steps为负数则为后退,正数则为前进
//        this.goBackOrForward(1);
//    }
//
//    // ---------------------------------------------------------------------------------------------
//    // 同步生命周期
//
//    public void onResume() {
//        super.onResume();
//    }
//    public void onPause() {
//        super.onPause();
//    }
//    public void onDestroy() {
//        this.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//        this.clearHistory();
//        ((ViewGroup) this.getParent()).removeView(this);
//        this.destroy();
//    }
//
//}