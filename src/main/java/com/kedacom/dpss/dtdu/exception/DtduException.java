/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: TaskException.java
* 包名: com.kedacom.dpss.dtdu.exception 
* 描述: TODO 
* 作者:zhangkai 
* 创建日期:2013-12-5  
* 修改人:zhangkai   
* 修改日期: 2013-12-5
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.exception;

import kedanet.log.KedaLogger; 
/**
 * @author zhangkai
 *
 */
public class DtduException extends Exception{
	
	private static final long serialVersionUID = -7312763127104755745L;
	private static final KedaLogger log = KedaLogger.getLogger(DtduException.class);
	
	public DtduException(String cause)
	{
		super(cause);
	}
	
	public DtduException(Exception e)
	{
		super(e);
	}
	
//	public void writeLog()
//	{
//		if(null != originalException)
//		{
//			log.error(originalException.getCause(),originalException);
//			return;
//		}
//		log.error(getCause(), this);
//	}

}
