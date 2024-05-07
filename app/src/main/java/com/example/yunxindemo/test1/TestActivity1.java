package com.example.yunxindemo.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yunxindemo.R;
import com.example.yunxindemo.Utils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.netease.nimlib.sdk.lifecycle.SdkLifecycleObserver;

public class TestActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        observeOnlineStatus();
        observeLoginSyncDataStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unObserveOnlineStatus();
        unObserveLoginSyncDataStatus();
    }

    private void observeOnlineStatus() {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode> () {
                    public void onEvent(StatusCode status) {
                        //获取状态的描述
                        String desc = status.getDesc();
                        if (status.wontAutoLogin()) {
                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                        }
                    }
                }, true);

    }


    private void unObserveOnlineStatus() {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode> () {
                    public void onEvent(StatusCode status) {
                        //获取状态的描述
                        String desc = status.getDesc();
                        if (status.wontAutoLogin()) {
                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                        }
                    }
                }, false);

    }

    private void observeLoginSyncDataStatus() {
        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
            @Override
            public void onEvent(LoginSyncStatus status) {
                if (status == LoginSyncStatus.BEGIN_SYNC) {
                    Utils.log("login sync data begin");
                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                    Utils.log("login sync data completed");
                }
            }
        }, true);
    }

    private void unObserveLoginSyncDataStatus() {
        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
            @Override
            public void onEvent(LoginSyncStatus status) {
                if (status == LoginSyncStatus.BEGIN_SYNC) {
                    Utils.log("login sync data begin");
                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                    Utils.log("login sync data completed");
                }
            }
        }, false);
    }

    /**
     * 登录IM
     */
    public void onTest1(View v) {
        LoginInfo info = new LoginInfo("test", "123456789");
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        Utils.log("login success");
                        // your code
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == 302) {
                            Utils.log("账号密码错误");
                            // your code
                        } else {
                            // your code
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // your code
                    }
                };

        //执行手动登录
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);
    }

    /**
     * 查询登录状态
     */
    public void onTest2(View v) {
        StatusCode code = NIMClient.getStatus();
        if(code == StatusCode.LOGINED) {
            Utils.showToast(this, "登录成功");
        } else {
            Utils.showToast(this, "登录失败");
        }
    }

    /**
     * 登出IM
     */
    public void onTest3(View v) {
        NIMClient.getService(AuthService.class).logout();
    }

}