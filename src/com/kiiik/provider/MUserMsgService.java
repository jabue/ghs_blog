package com.kiiik.provider;

import java.util.ArrayList;


import com.kiiik.monogo.MongoDBGlobals;
import com.kiiik.monogo.MongoDBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 面向对象：用户（username）. 功能：跟用户所有相关消息（微博、评论）服务类. 对应Collection:ob_usermsg
 * 字段：（_id:用户ID
 * 、username;sendMsg:用户自己发过的消息ID;involvedMsg:未读的@自己的消息;hisInvolvedMsg:
 * 已读的@自己的消息;involvedCmt:未读的评论自己的评论;hisInvolvedCmt:已读的评论自己的评论）.
 * 说明：使用ArrayList<String>数据结构存放.
 * 
 * @author YCL
 * 
 */
public class MUserMsgService {

	private DB mongoDb = null;
	private DBCollection userMsgCollection = null;
	private static MUserMsgService instance = null;

	/**
	 * 单例模式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MUserMsgService getInstance() throws Exception {
		if (instance == null) {
			instance = new MUserMsgService();
		}
		return instance;
	}

	public MUserMsgService() throws Exception {
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
		userMsgCollection = mongoDb.getCollection(MongoDBGlobals
				.getUserMsgTableName());
	}

	/**
	 * 获取MongoDB中用户消息存储Collection
	 * 
	 * @return
	 * @throws Exception
	 */
	public DBCollection getMUserFanInfoTable() throws Exception {
		if (userMsgCollection == null) {
			if (mongoDb == null) {
				init();
			}
			userMsgCollection = mongoDb.getCollection(MongoDBGlobals
					.getBlogInfoTableName());
		}
		return userMsgCollection;
	}

