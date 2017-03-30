/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: TransObjDBBatchHandlerThread.java
 * 包名: com.kedacom.dpss.dtdu.task 
 * 描述: 传输对象数据库持久化处理线程
 * 作者:zhangkai 
 * 创建日期:2013-12-19  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-19
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.task;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.jdbc.core.JdbcTemplate;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.dao.AbsTransObjDao;
import com.kedacom.dpss.dtdu.exception.DtduException;
import com.kedacom.dpss.dtdu.consts.DtduMacro;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;
import com.kedacom.dpss.dtdu.util.DtduCommonUtil;

/**
 * @author zhangkai
 * 
 */
public class TransObjDBBatchHandlerThread extends Thread {

	private static final KedaLogger log = KedaLogger.getLogger(TransObjDBBatchHandlerThread.class);

	private LinkedBlockingQueue<Target> tgqueue = null; // 数据库需要处理的对象队列节点
	private String tgType = null; // 传输对象的类型，人车物中的一种
	private JdbcTemplate jt = null;
	private AbsTransObjDao daoimpl = null;

	public TransObjDBBatchHandlerThread(LinkedBlockingQueue<Target> queue, String targetType, JdbcTemplate jdbcTemp)
			throws DtduException
	{
		if ((null == queue) || (null == targetType) || (null == jdbcTemp))
		{
			throw new DtduException("tgqueue is null or targetType is null or jdbcTemp is null");
		}
		if (null == DtduCommonUtil.getTransObjDaoImplObj(targetType)) // 对类型做校验
		{
			throw new DtduException("targetType " + targetType + " unrecognized!");
		}
		tgqueue = queue;
		tgType = targetType;
		jt = jdbcTemp;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true)
		{
			try {
				if (tgqueue.size() > 0)
				{
					if (tgqueue.size() < DtduMacro.DTDU_DB_BATCH_COUNT)
					{//如果队列中节点少，等待一会待聚集多一点节点再批量处理，防止DAO那里频繁处理小数目的节点
						Thread.sleep(DtduMacro.DTDU_DB_QUEUE_AGGREGATION_TIME);
					}
					
					daoimpl = DtduCommonUtil.getTransObjDaoImplObj(tgType); // 构造函数中已经做了校验，不用再校验为空
					int handleCount = 0;
					for (handleCount = 0; handleCount < DtduMacro.DTDU_DB_BATCH_COUNT; handleCount++)
					{
						Target handleobj = tgqueue.poll();
						if (null != handleobj)
						{
							daoimpl.addTarget(handleobj);
						} else
						{
							break;
						}
					}
					if (handleCount > 0)
					{
						DtduSingletonFactory.getDBThreadPoolIns().execute(new ExcuteDbBatchRunnable(daoimpl));
					}
				} else {

					//如果队列中没有数据处理，延时3毫秒，防止空转时CPU冲高
					Thread.sleep(3);

				}
			} catch (InterruptedException ie)
			{
				log.error("TransObjDBBatchHandlerThread " + tgType + " Interrupted", ie);
			}

		}// end while(true)
	}

	/**
	 * 数据库batch操作工作线程
	 * 
	 * @author zhangkai
	 * 
	 */
	private class ExcuteDbBatchRunnable implements Runnable {

		AbsTransObjDao dao = null;

		public ExcuteDbBatchRunnable(AbsTransObjDao daoimpl)
		{
			dao = daoimpl;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			dao.insertTargetInfoBatch(jt);
		}

	}

}
