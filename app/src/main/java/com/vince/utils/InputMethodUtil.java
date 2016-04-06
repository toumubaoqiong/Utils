package com.vince.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
/**
 *description:输入法工具类
 *author:vince
 */
public class InputMethodUtil {
	private InputMethodUtil(){
		
	}
	
	/**
	 * 隐藏输入法
	 * @param view
	 */
	public static void hideIme(View view) {
		InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);		
	}
}
