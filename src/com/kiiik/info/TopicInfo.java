package com.kiiik.info;

/**
 * 话题信息封装类
 * 
 * @author YCL
 * 
 */
public class TopicInfo {
	// 话题ID
	private String topicID = "";
	// 话题名称
	private String createTime = "";
	// 话题创建人
	private String createUser = "";
	// 参与讨论人数
	private int involveNum = 0;
	
	public TopicInfo(){
		
	}
	
	public TopicInfo(String topicID, String createTime, String createUser,
			int involveNum) {
		super();
		this.topicID = topicID;
		this.createTime = createTime;
		this.createUser = createUser;
		this.involveNum = involveNum;
	}

	public String getTopicID() {
		return topicID;
	}

	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public int getInvolveNum() {
		return involveNum;
	}

	public void setInvolveNum(int involveNum) {
		this.involveNum = involveNum;
	}

	
}
