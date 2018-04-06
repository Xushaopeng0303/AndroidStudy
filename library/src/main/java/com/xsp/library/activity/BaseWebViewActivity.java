package com.xsp.library.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xsp.library.R;
import com.xsp.library.util.app.NetUtil;


/**
 * WebView 展示页，通常用于全屏且交互较少的 WebView 的展示
 */
@SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
public class BaseWebViewActivity extends BaseTitleActivity {
    public static final String URL_KEY = "url_key";

    protected WebView mWebView;
    protected String mUrl;

    /**
     * 当前页面的url
     */
    protected String mTargetUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_view);
        // 接收参数
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        mUrl = bundle.getString(URL_KEY);

        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.id_base_web_view);
        WebSettings settings = mWebView.getSettings();
        if (settings != null) {
            // 设置支持javascript脚本
            settings.setJavaScriptEnabled(true);
            // 网页自适应屏幕宽度
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            // 设置显示缩放按钮
            settings.setBuiltInZoomControls(false);
            settings.setSupportZoom(false);
            settings.setDomStorageEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4以上系统在onPageFinished时再恢复图片加载时,如果存在多张图片引用的是相同的src时，
                // 会只有一个image标签得到加载
                settings.setLoadsImagesAutomatically(true);
            } else {
                settings.setLoadsImagesAutomatically(false);
            }
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

            if (NetUtil.isAvailable(getApplicationContext())) {
                // 有网路的情况下，无论如何都会从网络上取数据
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                // 无网路的情况下，使用缓存中的数据
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }

        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // wolfEye master 扫描漏洞修复
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");

        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        backWebView();
    }

    protected void backWebView() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }


    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }
}
