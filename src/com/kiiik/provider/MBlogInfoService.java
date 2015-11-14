package com.kiiik.provider;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.kiiik.info.BlogInfo;
import com.kiiik.monogo.MongoDBGlobals;
import com.kiiik.monogo.MongoDBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 面向对象：微博（message）. 功能：微博的信息服务类. 对应Collection:ob_messages.
 * 字段：(_id:消息ID;topicID:话题ID;username:发消息用户名;message:消息内容;createTime:
 * 发消息时间;commentsNum:评论数量;quoteNum:转发数量;favourNum:赞数;fromID:消息的ID）
 * 说明：userFans及userStars字段类型均为HashMap<String,Integer>. String：用户名;
 * Integer:是否相互关注(0,未相互关注;1,相互关注).
 * 
 * @author YCL
 * 
 */
public class MBlogInfoService {

	private DB mongoDb = null;
	private DBCollection blogInfoCollection = null;
	private static MBlogInfoService instance = null;

	/**
	 * 单例模式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MBlogInfoService getInstance() throws Exception {
		if (instance == null) {
			instance = new MBlogInfoService();
		}
		return instance;
	}

	public MBlogInfoService() throws Exception {
		init();
	}

	/**
	 * 初始化服务
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		mongoDb = MongoDBManager.getBlogDB();
		blogInfoCollection = mongoDb.getCollection(MongoDBGlobals
				.getBlogInfoTableName());
	}

	/**
	 * 获取到MongoDB中微博信息存储Collection
	 * 
	 * @return
	 * @throws Exception
	 */
	public DBCollection getMBlogInfoTable() throws Exception {
		if (blogInfoCollection == null) {
			if (mongoDb == null) {
				init();
			}
			blogInfoCollection = mongoDb.getCollection(MongoDBGlobals
					.getBlogInfoTableName());
		}
		return blogInfoCollection;
	}

	/**
	 * 存储微博消息至MongoDB
	 * db.ob_messages.insert({"_id":ObjectId("45wefge85ef45sef"),"topicID"
	 * :"","username":"1001",...,})
	 * 
	 * @param blogInfo
	 */
	public String saveBlogInfotoMDB(BlogInfo blogInfo) {

		DBObject blogMessage = new BasicDBObject();
		ObjectId messageID = new ObjectId();

		blogMessage.put("_id", messageID);
		blogMessage.put("topicID", blogInfo.getTopicID());
		blogMessage.put("username", blogInfo.getUsername());
		blogMessage.put("message", blogInfo.getMessage());
		blogMessage.put("createTime", blogInfo.getCreateTime().toString());
		blogMessage.put("commentsNum", blogInfo.getCommentsNum());
		blogMessage.put("favourNum", blogInfo.getFavourNum());
		blogMessage.put("quoteNum", blogInfo.getQuoteNum());
		blogMessage.put("fromID", blogInfo.getFromID());
		blogMessage.put("picture", blogInfo.getPicture());

		if (blogInfoCollection.insert(blogMessage).getN() != 0) {
			return "fail";
		}

		return messageID.toString();
	}

	/**
	 * 根据客户端传过来的页数和每页微博数量查询微博内容
	 * 
	 * @param page
	 * @param capacity
	 * @return
	 */
	public ArrayList<BlogInfo> getBlogInfoFromMDB(int page, int capacity) {
		ArrayList<BlogInfo> resultList = new ArrayList<BlogInfo>();
		// 根据页数及文件大小读取微博数据
		List<DBObject> temp = blogInfoCollection.find().skip(page * capacity)
				.limit(capacity).sort(new BasicDBObject("createTime", -1))
				.toArray();
		for (DBObject dbobject : temp) {
			BlogInfo blogInfo = new BlogInfo();
			blogInfo.setMessageID(dbobject.get("_id").toString());
			blogInfo.setUsername(dbobject.get("username").toString());
			blogInfo.setMessage(dbobject.get("message").toString());
			blogInfo.setCreateTime(dbobject.get("createTime").toString());
			blogInfo.setCommentsNum(dbobject.get("commentsNum").toString());
			blogInfo.setFavourNum(dbobject.get("favourNum").toString());
			blogInfo.setQuoteNum(dbobject.get("quoteNum").toString());
			blogInfo.setFromID(dbobject.get("fromID").toString());
			blogInfo.setPicture(dbobject.get("picture").toString());
			resultList.add(blogInfo);
		}

		return resultList;
	}

	/**
	 * 根据客户端传过来的页数和每页微博数量查询微博内容
	 * 
	 * @param page
	 * @param capacity
	 * @return
	 */
	public ArrayList<BlogInfo> getTopicBlogInfoFromMDB(String[] topics, int capacity) {
		ArrayList<BlogInfo> resultList = new ArrayList<BlogInfo>();
		int size = topics.length;
		// 创建查询条件
		DBObject topicIn = new BasicDBObject();
		Object[] topicsList = new Object[size];
		for (int i = 0; i < size; i++) {
			topicsList[i] = topics[i];
		}
		topicIn.put("$in", topicsList);

		DBObject selectTopicIn = new BasicDBObject();
		selectTopicIn.put("topicID", topicIn);

		// 根据页数及文件大小读取微博数据
		List<DBObject> temp = blogInfoCollection.find(selectTopicIn).limit(capacity)
				.sort(new BasicDBObject("createTime", -1)).toArray();
		for (DBObject dbobject : temp) {
			BlogInfo blogInfo = new BlogInfo();
			blogInfo.setMessageID(dbobject.get("_id").toString());
			blogInfo.setTopicID(dbobject.get("topicID").toString());
			blogInfo.setUsername(dbobject.get("username").toString());
			blogInfo.setMessage(dbobject.get("message").toString());
			blogInfo.setCreateTime(dbobject.get("createTime").toString());
			blogInfo.setCommentsNum(dbobject.get("commentsNum").toString());
			blogInfo.setFavourNum(dbobject.get("favourNum").toString());
			blogInfo.setQuoteNum(dbobject.get("quoteNum").toString());
			blogInfo.setFromID(dbobject.get("fromID").toString());
			blogInfo.setPicture(dbobject.get("picture").toString());
			resultList.add(blogInfo);
		}

		return resultList;
	}

	/**
	 * 根据消息ID查询消息内容
	 * db.ob_messages.find({"_id":"1001"},{"_id":0,"username":1,"message":1})
	 * 
	 * @param messageID
	 * @return
	 */
	public BlogInfo getBlogInfo(String messageID) {
		BlogInfo blogInfo = new BlogInfo();
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		ObjectId messageid = new ObjectId(messageID);
		selectCondition.put("_id", messageid);
		// 封装需要显示的字段
		// DBObject selectField = new BasicDBObject();
		// selectField.put("_id", false);
		// selectField.put("username", true);
		// selectField.put("message", true);
		// selectField.put("picture", true);

		DBObject dbObject = blogInfoCollection.find(selectCondition).one();
		// 封装blogInfo
		blogInfo.setMessageID(dbObject.get("_id").toString());
		blogInfo.setUsername(dbObject.get("username").toString());
		blogInfo.setMessage(dbObject.get("message").toString());
		blogInfo.setCreateTime(dbObject.get("createTime").toString());
		blogInfo.setCommentsNum(dbObject.get("commentsNum").toString());
		blogInfo.setFavourNum(dbObject.get("favourNum").toString());
		blogInfo.setQuoteNum(dbObject.get("quoteNum").toString());
		blogInfo.setFromID(dbObject.get("fromID").toString());
		blogInfo.setPicture(dbObject.get("picture").toString());
		blogInfo.setTopicID(dbObject.get("topicID").toString());
		return blogInfo;
	}

	/**
	 * 根据消息id查询消息中包含的图片
	 * 
	 * @param messageID
	 * @return
	 */
	public BlogInfo getBlogPic(String messageID) {
		BlogInfo blogInfo = new BlogInfo();
		// 创建查询条件
		DBObject selectCondition = new BasicDBObject();
		ObjectId messageid = new ObjectId(messageID);
		selectCondition.put("_id", messageid);
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("picture", true);

		DBObject dbObject = blogInfoCollection.find(selectCondition,
				selectField).one();
		// 封装blogInfo
		blogInfo.setPicture(dbObject.get("picture").toString());
		return blogInfo;
	}

	/**
	 * 更新微博信息表某_id微博消息的评论次数
	 * db.ob_messages.update({id:ObjectId("54056f6e91e980889258d909"
	 * )},{$set:{commentsNum:3}});
	 * 
	 * @param commentsNum
	 */
	public void updateCommentsNum(String _id, int commentsNum) {
		// 封装查询条件
		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("_id", new ObjectId(_id));
		// 封装要update的字段及值
		DBObject updatedValue = new BasicDBObject();
		updatedValue.put("commentsNum", commentsNum);

		DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
		blogInfoCollection.update(updateCondition, updateSetValue);
	}

	/**
	 * 更新微博信息表某_id微博消息的赞数
	 * 
	 * @param _id
	 * @param favourNum
	 */
	public void updateFavourNum(String _id, int favourNum) {
		// 封装查询条件
		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("_id", new ObjectId(_id));
		// 封装要update的字段及值
		DBObject updatedValue = new BasicDBObject();
		updatedValue.put("favourNum", favourNum);

		DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
		blogInfoCollection.update(updateCondition, updateSetValue);
	}

	/**
	 * 获取某微博的转发数量
	 * 
	 * @param _id
	 * @return
	 */
	public int getQuoteNum(String _id) {
		int temp = 0;
		// 封装查询条件
		DBObject selectCondition = new BasicDBObject();
		selectCondition.put("_id", new ObjectId(_id));
		// 封装需要显示的字段
		DBObject selectField = new BasicDBObject();
		selectField.put("_id", false);
		selectField.put("quoteNum", true);

		DBObject dbObject = blogInfoCollection.find(selectCondition,
				selectField).one();
		temp = Integer.parseInt(dbObject.get("quoteNum").toString());
		return temp;
	}

	/**
	 * 更新微博信息表某_id微博消息的转发次数
	 * 
	 * @param _id
	 * @param quoteNum
	 */
	public void updateQuoteNum(String _id) {
		// 封装查询条件
		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("_id", new ObjectId(_id));
		// 封装要update的字段及值
		DBObject updatedValue = new BasicDBObject();
		updatedValue.put("quoteNum", getQuoteNum(_id) + 1);

		DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
		blogInfoCollection.update(updateCondition, updateSetValue);
	}

	/**
	 * 关闭数据库连接
	 */
	public void closeConnection() {

	}

	/**
	 * test
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		MBlogInfoService blogInfoService = MBlogInfoService.getInstance();
		// DBCollection blogInfoCollection =
		// blogInfoService.getMBlogInfoTable();
		// for (BlogInfo bloginfo : blogInfoService.getBlogInfoFromMDB(0, 5)) {
		// System.out.println(bloginfo.getMessageID());
		// }
		
		String[] topics = new String[2];
		topics[0]="你好";
		topics[1]="你好么";
		// blogInfoService.getTopicBlogInfoFromMDB(topics, 10);
		for (BlogInfo bloginfo : blogInfoService.getTopicBlogInfoFromMDB(topics, 10)) {
			System.out.println(bloginfo.getMessageID());
		}

		/*
		 * 查找全部记录
		 */
		// List<DBObject> idInfo = blogInfoCollection.find().toArray();
		// for (DBObject temp : idInfo) {
		// System.out.println(temp.toString());
		// blogInfoCollection
		// .remove(new BasicDBObject("_id", temp.get("_id")));
		// }
		/*
		 * 查询特定_id的记录
		 */
		// List<DBObject> idInfo = blogInfoCollection.find().toArray();
		// for (DBObject temp : idInfo) {
		// System.out.println("the createtime is:" + temp.get("createTime"));
		// // new ObjectId(temp.get("_id").toString())
		// System.out.println("the Data we find:"
		// + blogInfoCollection.find(
		// new BasicDBObject("_id", temp.get("_id")))
		// .toArray());
		// }

		/*
		 * 删除某特定_id的记录
		 */
		// blogInfoCollection.remove(new BasicDBObject("_id", new
		// ObjectId("540565d391e9a872213bf53c")));

		/*
		 * 测试saveBlogInfotoMDB(BlogInfo blogInfo)
		 */
		// BlogInfo blogInfo = new BlogInfo();
		// // 自生成ID
		// ObjectId _id = new ObjectId();
		// blogInfo.setMessageID(_id.toString());
		// blogInfo.setMessage("It is her Mistake!");
		// blogInfo.setUsername("1002");
		// Date date = new Date();
		// blogInfo.setCreateTime(date);
		// blogInfoService.saveBlogInfotoMDB(blogInfo);

		/*
		 * 测试updateCommentsNum(String _id, int commentsNum)方法
		 */
		// blogInfoService.updateCommentsNum("5405b4b791e9c08a8a49fa71", 3);

		/*
		 * 测试updateFavourNum(String _id, int favourNum)方法
		 */
		// blogInfoService.updateFavourNum("5405b4b791e9c08a8a49fa71", 3);

		/*
		 * 测试updateFavourNum(String _id, int favourNum)方法
		 */
		// blogInfoService.updateQuoteNum("5405b4b791e9c08a8a49fa71", 95559);

		/*
		 * 测试updateFavourNum(String _id, int favourNum)方法
		 */
		// blogInfoService.getBlogPic("543c956ebbd560f4175d4be6");
		// blogInfoService.getBlogInfo("543c956ebbd560f4175d4be6");

		/*
		 * 测试获取前多少条数据（先根据时间排序，后获取特定消息条数）
		 */
		// System.out.println("The latest message:"
		// + blogInfoCollection.find().skip(0).limit(1)
		// .sort(new BasicDBObject("createTime", -1)).toArray());

		/*
		 * 更新特定_id记录中的某个字段
		 * db.ob_messages.update({id:ObjectId("54056f6e91e980889258d909"
		 * )},{$set:{commentsNum:3}});
		 */
		// DBObject updateCondition = new BasicDBObject();
		//
		// updateCondition.put("_id", new ObjectId("54056f6e91e980889258d909"));
		//
		// DBObject updatedValue = new BasicDBObject();
		// updatedValue.put("commentsNum", 3);
		//
		// DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
		// blogInfoCollection.update(updateCondition, updateSetValue);
	}
}
