/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: ObjectInfoDaoImpl.java
* 包名: com.kedacom.dpss.dtdu.dao 
* 描述: TODO 
* 作者:zhangkai 
* 创建日期:2013-12-9  
* 修改人:zhangkai   
* 修改日期: 2013-12-9
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.pojo.transport.ObjectPicInfoType;

/**
 * @author zhangkai
 *
 */
public class ObjectInfoDaoImpl extends AbsTransObjDao{
	
	private static final KedaLogger log = KedaLogger.getLogger(ObjectInfoDaoImpl.class);
	
	private static final String sqlHead= "INSERT INTO TD_OBJECT_INFO (";
	private StringBuffer sbColumn = new StringBuffer();
	private StringBuffer sbValue = new StringBuffer();
	
	/**
	 * 构建SQL语句并插入过车数据表
	* @Title: InsertObjectInfo 
	* @Description: TODO 
	* @param @param jt
	* @param @param target    
	* @return void
	* @throws
	 */
	public  void insertTargetInfo(JdbcTemplate jt,Target target)
	{
		ObjectPicInfoType objinfo = target.getOInfo();
		if(null == objinfo)
		{
			log.error("get objinfo null:"+target.toString());
		}
	}

	@Override
	public int insertTargetInfoBatch(JdbcTemplate jt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTarget(Target tg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBatchSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
