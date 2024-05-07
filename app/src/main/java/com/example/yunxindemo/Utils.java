package com.example.yunxindemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.netease.nimlib.sdk.NotificationFoldStyle;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.ServerAddresses;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusBarNotificationFilter;

import java.io.IOException;

public class Utils {

    public static final String NOTIFY_SOUND_KEY =
            "android.resource://com.netease.yunxin.app.im/raw/msg";
    public static final int LED_ON_MS = 1000;
    public static final int LED_OFF_MS = 1500;

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static SDKOptions getSDKOptions(Context context, String appKey) {
        SDKOptions options = new SDKOptions();
        options.appKey = appKey;
        initStatusBarNotificationConfig(options);
        options.sdkStorageRootPath = getAppCacheDir(context);
        options.preloadAttach = true;
        options.thumbnailSize = (int) (222.0 / 375.0 * getScreenWidth(context));
//        options.userInfoProvider = new DefaultUserInfoPProvider(context);
        options.sessionReadAck = true;
        options.animatedImageThumbnailEnabled = true;
        options.asyncInitSDK = true;
        options.reducedIM = false;
        options.checkManifestConfig = false;
        options.enableTeamMsgAck = true;
        options.enableFcs = false;
        options.shouldConsiderRevokedMessageUnreadCount = true;
        // 会话置顶是否漫游
        options.notifyStickTopSession = true;
//        options.mixPushConfig = buildMixPushConfig();
//        options.serverConfig = configServer(context);
        // 打开消息撤回未读数-1的开关
        options.shouldConsiderRevokedMessageUnreadCount = true;
        return options;
    }

//    public static ServerAddresses configServer(Context context) {
//
//        if (DataUtils.getServerConfigType(context) == Constant.OVERSEA_CONFIG) {
//            ServerAddresses serverAddresses = new ServerAddresses();
//            serverAddresses.lbs = "https://lbs.netease.im/lbs/conf.jsp";
//            serverAddresses.nosUploadLbs = "http://wannos.127.net/lbs";
//            serverAddresses.nosUploadDefaultLink = "https://nosup-hz1.127.net";
//            serverAddresses.nosDownloadUrlFormat = "{bucket}-nosdn.netease.im/{object}";
//            serverAddresses.nosUpload = "nosup-hz1.127.net";
//            serverAddresses.nosSupportHttps = true;
//            ALog.d("ServerAddresses", "ServerConfig:use Singapore node");
//            return serverAddresses;
//        }
//        return null;
//    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null)
            return 0;

        Resources resources = context.getResources();
        if (resources == null)
            return 0;

        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (metrics == null)
            return 0;

        return metrics.widthPixels;
    }

    /** config app image/voice/file/log directory */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
        }
        return storageRootPath;
    }

    public static void initStatusBarNotificationConfig(SDKOptions options) {
        // load notification
        StatusBarNotificationConfig config = loadStatusBarNotificationConfig();
        // load 用户的 StatusBarNotificationConfig 设置项
        // SDK statusBarNotificationConfig 生效
        config.notificationFilter =
                imMessage -> StatusBarNotificationFilter.FilterPolicy.DEFAULT;
        options.statusBarNotificationConfig = config;
    }

    // config StatusBarNotificationConfig
    public static StatusBarNotificationConfig loadStatusBarNotificationConfig() {
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class;
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        config.notificationColor = Color.parseColor("#3a9efb");
        config.notificationSound = NOTIFY_SOUND_KEY;
        config.notificationFoldStyle = NotificationFoldStyle.ALL;
        config.downTimeEnableNotification = true;
        config.ledARGB = Color.GREEN;
        config.ledOnMs = LED_ON_MS;
        config.ledOffMs = LED_OFF_MS;
        config.showBadge = true;
        return config;
    }

    /**
     * 打印日志信息
     * @param str
     */
    public static void log(String str) {
        System.out.println("===================> " + str);
    }

    /**
     * 判断是否在主进程
     * @param context
     * @return
     */
    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     *
     * @return 进程名
     */
    public static String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        if(am == null || am.getRunningAppProcesses() == null)
            return "";
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
