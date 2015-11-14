package com.kiiik.info;

/**
 * 存储用户的登录信息
 * 
 * @author YCL
 * 
 */
public class UserInfo extends CodeInfo {
	
	public String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
