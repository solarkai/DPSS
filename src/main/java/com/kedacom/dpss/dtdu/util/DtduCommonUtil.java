/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: DtduCommonUtil.java
 * 包名: com.kedacom.dpss.dtdu.util 
 * 描述: DTDU所使用的公共工具类 
 * 作者:zhangkai 
 * 创建日期:2013-12-6  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-6
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;

import com.kedacom.dpss.dtdu.dao.AbsTransObjDao;
import com.kedacom.dpss.dtdu.dao.ObjectInfoDaoImpl;
import com.kedacom.dpss.dtdu.dao.PersonInfoDaoImpl;
import com.kedacom.dpss.dtdu.dao.VehicleInfoDaoImpl;
import com.kedacom.dpss.dtdu.consts.DtduMacro;

/**
 * @author zhangkai
 * 
 */
public class DtduCommonUtil {

	private static final int BUFFER_SIZE = 1024;

	/**
	 * 将输入流中的字节读入字节数组
	 * 
	 * @Title: InputStream2Byte
	 * @Description: TODO
	 * @param @param in
	 * @param @return
	 * @param @throws IOException
	 * @return byte[]
	 * @throws
	 */
	public static byte[] InputStream2Byte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return outStream.toByteArray();
	}

	/**
	 * 根据请求的对象类型获取对应的DAO实现对象
	 * 
	 * @Title: getTransObjDaoImplObj
	 * @Description: TODO
	 * @param @param type
	 * @param @return
	 * @return AbsTransObjDao
	 * @throws
	 */
	public static AbsTransObjDao getTransObjDaoImplObj(String type)
	{
		AbsTransObjDao daoimpl = null;
		if (DtduMacro.VEHICLE_TARGET_TYPE.equals(type))
		{
			daoimpl = new VehicleInfoDaoImpl();
		}
		else if (DtduMacro.PERSON_TARGET_TYPE.equals(type))
		{
			daoimpl = new PersonInfoDaoImpl();
		}

		else if (DtduMacro.OBJECT_TARGET_TYPE.equals(type))
		{
			daoimpl = new ObjectInfoDaoImpl();
		}
		return daoimpl;
	}

}
