/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: PostImgInfoDao.java
 * 包名: com.kedacom.dpss.dtdu.dao 
 * 描述: 对每条请求的结构化数据的持久化处理类 
 * 作者:zhangkai 
 * 创建日期:2013-12-9  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-9
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;
import com.kedacom.dpss.dtdu.util.DtduCommonUtil;

/**
 * @author zhangkai
 * 
 */
@Scope("request")
@Service("postImgInfoDao")
public class PostImgInfoDao {
	public static final KedaLogger log = KedaLogger.getLogger(PostImgInfoDao.class);

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbctemplate;

	/**
	 * 持久化target对象,单条方式
	 * 
	 * @Title: persistImgInfo
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void persistImgInfo(Target tg)
	{

		if(null == tg.getPicInfoType())
		{
			log.error("cannot recognize target type null");
			return;
		}
		
		AbsTransObjDao daoimpl = DtduCommonUtil.getTransObjDaoImplObj(tg.getPicInfoType());
		if(null != daoimpl)
		{
			daoimpl.insertTargetInfo(jdbctemplate, tg);
		} else
		{
			log.error("cannot recognize target type:" + tg.getPicInfoType());
		}

	}

	/**
	 * 持久化target对象,批量方式。把收到的对象放入到数据库处理队列中，根据人、车、物划分
	 * 
	 * @Title: throwImgInfo2DBqueue
	 * @Description: TODO
	 * @param @param tg
	 * @return void
	 * @throws
	 */
	public void persistImgInfoBatch(Target tg)
	{
		if (null == tg.getPicInfoType())
		{
			log.error("cannot recognize target type null");
			return;
		}

		if (null != DtduSingletonFactory.getTargetQueueByType(tg.getPicInfoType()))
		{
			DtduSingletonFactory.getTargetQueueByType(tg.getPicInfoType()).add(tg);
		} else
		{
			log.error("cannot recognize target type:" + tg.getPicInfoType());
		}

	}

	/**
	 * 获取持久类的Jdbctemplate模板
	 * 
	 * @Title: getJdbctemplate
	 * @Description: TODO
	 * @param @return
	 * @return JdbcTemplate
	 * @throws
	 */
	public JdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}

	/**
	 * 查询预留接口
	 * 
	 * @Title: queryImgInfo
	 * @Description: TODO
	 * @param @param filter
	 * @return void
	 * @throws
	 */
	public void queryImgInfo(String filter)
	{

	}

}
