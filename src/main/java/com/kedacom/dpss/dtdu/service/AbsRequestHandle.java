/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: AbsRequestHandle.java
* 包名: com.kedacom.dpss.dtdu.service 
* 描述: 对HttpServlet的处理类 
* 作者:zhangkai 
* 创建日期:2013-12-6  
* 修改人:zhangkai   
* 修改日期: 2013-12-6
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.service;

import javax.servlet.http.HttpServletRequest;

import kedanet.log.KedaLogger;

/**
 * @author zhangkai
 *
 */
public abstract class AbsRequestHandle {
	
	private static final KedaLogger log = KedaLogger.getLogger(KedaLogger.class);
	
	//抽象方法，处理具体请求
	public abstract void handleRequest(HttpServletRequest req);

}
