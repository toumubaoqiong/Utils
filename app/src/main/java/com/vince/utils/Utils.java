package com.vince.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.List;

/**
 * decription ：获取AndroidManifest.xml中配置数据
 * author ： vince
 */

/*<meta-data//在Application里面中
        android:name="android.app.lib_name"
        android:value="cocos2dlua" />*/
public class Utils {


    public static String getAppInfo(String infoName){

        Context c;
        ApplicationInfo ai = c.getPackageManager().getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = ai.metaData;
//        String libName = bundle.getString("android.app.lib_name");
        String libName = bundle.getString(infoName);

        return libName;
    }


    //获取版本号
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 1).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    //获取版本名称
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本名称
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 1).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 可以启动额外进程时，重复初始化
     */
    /*
    String processName = OsUtils.getProcessName(this,
            android.os.Process.myPid());
    L.d(WModel.Time, "进程名称"+processName);
    if (processName != null) {
        boolean defaultProcess = processName
                .equals(WMapConstants.REAL_PACKAGE_NAME);
        if (defaultProcess) {
            //必要的初始化资源操作
        }
    }
    */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
