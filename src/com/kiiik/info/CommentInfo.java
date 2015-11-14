package com.kiiik.info;

import java.sql.Timestamp;

/**
 * 微博评论封装类
 * 
 * @author YCL
 * 
 */
public class CommentInfo {
	// 评论ID
	private int commentID;
	// 微博消息ID
	private int messageID;
	// 评论内容
	private String message;
	// 评论者（评论此消息的人）
	private String username;
	// 被评论者（评论消息中被评论的人）
	private String commentedUser;
	// 评论创建时间
	private Timestamp createTime;

	public CommentInfo(int commentID, int messageID, String message,
			String username, String commentedUser, Timestamp createTime) {
		super();
		this.commentID = commentID;
		this.messageID = messageID;
		this.message = message;
		this.username = username;
		this.commentedUser = commentedUser;
		this.createTime = createTime;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommentedUser() {
		return commentedUser;
	}

	public void setCommentedUser(String commentedUser) {
		this.commentedUser = commentedUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
