/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: PersonInfoDaoImpl.java
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

/**
 * @author zhangkai
 *
 */
public class PersonInfoDaoImpl extends AbsTransObjDao{
	
	private static final KedaLogger log = KedaLogger.getLogger(PersonInfoDaoImpl.class);
	
	public void insertTargetInfo(JdbcTemplate jt,Target target)
	{
		log.info(jt.getDataSource().toString());
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
