/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: RTClientConnector.java
* 包名: com.kedacom.dpss.dtdu.rtclient 
* 描述: 处理客户端实时订阅请求的处理类
* 作者:zhangkai 
* 创建日期:2013-12-13  
* 修改人:zhangkai   
* 修改日期: 2013-12-13
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.rtclient;

import java.nio.channels.SocketChannel; 

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.util.DSocketConnector;

/**
 * @author zhangkai
 *
 */
public class RTClientConnector extends DSocketConnector {

	private static final KedaLogger log =  KedaLogger.getLogger(KedaLogger.class);
	
	/**
	 * <p>Title: readData</p> 
	 * <p>Description:实现对客户端订阅请求的处理 </p>  
	 * @see com.kedacom.dpss.dtdu.util.DSocketConnector#readData() 
	 */
	@Override
	public void readData() {
		// TODO Auto-generated method stub
		SocketChannel sc = this.getChannel();
		//增加对读入数据的处理，数据来自连接DTDU的实时客户端，主要为发送订阅请求。
		//可将订阅的客户端信息和订阅请求发送到注册到一个全局MAP中，供接收数据线程访问

	}

}
