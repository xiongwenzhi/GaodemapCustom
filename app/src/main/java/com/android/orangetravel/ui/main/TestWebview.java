package com.android.orangetravel.ui.main;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2021/3/11
 */

public class TestWebview extends BaseActivity {
    @BindView(R.id.forum_context)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.test_webview;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        initWebView();
        webView.loadUrl("http://www.demo.ly.yns12316.cn/forestry/weixin/airClassroom/androidPrompt.html/3619101?code=forestry&id=1328968636874199041&userName=user20040973&userId=29488545454");
    }


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        com.tencent.smtt.sdk.WebSettings ws = webView.getSettings();
        // 网页内容的宽度自适应屏幕
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。MIXED_CONTENT_ALWAYS_ALLOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(com.tencent.smtt.sdk.WebSettings.LOAD_NORMAL);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

//        mWebChromeClient = new MyX5WebChromeClient(this);
//        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
//        webView.addJavascriptInterface(new MyJavascriptInterface(this), "injectedObject");
//        webView.setWebViewClient(new MyX5WebViewClient(this));
//        webView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return handleLongImage();
//            }
//        });

    }

    @Override
    public void requestData() {

    }
}
