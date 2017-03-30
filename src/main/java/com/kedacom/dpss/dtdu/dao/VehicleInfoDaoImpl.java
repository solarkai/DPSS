/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: VehicleInfoDaoImpl.java
 * 包名: com.kedacom.dpss.dtdu.dao 
 * 描述: TODO 
 * 作者:zhangkai 
 * 创建日期:2013-12-9  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-9
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

import kedanet.log.KedaLogger;

import com.kedacom.dpss.dtdu.pojo.transport.PicDtl;
import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.pojo.transport.VehiclePicInfoType;
import com.kedacom.dpss.dtdu.pojo.transport.CommonPicInfoType;
import com.kedacom.dpss.dtdu.consts.DtduMacro;
import com.kedacom.dpss.dtdu.util.DtduSingletonFactory;

/**
 * @author zhangkai
 * 
 */
public class VehicleInfoDaoImpl extends AbsTransObjDao {

	private static final KedaLogger log = KedaLogger.getLogger(VehicleInfoDaoImpl.class);

	// 单条SQL处理需要的变量
	private static final String sqlHead = "INSERT INTO TD_VEHICLE_INFO (ID_PK,";

	private static DataSourceTransactionManager dstrans = null;

	private StringBuffer sbColumn = null;
	private StringBuffer sbValue = null;

	// 批量SQL处理需要的变量
	private List<Target> tglist = new ArrayList<Target>();

	static {

		if (null != DtduSingletonFactory.getMvcWebApplicationContextIns())
		{
			dstrans = (DataSourceTransactionManager) DtduSingletonFactory.getMvcWebApplicationContextIns().getBean(
					"transactionManager");
			if (null == dstrans)
			{
				log.error("VehicleInfoDaoImpl get transactionManager fail!");
			}
		} else
		{
			log.error("VehicleInfoDaoImpl get MvcWebApplicationContextIns fail!");
		}

	}

	/**
	 * 添加需要数据库持久化的对象
	 * 
	 * @Title: addTarget
	 * @Description: TODO
	 * @param @param tg
	 * @return void
	 * @throws
	 */
	public void addTarget(Target tg)
	{
		tglist.add(tg);
	}

	/**
	 * 返回需要批量处理的对象数目
	 * 
	 * @Title: getBatchSize
	 * @Description: TODO
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int getBatchSize()
	{
		return tglist.size();
	}

	/**
	 * 解析过车对象，并插入数据库
	 * 
	 * @Title: InsertVehicleInfo
	 * @Description: TODO
	 * @param @param jt
	 * @param @param target
	 * @return void
	 * @throws
	 */
	public void insertTargetInfo(JdbcTemplate jt, Target target)
	{
		
		insertVehicleInfo(jt, target);
	}

	/**
	 * 解析过车对象，使用prepareStatement
	 * 
	 * @Title: InsertVehicleInfoPS
	 * @Description: TODO
	 * @param @param jt
	 * @param @param target
	 * @return void
	 * @throws
	 */
	public int insertTargetInfoBatch(JdbcTemplate jt)
	{
		int count = 0;

		if ((0 == tglist.size()) || (null == dstrans))
		{
			log.error("get vehicleinfo to persist count 0 or null equals dstrans");
			return 0;
		}
		// 批量操作加入事务控制
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		TransactionStatus status = dstrans.getTransaction(dtd);

		try {
			jt.batchUpdate(sqlps, new VIPS());
			log.info("update vehicle sql count " + getBatchSize() + " affected!");
			dstrans.commit(status);
		} catch (DataAccessException dae)
		{
			dstrans.rollback(status);
			log.error("update vehicle sql count " + getBatchSize() + " fail", dae);
			log.info("VEHICLE-DB-ERROR-HANDLE-TIMER should start after "
					+ DtduMacro.DTDU_DB_EXCEPTION_DELAY_HANDLE_TIME + " millseconds!");
			Timer errorHandleTimer = new Timer("VEHICLE-DB-ERROR-HANDLE-TIMER");
			TimerTask errorHandleTask = new ErrorHandleTask(jt);
			errorHandleTimer.schedule(errorHandleTask, DtduMacro.DTDU_DB_EXCEPTION_DELAY_HANDLE_TIME);
		}

		return count;
	}

