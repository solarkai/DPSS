/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: IDtduTask.java
* 包名: com.kedacom.dpss.dtdu.task 
* 描述: DTDU任务接口
* 作者:zhangkai 
* 创建日期:2013-12-5  
* 修改人:zhangkai   
* 修改日期: 2013-12-5
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.task;

import com.kedacom.dpss.dtdu.exception.*;
/**
 * @author zhangkai
 *
 */

public abstract class AbsDtduTask {
	
	
	public enum TaskState{
		inited,
		started,
		startfailure,
		running,
		complete
	}
	
	private TaskState state = null;
	
	public AbsDtduTask() throws  DtduException
	{
		initTask();
	}
	
	/**
	 * 初始化任务
	* @Title: initTask 
	* @Description: TODO 
	* @param @throws DtduException    
	* @return void
	* @throws
	 */
	public abstract void initTask() throws  DtduException;
	
	/**
	 * 开始任务
	* @Title: startTask 
	* @Description: TODO 
	* @param @throws DtduException    
	* @return void
	* @throws
	 */
	public abstract void startTask() throws  DtduException;
	
	/**
	 * 停止任务
	* @Title: StopTask 
	* @Description: TODO 
	* @param @throws DtduException    
	* @return void
	* @throws
	 */
	public abstract void StopTask() throws  DtduException;
	
	/**
	 * 设置任务状态
	* @Title: setTaskState 
	* @Description: TODO 
	* @param @param tstate    
	* @return void
	* @throws
	 */
	public void setTaskState(TaskState tstate)
	{
		state = tstate;
	}
	
	/**
	 * 返回任务状态
	* @Title: getTaskState 
	* @Description: TODO 
	* @param @return    
	* @return TaskState
	* @throws
	 */
	public TaskState getTaskState()
	{
		return state; 
	}
	

}
