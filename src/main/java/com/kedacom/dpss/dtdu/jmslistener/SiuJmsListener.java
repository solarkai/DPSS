/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: SiuJmsListener.java
* 包名: com.kedacom.dpss.dtdu.jmslistener 
* 描述: 订阅来自SIU模块的布控信息 
* 作者:zhangkai 
* 创建日期:2013-12-13  
* 修改人:zhangkai   
* 修改日期: 2013-12-13
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.jmslistener;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.util.AbsTopicListener;

/**
 * @author zhangkai
 *
 */
@Scope("singleton")
@Service("siuJmsListener")
public class SiuJmsListener extends AbsTopicListener {

	private static final KedaLogger log = KedaLogger.getLogger(SiuJmsListener.class);
	
	/**
	 * <p>Title: handleReceiveMessage</p> 
	 * <p>Description: </p> 
	 * @param message 
	 * @see com.kedacom.dpss.dtdu.util.AbsTopicListener#handleReceiveMessage(javax.jms.Message) 
	 */
	@Override
	public void handleReceiveMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			log.info(((TextMessage) message).getText());
		} catch (JMSException je)
		{

		}
	}

}