	/**
	 * 插入过车对象数据
	 * 
	 * @Title: insertVehicleInfo
	 * @Description: TODO
	 * @param @param jt
	 * @param @param vi
	 * @return void
	 * @throws
	 */
	private void insertVehicleInfo(JdbcTemplate jt, Target target)
	{
		VehiclePicInfoType vi = target.getVInfo();
		if (null == vi)
		{
			log.error("get vehicleinfo null");
			return;
		}
		parseComPicInfo(vi.getComPicInfo());
		//数据来源平台类型
		sbColumn.append(",PLATFORM_TYPE_TX");
		//数据来源类型
		sbValue.append(",'").append(target.getDsPltType()).append("'");
		if(null != target.getDsPltType())
		{
			sbColumn.append(",FROM_SYS_INT"); 
			sbValue.append(",'").append(target.getDsType()).append("'");
		}
		
		parseVPicInfo(vi);
		String sql = (new StringBuffer()).append(sqlHead).append(sbColumn).append(") VALUES(SEQ_TD_VEHICLE.nextval,")
				.append(sbValue)
				.append(")").toString();
		log.debug(sql);
		try {
			jt.execute(sql);
		} catch (DataAccessException dae)
		{
			log.error("execute sql:" + sql + " fail", dae);
			return;
		}
		log.info("insert vehicle 1 sql!");
	}

	/**
	 * 分析图片的一些通用属性
	 * 
	 * @Title: parseComPicInfo
	 * @Description: TODO
	 * @param @param cpi
	 * @return void
	 * @throws
	 */
	private void parseComPicInfo(CommonPicInfoType cpi)
	{

		if (null != cpi.getKedaDevId()) // KEDA_DEVICE_ID_TX
		{
			sbColumn = new StringBuffer().append("KEDA_DEVICE_ID_TX");
			sbValue = new StringBuffer().append("'").append(cpi.getKedaDevId()).append("'");
		}
		if (null != cpi.getGbDevId()) // GB_DEVICE_ID_TX
		{
			sbColumn = (null == sbColumn) ?
					(new StringBuffer()).append("GB_DEVICE_ID_TX")
					: sbColumn.append(",").append("GB_DEVICE_ID_TX");

			sbValue = (null == sbValue) ?
					(new StringBuffer()).append("'").append(cpi.getGbDevId()).append("'")
					: sbValue.append(",").append("'").append(cpi.getGbDevId()).append("'");
		}
		{ // LANE_NUM
			sbColumn.append(",LANE_NUM");
			sbValue.append(",").append(cpi.getLane());
		}
		{ // PIC_NUM_INT
			sbColumn.append(",PIC_NUM_INT");
			sbValue.append(",").append(cpi.getPicNum());
		}
		if (null != cpi.getInTime()) // IN_TIME_TX
		{
			sbColumn.append(",IN_TIME_TX");
			sbValue.append(",to_timestamp('").append(formatXMLDateTime(cpi.getInTime()))
					.append("','syyyy-mm-dd hh24:mi:ss.ff3')");
		}
		if (null != cpi.getOutTime()) // OUT_TIME_TX
		{
			sbColumn.append(",OUT_TIME_TX");
			sbValue.append(",to_timestamp('").append(formatXMLDateTime(cpi.getOutTime()))
					.append("','syyyy-mm-dd hh24:mi:ss.ff3')");
		}
		if (null != cpi.getBestTime()) // BEST_TIME_TX
		{
			sbColumn.append(",BEST_TIME_TX");
			sbValue.append(",to_timestamp('").append(formatXMLDateTime(cpi.getBestTime()))
					.append("','syyyy-mm-dd hh24:mi:ss.ff3')");
		}

		List<PicDtl> pdlist = cpi.getPicDtlList().getPicDtl();
		String picflag = cpi.getPicFlag();
		StringBuffer picurl = null; // 图片的URL，用逗号分隔
		StringBuffer piccood = null; // 图片的坐标，用冒号分隔
		StringBuffer featureurl = null; // 特写图片的URL，用逗号分隔
		StringBuffer featurecood = null; // 特写图片的坐标，用冒号分隔
		if (null != pdlist)
		{
			for (PicDtl dtl : pdlist)
			{
				if ("1".equals(picflag)) // 传的是URL,拼出URL
				{
					if (!dtl.isIsFtrPic()) // 不是特写图片
					{
						picurl = (null == picurl) ?
								(new StringBuffer()).append(dtl.getPicUrl())
								: picurl.append(",").append(dtl.getPicUrl());
					} else // 是特写图片
					{
						featureurl = (null == featureurl) ?
								(new StringBuffer()).append(dtl.getPicUrl())
								: featureurl.append(",").append(dtl.getPicUrl());
					}
				}
				if (null != dtl.getTrgtCoord())
				{
					piccood = (null == piccood) ?
							(new StringBuffer()).append(dtl.getTrgtCoord())
							: piccood.append(":").append(dtl.getTrgtCoord());
				}

				if (null != dtl.getFtrCoord())
				{
					featurecood = (null == featurecood) ?
							(new StringBuffer()).append(dtl.getFtrCoord())
							: featurecood.append(":").append(dtl.getFtrCoord());
				}
			}// end for
		}// end if

		if (null != picurl) // PIC_URL_TX
		{
			sbColumn.append(",PIC_URL_TX");
			sbValue.append(",'").append(picurl).append("'");
		}

		if (null != featureurl) // FEATURE_PIC_URL_TX
		{
			sbColumn.append(",FEATURE_PIC_URL_TX");
			sbValue.append(",'").append(featureurl).append("'");
		}

		if (null != piccood) // TARGET_COORDINATE
		{
			sbColumn.append(",TARGET_COORDINATE");
			sbValue.append(",'").append(piccood).append("'");
		}

		if (null != featurecood) // CLOSEUP_COORDINATE
		{
			sbColumn.append(",CLOSEUP_COORDINATE");
			sbValue.append(",'").append(featurecood).append("'");
		}
		// 增加入库时间
		sbColumn.append(",INLIB_TIME_TX");
		sbValue.append(",systimestamp");

	}

