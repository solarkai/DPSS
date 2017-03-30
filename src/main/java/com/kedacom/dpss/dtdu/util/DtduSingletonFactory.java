/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: DtduInstanceFactory.java
 * 包名: com.kedacom.dpss.dtdu.util 
 * 描述: dtdu使用的工厂，返回DTDU所需要的单子实例 
 * 作者:zhangkai 
 * 创建日期:2013-12-5  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-5
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.util;

import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.consts.DtduMacro;
import com.kedacom.dpss.dtdu.dao.ObjectInfoDaoImpl;
import com.kedacom.dpss.dtdu.dao.PersonInfoDaoImpl;
import com.kedacom.dpss.dtdu.dao.VehicleInfoDaoImpl;
import com.kedacom.dpss.dtdu.task.AbsDtduTask;
import com.kedacom.dpss.dtdu.pojo.transport.Target;

/**
 * @author zhangkai
 * 
 */
public class DtduSingletonFactory {

	private static final KedaLogger log = KedaLogger.getLogger(DtduSingletonFactory.class);

	private static ServletContext sc = null; // web应用的全局上下文
	private static Properties porperties = null; // 配置对象
	private static ThreadPoolExecutor dataHandlerThreadpool = null; // 结构化数据处理线程池
	private static ThreadPoolExecutor dbHandlerThreadpool = null; // 持久结构化数据线程池
	private static WebApplicationContext webappCxt = null; // spring
															// mvc所使用的容器上下文
	private static HashMap<String, AbsDtduTask> dtduTaskMap = null; // DTDU启动任务的MAP

	public static LinkedBlockingQueue<Target> vehicleTargetQueue = new LinkedBlockingQueue<Target>(); // 包含过车传输对象的的队列,直接初始化
	public static LinkedBlockingQueue<Target> personTargetQueue = new LinkedBlockingQueue<Target>(); // 包含过人传输对象的的队列,直接初始化
	public static LinkedBlockingQueue<Target> objectTargetQueue = new LinkedBlockingQueue<Target>(); // 包含过人传输对象的的队列,直接初始化

	/**
	 * 返回DTDU全局配置对象
	 * 
	 * @Title: getDtduProsIns
	 * @Description: TODO
	 * @param @return
	 * @return Properties
	 * @throws
	 */
	public static Properties getDtduProsIns()
	{
		try {
			if (null == porperties)
			{
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				porperties = new Properties();
				porperties.load(new FileInputStream(loader.getResource(DtduMacro.CONFIG_FILENAME).getPath()));
			}
		} catch (Exception e)
		{
			log.error("getDtduProsIns fail", e);
			return null;
		}
		return porperties;
	}

	/**
	 * 创建数据处理线程池，corePoolSize和maximumPoolSize大小相同，则任务保存在无界的缓冲队列中
	 * 
	 * @Title: getDHThreadPoolIns
	 * @Description: TODO
	 * @param @return
	 * @return ThreadPoolExecutor
	 * @throws
	 */
	public static ThreadPoolExecutor getDHThreadPoolIns()
	{

		try {
			if (null == dataHandlerThreadpool)
			{
				int threadPoolSize = Integer.parseInt(getDtduProsIns().getProperty(DtduMacro.DTDU_DHTHREADPOOL_SIZE));
				int threadTaskKeepAlive = Integer.parseInt(getDtduProsIns().getProperty(
						DtduMacro.DTDU_DHTHREAD_KEEPALIVE));

				dataHandlerThreadpool = new ThreadPoolExecutor(threadPoolSize, threadPoolSize,
						(long) threadTaskKeepAlive,
						TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

			}
		} catch (Exception e)
		{
			log.error("getDHThreadPoolIns fail", e);
			return null;
		}
		return dataHandlerThreadpool;
	}

	/**
	 * 创建结构化数据持久化处理线程池，corePoolSize和maximumPoolSize大小相同，则任务保存在无界的缓冲队列中
	 * 
	 * @Title: getDBThreadPoolIns
	 * @Description: TODO
	 * @param @return
	 * @return ThreadPoolExecutor
	 * @throws
	 */
	public static ThreadPoolExecutor getDBThreadPoolIns()
	{

		try {
			if (null == dbHandlerThreadpool)
			{
				int threadPoolSize = Integer.parseInt(getDtduProsIns().getProperty(DtduMacro.DTDU_DHTHREADPOOL_SIZE));
				int threadTaskKeepAlive = Integer.parseInt(getDtduProsIns().getProperty(
						DtduMacro.DTDU_DHTHREAD_KEEPALIVE));

				dbHandlerThreadpool = new ThreadPoolExecutor(threadPoolSize, threadPoolSize,
						(long) threadTaskKeepAlive,
						TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

			}
		} catch (Exception e)
		{
			log.error("getDBThreadPoolIns fail", e);
			return null;
		}
		return dataHandlerThreadpool;
	}

	/**
	 * 设置DTDU这个WEB应用的servletconfig
	 * 
	 * @Title: setDtduServletContext
	 * @Description: TODO
	 * @param @param servletconfig
	 * @return void
	 * @throws
	 */
	public static void setDtduServletContext(ServletContext servletcontext)
	{
		sc = servletcontext;
	}

	/**
	 * 获取springmvc的上下文实例
	 * 
	 * @Title: getMvcWebApplicationContextIns
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public static WebApplicationContext getMvcWebApplicationContextIns()
	{
		if (null == sc)
		{
			return null;
		}
		if (null == webappCxt)
		{
			webappCxt = WebApplicationContextUtils.getWebApplicationContext(sc, DtduMacro.SPRING_MVC_CONTEXT);

		}
		return webappCxt;
	}

	/**
	 * 获取存放DTDU启动任务的MAP
	 * 
	 * @Title: getDtduTaskMapIns
	 * @Description: TODO
	 * @param @return
	 * @return HashMap<String,AbsDtduTask>
	 * @throws
	 */
	public static HashMap<String, AbsDtduTask> getDtduTaskMapIns()
	{
		if (null == dtduTaskMap)
		{
			return new HashMap<String, AbsDtduTask>();
		}
		return dtduTaskMap;
	}

	/**
	 * 根据类型获取目标对象的对象的队列
	 * @Title: getTargetQueueByType
	 * @Description: TODO
	 * @param @param type
	 * @param @return
	 * @return LinkedBlockingQueue<Target>
	 * @throws
	 */
	public static LinkedBlockingQueue<Target> getTargetQueueByType(String type)
	{
		if (DtduMacro.VEHICLE_TARGET_TYPE.equals(type))
		{
			return vehicleTargetQueue;
		}
		else if (DtduMacro.PERSON_TARGET_TYPE.equals(type))
		{
			return personTargetQueue;
		}

		else if (DtduMacro.OBJECT_TARGET_TYPE.equals(type))
		{
			return objectTargetQueue;
		}
		return null;
	}

}
