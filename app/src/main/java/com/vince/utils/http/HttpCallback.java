package com.vince.utils.http;


/**
 *description: 回调接口
 *@author:vince
 */
public interface HttpCallback {
	public boolean onResult(String type, Object object);
}