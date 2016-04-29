package com.vince.utils.http;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.conn.ConnectTimeoutException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 *description:处理网络请求的抽象类，异步发出网络请求，返回网络数据后并响应回调函数
 *@author:vince
 */
public class HttpReq extends AsyncTask<Void, Integer, Object>
{
	private static final String TAG = "HttpReq";
	protected ReqParam mParam = null;  //参数封装类
	protected HttpCallback mCallBack = null;//回调函数
	private int mServiceTag = -1; 
	private ProgressDialog mDialog = null;

	/**
	 * 创建http请求 那必须得有请求参数呀
	 * @param mParam
	 */
	public HttpReq(ReqParam mParam)
	{
		super();
		this.mParam = mParam;
	}

	public void setServiceTag(int nTag) {
		mServiceTag = nTag;
	}
	
	public int getServiceTag() {
		return mServiceTag;
	}

	public void setCallBack(HttpCallback callBack)
	{
		mCallBack = callBack;
	}
	
	/**
	 * 返回回调方法
	 */
	public HttpCallback getCallBack() {
		return mCallBack;
	}

	/**
	 * 响应网络请求得抽象函数，用于重写对返回数据的处理
	 * @param response 网络请求返回的数据
	 * @return Object 返回的数据对象
	 */
	protected Object processResponse(Object response)throws Exception
	{
		return null;
	}

	/**
	 * 设置网络请求参数封装类的函数
	 * @param param 网络请求参数封装类
	 */
	public void setParam(ReqParam param) {
		mParam = param;
	}

	/**
	 * 添加网络请求参数
	 * @param key 请求参数的名称
	 * @param value 请求参数的值，字符串类型
	 */
	public void addParam(String key, String value) {
		mParam.addParam(key, value);
	}
	
	/**
	 * 添加网络请求参数
	 * @param key 请求参数的名称
	 * @param value 请求参数的值，对象类型
	 */
	public void addParam(String key, Object value) {
		mParam.addParam(key, value);
	}

	/**
	 * 网络请求函数，该函数中通过判断网络请求的方式（post 或者 get）
	 * 发出网络请求，并返回响应的数据结果
	 * @return Object 返回的数据对象
	 * @throws Exception
	 */
	public Object runReq() throws Exception {

		String url = HttpConfig.SERVER_NAME + mParam.toString();

		Object result = readData(url);
		if(result == null || result.equals("null"))
		{
			result = requestOldResponse();
		}
		
		if(HttpConfig.ERROR_ERROE_SEVER.equals(result)
				||HttpConfig.ERROR_TIME_OUT.equals(result)
				||HttpConfig.ERROR_TYPE.equals(result))
		{
			return result;
		}
		
		if(result != null && !"null".equals(result))
		{
			Object object = processResponse(result);
			return object;
		}
		return null;
	}
	
	/**
	 * 请求旧数据，用于子类重写
	 * @return
	 */
	protected Object requestOldResponse()
	{
		return null;
	}
	
	/**
	 *  AsyncTask方法重载
	 *  预执行
	 */
	@Override
	protected void onPreExecute() {
		
		if(mDialog != null)
		{
			mDialog.show();
		}
		
		super.onPreExecute();
	}

	/**
	 *  AsyncTask方法重载
	 *  后台异步执行
	 */
	@Override
	protected Object doInBackground(Void... params) {
		try 
		{
			Object result = this.runReq();
			return result;
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
			return HttpConfig.ERROR_ERROE_SEVER;
		}
	}

	/**
	 *  AsyncTask方法重载
	 *  在任务执行成功时回调
	 */
	@Override
	protected void onPostExecute(Object result) {
		if (mCallBack != null) {
			mCallBack.onResult(mParam.getType(), result);
		}
		
		HttpService.getInstance().onReqFinish(this);
		
		if(mDialog != null)
		{
			mDialog.dismiss();
		}
	}

	/**
	 *  AsyncTask方法重载
	 *  在任务成功取消时回调
	 */
	@Override
	protected void onCancelled() {
		if (mCallBack != null) {
			mCallBack.onResult(HttpConfig.ERROR_TIME_OUT, null);
		}
		HttpService.getInstance().onReqFinish(this);
		if(mDialog != null)
		{
			mDialog.dismiss();
		}
	}

	private static String readData(String url)
	{
		try
		{
			URL urlObj = new URL(url);
			HttpURLConnection urlCon =(HttpURLConnection) urlObj.openConnection();
			urlCon.setConnectTimeout(30000);
			urlCon.setReadTimeout(30000);

			if (urlCon.getResponseCode() == 200) {

				InputStream in = urlCon.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null)
				{
					sb.append(line);
				}
				reader.close();
				return sb.toString();
			}
			else
			{
				return HttpConfig.ERROR_ERROE_SEVER;
			}

		}
		catch (ConnectTimeoutException c) {
			Log.e(TAG, "Ace 捕获异常ConnectTimeoutException: " + c.toString());
			return HttpConfig.ERROR_TIME_OUT;
		} catch (SocketTimeoutException s) {
			Log.e(TAG, "Ace 捕获异常SocketTimeoutException: " + s.toString());
			return HttpConfig.ERROR_TIME_OUT;
		} catch (ConnectException ce) {
			Log.e(TAG, "Ace 捕获异常ConnectException: " + ce.toString());
			return HttpConfig.ERROR_ERROE_SEVER;
		} catch (Exception e) {
			Log.e(TAG, "Ace 捕获异常Exception: " + e.toString());
			return HttpConfig.ERROR_ERROE_SEVER;
		}
	}

	public ProgressDialog getDialog()
	{
		return mDialog;
	}

	public void setDialog(ProgressDialog mDialog)
	{
		this.mDialog = mDialog;
	}
}