	/**
	 * 分析过车图片的特有属性
	 * 
	 * @Title: parseVPicInfo
	 * @Description: 前面分析过了通用属性，这里直接加上特有属性
	 * @param @param vi
	 * @return void
	 * @throws
	 */
	public void parseVPicInfo(VehiclePicInfoType vi)
	{
		if (null != vi.isIfHavePlt())
		{
			sbColumn.append(",HAVE_PLATE_TX"); // 0：无、1：有
			sbValue = vi.isIfHavePlt().booleanValue() ? sbValue.append(",'1'") : sbValue.append(",'0'");
		}
		if (null != vi.getPltType())// PLATE_TYPE_TX
		{
			sbColumn.append(",PLATE_TYPE_TX");
			sbValue.append(",'").append(vi.getPltType()).append("'");
		}
		if (null != vi.getPltCol())// PLATE_COLOUR_TX
		{
			sbColumn.append(",PLATE_COLOUR_TX");
			sbValue.append(",'").append(vi.getPltCol()).append("'");
		}
		if (null != vi.getPltNum())// PLATE_NUM_TX
		{
			sbColumn.append(",PLATE_NUM_TX");
			sbValue.append(",'").append(vi.getPltNum()).append("'");
		}
		if (null != vi.getPltGNum())// PltGNum
		{
			sbColumn.append(",PLATE_GNUMBER_TX");
			sbValue.append(",'").append(vi.getPltGNum()).append("'");
		}
		if (null != vi.isIsPltAlt())
		{
			sbColumn.append(",PLATE_ISALTER_TX"); // 0：无、1：有
			sbValue = vi.isIsPltAlt().booleanValue() ? sbValue.append(",'1'") : sbValue.append(",'0'");
		}
		if (null != vi.isIsPltBlc())
		{
			sbColumn.append(",PLATE_ISBLOCK_TX"); // 0：无、1：有
			sbValue = vi.isIsPltBlc().booleanValue() ? sbValue.append(",'1'") : sbValue.append(",'0'");
		}

		if (null != vi.getSpeed())
		{
			sbColumn.append(",SPEED_INT"); // SPEED_INT
			sbValue.append(",").append(vi.getSpeed());
		}
		if (null != vi.getSpdLmt())
		{
			sbColumn.append(",SPEED_LIMIT_INT"); // SPEED_LIMIT_INT
			sbValue.append(",").append(vi.getSpdLmt());
		}
		if (null != vi.getStatus())// STATUS_TX
		{
			sbColumn.append(",STATUS_TX");
			sbValue.append(",'").append(vi.getStatus()).append("'");
		}
		if (null != vi.getBrand())// Brand
		{
			sbColumn.append(",BRAND_TX");
			sbValue.append(",'").append(vi.getBrand()).append("'");
		}
		if (null != vi.getStyle())// CLASS_TX
		{
			sbColumn.append(",CLASS_TX");
			sbValue.append(",'").append(vi.getStyle()).append("'");
		}

		if (null != vi.getVLength())
		{
			sbColumn.append(",VEHICLE_LENGTH_INT"); // VEHICLE_LENGTH_INT
			sbValue.append(",").append(vi.getVLength());
		}

		if (null != vi.getVWidth())
		{
			sbColumn.append(",VEHICLE_WIDTH_INT"); // VEHICLE_WIDTH_INT
			sbValue.append(",").append(vi.getVWidth());
		}

		if (null != vi.getVHeight())
		{
			sbColumn.append(",VEHICLE_HEIGHT_INT"); // VEHICLE_HEIGHT_INT
			sbValue.append(",").append(vi.getVHeight());
		}

		if (null != vi.getVCol())// VEHICLE_COLOUR_TX
		{
			sbColumn.append(",VEHICLE_COLOUR_TX");
			sbValue.append(",'").append(vi.getVCol()).append("'");
		}
		if (null != vi.getTrafficOffence())// TRAFFIC_OFFENCE_TX
		{
			sbColumn.append(",TRAFFIC_OFFENCE_TX");
			sbValue.append(",'").append(vi.getTrafficOffence()).append("'");
		}

	}

