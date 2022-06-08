package com.zcdc.webviewtest.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView

class FloatPrintService : Service() {
    private var isStarted: Boolean = false
    private var isShow: Boolean = false

    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    private var displayView: View? = null
    private var floatPrintView: TextView? = null

    private var dataReceiver: BroadcastReceiver? = null
    private val TAG = "FloatPrintService"

    override fun onCreate() {
        super.onCreate()

        this.isStarted = true
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
        // 可点击穿透, 可聚焦
        layoutParams!!.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        layoutParams!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams!!.y = 200
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}