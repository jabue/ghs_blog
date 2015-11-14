package com.kiiik.info;

/**
 * 用户关系封装类
 * 
 * @author YCL
 * 
 */
public class UserFanInfo {
	// 用户名
	private String username;
	// 粉丝用户名
	private String fanName = "";
	// 被关注用户名（用户关注的user）
	private String starName = "";

	public UserFanInfo() {

	}

	public UserFanInfo(String username, String fanName) {
		super();
		this.username = username;
		this.fanName = fanName;
	}

	public String getStarName() {
		return starName;
	}

	public void setStarName(String starName) {
		this.starName = starName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFanName() {
		return fanName;
	}

	public void setFanName(String fanName) {
		this.fanName = fanName;
	}

}