	/**
	 * 更新用户自己发的微博
	 * 
	 * @param username
	 * @param msgId
	 */
	public void updateSendMsg(String username, String msgId) {
		// 如果不存在，先创建一条
		if (!isExist(username)) {
			createInfo(username);
		}

		ArrayList<String> sendMsg = getSendMsg(username);
		sendMsg.add(msgId);

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("sendMsg", sendMsg);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		userMsgCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 更新未读的@用户的消息
	 * 
	 * @param username
	 * @param msgId
	 */
	public void updateInvolvedMsg(String username, String msgId) {
		// 如果不存在，先创建一条
		if (!isExist(username)) {
			createInfo(username);
		}

		ArrayList<String> involvedMsg = getInvolvedMsg(username);
		involvedMsg.add(msgId);

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("involvedMsg", involvedMsg);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		userMsgCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 更新已读的@用户的消息
	 * 
	 * @param username
	 * @param msgId
	 */
	public void updateHisInvolvedMsg(String username, String msgId) {
		// 如果不存在，先创建一条
		if (!isExist(username)) {
			createInfo(username);
		}

		ArrayList<String> hisInvolvedMsg = getHisInvolvedMsg(username);
		hisInvolvedMsg.add(msgId);

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("hisInvolvedMsg", hisInvolvedMsg);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		userMsgCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 更新用户自己赞过的消息
	 * 
	 * @param username
	 * @param msgId
	 */
	public void updateFavouredMsg(String username, String msgId) {
		// 如果不存在，先创建一条
		if (!isExist(username)) {
			createInfo(username);
		}

		ArrayList<String> favouredMsg = getFavouredMsg(username);
		favouredMsg.add(msgId);

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("favouredMsg", favouredMsg);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		userMsgCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 更新用户未读的评论消息
	 * 
	 * @param username
	 * @param msgId
	 */
	public void updateInvolvedCmt(String username, String msgId) {
		// 如果不存在，先创建一条
		if (!isExist(username)) {
			createInfo(username);
		}

		ArrayList<String> involvedCmt = getInvolvedCmt(username);
		involvedCmt.add(msgId);

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("involvedCmt", involvedCmt);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		userMsgCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 更新用户已读的评论消息
	 * 
	 * @param username
	 * @param msgId
	 */
	public void updateHisInvolvedCmt(String username, String msgId) {
		// 如果不存在，先创建一条
		if (!isExist(username)) {
			createInfo(username);
		}

		ArrayList<String> hisInvolvedCmt = getHisInvolvedCmt(username);
		hisInvolvedCmt.add(msgId);

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("hisInvolvedCmt", hisInvolvedCmt);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		userMsgCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 获取某用户username自己已发微博信息列表
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"userMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getSendMsg(String username) {

		ArrayList<String> sendMsg = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("sendMsg", true);

		DBObject temp = userMsgCollection.find(selectCondition, selectField)
				.one();
		sendMsg = (ArrayList<String>) temp.get("sendMsg");

		return sendMsg;
	}

	/**
	 * 获取某用户username未读的@自己的消息
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"involvedMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getInvolvedMsg(String username) {

		ArrayList<String> involvedMsg = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("involvedMsg", true);

		DBObject temp = userMsgCollection.find(selectCondition, selectField)
				.one();
		involvedMsg = (ArrayList<String>) temp.get("involvedMsg");

		return involvedMsg;
	}

	/**
	 * 获取某用户username已读的@自己的消息
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"hisInvolvedMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getHisInvolvedMsg(String username) {

		ArrayList<String> hisInvolvedMsg = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("hisInvolvedMsg", true);

		DBObject temp = userMsgCollection.find(selectCondition, selectField)
				.one();
		hisInvolvedMsg = (ArrayList<String>) temp.get("hisInvolvedMsg");

		return hisInvolvedMsg;
	}

	/**
	 * 获取某用户username自己赞过的@自己的消息
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"favoureddMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getFavouredMsg(String username) {

		ArrayList<String> favouredMsg = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("favouredMsg", true);

		DBObject temp = userMsgCollection.find(selectCondition, selectField)
				.one();
		favouredMsg = (ArrayList<String>) temp.get("favouredMsg");

		return favouredMsg;
	}

	/**
	 * 获取某条消息是否被用户赞过
	 * 
	 * @param username
	 * @return
	 */
	public String getFavouredFlag(String username, String messageid) {
		// 获取用户赞过的所有消息id
		ArrayList<String> favouredMsg = getFavouredMsg(username);
		return favouredMsg.contains(messageid) ? "1" : "0";
	}

	/**
	 * 获取某用户username未读的评论自己的评论
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"involvedMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getInvolvedCmt(String username) {

		ArrayList<String> involvedCmt = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("involvedCmt", true);

		DBObject temp = userMsgCollection.find(selectCondition, selectField)
				.one();
		involvedCmt = (ArrayList<String>) temp.get("involvedCmt");

		return involvedCmt;
	}

	/**
	 * 获取某用户username已读的评论自己的评论
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"involvedMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getHisInvolvedCmt(String username) {

		ArrayList<String> hisInvolvedCmt = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", username);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("hisInvolvedCmt", true);

		DBObject temp = userMsgCollection.find(selectCondition, selectField)
				.one();
		hisInvolvedCmt = (ArrayList<String>) temp.get("hisInvolvedCmt");

		return hisInvolvedCmt;
	}

	/**
	 * 创建一条空白数据
	 * 
	 * @param username
	 */
	private void createInfo(String username) {
		// TODO Auto-generated method stub
		// 封装写库用的用户数据
		DBObject userObject = new BasicDBObject();
		userObject.put("_id", username);

		ArrayList<String> temp = new ArrayList<String>();
		userObject.put("sendMsg", temp);
		userObject.put("involvedMsg", temp);
		userObject.put("hisInvolvedMsg", temp);
		userObject.put("favouredMsg", temp);
		userObject.put("involvedCmt", temp);
		userObject.put("hisInvolvedCmt", temp);

		System.out.println(userMsgCollection.save(userObject).getN());
	}

	/**
	 * 判断用户username的记录是否存在 db.ob_usermsg.find({"_id":"1001"})
	 * 
	 * @param username
	 * @return
	 */
	private boolean isExist(String username) {
		DBObject userInfo = new BasicDBObject();
		userInfo.put("_id", username);

		if (userMsgCollection.find(userInfo).toArray().size() == 0) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) throws Exception {
		MUserMsgService userMsgService = new MUserMsgService();
		// System.out.println(userMsgService.isExist("1001"));

		// 测试获取用户自己发的微博消息,其他方法都一样
		// System.out.println(userMsgService.getHisInvolvedCmt("1001"));

		// 测试更新未读的@自己的消息
		userMsgService.updateHisInvolvedCmt("1001", "fghjiuytreeewqioopdsacv");
	}
}
