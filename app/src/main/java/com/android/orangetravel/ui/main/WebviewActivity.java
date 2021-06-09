package com.android.orangetravel.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.utils.NetWorkUtil;
import com.android.orangetravel.base.widgets.YangTitleBar;

import butterknife.BindView;
import me.jingbin.progress.WebProgress;

/**
 * @author Mr Xiong
 * @date 2020/12/25
 * 网页加载
 */

public class WebviewActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView mwebview;
    @BindView(R.id.progress)
    WebProgress mProgressBar;
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    private String title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setTitleBar("特别声明");
        setmYangStatusBar(ContextCompat.getColor(WebviewActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(WebviewActivity.this, R.color.title_bar_black));
        webViewSetting();
    }

    @Override
    public void requestData() {

    }

    private void webViewSetting() {
        WebSettings ws = mwebview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放。
        // 自适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setLoadWithOverviewMode(true);
        mwebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    return true;
                } else if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // html加载完成之后，无网隐藏进度条
                if (!NetWorkUtil.isNetConnected(WebviewActivity.this)) {
                    mProgressBar.hide();
                }

            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("url")) { //如果是传递地址跟标题分开传递了
                mwebview.loadUrl(bundle.getString("url"));
                initProgressBar();
                if (bundle.containsKey("title")) {
                    setTitleBar(bundle.getString("title"));
                    mYangTitleBar.setRightIconVisible(false);
                }

            }
        }
        mwebview.addJavascriptInterface(new JsInterface(WebviewActivity.this), "zp");//AndroidtoJS类对象映射到js的test对象
    }

    /**
     * 初始化进度条
     */
    private void initProgressBar() {
        mProgressBar.setColor("#f9905a");
        mProgressBar.show();
        mwebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(mProgressBar!= null){
                    mProgressBar.setWebProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }


    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        // 这个声明很重要 如果target 大于等于API 17，则需要加上如下注解
        public void appPay(String value) {

        }

        @JavascriptInterface
        public void appShare(String value) {
        }
    }

}
