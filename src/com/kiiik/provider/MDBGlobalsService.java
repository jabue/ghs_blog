package com.kiiik.provider;

import org.bson.types.ObjectId;

import com.kiiik.monogo.MongoDBGlobals;
import com.kiiik.monogo.MongoDBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * ftp相关信息服务类
 * 
 * @author YCL
 * 
 */
public class MDBGlobalsService {

	private DB mongoDb = null;
	private DBCollection globalCollection = null;
	private static MDBGlobalsService instance = null;

	public MDBGlobalsService() throws Exception {
		init();
	}

	/**
	 * 初始化服务
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		mongoDb = MongoDBManager.getBlogDB();
		boolean token = mongoDb.authenticate(MongoDBGlobals.getmDbAccount(),
				MongoDBGlobals.getmDbPassword());
		if (!token) {
			throw new Exception();
		}
		globalCollection = mongoDb.getCollection(MongoDBGlobals
				.getGlobalTableName());
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MDBGlobalsService getInstance(){
		if (instance == null) {
			try {
				instance = new MDBGlobalsService();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * 根据属性名获取属性值
	 * 
	 * @param valueName
	 * @return
	 */
	public String getGlobalValue(String valueName) {
		String value = "";
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", valueName);
		
		DBObject dbObject = globalCollection.find(selectCondition).one();
		value = dbObject.get("value").toString();
		return value;
	}
}
