/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: TransObjDBPersistTask.java
 * 包名: com.kedacom.dpss.dtdu.task 
 * 描述: 数据库批量处理任务，使用线程池实现 
 * 作者:zhangkai 
 * 创建日期:2013-12-19  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-19
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.exception.DtduException;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;
import com.kedacom.dpss.dtdu.consts.DtduMacro;

/**
 * @author zhangkai
 * 
 */
@Scope("singleton")
@Service("transObjDBPersistTask")
public class TransObjDBPersistTask extends AbsDtduTask {

	private static final KedaLogger log = KedaLogger.getLogger(TransObjDBPersistTask.class);

	private static final String[] DB_PERSIST_THREADS = { DtduMacro.VEHICLE_TARGET_TYPE, DtduMacro.PERSON_TARGET_TYPE,
			DtduMacro.OBJECT_TARGET_TYPE };

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jt;

	public TransObjDBPersistTask() throws DtduException
	{
		super();
	}

	/**
	 * <p>
	 * Title: initTask
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws DtduException
	 * @see com.kedacom.dpss.dtdu.task.AbsDtduTask#initTask()
	 */
	@Override
	public void initTask() throws DtduException {
		// TODO Auto-generated method stub

	}

	/**
	 * <p>
	 * Title: startTask
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws DtduException
	 * @see com.kedacom.dpss.dtdu.task.AbsDtduTask#startTask()
	 */
	@Override
	public void startTask() throws DtduException {
		// TODO Auto-generated method stub
		log.info("starting dtdu transObjDBPersistTask");
		for (String type : DB_PERSIST_THREADS)
		{
			TransObjDBBatchHandlerThread tdbThread = new TransObjDBBatchHandlerThread(
					DtduSingletonFactory.getTargetQueueByType(type),
					type, jt);
			tdbThread.start();
		}
		log.info("dtdu transObjDBPersistTask started");
	}

	/**
	 * <p>
	 * Title: StopTask
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws DtduException
	 * @see com.kedacom.dpss.dtdu.task.AbsDtduTask#StopTask()
	 */
	@Override
	public void StopTask() throws DtduException {
		// TODO Auto-generated method stub

	}

}
