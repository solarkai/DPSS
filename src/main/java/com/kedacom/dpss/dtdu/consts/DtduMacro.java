/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: DtduMacro.java
* 包名: com.kedacom.dpss.dtdu.consts 
* 描述: DTDU中定义的常量 
* 作者:zhangkai 
* 创建日期:2013-12-5  
* 修改人:zhangkai   
* 修改日期: 2013-12-5
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.consts;

/**
 * @author zhangkai
 *
 */
public interface DtduMacro {
	
	//spring的DispatcherServlet在servletcontext中注册的属性名
	String SPRING_MVC_CONTEXT="org.springframework.web.servlet.FrameworkServlet.CONTEXT.springMvc";
	
	//配置文件名
	String CONFIG_FILENAME = "dtduCfg.properties";
	String JMS_JNDI_FILENAME = "jndi.properties";
	
	//DTDU的ID
	String DTDU_ID = "DTDU_ID";
	//该DTDU模块的别名
	String DTDU_ALIAS = "DTDU_ALIAS";
	
	
	//DTDU结构化数据处理的线程池大小
	String DTDU_DHTHREADPOOL_SIZE = "DTDU_DHTHREADPOOL_SIZE";
	//DTDU结构化数据的线程池保活时间，单位为秒
	String DTDU_DHTHREAD_KEEPALIVE = "DTDU_DHTHREADPOOL_SIZE";
	
	//DTDU的监听客户端连接的端口
	String DTDU_RT_CLIENT_LISTENPORT = "DTDU_RT_CLIENT_LISTENPORT";
	int DTDU_RTCLIENT_LISTENPORT_DEFAULT = 7777;
	
	//数据库处理相关参数,一次数据库处理的SQL条数
	int DTDU_DB_BATCH_COUNT = 500; 
    //结构化数据处理队列每次处理的积攒（节点到达一定规模再批量处理的）时间,单位为毫秒
	int DTDU_DB_QUEUE_AGGREGATION_TIME = 100;
	//结构化传输信息如果批量处理之后有异常需要隔一段时间之后对每条记录处理，出错后处理的间隔时间单位为毫秒。
	long DTDU_DB_EXCEPTION_DELAY_HANDLE_TIME = 5*60*1000; //5分钟
	
	
    //人，车，物的对象类型常量                                                                                                            
	String VEHICLE_TARGET_TYPE = "vehicle";
	String PERSON_TARGET_TYPE = "person";
	String OBJECT_TARGET_TYPE = "object";

}
