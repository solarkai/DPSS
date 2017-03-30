/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: DcmuJmsListener.java
 * 包名: com.kedacom.dpss.dtdu.jmslistener 
 * 描述: 处理来自系统管理模块的消息，主要是注册请求回应等
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
@Service("smuJmsListener")
public class SmuJmsListener extends AbsTopicListener {

	private static final KedaLogger log = KedaLogger.getLogger(SmuJmsListener.class);

	/**
	 * <p>
	 * Title: handleReceiveMessage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
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
