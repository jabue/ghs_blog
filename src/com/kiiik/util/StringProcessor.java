package com.kiiik.util;

/**
 * 
 * 字符串处理类
 * 
 * @author YCL
 * 
 */
public class StringProcessor {

	public StringProcessor() {

	}

	/**
	 * 字符串处理
	 * @param string
	 * @return
	 */
	public String[] getStringSplited(String string) {
		String[] stringList = string.split("\\|");
		return stringList;
	}
}
