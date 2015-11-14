package com.kiiik.monogo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * MongoDB管理类
 * 
 * @author YCL
 * 
 */
public class MongoDBManager {

	private static Mongo mongoInstance = null;

	private static DB blogDB = null;

	/**
	 * 单例模式，获取Mongo实例
	 * @return
	 */
	public static Mongo getInstance() {
		if (mongoInstance == null) {
			try {
				mongoInstance = new Mongo();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mongoInstance;
	}

	/**
	 * 用户可直接从此方法获取特定数据库
	 * @return
	 * @throws Exception 
	 */
	public static DB getBlogDB() throws Exception {
		if (blogDB == null) {
			if (mongoInstance == null) {
				try {
					mongoInstance = new Mongo();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// MongoDBGlobals.getBlogDBName()
			blogDB = mongoInstance.getDB(MongoDBGlobals.getBlogDBName());
			// 对数据库进行登录验证
			boolean token = blogDB.authenticate(MongoDBGlobals.getmDbAccount(),
					MongoDBGlobals.getmDbPassword());
			if (!token) {
				throw new Exception();
			}
		}
		return blogDB;
	}
	
	/**
	 * test
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		DB db = MongoDBManager.getBlogDB();
		// Mongo mongo = MongoDBManager.getInstance();
		// char[] password = "kiiik".toCharArray();
		// if(db.authenticate("kiiik", password)){
		// System.out.println("successful");
		// }
		// System.out.println("failure");
	}
}
