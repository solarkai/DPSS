/**
* 描述: TODO 
* 作者:Administrator 
* 创建日期:Administrator  
* 修改人:Administrator   
* 修改日期: 2013-12-11
*/
package com.kedacom.dpss.dtdu.test.util;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.kedacom.dpss.dtdu.pojo.transport.CommonPicInfoType;
import com.kedacom.dpss.dtdu.pojo.transport.PicDtl;
import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.pojo.transport.VehiclePicInfoType;

/**
 * @author Administrator
 *
 */
public class EntitiesHelper {
	//Target的属性
	public static final String PicInfoType_PERSION = "persion";
	public static final String PicInfoType_OBJECT = "object";
	public static final String PicInfoType_VEHICLE = "vehicle";	

	//CommonPicInfoType的属性
	public static final String KedaDevId = "55123456789012345678901234567890_10";
	public static final String GbDevId = "37010101000001234567";
	public static final int Lane = 1;		
	public static final String PicFlag = "1";	//图片标志：0-图片数据流；1-图片URL
	public static final int PicNum = 1;		
	public static final String BestTime = "2013-12-17T09:30:47Z";
	public static final String InTime = "2013-12-17T09:30:40Z";
	public static final String OutTime = "2013-12-17T09:30:50Z";
	
	//PiCDtl的属性
	public static final boolean  IsFtrPic = false;
	public static final String PicUrl = "http://127.0.0.1/img/1.jpg";
	public static final String PicStreamId = "55123456789012345678901234567890_10_001";
	public static final String FtrCoord = "100,200,1000,2000";	
	public static final String TrgtCoord = "300,400,500,600";	
	
	//VehiclePicInfoType的属性
	public static final boolean IfHavePlt = true;//1有车牌，0无车牌
	public static final String PltType = "1";//车牌类型
	public static final String PltCol = "1"; //车牌颜色
	public static final String PltNum = "鲁HZC806";//
	public static final String PltGNum = "";//挂车车牌
	public static final boolean IsPltAlt = false; //是否涂改
	public static final boolean IsPltBlc = false;//是否遮盖
	public static final int Speed = 80;//速度
	public static final String Status = "0"; //车运行状态：0－正常，1－嫌疑，2072超速，2027逆行
	public static final String Brand = "";
	public static final String vCol = "1";
	public static final String TrafficOffence = "0"; 
	public static final String Style = "M21";//被标注车辆的款式型号
	public static final int  vLength = 210;
	public static final int  vWidth = 140;
	public static final int  vHeight = 70;
	
	public static Target newVehicle() throws DatatypeConfigurationException{
		Target tg = new Target();
		tg.setPicInfoType(PicInfoType_VEHICLE);
		
		//构建CommonPicInfoType
		CommonPicInfoType cp = new CommonPicInfoType();
		cp.setKedaDevId(KedaDevId);
		cp.setGbDevId(GbDevId);
		cp.setLane(Lane);
		cp.setPicFlag(PicFlag);
		cp.setPicNum(PicNum);		
		
		XMLGregorianCalendar bestTime = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		bestTime.setYear(2013);
		bestTime.setMonth(12);
		bestTime.setDay(17);
		bestTime.setHour(9);
		bestTime.setMinute(30);
		bestTime.setSecond(47);
		cp.setBestTime(bestTime);

		XMLGregorianCalendar inTime = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		inTime.setYear(2013);
		inTime.setMonth(12);
		inTime.setDay(17);
		inTime.setHour(9);
		inTime.setMinute(30);
		inTime.setSecond(40);
		cp.setInTime(inTime);
		
		XMLGregorianCalendar outTime = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		outTime.setYear(2013);
		outTime.setMonth(12);
		outTime.setDay(17);
		outTime.setHour(9);
		outTime.setMinute(30);
		outTime.setSecond(40);	
		cp.setOutTime(outTime);
		
		CommonPicInfoType.PicDtlList picDtlList = new CommonPicInfoType.PicDtlList();
		List<PicDtl> list = picDtlList.getPicDtl();
		PicDtl picDtl = new PicDtl();
		picDtl.setIsFtrPic(false);
		picDtl.setPicUrl(PicUrl);
		picDtl.setTrgtCoord(TrgtCoord);
		picDtl.setFtrCoord(FtrCoord);
		picDtl.setPicStreamId(PicStreamId);
		list.add(picDtl);
		cp.setPicDtlList(picDtlList);
		
		//构建VehiclePicInfoType
		VehiclePicInfoType vp = new VehiclePicInfoType();
		vp.setComPicInfo(cp);
		vp.setBrand(Brand);
		vp.setIfHavePlt(IfHavePlt);
		vp.setIsPltAlt(IsPltAlt);
		vp.setIsPltBlc(IsPltBlc);
		vp.setPltCol(PltCol);
		vp.setPltGNum(PltGNum);
		vp.setPltNum(PltNum);
		vp.setPltType(PltType);
		vp.setSpeed(Speed);
		vp.setStatus(Status);
		vp.setStyle(Style);
		vp.setTrafficOffence(TrafficOffence);
		vp.setVCol(vCol);
		vp.setVHeight(vHeight);
		vp.setVLength(vLength);
		vp.setVWidth(vWidth);
		
		tg.setVInfo(vp);
		return tg;
	}
	

}
