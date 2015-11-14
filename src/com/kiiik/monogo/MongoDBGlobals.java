package com.kiiik.monogo;

import com.kiiik.provider.MDBGlobalsService;
/**
 * 存放MongoDB相关的全局变量
 * 
 * @author YCL
 * 
 */
public class MongoDBGlobals {
	// MongoDB 数据库名称
	private static String blogDBName = "openfire_blog_db";
	// MongoDB 用户名
	private static String mDbAccount = "kiiik";
	// MongoDB 密码
	private static char[] mDbPassword = "kiiik".toCharArray();
	// MongoDB 微博消息存储Collection名(存储所有的微博消息)
	private static String blogInfoTableName = "ob_messages";
	// MongoDB 微博用户粉丝存储Collection名
	private static String userFanInfoTableName = "ob_userfan";
	// MongoDB 微博用户相关消息存储Collection名
	private static String userMsgTableName = "ob_usermsg";
	// MongoDB 微博用户相关消息存储Collection名
	private static String topicTableName = "ob_topics";
	// 微博ftp相关消息存储Collection名
	private static String globalTableName = "ob_globals";
	// 微博图片服务器地址 开发：192.168.27.32 生产：180.169.108.225
	private static String ftpAddress = MDBGlobalsService.getInstance().getGlobalValue("ftpaddress");
	// 微博图片服务器端口
	private static String ftpPort = MDBGlobalsService.getInstance().getGlobalValue("ftpport");
	// 微博图片服务器用户名
	private static String ftpAccount = MDBGlobalsService.getInstance().getGlobalValue("ftpaccount");
	// 微博图片服务器密码
	private static String ftpPassword = MDBGlobalsService.getInstance().getGlobalValue("ftppassword");
	
	public static String getGlobalTableName() {
		return globalTableName;
	}

	public static void setGlobalTableName(String globalTableName) {
		MongoDBGlobals.globalTableName = globalTableName;
	}

	public static String getFtpAddress() {
		return ftpAddress;
	}

	public static void setFtpAddress(String ftpAddress) {
		MongoDBGlobals.ftpAddress = ftpAddress;
	}

	public static String getFtpPort() {
		return ftpPort;
	}

	public static void setFtpPort(String ftpPort) {
		MongoDBGlobals.ftpPort = ftpPort;
	}

	public static String getFtpAccount() {
		return ftpAccount;
	}

	public static void setFtpAccount(String ftpAccount) {
		MongoDBGlobals.ftpAccount = ftpAccount;
	}

	public static String getFtpPassword() {
		return ftpPassword;
	}

	public static void setFtpPassword(String ftpPassword) {
		MongoDBGlobals.ftpPassword = ftpPassword;
	}

	public static String getTopicTableName() {
		return topicTableName;
	}

	public static void setTopicTableName(String topicTableName) {
		MongoDBGlobals.topicTableName = topicTableName;
	}

	public static String getUserMsgTableName() {
		return userMsgTableName;
	}

	public static void setUserMsgTableName(String userMsgTableName) {
		MongoDBGlobals.userMsgTableName = userMsgTableName;
	}

	public static String getUserFanInfoTableName() {
		return userFanInfoTableName;
	}

	public static void setUserFanInfoTableName(String userFanInfoTableName) {
		MongoDBGlobals.userFanInfoTableName = userFanInfoTableName;
	}

	public static String getBlogInfoTableName() {
		return blogInfoTableName;
	}

	public static void setBlogInfoTableName(String blogInfoTableName) {
		MongoDBGlobals.blogInfoTableName = blogInfoTableName;
	}

	public static String getmDbAccount() {
		return mDbAccount;
	}

	public static void setmDbAccount(String mDbAccount) {
		MongoDBGlobals.mDbAccount = mDbAccount;
	}

	public static char[] getmDbPassword() {
		return mDbPassword;
	}

	public static void setmDbPassword(char[] mDbPassword) {
		MongoDBGlobals.mDbPassword = mDbPassword;
	}

	public static String getBlogDBName() {
		return blogDBName;
	}

	public static void setBlogDBName(String blogDBName) {
		MongoDBGlobals.blogDBName = blogDBName;
	}
}
