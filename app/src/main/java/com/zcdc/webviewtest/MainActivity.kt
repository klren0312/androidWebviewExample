package com.zcdc.webviewtest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.zcdc.webviewtest.service.FloatPrintService
import com.zcdc.webviewtest.x5.TheX5App
import com.zcdc.webviewtest.x5.TheX5WebView

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MAIN"
        private var mWebView: TheX5WebView? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFloatPrintService()

        val x5App = TheX5App()
        x5App.initX5(this)

        mWebView = findViewById(R.id.theWebView)
        mWebView?.addJavascriptInterface(WebAppInterface(this), "Android")
        mWebView?.loadUrl("file:///android_asset/index.html")
    }

    /**
     * 开启悬浮框
     */
    private fun startFloatPrintService() {
        if (FloatPrintService.isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show()
            val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == 0) {
                    if (!Settings.canDrawOverlays(this)) {
                        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                        startService(Intent(this@MainActivity, FloatPrintService::class.java))
                    }
                }
            }
            activityResultLauncher.launch(Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                    "package:$packageName"
                )
            ))
        } else {
            startService(Intent(this@MainActivity, FloatPrintService::class.java))
        }
    }

    /**
     * 发送消息给悬浮框组件
     * @param text 显示的文字
     */
    fun sendToFloating(context: Context, text: String?) {
        Log.d(TAG, text!!)
        val intent = Intent("floatPrintData")
        intent.putExtra("data", text)
        context.sendBroadcast(intent)
        // 调用js示例
        runOnUiThread {
            Log.d(TAG, "$text 测试")
            mWebView?.evaluateJavascript("changeTextView(\"$text\")") {
                    res -> Log.d(TAG, "$res data")
            }
        }
    }

    class WebAppInterface(private val mContext: Context) {

        private val mainActivity = MainActivity()

        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun changeFloatText(text: String?) {
            mainActivity.sendToFloating(mContext, text)
        }

    }
}