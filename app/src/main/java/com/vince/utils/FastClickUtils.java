package com.vince.utils;

/**
 *description:判断是否进行双击
 *@author:vince
 */
public class FastClickUtils {
    private static long mLastClickTime = 0L;

    private FastClickUtils() {
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if(0L < timeD && timeD < 500L) {
            mLastClickTime = time;
            return true;
        } else {
            mLastClickTime = time;
            return false;
        }
    }
}
