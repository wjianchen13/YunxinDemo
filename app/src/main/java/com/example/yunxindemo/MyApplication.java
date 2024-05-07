package com.example.yunxindemo;

import android.app.Application;
import android.content.Context;

import com.netease.nimlib.sdk.NIMClient;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNim();
    }

    private void initNim() {
        NIMClient.init(this, null, Utils.getSDKOptions(this, "7de7c90b168916f4f4b1b6de5279115d"));
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
