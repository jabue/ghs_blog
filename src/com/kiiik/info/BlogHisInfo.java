package com.kiiik.info;

import java.sql.Timestamp;

/**
 * 某用户被@的微博消息封装类
 * 
 * @author YCL
 * 
 */
public class BlogHisInfo {
	// 被@用户名
	private String username;
	// 消息ID
	private int messageID;
	// 评论ID
	private int commentID;
	// 是否已读标记
	private int readSign;
	// 消息时间
	private Timestamp messageTime;

	public BlogHisInfo(String username, int messageID, int commentID,
			int readSign, Timestamp messageTime) {
		super();
		this.username = username;
		this.messageID = messageID;
		this.commentID = commentID;
		this.readSign = readSign;
		this.messageTime = messageTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getReadSign() {
		return readSign;
	}

	public void setReadSign(int readSign) {
		this.readSign = readSign;
	}

	public Timestamp getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Timestamp messageTime) {
		this.messageTime = messageTime;
	}

}
