package com.vince.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

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





}
