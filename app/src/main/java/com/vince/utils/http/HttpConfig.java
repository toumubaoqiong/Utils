package com.vince.utils.http;

/**
 *description: 网络请求地址常量
 *@author:vince
 */
public class HttpConfig
{
	//=============================请求地址/端口=================================================
	public static final String SERVER_NAME = "http://172.28.1.140:9090/syncmath/question/";
	/*接口字段*/
	public static final String GETBOOKLIST = "getBookList";
	//============================异常字段============================================================
	public static final String ERROR_TYPE = "error_type";
	
	public static final String ERROR_NETWORK_TYPE = "error_net_type";
	
	public static final String ERROR_CANCEK_TYPE = "error_cancel_type";
	
	public static final String ERROR_NO_DATA = "error_no_data";
	
	public static final String ERROR_TIME_OUT = "error_time_out";
	
	public static final String ERROR_ERROE_SEVER = "error_sever";
}