	/**
	 * 将XML日期类型转成yyyy-MM-dd HH:mm:ss.SSS 格式字符串
	 * 
	 * @Title: formatXMLDateTime
	 * @Description: TODO
	 * @param @param xg
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String formatXMLDateTime(XMLGregorianCalendar xg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(xg.toGregorianCalendar().getTime());
	}

	// preparedstatement语句
	private static final String sqlps = "INSERT INTO TD_VEHICLE_INFO ("
			+
			"ID_PK,KEDA_DEVICE_ID_TX,GB_DEVICE_ID_TX,LANE_NUM,PIC_NUM_INT"
			+
			",IN_TIME_TX,OUT_TIME_TX,BEST_TIME_TX"
			+
			",PIC_URL_TX,FEATURE_PIC_URL_TX,TARGET_COORDINATE,CLOSEUP_COORDINATE"
			+
			",INLIB_TIME_TX,HAVE_PLATE_TX,PLATE_TYPE_TX,PLATE_COLOUR_TX,PLATE_NUM_TX"
			+
			",PLATE_GNUMBER_TX,PLATE_ISALTER_TX,PLATE_ISBLOCK_TX,SPEED_INT,SPEED_LIMIT_INT"
			+
			",STATUS_TX,BRAND_TX,CLASS_TX,VEHICLE_LENGTH_INT,VEHICLE_WIDTH_INT"
			+
			",VEHICLE_HEIGHT_INT,VEHICLE_COLOUR_TX,TRAFFIC_OFFENCE_TX,PLATFORM_TYPE_TX,FROM_SYS_INT"
			+
			") VALUES("
			+
			"SEQ_TD_VEHICLE.nextval,?,?,?,?"
			+
			",?,?,?"
			+
			",?,?,?,?"
			+
			",systimestamp,?,?,?,?"
			+
			",?,?,?,?,?"
			+
			",?,?,?,?,?"
			+
			",?,?,?,?,?"
			+
			")";

	/**
	 * 将XML日期类型设置为Timestamp对象
	 * 
	 * @Title: formatXml2TimeStamp
	 * @Description: TODO
	 * @param @param xg
	 * @param @return
	 * @return Timestamp
	 * @throws
	 */
	private Timestamp formatXml2TimeStamp(XMLGregorianCalendar xg)
	{
		Timestamp ts = new Timestamp(xg.toGregorianCalendar().getTimeInMillis());
		return ts;
	}

	/**
	 * 根据tglist来设置BatchPreparedStatementSetter
	 * 
	 * @author zhangkai
	 * 
	 */
	private class VIPS implements BatchPreparedStatementSetter
	{

		@Override
		public int getBatchSize() {
			// TODO Auto-generated method stub
			return tglist.size();
		}

