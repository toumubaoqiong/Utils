package com.vince.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Toast;

/**
 *description:显示信息类 用于显示提示信息、打印信息等
 *author:vince
 */
public class DebugPrint {
    public static final String TAG = "DebugPrint";

    private DebugPrint() {
        // 避免被外部实例化
    }

    public static void println(String printInfo) {
        if (Debug.DEBUG_MODE && null != printInfo) {
            System.out.println(printInfo);
        }
    }

    public static void print(String printInfo) {
        if (Debug.DEBUG_MODE && null != printInfo) {
            System.out.print(printInfo);
        }
    }

    public static void printLogI(String logInfo) {
        printLogI(TAG, logInfo);
    }

    public static void printLogI(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.i(tag, logInfo);
        }
    }

    public static void printLogE(String logInfo) {
        printLogE(TAG, logInfo);
    }

    public static void printLogE(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.e(tag, logInfo);
        }
    }

    public static void printLogW(String logInfo) {
        printLogW(TAG, logInfo);
    }

    public static void printLogW(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.w(tag, logInfo);
        }
    }

    public static void printLogD(String logInfo) {
        printLogD(TAG, logInfo);
    }

    public static void printLogD(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.d(tag, logInfo);
        }
    }

    public static void printLogV(String logInfo) {
        printLogV(TAG, logInfo);
    }

    public static void printLogV(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag || null != logInfo) {
            Log.v(tag, logInfo);
        }
    }

    public static void printLogWtf(String logInfo) {
        printLogWtf(TAG, logInfo);
    }

    public static void printLogWtf(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.wtf(tag, logInfo);
        }
    }

    public static void showToast(Context context, String toastInfo) {
        if (null != context && null != toastInfo) {
            Toast.makeText(context, toastInfo, Toast.LENGTH_LONG).show();
        }
    }

    public static void showToast(Context context, String toastInfo, int timeLen) {
        if (null != context && null != toastInfo && (timeLen > 0)) {
            Toast.makeText(context, toastInfo, timeLen).show();
        }
    }

    public static void printBaseInfo() {
        if (Debug.DEBUG_MODE) {
            StringBuilder strBuffer = new StringBuilder();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; class:").append(stackTrace[1].getClassName())
                    .append("; method:").append(stackTrace[1].getMethodName())
                    .append("; number:").append(stackTrace[1].getLineNumber())
                    .append("; fileName:").append(stackTrace[1].getFileName());

            println(strBuffer.toString());
        }
    }

    public static void printFileNameAndLinerNumber() {
        if (Debug.DEBUG_MODE) {
            StringBuilder strBuffer = new StringBuilder();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; fileName:").append(stackTrace[1].getFileName())
                    .append("; number:").append(stackTrace[1].getLineNumber());

            println(strBuffer.toString());
        }
    }

    public static int printLineNumber() {
        if (Debug.DEBUG_MODE) {
            StringBuilder strBuffer = new StringBuilder();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; number:").append(stackTrace[1].getLineNumber());

            println(strBuffer.toString());
            return stackTrace[1].getLineNumber();
        } else {
            return 0;
        }
    }

    public static void printMethod() {
        if (Debug.DEBUG_MODE) {
            StringBuilder strBuffer = new StringBuilder();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; number:").append(stackTrace[1].getMethodName());

            println(strBuffer.toString());
        }
    }

    public static void printFileNameAndLinerNumber(String printInfo) {
        if (null == printInfo || !Debug.DEBUG_MODE) {
            return;
        }
        StringBuilder strBuffer = new StringBuilder();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        strBuffer.append("; fileName:").append(stackTrace[1].getFileName())
                .append("; number:").append(stackTrace[1].getLineNumber())
                .append("\n").append((null != printInfo) ? printInfo : "");

        println(strBuffer.toString());
    }

    public static void showToastDialog(Context context, int msgId) {
        if (null == context || msgId < 0) {
            return;
        }
        AlertDialog.Builder builder = new Builder(context);

        builder.setMessage(msgId);
        builder.setTitle("温馨提示：");
        builder.setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public static void showToastDialog(Context context, String toastInfo) {
        if (null == context || null == toastInfo) {
            return;
        }
        AlertDialog.Builder builder = new Builder(context);

        builder.setMessage(toastInfo);
        builder.setTitle("温馨提示：");
        builder.setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public class Debug {
        public static final boolean DEBUG_MODE = true;
    }
}
