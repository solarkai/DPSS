/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: TransPostReqHandle.java
 * 包名: com.kedacom.dpss.dtdu.service 
 * 描述: 处理传输的结构化信息 
 * 作者:zhangkai 
 * 创建日期:2013-12-6  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-6
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;
import com.kedacom.dpss.dtdu.util.DtduCommonUtil;

/**
 * @author zhangkai
 * 
 */
@Scope("request")
@Service("transPostReqHandle")
public class TransPostReqHandle extends AbsRequestHandle {

	private static final KedaLogger log = KedaLogger.getLogger(TransPostReqHandle.class);

	@Autowired
	@Qualifier("postRunnableTask")
	private PostRunnableTask requstTask;//应该在每个请求来时重新生成一个实例

	/**
	 * <p>
	 * Title: handleRequest
	 * </p>
	 * <p>
	 * 重写handleRequest方法
	 * </p>
	 * 
	 * @see com.kedacom.dpss.dtdu.service.AbsRequestHandle#handleRequest()
	 */
	@Override
	public void handleRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (null == request)
		{
			log.error("TransPostReqHandle get Request null !");
			return;
		}

		//获取请求参数，放入线程池中运行
		try {
			requstTask.setUrl(request.getRequestURL().toString());
			requstTask.setContent_type(request.getHeader("Content-Type"));
			requstTask.setReqbody(DtduCommonUtil.InputStream2Byte(request.getInputStream()));
		} catch (IOException ioe)
		{
			log.error("handleRequest fail", ioe);
		}
		DtduSingletonFactory.getDHThreadPoolIns().execute(requstTask);
	}

}
