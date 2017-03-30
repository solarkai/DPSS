/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: PicInfoController.java
 * 包名: com.kedacom.dpss.dtdu.controller 
 * 描述: 接收图片数据的控制器
 * 作者:zhangkai 
 * 创建日期:2013-12-6  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-6
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.controller;

/**
 * @author zhangkai
 *
 */
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.service.AbsRequestHandle;

@Scope("request")
@Controller
// 来自PIU和相机的结构化数据接收控制器
public class PicInfoController {
	private static final KedaLogger log = KedaLogger.getLogger(PicInfoController.class);

	@Autowired
	@Qualifier("transPostReqHandle")
	private AbsRequestHandle postReqHandle; // 处理post请求的服务

	@Autowired
	@Qualifier("transPutReqHandle")
	private AbsRequestHandle putReqHandle; // 处理put请求的服务

	/**
	 * 处理post请求，为结构化数据
	 * @Title: picXmlPost
	 * @Description: TODO
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/pic/*/*/transData.do", method = { RequestMethod.POST })
	public ModelAndView picXmlPost(HttpServletRequest request)
	{
		log.debug(request.getRequestURL().toString()+" length:"+request.getContentLength());
		postReqHandle.handleRequest(request);
		return null;
	}

	/**
	 * 处理get请求，一般不使用，可用作功能验证的测试打印
	 * 
	 * @Title: picXmlGet
	 * @Description: TODO
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/pic/*/*/transData.do", method = { RequestMethod.GET })
	public ModelAndView picXmlGet(HttpServletRequest request)
	{
		log.info(request.getRequestURL().toString());
		printPostXmlData(request);
		return null;
	}

	/**
	 * 处理put请求，接收图片的二进制数据流
	 * 
	 * @Title: picXmlPut
	 * @Description: TODO
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/pic/*/*/transData.do", method = { RequestMethod.PUT })
	public ModelAndView picXmlPut(HttpServletRequest request)
	{
		log.info(request.getRequestURL().toString());
		putReqHandle.handleRequest(request);
		return null;
	}

	/**
	 * 打印测试函数
	 * 
	 * @Title: printPostXmlData
	 * @Description: TODO
	 * @param @param request
	 * @return void
	 * @throws
	 */
	private void printPostXmlData(HttpServletRequest request)
	{
		try {
			while (true)
			{
				request.setCharacterEncoding("utf-8");
				String line = request.getReader().readLine();

				if (null == line)
				{
					break;
				}
				log.info(line);
			}
		} catch (IOException ioe)
		{
			log.error("picXmlPost fail", ioe);
		}
	}

}
