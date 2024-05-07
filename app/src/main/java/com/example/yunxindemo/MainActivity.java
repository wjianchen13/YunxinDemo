package com.example.yunxindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.yunxindemo.test1.TestActivity1;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.lifecycle.SdkLifecycleObserver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        NIMClient.getService(SdkLifecycleObserver.class).observeMainProcessInitCompleteResult(new Observer<Boolean>() {
//            @Override
//            public void onEvent(Boolean aBoolean) {
//                Utils.log("registerInitObserver: " + aBoolean);
//                if (aBoolean != null && aBoolean) {
//
//                }
//            }
//        }, true);

    }

    /**
     * 初始化
     */
    public void onTest1(View v) {
        NIMClient.getService(SdkLifecycleObserver.class).observeMainProcessInitCompleteResult(new Observer<Boolean>() {
            @Override
            public void onEvent(Boolean aBoolean) {
                Utils.log("registerInitObserver: " + aBoolean);
                if (aBoolean != null && aBoolean) {

                }
            }
        }, true);
    }

    /**
     * 登录IM
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, TestActivity1.class));
    }


}