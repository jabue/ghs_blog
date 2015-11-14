package com.kiiik.client;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 消息解析类（主要针对XML）
 * 
 * @author YCL
 * 
 */
public class XmlParser {

	public XmlParser() {

	}

	/**
	 * 解析收到的字符串，并封装成Message <message id=""><query cmd="" user=""><body
	 * topicname=""></query></message>
	 * 
	 * @param contend
	 * @return
	 */
	public Message parseToMessage(String contend) {
		Message message = null;
		try {
			// 转换String为xml，若不可以则抛出异常
			Document document = DocumentHelper.parseText(contend);
			Element rootElement = document.getRootElement();
			// 消息开头不正确
			if (!rootElement.getName().equalsIgnoreCase("message")) {
				throw new Exception();
			}
			// 没有query节点
			if (rootElement.element("query") == null) {
				throw new Exception();
			}
			message = new Message(document);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			message = Message.createErrorMessage(Message.XML_FORMAT_ERROR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = Message.createErrorMessage(Message.XML_FORMAT_ERROR);
		}
		return message;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		XmlParser pp = new XmlParser();
		String msg = "<ni id=\"error\"><query cmd=\"blog\"><body topicname=\"nihao\"/></query></ni>";
		Message message = pp.parseToMessage(msg);
		System.out.println(message.getElement().asXML());
	}
}
