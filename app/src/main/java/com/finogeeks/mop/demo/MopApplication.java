package com.finogeeks.mop.demo;

import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

import com.finogeeks.lib.applet.client.FinAppClient;
import com.finogeeks.lib.applet.client.FinAppConfig;
import com.finogeeks.lib.applet.interfaces.FinCallback;

public class MopApplication extends MultiDexApplication {

    private static final String TAG = "SampleApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        if (FinAppClient.INSTANCE.isFinAppProcess(this)) {
            // 小程序进程不执行任何初始化操作
            return;
        }

        FinAppConfig.UIConfig uiConfig = new FinAppConfig.UIConfig();
        uiConfig.setHideNavigationBarCloseButton(true);
        FinAppConfig config = new FinAppConfig.Builder()
                .setSdkKey(BuildConfig.APP_KEY)
                .setSdkSecret(BuildConfig.APP_SECRET)
                .setApiUrl(BuildConfig.API_URL)
                .setApiPrefix(BuildConfig.API_PREFIX)
                .setDebugMode(BuildConfig.DEBUG)
                .setUiConfig(uiConfig)
                .setEncryptionType(FinAppConfig.ENCRYPTION_TYPE_SM)
                .build();

        FinAppClient.INSTANCE.init(this, config, new FinCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, "init result : " + result);
            }

            @Override
            public void onError(int code, String error) {
                Toast.makeText(MopApplication.this, "SDK初始化失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int status, String error) {

            }
        });
    }
}