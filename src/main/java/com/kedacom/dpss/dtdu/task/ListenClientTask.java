/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: ListenClientTask.java
 * 包名: com.kedacom.dpss.dtdu.task 
 * 描述: 监听实时订阅客户端的任务 
 * 作者:zhangkai 
 * 创建日期:2013-12-13  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-13
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.task;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.exception.DtduException;
import com.kedacom.dpss.dtdu.consts.DtduMacro;
import com.kedacom.dpss.dtdu.rtclient.RTClientConnector;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;
import com.kedacom.dpss.dtdu.util.DServerListener;

/**
 * @author zhangkai
 * 
 */
@Scope("singleton")
@Service("listenClientTask")
public class ListenClientTask extends AbsDtduTask {

	private static final KedaLogger log = KedaLogger.getLogger(ListenClientTask.class);

	private int listerport;

	public ListenClientTask() throws DtduException
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
		// 获取配置文件中的客户端监听端口
		listerport = DtduMacro.DTDU_RTCLIENT_LISTENPORT_DEFAULT;
		try {
			listerport = Integer.parseInt(DtduSingletonFactory.getDtduProsIns().getProperty(
					DtduMacro.DTDU_RT_CLIENT_LISTENPORT).trim());
		} catch (NumberFormatException nfe)
		{
			this.setTaskState(TaskState.startfailure);
			throw new DtduException(nfe);
		}
		this.setTaskState(TaskState.inited);
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
		log.info("starting dtdu listenClientTask");

		//启动监听客户端的线程
		new Thread() {
			public void run()
			{
				try {
					DServerListener dl = new DServerListener();
					dl.setPort(listerport);
					dl.listen(RTClientConnector.class);
				} catch (Exception e)
				{
					log.error(e);
				}
			}

		}.start();

		this.setTaskState(TaskState.started);
		log.info("dtdu listenClientTask started");

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
