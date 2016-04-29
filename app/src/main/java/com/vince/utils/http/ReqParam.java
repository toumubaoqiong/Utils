package com.vince.utils.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *description:网络请求参数存储类
 *@author:vince
 */
public class ReqParam
{
	private Map<String, String> mParams = new HashMap<String, String>();// 参数存储容器map
	
	private String mType;//对应HttpConfig中的接口字段

	public ReqParam(String mType)
	{
		super();
		this.mType = mType;
	}

	public void clean()
	{
		if(mParams != null)
		{
			mParams.clear();
		}
	}
	
	public String getType()
	{
		return mType;
	}
	
	public void setType(String type)
	{
		this.mType = type;
	}

	public Map<String, String> getParams()
	{
		return mParams;
	}

	public void setParams(Map<String, String> mParams)
	{
		this.mParams = mParams;
	}

	public void addParam(String key, String val)
	{
		mParams.put(key, val);
	}
	
	public void addParam(String key, Object val)
	{
		mParams.put(key, val.toString());
	}
	
	public void removeParam(String key)
	{
		mParams.remove(key);
	}
	
	public String get(String key)
	{
		return mParams.get(key);
	}
	
	public String toString()
	{
		List<String> keyList = new ArrayList<String>();
		Iterator<String> it = mParams.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			keyList.add(key);
		}

		Collections.sort(keyList, new Comparator<String>()
		{
			public int compare(String str1, String str2)
			{
				if (str1.compareTo(str2) > 0)
				{
					return 1;
				}
				if (str1.compareTo(str2) < 0)
				{
					return -1;
				}
				return 0;
			}
		});
		
		StringBuilder strbuf = new StringBuilder();
		strbuf.append(mType).append("?");
		for (String key : keyList)
		{
			String val = mParams.get(key);
			if(val != null && !val.equals("null") ) //&& !TextUtils.isEmpty(val)
			{
				strbuf.append(key);
				strbuf.append("=");
				strbuf.append(mParams.get(key));
				strbuf.append("&");
			}
			
		}
		
		String p = strbuf.toString().replaceAll("\n", "").replaceAll("\r", "").substring(0, strbuf.length() - 1);
		return p;
	}
}
