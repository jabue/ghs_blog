package com.kiiik.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

import com.kiiik.client.Message;
import com.kiiik.client.MessageSender;
import com.kiiik.info.BlogInfo;
import com.kiiik.monogo.MongoDBGlobals;
import com.kiiik.provider.MBlogInfoService;
import com.kiiik.provider.MTopicInfoService;
import com.kiiik.provider.MUserMsgService;
import com.kiiik.provider.NickNameProvider;

/**
 * 用于处理客户端的博客相关的iq消息
 * 
 * @author YCL
 * 
 */
public class BlogMessageService {

	private static BlogMessageService instance = null;
	private MBlogInfoService mBlogService = null;
	private MUserMsgService mUserMsgService = null;
	private MTopicInfoService mTopicInfoService = null;
	private NickNameProvider nickNameProvider = null;

	public BlogMessageService() throws Exception {
		if (mBlogService == null) {
			mBlogService = MBlogInfoService.getInstance();
		}
		if (mUserMsgService == null) {
			mUserMsgService = MUserMsgService.getInstance();
		}
		if (mTopicInfoService == null) {
			mTopicInfoService = MTopicInfoService.getInstance();
		}
		if (nickNameProvider == null) {
			nickNameProvider = NickNameProvider.getInstance();
		}
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static BlogMessageService getInstance() throws Exception {
		if (instance == null) {
			instance = new BlogMessageService();
		}
		return instance;
	}

	/**
	 * 看全部微博：请求所有的微博消息
	 * 
	 * @param packet
	 * @param presenceRouter
	 * @return
	 * @throws SQLException
	 */
	public void reqstAllBlogMessage(Message message, MessageSender sender)
			throws SQLException {
		int page = 0;
		int capacity = 5;

		page = Integer.parseInt(message.getBodyValue("page"));
		capacity = Integer.parseInt(message.getBodyValue("capacity"));

		// 根据查询条件获取微博信息
		ArrayList<BlogInfo> blogList = mBlogService.getBlogInfoFromMDB(page,
				capacity);
		// 封装微博消息并发送给客户端
		sendAllBlogMessage(blogList, sender);
	}

	/**
	 * 看全部微博：封装微博消息并发送给客户端
	 * 
	 * @param blogList
	 * @param packet
	 * @return
	 * @throws SQLException
	 */
	private void sendAllBlogMessage(ArrayList<BlogInfo> blogList,
			MessageSender sender) throws SQLException {
		// TODO Auto-generated method stub
		// 封装全部微博消息
		Document document = DocumentHelper.createDocument();
		Element iqe = document.addElement("presence");
		iqe.addAttribute("id", "ycl_blog_getAllMes");

		// Namespace namespace = new Namespace("",
		// "http://kiiik.openfire.com/blog");
		// Element query = iqe.addElement("query");
		// query.add(namespace);
		// 封装每条微博消息
		for (BlogInfo blogInfo : blogList) {
			// 封装微博消息
			Element mes = iqe.addElement("mes");
			mes.addAttribute("messageid", blogInfo.getMessageID());
			mes.addAttribute("topicid", blogInfo.getTopicID());
			mes.addAttribute("message", blogInfo.getMessage());
			mes.addAttribute("userjid", blogInfo.getUsername());
			mes.addAttribute("createtime", blogInfo.getCreateTime());
			mes.addAttribute("commentsnum", blogInfo.getCommentsNum());
			mes.addAttribute("favournum", blogInfo.getFavourNum());
			mes.addAttribute("fromid", blogInfo.getFromID());
			mes.addAttribute("picture", blogInfo.getPicture());
			mes.addAttribute("nickname",
					nickNameProvider.getNickname(blogInfo.getUsername()));
			// 如果不为空，则说明为转发微博；开始封装原微博消息
			if (!blogInfo.getFromID().equalsIgnoreCase("")) {
				// 获取原始微博消息
				BlogInfo temp = mBlogService.getBlogInfo(blogInfo.getFromID());
				mes.addAttribute("frommessage", temp.getMessage());
				mes.addAttribute("fromuserid", temp.getUsername());
				mes.addAttribute("frompicture", temp.getPicture());
				mes.addAttribute("fromnickname", nickNameProvider.getNickname(temp.getUsername()));
			}
			mes.addAttribute("quotenum", blogInfo.getQuoteNum());
			// 获取点赞标记
			mes.addAttribute("favourflag", "0");
			// ftp服务器信息
			mes.addAttribute("ftpaddress", MongoDBGlobals.getFtpAddress());
			mes.addAttribute("ftpport", MongoDBGlobals.getFtpPort());
			mes.addAttribute("ftpaccount", MongoDBGlobals.getFtpAccount());
			mes.addAttribute("ftppassword", MongoDBGlobals.getFtpPassword());
		}
		// 添加结束标记
		Element mes = iqe.addElement("mes");
		mes.addAttribute("lastmessage", "true");
		
		Message message = new Message(document);
		sender.sendMsg(message);
		// 相应客户端请求后断开连接
		sender.closeSession();
	}
}
