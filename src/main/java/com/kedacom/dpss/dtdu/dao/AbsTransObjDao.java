/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: AbsTransObjDao.java
* 包名: com.kedacom.dpss.dtdu.dao 
* 描述: 传输的结构化数据持久化抽象类
* 作者:zhangkai 
* 创建日期:2013-12-19  
* 修改人:zhangkai   
* 修改日期: 2013-12-19
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.kedacom.dpss.dtdu.pojo.transport.Target;

/**
 * @author zhangkai
 *
 */
public abstract class AbsTransObjDao {
	
	//持久化单条结构化数据
	public abstract void insertTargetInfo(JdbcTemplate jt,Target target);
	
	//批量持久化结构化数据
	public abstract  int insertTargetInfoBatch(JdbcTemplate jt);
	//添加批量处理的结构化数据
	public abstract void addTarget(Target tg);
	//获取批量处理的结构化数据的数目
	public abstract int getBatchSize();

}
