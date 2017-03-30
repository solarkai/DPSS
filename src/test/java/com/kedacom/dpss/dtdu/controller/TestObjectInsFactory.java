/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: TestObjectInsFactory.java
* 包名: com.kedacom.dpss.dtdu.controller 
* 描述: TODO 
* 作者:zhangkai 
* 创建日期:2013-12-16  
* 修改人:zhangkai   
* 修改日期: 2013-12-16
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.controller;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;

import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.pojo.transport.CommonPicInfoType;
import com.kedacom.dpss.dtdu.pojo.transport.ObjectFactory;
import com.kedacom.dpss.dtdu.pojo.transport.PicDtl;
import com.kedacom.dpss.dtdu.pojo.transport.VehiclePicInfoType;

/**
 * @author zhangkai
 *
 */
public class TestObjectInsFactory {
	
	/**
	 * 获取过车对象实例
	* @Title: getVehicleObjIns 
	* @Description: TODO 
	* @param @return    
	* @return Target
	* @throws
	 */
	public static Target getVehicleObjIns()
	{
		ObjectFactory objfactory = new ObjectFactory();

		Target target = new ObjectFactory().createTarget();
		target.setPicInfoType("vehicle");
		target.setDsType("3");

		VehiclePicInfoType vpic = objfactory.createVehiclePicInfoType();
		target.setVInfo(vpic);

		// 设置通用属性
		CommonPicInfoType cpinfo = objfactory.createCommonPicInfoType();
		vpic.setComPicInfo(cpinfo);
		try {
			cpinfo.setBestTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
			cpinfo.setInTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
			cpinfo.setOutTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		cpinfo.setKedaDevId("KedaDevIdzhangkai126697");
		cpinfo.setGbDevId("GbIdzhangkai126697");
		cpinfo.setLane(3);
		cpinfo.setPicFlag("1");
		cpinfo.setPicNum(1);

		CommonPicInfoType.PicDtlList plist = objfactory.createCommonPicInfoTypePicDtlList();
		cpinfo.setPicDtlList(plist);
		PicDtl pd = new PicDtl();
		pd.setIsFtrPic(false);
		pd.setPicUrl("http://172.16.239.140:21100/opticm-server/downloadImage?appId=kdkk&amp;contentId=00011000043420131201091551143963&amp;imgType2&amp;imgOrder=0&amp;timeStamp=1385861538000");
		pd.setTrgtCoord("1,1,100,100");
		plist.getPicDtl().add(pd);
		pd = new PicDtl();
		pd.setIsFtrPic(true);
		pd.setPicUrl("http://172.16.239.141:21100/opticm-server/downloadImage?appId=kdkk&amp;contentId=00011000043420131201091551143963&amp;imgType2&amp;imgOrder=0&amp;timeStamp=1385861538000");
		pd.setFtrCoord("2,2,100,100");
		plist.getPicDtl().add(pd);
		
		// 设置过车属性
		vpic.setIfHavePlt(true);
		vpic.setPltType("1");
		vpic.setPltCol("2");
		vpic.setPltGNum("挂沪G34567");
		vpic.setPltNum("沪G34567");
		vpic.setIsPltAlt(false);
		vpic.setIsPltBlc(false);
		vpic.setSpeed(120);
		vpic.setSpdLmt(180);
		vpic.setStatus("1");
		vpic.setBrand("大众");
		vpic.setStyle("123");
		vpic.setVCol("1");
		vpic.setVLength(470);
		vpic.setVWidth(230);
		vpic.setVHeight(150);
		vpic.setTrafficOffence("01");

		return target;
	}

}