		@Override
		public void setValues(PreparedStatement pstmt, int index) throws SQLException {

			VehiclePicInfoType vi = tglist.get(index).getVInfo();
			if (null == vi)
			{
				log.error("vehicle tglist setvalues index " + index + "error!");
				return;
			}
			CommonPicInfoType cpi = vi.getComPicInfo();
			pstmt.setObject(1, null != cpi.getKedaDevId() ? cpi.getKedaDevId() : null);
			pstmt.setObject(2, null != cpi.getGbDevId() ? cpi.getGbDevId() : null);
			pstmt.setObject(3, cpi.getLane());
			pstmt.setObject(4, cpi.getPicNum());

			pstmt.setTimestamp(5, null != cpi.getInTime() ? formatXml2TimeStamp(cpi.getInTime()) : null);
			pstmt.setTimestamp(6, null != cpi.getOutTime() ? formatXml2TimeStamp(cpi.getOutTime()) : null);
			pstmt.setTimestamp(7, null != cpi.getBestTime() ? formatXml2TimeStamp(cpi.getBestTime()) : null);

			List<PicDtl> pdlist = cpi.getPicDtlList().getPicDtl();
			String picflag = cpi.getPicFlag();
			StringBuffer picurl = null; // 图片的URL，用逗号分隔
			StringBuffer piccood = null; // 图片的坐标，用冒号分隔
			StringBuffer featureurl = null; // 特写图片的URL，用逗号分隔
			StringBuffer featurecood = null; // 特写图片的坐标，用冒号分隔
			if (null != pdlist)
			{
				for (PicDtl dtl : pdlist)
				{
					if ("1".equals(picflag)) // 传的是URL,拼出URL
					{
						if (!dtl.isIsFtrPic()) // 不是特写图片
						{
							picurl = (null == picurl) ?
									(new StringBuffer()).append(dtl.getPicUrl())
									: picurl.append(",").append(dtl.getPicUrl());
						} else // 是特写图片
						{
							featureurl = (null == featureurl) ?
									(new StringBuffer()).append(dtl.getPicUrl())
									: featureurl.append(",").append(dtl.getPicUrl());
						}
					}
					if (null != dtl.getTrgtCoord())
					{
						piccood = (null == piccood) ?
								(new StringBuffer()).append(dtl.getTrgtCoord())
								: piccood.append(":").append(dtl.getTrgtCoord());
					}

					if (null != dtl.getFtrCoord())
					{
						featurecood = (null == featurecood) ?
								(new StringBuffer()).append(dtl.getFtrCoord())
								: featurecood.append(":").append(dtl.getFtrCoord());
					}
				}// end for
			}// end if

			pstmt.setObject(8, null != picurl ? picurl.toString() : null);
			pstmt.setObject(9, null != featureurl ? featureurl.toString() : null);
			pstmt.setObject(10, null != piccood ? piccood.toString() : null);
			pstmt.setObject(11, null != featurecood ? featurecood.toString() : null);

			pstmt.setObject(12, null != vi.isIfHavePlt() ? vi.isIfHavePlt().booleanValue() ? "1" : "0" : null);
			pstmt.setObject(13, null != vi.getPltType() ? vi.getPltType() : null);
			pstmt.setObject(14, null != vi.getPltCol() ? vi.getPltCol() : null);
			pstmt.setObject(15, null != vi.getPltNum() ? vi.getPltNum() : null);
			pstmt.setObject(16, null != vi.getPltGNum() ? vi.getPltGNum() : null);
			pstmt.setObject(17, null != vi.isIsPltAlt() ? vi.isIsPltAlt().booleanValue() ? "1" : "0" : null);
			pstmt.setObject(18, null != vi.isIsPltBlc() ? vi.isIsPltBlc().booleanValue() ? "1" : "0" : null);
			pstmt.setObject(19, null != vi.getSpeed() ? vi.getSpeed() : null);
			pstmt.setObject(20, null != vi.getSpdLmt() ? vi.getSpdLmt() : null);
			pstmt.setObject(21, null != vi.getStatus() ? vi.getStatus() : null);
			pstmt.setObject(22, null != vi.getBrand() ? vi.getBrand() : null);
			pstmt.setObject(23, null != vi.getStyle() ? vi.getStyle() : null);
			pstmt.setObject(24, null != vi.getVLength() ? vi.getVLength() : null);
			pstmt.setObject(25, null != vi.getVWidth() ? vi.getVWidth() : null);
			pstmt.setObject(26, null != vi.getVHeight() ? vi.getVHeight() : null);
			pstmt.setObject(27, null != vi.getVCol() ? vi.getVCol() : null);
			pstmt.setObject(28, null != vi.getTrafficOffence() ? vi.getTrafficOffence() : null);
			pstmt.setObject(29,tglist.get(index).getDsPltType());
			pstmt.setObject(30,tglist.get(index).getDsType());
		}
	}

	/**
	 * 出错处理时的定时器任务类
	 * 
	 * @author zhangkai
	 * 
	 */
	private class ErrorHandleTask extends TimerTask {

		private JdbcTemplate jt;

		public ErrorHandleTask(JdbcTemplate jte)
		{
			jt = jte;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (Target tg : tglist)
			{
				insertVehicleInfo(jt, tg);
			}
		}
	}

}
