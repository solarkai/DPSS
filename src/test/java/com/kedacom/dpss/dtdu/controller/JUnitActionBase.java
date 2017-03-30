/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: JUnitActionBase.java
 * 包名: com.kedacom.dpss.dtdu.controller 
 * 描述: Spring MVC controller测试需要继承的基类 
 * 作者:zhangkai 
 * 创建日期:2013-12-16  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-16
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.controller;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * @author zhangkai
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "file:src/test/resources/applicationContext-test.xml" })
@TestExecutionListeners({ WebContextTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })
public class JUnitActionBase extends AbstractJUnit4SpringContextTests {

	@Resource(type = RequestMappingHandlerMapping.class, name = "reqMappingMapping")
	private RequestMappingHandlerMapping reqMapping;

	@Resource(type = RequestMappingHandlerAdapter.class, name = "reqMappingAdaper")
	private RequestMappingHandlerAdapter reqAdaper;


	/**
	 * 执行函数，执行request到对应control的映射处理
	* @Title: excuteAction 
	* @Description: TODO 
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws Exception    
	* @return ModelAndView
	* @throws
	 */
	public final ModelAndView excuteAction(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING,true);
		HandlerExecutionChain chain = reqMapping.getHandler(request);
		assertNotNull(chain);
		ModelAndView model = null;
		try {
			model = reqAdaper.handle(request, response, chain.getHandler());
			//因为有的请求需要多线程处理，而JUNIT执行对应的handler之后会销毁bean工厂，而这时handler派生的工作线程还没有完成，
			//所以延迟一下,等待工作线程完成。
			Thread.sleep(1000); 
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		return model;

	}

}
