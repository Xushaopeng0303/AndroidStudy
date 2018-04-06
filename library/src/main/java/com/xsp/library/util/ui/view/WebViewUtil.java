package com.xsp.library.util.ui.view;

import android.text.TextUtils;
import android.webkit.WebView;

import com.xsp.library.util.BaseUtil;

/**
 * WebView util
 */
public class WebViewUtil extends BaseUtil {
    private static final String JAVA_SCRIPT = "javascript: ";

    public static void invokeJavaScript(WebView webView, String jsMethod, String param) {
        if (null == webView || TextUtils.isEmpty(jsMethod)) {
            return;
        }

        String url;
        if (TextUtils.isEmpty(param)) {
            url = JAVA_SCRIPT + jsMethod + "()";
        } else {
            if (param.contains("'")) {
                param = param.replace("'", "");
            }
            url = JAVA_SCRIPT + jsMethod + "('" + param + "')";
        }
        webView.loadUrl(url);
    }

    public static void invokeJavaScript(WebView webView, String jsMethod, int param) {
        if (null == webView || TextUtils.isEmpty(jsMethod)) {
            return;
        }

        String url = JAVA_SCRIPT + jsMethod + "(" + param + ")";
        webView.loadUrl(url);
    }
}
