package com.kiiik.info;

import java.util.Date;

/**
 * 微博消息封装类 写数据到MongoDB时，数据库会默认生成一个_id（Stirng） 因此fromID为String类型
 * 
 * @author YCL
 * 
 */
public class BlogInfo {
	// 微博消息ID
	private String messageID;
	// 话题ID
	private String topicID = "";
	// 用户名
	private String username = "";
	// 微博消息内容
	private String message = "";
	// 微博图片信息
	private String picture = "0";
	// 微博发布时间
	private String createTime;
	// 评论数量
	private String commentsNum = "0";
	// 赞数
	private String favourNum = "0";
	// 转发源（messageID）
	private String fromID = "";
	// 转发量
	private String quoteNum = "0";

	public BlogInfo() {

	}
	
	public BlogInfo(String messageID, String topicID, String username,
			String message, String picture, String createTime,
			String commentsNum, String favourNum, String fromID, String quoteNum) {
		super();
		this.messageID = messageID;
		this.topicID = topicID;
		this.username = username;
		this.message = message;
		this.picture = picture;
		this.createTime = createTime;
		this.commentsNum = commentsNum;
		this.favourNum = favourNum;
		this.fromID = fromID;
		this.quoteNum = quoteNum;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTopicID() {
		return topicID;
	}

	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(String commentsNum) {
		this.commentsNum = commentsNum;
	}

	public String getFavourNum() {
		return favourNum;
	}

	public void setFavourNum(String favourNum) {
		this.favourNum = favourNum;
	}

	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(String quoteNum) {
		this.quoteNum = quoteNum;
	}
}
