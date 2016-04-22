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

    Context c;
    ApplicationInfo ai = c.getPackageManager().getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA);
    Bundle bundle = ai.metaData;
    String libName = bundle.getString("android.app.lib_name");
}
