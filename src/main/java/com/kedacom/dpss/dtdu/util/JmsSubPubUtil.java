/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: JmsSubPubUtil.java
 * 包名: com.kedacom.dpss.dtdu.util 
 * 描述: JMS 订阅发布方式工具类 
 * 作者:zhangkai 
 * 创建日期:2013-12-5  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-5
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.Topic;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.MessageListener;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.consts.DtduMacro;



/**
 * @author zhangkai
 * 
 */
public class JmsSubPubUtil {

	public static TopicConnectionFactory tcf = null;
	public static final KedaLogger log = KedaLogger.getLogger(JmsSubPubUtil.class);

	static {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Properties porperties = new Properties();
			porperties.load(new FileInputStream(loader.getResource(DtduMacro.JMS_JNDI_FILENAME).getPath()));
			InitialContext ctx = new InitialContext(porperties);
			tcf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
		} catch (NamingException ne)
		{
			log.error("JmsSubPubUtil init ConnectionFactory fail", ne);
		} catch (IOException ioe)
		{
			log.error("JmsSubPubUtil init ConnectionFactory fail", ioe);
		}
	}

	/**
	 * 发送jms文本消息
	 * 
	 * @Title: sendJmsTextMsg
	 * @Description: TODO
	 * @param @param msg
	 * @param @param topic
	 * @param @param conn
	 * @param @throws JMSException
	 * @return void
	 * @throws
	 */
	public static void sendJmsTextMsg(String msg, String topic, TopicConnection conn) throws JMSException
	{

		TopicSession vsession = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		Topic vtopic = vsession.createTopic(topic);
		TopicPublisher vpublisher = vsession.createPublisher(vtopic);
		TextMessage tmsg = vsession.createTextMessage();
		tmsg.setText(msg);
		vpublisher.publish(tmsg);
	}

	/**
	 * 获取JMS连接
	 * 
	 * @Title: getJmsConnection
	 * @Description: TODO
	 * @param @return
	 * @param @throws JMSException
	 * @return TopicConnection
	 * @throws
	 */
	public static TopicConnection getJmsConnection() throws JMSException
	{
		TopicConnection conn = tcf.createTopicConnection();
		conn.start();
		return conn;
	}

	/**
	 * 订购JMS消息
	 * 
	 * @Title: subscriberTopic
	 * @Description: TODO
	 * @param @param listener
	 * @param @param topic
	 * @param @param conn
	 * @param @throws JMSException
	 * @return void
	 * @throws
	 */
	public static void subscriberTopic(MessageListener listener, String topic, TopicConnection conn)
			throws JMSException
	{
		TopicSession vsession = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		Topic vtopic = vsession.createTopic(topic);
		TopicSubscriber vsubscriber = vsession.createSubscriber(vtopic, null, true);
		vsubscriber.setMessageListener(listener);
	}

}
