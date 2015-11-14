package com.kiiik.client;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 消息封装类 <message id=""><query cmd="" user=""><body topicname="" page=""
 * capacity=""></query></message>
 * 
 * @author YCL
 * 
 */
public class Message {
	// 错的xml格式
	static final String XML_FORMAT_ERROR = "wrong-xml-format";
	// 消息处理器不存在
	static final String NO_MESSAGE_HANDLER = "no-message-handler";
	// 用户未登录，没有权限
	static final String USER_NO_AUTHORITY = "user_no_authority";
	// 格式
	static final String ERROR = "error";

	private Document document = null;
	private Element element = null;

	public Message(Document document) {
		this.document = document;
		this.element = document.getRootElement();
	}

	public Message(Element element) {
		this.element = element;
	}

	/**
	 * 获取Message包中xml内容
	 * 
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * 获取Message包中xml内容
	 * 
	 * @return
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * 获取消息的id
	 * 
	 * @return
	 */
	public String getId() {
		String id = element.attributeValue("id");
		return id;
	}

	/**
	 * 获取Message的body节点
	 * 
	 * @return
	 */
	public Element getBody() {
		return element.element("query").element("body");
	}

	/**
	 * 获取消息的命令
	 * 
	 * @return
	 */
	public String getQueryCmd() {
		String cmd = element.element("query").attributeValue("cmd");
		return cmd;
	}
	
	/**
	 * 获取消息的用户
	 * 
	 * @return
	 */
	public String getUserName() {
		String cmd = element.element("query").attributeValue("user");
		return cmd;
	}

	/**
	 * 获取Body节点中的属性值
	 * 
	 * @param attribute
	 * @return
	 */
	public String getBodyValue(String attribute) {
		String value = element.element("query").element("body")
				.attributeValue(attribute);
		return value;
	}

	/**
	 * 根据错误类型生成消息包
	 * 
	 * @param type
	 * @return
	 */
	public static Message createErrorMessage(String type) {
		Document document = DocumentHelper.createDocument();
		Element iqe = document.addElement("message");
		iqe.addAttribute("id", ERROR);
		iqe.addAttribute("reason", type);
		Message message = new Message(document);
		return message;
	}

	/**
	 * 打印字符串消息
	 * 
	 * @param message
	 * @return
	 */
	public String toString() {
		return element.asXML();
	}

	public static void main(String[] args) {
		Message message = Message.createErrorMessage(Message.XML_FORMAT_ERROR);
		System.out.println(message.getElement().asXML());
	}
}
