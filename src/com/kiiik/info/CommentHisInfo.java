package com.kiiik.info;

import java.sql.Timestamp;

/**
 * 离线用户的微博评论消息封装类
 * 
 * @author YCL
 * 
 */
public class CommentHisInfo {
	// 用户名（被评论人的用户名）
	private String userame;
	// 评论ID
	private int commentID;
	// 是否读标记
	private int readSign = 0;
	// 评论时间
	private Timestamp commentTime;

	public CommentHisInfo(String userame, int commentID, int readSign,
			Timestamp commentTime) {
		super();
		this.userame = userame;
		this.commentID = commentID;
		this.readSign = readSign;
		this.commentTime = commentTime;
	}

	public String getUserame() {
		return userame;
	}

	public void setUserame(String userame) {
		this.userame = userame;
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

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

}
