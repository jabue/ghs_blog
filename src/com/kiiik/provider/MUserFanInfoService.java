package com.kiiik.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kiiik.info.UserFanInfo;
import com.kiiik.monogo.MongoDBGlobals;
import com.kiiik.monogo.MongoDBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 面向对象：用户（username）. 功能：用户粉丝及关注好友信息服务类. 对应Collection:ob_userfan.
 * 字段：(_id:用户名;userFans:粉丝列表;userStars:关注好友列表）.
 * 说明：userFans及userStars字段类型均为HashMap<String,Integer>. String：用户名;
 * Integer:是否相互关注(0,未相互关注;1,相互关注).
 * 
 * @author YCL
 * 
 */
public class MUserFanInfoService {

	private DB mongoDb = null;
	private DBCollection userFanInfoCollection = null;

	public MUserFanInfoService() throws Exception {
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
		userFanInfoCollection = mongoDb.getCollection(MongoDBGlobals
				.getUserFanInfoTableName());
	}

	/**
	 * 获取MongoDB中用户粉丝存储Collection
	 * 
	 * @return
	 * @throws Exception
	 */
	public DBCollection getMUserFanInfoTable() throws Exception {
		if (userFanInfoCollection == null) {
			if (mongoDb == null) {
				init();
			}
			userFanInfoCollection = mongoDb.getCollection(MongoDBGlobals
					.getBlogInfoTableName());
		}
		return userFanInfoCollection;
	}

	/**
	 * 添加相应用户的微博粉丝到MongoDB
	 * 
	 * @param username
	 * @param userFans
	 */
	public void addUserFantoMDB(String username, ArrayList<String> userFans) {
		DBObject userFanInfo = new BasicDBObject();

		userFanInfo.put("_id", username);
		// userFanInfo.put("username", username);
		userFanInfo.put("userFans", userFans);

		System.out.println(userFanInfoCollection.save(userFanInfo).getN());
	}

	/**
	 * 重写上面的方法，添加用户的微博粉丝到MongoDB
	 * 
	 * @param userFanInfo
	 */
	public void addUserFantoMDB(UserFanInfo userFanInfo) {
		// 首先到库里查询是否有这条User的相关记录
		DBObject userInfo = new BasicDBObject();
		userInfo.put("_id", userFanInfo.getUsername());
		// 如果数据库中没有这条记录，则插入一条新数据
		if (userFanInfoCollection.find(userInfo).toArray().size() == 0) {
			addNewUserFan(userFanInfo);
			return;
		}

		// 数据库中有这条记录
		updateUserFan(userFanInfo);
	}

	/**
	 * 当数据库中有某user的粉丝信息时，则更新
	 * db.ob_userfan.update({"_id":"1001"},{$set,{"userFans"
	 * :["1001","1002","1003"]}})
	 * 
	 * @param userFanInfo
	 */
	private void updateUserFan(UserFanInfo userFanInfo) {
		// 封装更新条件
		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("_id", userFanInfo.getUsername());
		// 获取旧的粉丝map
		HashMap<String, Integer> fanMap = getUserFans(userFanInfo.getUsername());
		// 获取旧的关注好友Map
		HashMap<String, Integer> starMap = getUserStars(userFanInfo
				.getUsername());

		DBObject updateValue = new BasicDBObject();

		// 更新粉丝列表
		if (!userFanInfo.getFanName().equalsIgnoreCase("")) {
			// 如果对方是自己已经关注的人,则需要同时更新粉丝和自己关注的人两个列表
			if (starMap.containsKey(userFanInfo.getFanName())) {
				fanMap.put(userFanInfo.getFanName(), 1);
				starMap.put(userFanInfo.getFanName(), 1);
				updateValue.put("userStars", starMap);
			} else {
				// 添加新的粉丝
				fanMap.put(userFanInfo.getFanName(), 0);
			}
			// 封装更新字段
			updateValue.put("userFans", fanMap);
		}

		// 更新关注好友列表
		if (!userFanInfo.getStarName().equalsIgnoreCase("")) {
			// 如果对方已经是自己的粉丝，也需要更新两个列表
			if (fanMap.containsKey(userFanInfo.getStarName())) {
				starMap.put(userFanInfo.getStarName(), 1);
				fanMap.put(userFanInfo.getStarName(), 1);
				updateValue.put("userFans", fanMap);
			} else {
				// 更新关注好友列表
				starMap.put(userFanInfo.getStarName(), 0);
			}
			// 封装跟新字段
			updateValue.put("userStars", starMap);
		}

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);
		userFanInfoCollection.update(updateCondition, updateSetValue);
	}

	public void updateUserFan(String username, String topicID) {
		// 封装更新条件
		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("_id", username);
		
		ArrayList<String> userTopics = getUserTopics(username);
		userTopics.add(topicID);
		// 封装跟新字段
		DBObject updateValue = new BasicDBObject();
		updateValue.put("userTopics", userTopics);
		
		DBObject updateSetValue = new BasicDBObject("$set", updateValue);
		userFanInfoCollection.update(updateCondition, updateSetValue);
		
	}

	public ArrayList<String> getUserTopics(String username) {
		// TODO Auto-generated method stub
		ArrayList<String> userTopics = null;
		
		// 封装查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("userTopics", true);

		DBObject topicList = userFanInfoCollection.find(selectCondition,
				selectField).one();
		userTopics = (ArrayList<String>) topicList.get("userFans");
		
		return userTopics;
	}

	/**
	 * 当数据库中没有某user的信息，则新添加
	 * db.ob_userfan.save({"_id":"1001","userFans":[],"userStars":[]})
	 * 
	 * @param userFanInfo
	 */
	private void addNewUserFan(UserFanInfo userFanInfo) {
		// 封装写库用的用户数据
		DBObject userObject = new BasicDBObject();
		userObject.put("_id", userFanInfo.getUsername());
		ArrayList<String> topicList = new ArrayList<String>();

		// 生成粉丝list
		// ArrayList<String> fanList = new ArrayList<String>();
		Map<String, Integer> fanMap = new HashMap<String, Integer>();
		if (!userFanInfo.getFanName().equalsIgnoreCase("")) {
			// fanList.add(userFanInfo.getFanName());
			fanMap.put(userFanInfo.getFanName(), 0);
		}

		// 生成自己关注好友List
		// ArrayList<String> starList = new ArrayList<String>();
		Map<String, Integer> starMap = new HashMap<String, Integer>();
		if (!userFanInfo.getStarName().equalsIgnoreCase("")) {
			// starList.add(userFanInfo.getStarName());
			starMap.put(userFanInfo.getStarName(), 0);
		}

		userObject.put("userFans", fanMap);
		userObject.put("userStars", starMap);
		userObject.put("userTopics", topicList);

		System.out.println(userFanInfoCollection.save(userObject).getN());
	}

	/**
	 * 获取某用户的所有粉丝 db.ob_userfan.find({"_id":"1001"},{"_id":0,"userFans":1})
	 * 
	 * @param username
	 * @return
	 */
	public HashMap<String, Integer> getUserFans(String username) {
		HashMap<String, Integer> userfans = null;
		// 封装查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("userFans", true);

		DBObject fansList = userFanInfoCollection.find(selectCondition,
				selectField).one();
		userfans = (HashMap<String, Integer>) fansList.get("userFans");

		return userfans;
	}

	/**
	 * 获取某用户所关注过的所有好友 db.ob_userfan.find({"_id":"1001"},{"_id":0,"userStars":1})
	 * 
	 * @param username
	 * @return
	 */
	public HashMap<String, Integer> getUserStars(String username) {
		HashMap<String, Integer> userStars = null;
		// 封装查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("userStars", true);

		DBObject fansList = userFanInfoCollection.find(selectCondition,
				selectField).one();
		userStars = (HashMap<String, Integer>) fansList.get("userStars");

		return userStars;
	}

	public static void main(String[] args) throws Exception {

		MUserFanInfoService userFanInfoService = new MUserFanInfoService();
		DBCollection userFanInfoCollection = userFanInfoService
				.getMUserFanInfoTable();

		/*
		 * 查找全部记录/删除
		 */
		// List<DBObject> idInfo = userFanInfoCollection.find().toArray();
		// for (DBObject temp : idInfo) {
		// System.out.println(temp.toString());
		// userFanInfoCollection.remove(new BasicDBObject("_id", temp
		// .get("_id")));
		// }

		/*
		 * 测试addUserFantoMDB(String username, ArrayList<String> userFans)方法 //
		 */
		// ArrayList<String> userFans = new ArrayList<String>();
		// userFans.add("1010");
		// userFans.add("1001");
		// userFans.add("1009");
		// userFanInfoService.addUserFantoMDB("1002", userFans);

		/*
		 * 测试addUserFantoMDB(UserFanInfo userFanInfo)方法 //
		 */
		// ArrayList<String> userFans = new ArrayList<String>();
		// userFans.add("1010");
		// userFans.add("1001");
		// userFans.add("1009");
		// UserFanInfo userFanInfo = new UserFanInfo();
		// userFanInfo.setUsername("1011");
		// // userFanInfo.setStarName("1000");
		// userFanInfo.setStarName("1014");
		// userFanInfoService.addUserFantoMDB(userFanInfo);

		/*
		 * 测试getUserFans(String username)方法
		 */
		// HashMap<String, Integer> userfans = userFanInfoService
		//		.getUserStars("1011");\
		ArrayList<String> userfan = userFanInfoService.getUserTopics("1011");
		System.out.println(userfan);

		// Map<String,Integer> map = new HashMap<String,Integer>();
		// map.put("1001", 0);
		// map.put("1002", 1);
		// System.out.print(map.keySet());
		// System.out.println(map.containsKey("1001"));
		// map.put("1001", 1);
		// System.out.print(map);
	}
}
