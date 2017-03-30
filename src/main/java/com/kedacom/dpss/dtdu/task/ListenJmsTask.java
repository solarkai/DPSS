/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: ListenJmsTask.java
 * 包名: com.kedacom.dpss.dtdu.task 
 * 描述: 订阅任务 
 * 作者:zhangkai 
 * 创建日期:2013-12-5  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-5
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.task;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.util.AbsTopicListener;
import com.kedacom.dpss.dtdu.exception.DtduException;

/**
 * @author zhangkai
 * 
 */
@Scope("singleton")
@Service("listenJmsTask")
public class ListenJmsTask extends AbsDtduTask {

	private static final KedaLogger log = KedaLogger.getLogger(ListenJmsTask.class);
	private static final String SMU_PUB_TOPIC = "SMU_PUB"; // 暂定
	private static final String SIU_PUB_TOPIC = "SIU_PUB"; // 暂定

	@Autowired
	@Qualifier("smuJmsListener")
	private AbsTopicListener smuListener;  //监听SMU系统管理模块
	
	@Autowired
	@Qualifier("siuJmsListener")
	private AbsTopicListener siuListener;  //监听SIU模块,处理布控请求

	public ListenJmsTask() throws DtduException
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
		log.info("starting dtdu listenJmsTask");
		
		smuListener.setListenTopic(SMU_PUB_TOPIC);
		if (!smuListener.startListen())
		{
			this.setTaskState(TaskState.startfailure);
			throw new DtduException("dtdu start smu listener fail!");
		}
		siuListener.setListenTopic(SIU_PUB_TOPIC);
		if (!siuListener.startListen())
		{
			this.setTaskState(TaskState.startfailure);
			throw new DtduException("dtdu start siu listener fail!");
		}

		this.setTaskState(TaskState.started);
		log.info("dtdu listenJmsTask started");

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
