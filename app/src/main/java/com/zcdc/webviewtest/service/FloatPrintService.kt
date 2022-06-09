package com.zcdc.webviewtest.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.TextView
import com.zcdc.webviewtest.R
import java.util.*

@SuppressLint("StaticFieldLeak")
class FloatPrintService : Service() {
    companion object {
        @JvmField
        var isStarted: Boolean = false
        private var isShow: Boolean = false

        private var windowManager: WindowManager? = null
        private var layoutParams: WindowManager.LayoutParams? = null

        private var displayView: View? = null
        private var floatTextView: TextView? = null

        private var dataReceiver: BroadcastReceiver? = null
        private const val TAG = "FloatPrintService"
    }


    override fun onCreate() {
        super.onCreate()
        isStarted = true

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams!!.type = WindowManager.LayoutParams.TYPE_PHONE
        }

        layoutParams!!.format = PixelFormat.RGBA_8888
        layoutParams!!.gravity = Gravity.CENTER or Gravity.BOTTOM
        // 可点击穿透, 可聚焦
        layoutParams!!.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        layoutParams!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams!!.y = 200

        initReceiver()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initFloatWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("InflateParams")
    private fun initFloatWindow() {
        if (Settings.canDrawOverlays(this)) {
            val layoutInflater = LayoutInflater.from(this)
            displayView = layoutInflater.inflate(R.layout.float_print, null)
            floatTextView = displayView!!.findViewById(R.id.floatText)
            floatTextView?.text = ""
        }
    }

    private fun showFloatWindow() {
        if (!isShow) {
            windowManager?.addView(displayView, layoutParams)
            isShow = true
            val timer = Timer()
            val task = object: TimerTask() {
                override fun run() {
                    hideFloatWindow()
                }
            }
            timer.schedule(task, 5000)
        }
    }

    private fun hideFloatWindow() {
        if (isShow) {
            isShow = false
            windowManager?.removeView(displayView)
        }
    }

    private fun setPrintText(text: String) {
        if (text != "") {
            showFloatWindow()
        } else {
            hideFloatWindow()
        }
        floatTextView?.text = text
    }

    /**
     * 创建一个本地广播
     */
    private class DataReceiver: BroadcastReceiver() {
        val floatPrintService = FloatPrintService()
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.getStringExtra("data")
            if (data != null) {
                Log.d(TAG, "======================== $data")
                floatPrintService.setPrintText(data)
            }
        }
    }

    /**
     * 注册广播接收器
     */
    private fun initReceiver() {
        dataReceiver = DataReceiver()
        //ConnectivityManager.CONNECTIVITY_ACTION
        val filter = IntentFilter()
        filter.addAction("floatPrintData")
        this.registerReceiver(dataReceiver, filter)
    }

    override fun onDestroy() {
        Log.d(TAG, "==============DESTROY============")
        super.onDestroy()
        isStarted = false
        isShow = false
        unregisterReceiver(dataReceiver)
    }
}