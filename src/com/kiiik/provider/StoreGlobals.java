package com.kiiik.provider;

/**
 * 全局变量
 * 
 * @author kf13
 *
 */
public class StoreGlobals {
	/**
	 * 默认数据库链接地址
	 * "jdbc:mysql://localhost/kiiikstoredb";
	 */
	private static String DBURL;

	/**
	 * 默认数据库连接端口
	 */
	private static int CONNECTION_PORT;
	
	public static int getCONNECTION_PORT() {
		return CONNECTION_PORT;
	}

	public static void setCONNECTION_PORT(int cONNECTION_PORT) {
		CONNECTION_PORT = cONNECTION_PORT;
	}

	public static String getDBURL() {
		return DBURL;
	}

	public static void setDBURL(String dBURL) {
		DBURL = dBURL;
	}
}
