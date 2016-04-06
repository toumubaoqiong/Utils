package com.vince.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *description:文本显示处理相关工具类
 *author:vince
 */
public class StringTextUtil {
	private static Toast mToast = null;

	/**
	 *description:传入字符串，用正则表达式取出其中中文字符
	 *author:vince
	 */
	public static List<String> getChinese(List<String> textList, boolean isHaveEndSymbol) {//isHaveEndSymbol 结尾有标点符号
		List<String> wordList = new ArrayList<String>(5);
		for (String str : textList) {
			// 用正则表达式匹配中文字符
			String pstr = "[\u4e00-\u9fa5]+";
			Pattern p = Pattern.compile(pstr);
			Matcher m = p.matcher(str);
			String word = "";
			while (m.find()) {
				word = word + m.group();
			}
			if (isHaveEndSymbol && str.lastIndexOf(';') < str.length() - 1) {
				String temp = str.charAt(str.lastIndexOf(';') + 1) + "";
				wordList.add(word + temp);
			} else {
				wordList.add(word);
			}
		}
		
		return wordList;
	}
}
