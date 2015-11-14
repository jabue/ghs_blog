package com.kiiik.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.kiiik.info.BlogInfo;
import com.kiiik.info.TopicInfo;
import com.kiiik.monogo.MongoDBGlobals;
import com.kiiik.monogo.MongoDBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 面向对象：微博话题； Collection名称：ob_topics； 存储信息说明：用来存储话题相关信息；
 * 
 * @author YCL
 * 
 */
public class MTopicInfoService {

	private DB mongoDb = null;
	private DBCollection topicCollection = null;
	private static MTopicInfoService instance = null;

	/**
	 * 单例模式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MTopicInfoService getInstance() throws Exception {
		if (instance == null) {
			instance = new MTopicInfoService();
		}
		return instance;
	}

	public MTopicInfoService() throws Exception {
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
		topicCollection = mongoDb.getCollection(MongoDBGlobals
				.getTopicTableName());
	}

	/**
	 * 获取MongoDB中话题存储Collection
	 * 
	 * @return
	 * @throws Exception
	 */
	public DBCollection getMUserFanInfoTable() throws Exception {
		if (topicCollection == null) {
			if (mongoDb == null) {
				init();
			}
			topicCollection = mongoDb.getCollection(MongoDBGlobals
					.getBlogInfoTableName());
		}
		return topicCollection;
	}

	/**
	 * 存储消息至MongoDB
	 * db.ob_messages.insert({"_id":ObjectId("45wefge85ef45sef"),"topicID"
	 * :"","username":"1001",...,})
	 * 
	 * @param blogInfo
	 */
	public void updateTopicInfo(BlogInfo blogInfo) {
		String topicName = blogInfo.getTopicID();
		// 如果不存在，先创建一条
		if (!isExist(topicName)) {
			createInfo(blogInfo);
			return;
		}
		// 首先获取到话题微博列表
		ArrayList<String> messages = getMessages(topicName);
		messages.add(blogInfo.getMessageID());

		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", topicName);
		// 封装更新数据
		DBObject updateValue = new BasicDBObject();
		updateValue.put("messages", messages);

		DBObject updateSetValue = new BasicDBObject("$set", updateValue);

		topicCollection.findAndModify(selectCondition, updateSetValue);
	}

	/**
	 * 判断用户username的记录是否存在 db.ob_topics.find({"_id":"1001"})
	 * 
	 * @param username
	 * @return
	 */
	private boolean isExist(String topicName) {
		DBObject userInfo = new BasicDBObject();
		userInfo.put("_id", topicName);

		if (topicCollection.find(userInfo).toArray().size() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * 创建第一条数据
	 * 
	 * @param username
	 */
	private void createInfo(BlogInfo blogInfo) {
		// TODO Auto-generated method stub
		// 封装写库用的用户数据
		DBObject userObject = new BasicDBObject();
		userObject.put("_id", blogInfo.getTopicID());
		// 记录跟该话题相关的微博消息
		ArrayList<String> messages = new ArrayList<String>();
		messages.add(blogInfo.getMessageID());
		// 记录话题的粉丝：暂时不做任何处理
		ArrayList<String> fans = new ArrayList<String>();

		userObject.put("createUser", blogInfo.getUsername());
		userObject.put("createTime", blogInfo.getCreateTime());
		// 参与讨论人数，先不做记录
		userObject.put("involveNum", 0);
		userObject.put("fans", fans);
		userObject.put("messages", messages);

		System.out.println(topicCollection.save(userObject).getN());
	}

	/**
	 * 获取某用户username自己已发微博信息列表
	 * db.ob_usermsg.find({"_id":"1001"},{"_id":0,"userMsg":1})
	 * 
	 * @param username
	 * @return
	 */
	public ArrayList<String> getMessages(String topicName) {

		ArrayList<String> messages = null;
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", topicName);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("messages", true);

		DBObject temp = topicCollection.find(selectCondition, selectField)
				.one();
		messages = (ArrayList<String>) temp.get("messages");

		return messages;
	}

	/**
	 * 获取微博的基本信息
	 * 
	 * @param topicName
	 * @return
	 */
	public TopicInfo getTopicInfo(String topicName) {
		TopicInfo topicInfo = new TopicInfo();
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", topicName);
		return topicInfo;
	}

	public static void main(String[] Args) throws Exception {
		MTopicInfoService g = new MTopicInfoService();

		BlogInfo blogInfo = new BlogInfo();
		blogInfo.setTopicID("大商所");
		blogInfo.setUsername("1002");
		blogInfo.setMessageID("gljoijapaklalkaaklak");
		Date date = new Date();
		blogInfo.setCreateTime(date.toString());
		g.updateTopicInfo(blogInfo);
	}
}
