package com.zcdc.webviewtest.x5

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class TheX5WebView(arg0: Context, arg1: AttributeSet) : WebView(arg0, arg1) {
    private val client = object: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    init {
        this.webViewClient = client
        this.view.isClickable = true
        initWebViewSettings()
        this.settingsExtension.setDisplayCutoutEnable(true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebViewSettings() {
        val mWebSettings: WebSettings = this.settings
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
        mWebSettings.allowFileAccess = true
        mWebSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        mWebSettings.setSupportZoom(true)
        mWebSettings.builtInZoomControls = true
        mWebSettings.useWideViewPort = true
        mWebSettings.setSupportMultipleWindows(true)
        mWebSettings.setAppCacheEnabled(true)
        mWebSettings.domStorageEnabled = true
        mWebSettings.setGeolocationEnabled(true)
        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        mWebSettings.javaScriptEnabled = true
    }
}