package com.zcdc.webviewtest.x5

import android.content.Context
import android.util.Log
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk

class TheX5App {
    val TAG = "X5"

    fun initX5(context: Context) {
        QbSdk.setDownloadWithoutWifi(true)
        val map = mapOf(
            TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
            TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true,
        )
        QbSdk.initTbsSettings(map)
        QbSdk.initX5Environment(context, object: QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {}

            override fun onViewInitFinished(p0: Boolean) {
                //x5內核初始化完成的回调，true表x5内核加载成功，否则表加载失败，会自动切换到系统内核。
                Log.d(TAG, " 内核加载 $p0")
            }
        })
    }
}