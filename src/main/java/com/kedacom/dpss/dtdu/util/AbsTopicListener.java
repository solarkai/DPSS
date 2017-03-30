/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: AbsTopicListener.java
 * 包名: com.kedacom.dpss.dtdu.util 
 * 描述: TODO 
 * 作者:zhangkai 
 * 创建日期:2013-12-12  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-12
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.util;

import javax.jms.Message;
import javax.jms.TopicConnection;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ExceptionListener;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.util.JmsSubPubUtil;

/**
 * @author zhangkai
 * 
 */
public abstract class AbsTopicListener implements MessageListener, ExceptionListener {

	private static final KedaLogger log = KedaLogger.getLogger(AbsTopicListener.class);

	private static final int CONN_RESET_INTERVAL = 60 * 1000; // 出现异常重连的间隔时间，为一分钟。

	private String listenTopic = null;

	private TopicConnection conn = null;

	/**
	 * 抽象函数，处理接收的JMS消息，由继承类实现
	 * 
	 * @Title: handleReceiveMessage
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	public abstract void handleReceiveMessage(Message message);

	/**
	 * 开始监听
	 * 
	 * @Title: startListen
	 * @Description: TODO
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean startListen()
	{
		boolean startup = true;
		try {
			conn = JmsSubPubUtil.getJmsConnection();
			JmsSubPubUtil.subscriberTopic(this, listenTopic, conn);
			conn.setExceptionListener(this);
			conn.start();
		} catch (JMSException je)
		{
			log.error("start jms listen " + listenTopic + " fail", je);
			try {
				if (null != conn)
				{
					conn.close();
				}
			} catch (JMSException je1)
			{
				log.error("close jms listener " + listenTopic + " fail", je1);
			}
			startup = false;
		}
		return startup;
	}

	/**
	 * 处理消息的工作函数
	 * <p>
	 * Title: onMessage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param message
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		handleReceiveMessage(message);
	}

	/**
	 * 出现异常后，需要断掉连接后重连
	 * <p>
	 * Title: onException
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param exception
	 * @see javax.jms.ExceptionListener#onException(javax.jms.JMSException)
	 */
	public void onException(JMSException exception) {
		// TODO Auto-generated method stub
		log.error(exception);
		try {
			if (null != conn)
			{
				conn.close();
			}

		} catch (JMSException je)
		{
			log.error(this.getClass().getName() + " close conn fail", je);
		}

		while (!startListen())
		{
			try {
				Thread.sleep(CONN_RESET_INTERVAL);
			} catch (InterruptedException ie)
			{
				log.error(this.getClass().getName() + " interrupted ", ie);
			}
		}
		
	}

	public String getListenTopic() {
		return listenTopic;
	}

	public void setListenTopic(String listenTopic) {
		this.listenTopic = listenTopic;
	}



		
}
