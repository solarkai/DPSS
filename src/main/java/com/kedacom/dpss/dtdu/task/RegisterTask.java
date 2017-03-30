/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: RegisterTask.java
 * 包名: com.kedacom.dpss.dtdu.task 
 * 描述: DTDU启动之后向SMU的注册任务
 * 作者:zhangkai 
 * 创建日期:2013-12-5  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-5
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.task;

import javax.jms.TopicConnection;
import javax.jms.JMSException;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.exception.DtduException;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;
import com.kedacom.dpss.dtdu.consts.DtduMacro;
import com.kedacom.dpss.dtdu.util.JmsSubPubUtil;

/**
 * @author zhangkai
 * 
 */
@Scope("singleton")
@Service("registerTask")
public class RegisterTask extends AbsDtduTask {

	private static final KedaLogger log = KedaLogger.getLogger(RegisterTask.class);

	private static final String SMU_SUB_TOPIC = "SMU_SUB"; //暂定

	private String dtduId;
	private String dtduAlias;

	public RegisterTask() throws DtduException
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
	 * @see com.kedacom.dpss.dtdu.task.IDtduTask#initTask()
	 */
	@Override
	public void initTask() throws DtduException {
		// TODO Auto-generated method stub
		this.setDtduId(DtduSingletonFactory.getDtduProsIns().getProperty(DtduMacro.DTDU_ID));
		this.setDtduAlias(DtduSingletonFactory.getDtduProsIns().getProperty(DtduMacro.DTDU_ALIAS));
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
		log.info("starting dtdu registerTask");
		log.info("dtdu " + this.getDtduId() + " register to SMU!");
		TopicConnection conn = null;
		try {
			conn = JmsSubPubUtil.getJmsConnection();
			JmsSubPubUtil.sendJmsTextMsg(constructRegInfo(), SMU_SUB_TOPIC, conn);
		} catch (JMSException je)
		{
			DtduException de = new DtduException(je);
			this.setTaskState(TaskState.startfailure);
			throw de;

		} finally {
			try {
				if (null != conn)
				{
					conn.close();
				}

			} catch (JMSException jec)
			{
				log.error("close jms conn fail!", jec);
			}
		}
		this.setTaskState(TaskState.started);
		log.info("dtdu registerTask started");
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

	public String getDtduId() {
		return dtduId;
	}

	public void setDtduId(String dtduId) {
		this.dtduId = dtduId;
	}

	public String getDtduAlias() {
		return dtduAlias;
	}

	public void setDtduAlias(String dtduAlias) {
		this.dtduAlias = dtduAlias;
	}

	/**
	 * 构建注册信息
	 * 
	 * @Title: constructRegInfo
	 * @Description: TODO
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String constructRegInfo()
	{
		// add implemention here
		return null;
	}

}
