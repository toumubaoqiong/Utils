package com.vince.utils.http;

public interface HandlerCallBack {
    /**
     * 网络请求完，用于更新UI
     * @param type   HttpConfig 中的type
     * @param result 请求结果，请求失败为null，成功为结果列表
     */
    public void onHandlerCallBack(Object type, Object result);
}











