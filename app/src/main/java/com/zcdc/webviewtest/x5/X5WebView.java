package com.zcdc.webviewtest.x5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setWebViewClient(client);
        initWebViewSettings();
        this.getView().setClickable(true);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings mWebSettings = this.getSettings();
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setJavaScriptEnabled(true);
        this.getSettingsExtension().setDisplayCutoutEnable(true);

    }
    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }
}
