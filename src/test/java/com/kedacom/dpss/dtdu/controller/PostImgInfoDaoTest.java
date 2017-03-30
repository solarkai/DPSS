/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: PicInfoControllerTest.java
* 包名: com.kedacom.dpss.dtdu.controller 
* 描述: TODO 
* 作者:zhangkai 
* 创建日期:zhangkai  
* 修改人:zhangkai   
* 修改日期: 2013-12-13
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.controller;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;     
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


import com.kedacom.dpss.dtdu.dao.PostImgInfoDao;
import com.kedacom.dpss.dtdu.pojo.transport.Target;
/**
 * @author zhangkai
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/springMvc-servlet.xml"})   
@TestExecutionListeners({    
    WebContextTestExecutionListener.class,    
    DependencyInjectionTestExecutionListener.class,   
    DirtiesContextTestExecutionListener.class })  
public class PostImgInfoDaoTest {

	@Autowired
	@Qualifier("postImgInfoDao")
	PostImgInfoDao pid;
	
	/**
	 * 测试持久化过车记录方法
	 */
	@Test
	public void testPersistImgInfo() {
		//fail("Not yet implemented");
		System.out.println("-----test postImgInfoDao begin-------");
		//assertTrue(true);
		assertNotNull(pid.getJdbctemplate());
		System.out.println(pid.getJdbctemplate().getDataSource());
		Target tg = TestObjectInsFactory.getVehicleObjIns();
		pid.persistImgInfo(tg);
		//查询插入数据库中的属性
		 List<Map<String,Object>> recordset = pid.getJdbctemplate().queryForList(
				"select * from td_vehicle_info where KEDA_DEVICE_ID_TX='"+tg.getVInfo().getComPicInfo().getKedaDevId()+"'");
		 assertTrue(recordset.size()>0); //断言查询出的记录数目可定大于0，表示插入成功
		 
		 //只取第一条判断,判断几个必填属性
		 Map<String,Object> result =  recordset.get(0);
		 assertTrue(result.get("GB_DEVICE_ID_TX").equals(tg.getVInfo().getComPicInfo().getGbDevId()));
		 System.out.println("get test GB_DEVICE_ID_TX is:"+result.get("GB_DEVICE_ID_TX").toString());
		 assertTrue(result.get("PLATE_NUM_TX").equals(tg.getVInfo().getPltNum()));
		 System.out.println("get test PLATE_NUM_TX is:"+result.get("PLATE_NUM_TX").toString());
		 
		 //删除该条记录，用于下次测试
		 pid.getJdbctemplate().update("delete from td_vehicle_info where KEDA_DEVICE_ID_TX='"+tg.getVInfo().getComPicInfo().getKedaDevId()+"'");
		 
	}

}
