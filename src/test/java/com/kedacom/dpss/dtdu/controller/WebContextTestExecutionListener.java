/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: WebContextTestExecutionListener.java
 * 包名: com.kedacom.dpss.dtdu.controller 
 * 描述: web测试上下文基础类 
 * 作者:zhangkai 
 * 创建日期:2013-12-16  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-16
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.controller;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * @author zhangkai
 * 
 */
public class WebContextTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {

		if (testContext.getApplicationContext() instanceof GenericApplicationContext) {
			GenericApplicationContext context = (GenericApplicationContext) testContext.getApplicationContext();
			ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
			Scope requestScope = new SimpleThreadScope();
			beanFactory.registerScope("request", requestScope);
			Scope sessionScope = new SimpleThreadScope();
			beanFactory.registerScope("session", sessionScope);
		}
	}

}
