package com.zcdc.webviewtest.x5;

import android.content.Context;
import android.util.Log;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

public class X5App {
    private String TAG = "X5";
    public void initX5(Context context) {
        QbSdk.setDownloadWithoutWifi(true);
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，true表x5内核加载成功，否则表加载失败，会自动切换到系统内核。
                Log.d(TAG, " 内核加载 " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
            }
        };

        //x5内核初始化接口
        QbSdk.initX5Environment(context,  cb);
    }
}
