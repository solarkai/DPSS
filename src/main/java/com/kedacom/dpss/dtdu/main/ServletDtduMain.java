/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: ServletDtduMain.java
* 包名: com.kedacom.dpss.dtdu.main 
* 描述: DTDU入口servlet
* 作者:zhangkai 
* 创建日期:2013-12-6  
* 修改人:zhangkai   
* 修改日期: 2013-12-6
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.main;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext; 
import  org.springframework.beans.BeansException;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.util.DtduSingletonFactory; 
import com.kedacom.dpss.dtdu.task.AbsDtduTask;
import com.kedacom.dpss.dtdu.exception.DtduException;

/**
 * Servlet implementation class ServletDtduMain
 */
public class ServletDtduMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final KedaLogger log = KedaLogger.getLogger(ServletDtduMain.class);
	
	//DTDU启动时的任务BEAN，从容器中生成。数组中的顺序即为启动时的顺序
	private static final String[] TASK_BEAN = {
		//"listenJmsTask",
		"listenClientTask",
		//"registerTask",
		"transObjDBPersistTask"};
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDtduMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	/**
	* <p>Title: init</p> 
	* <p>DTDU初始启动servlet</p> 
	* @param config
	* @throws ServletException 
	* @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		log.info("DTDU main start!");
		DtduSingletonFactory.setDtduServletContext(this.getServletContext());
		WebApplicationContext wactx = DtduSingletonFactory.getMvcWebApplicationContextIns();
		log.info("launching dtdu task!");
		launchDtduTask(wactx);
		log.info("all dtdu task launched!");
	}
	
	/**
	 * 从TASK_BEAN数组中依次加载DTDU的任务
	* @Title: launchDtduTask 
	* @Description: TODO 
	* @param @param ctx    
	* @return void
	* @throws
	 */
	private void launchDtduTask(WebApplicationContext ctx)
	{
		try{
			for(String beanname:TASK_BEAN)
			{
				AbsDtduTask task = (AbsDtduTask)ctx.getBean(beanname);
				DtduSingletonFactory.getDtduTaskMapIns().put(beanname, task);
				task.startTask();
			}
		}catch(BeansException e)
		{
			log.error("get task bean fail",e);
			//System.exit(0);
		}catch(DtduException de)
		{
			log.error("start dtdu task fail",de);
			//System.exit(0);
		}
	}

}
