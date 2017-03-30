/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: TransPutReqHandle.java
* 包名: com.kedacom.dpss.dtdu.service 
* 描述: 接收PUT请求的处理类，主要处理二进制图片数据
* 作者:zhangkai 
* 创建日期:2013-12-9  
* 修改人:zhangkai   
* 修改日期: 2013-12-9
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import kedanet.log.KedaLogger;
/**
 * @author zhangkai
 *
 */
@Scope("request")
@Service("transPutReqHandle")
public class TransPutReqHandle extends AbsRequestHandle {

	/**
	 * <p>Title: handleRequest</p> 
	 * <p>Description: </p> 
	 * @param req 
	 * @see com.kedacom.dpss.dtdu.service.AbsRequestHandle#handleRequest(javax.servlet.http.HttpServletRequest) 
	 */
	@Override
	public void handleRequest(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

}
