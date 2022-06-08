package com.zcdc.webviewtest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.zcdc.webviewtest.x5.TheX5App
import com.zcdc.webviewtest.x5.TheX5WebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val x5App = TheX5App()
        x5App.initX5(this)

        val mWebView: TheX5WebView = findViewById(R.id.theWebView)
        mWebView.addJavascriptInterface(WebAppInterface(this), "Android")
        Log.d("Interface", "==============" + mWebView.settings.userAgentString)
        mWebView.loadUrl("file:///android_asset/index.html")
    }

    class WebAppInterface(private val mContext: Context) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }

    }
}