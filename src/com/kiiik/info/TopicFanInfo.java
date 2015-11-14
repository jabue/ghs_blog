package com.kiiik.info;

/**
 * 话题粉丝
 * 
 * @author YCL
 * 
 */
public class TopicFanInfo {
	// 话题ID
	private int topicID;
	// 话题粉丝
	private String username;

	public TopicFanInfo(int topicID, String username) {
		super();
		this.topicID = topicID;
		this.username = username;
	}

	public int getTopicID() {
		return topicID;
	}

	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
