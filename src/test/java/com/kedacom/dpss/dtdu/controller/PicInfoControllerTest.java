/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: PicInfoControllerTest.java
 * 包名: com.kedacom.dpss.dtdu.controller 
 * 描述: TODO 
 * 作者:zhangkai 
 * 创建日期:2013-12-16  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-16
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.controller;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.web.servlet.ModelAndView;

import  com.kedacom.dpss.dtdu.pojo.transport.Target;

/**
 * @author zhangkai
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "file:src/main/webapp/WEB-INF/springMvc-servlet.xml"})
@TestExecutionListeners({ WebContextTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })
public class PicInfoControllerTest extends JUnitActionBase {
	
	private static JAXBContext jc;
	private static Marshaller ms;

	/**
	 * Test method for
	 * {@link com.kedacom.dpss.dtdu.controller.PicInfoController#picXmlPost(javax.servlet.http.HttpServletRequest)}
	 * .
	 */
	@Test
	public void testPicXmlPost() {
		// fail("Not yet implemented");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		try {
			jc = JAXBContext.newInstance("com.kedacom.dpss.dtdu.pojo.transport");
			ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Target tg = TestObjectInsFactory.getVehicleObjIns();
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ms.marshal(tg, bo);
			
			request.setServletPath("/pic/cam1/kk2/transData.do");
			request.setMethod("POST");
			request.setCharacterEncoding("utf-8");
			request.setContent(bo.toByteArray());
			
			final ModelAndView mav = this.excuteAction(request, response);
		} catch (Exception e)
		{
			assertTrue(false);
			e.printStackTrace();
		}
	}

}
