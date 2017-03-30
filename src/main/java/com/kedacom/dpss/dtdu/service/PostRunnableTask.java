/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: postRunnableTask.java
 * 包名: com.kedacom.dpss.dtdu.service 
 * 描述: 处理post数据的任务（放在线程池中） 
 * 作者:zhangkai 
 * 创建日期:2013-12-6  
 * 修改人:zhangkai   
 * 修改日期: 2013-12-6
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kedanet.log.KedaLogger;
import com.kedacom.dpss.dtdu.pojo.transport.*;
import com.kedacom.dpss.dtdu.dao.PostImgInfoDao;

/**
 * @author zhangkai
 * 
 */
@Scope("request")
@Service("postRunnableTask")
public class PostRunnableTask implements Runnable, Serializable {

	private static final long serialVersionUID = -8496758573519101719L;

	private static final KedaLogger log = KedaLogger.getLogger(PostRunnableTask.class);

	private static JAXBContext jc; // 建立JAXB的上下文

	static {
		try {
			jc = JAXBContext.newInstance("com.kedacom.dpss.dtdu.pojo.transport");
		} catch (JAXBException je)
		{
			log.error("create postRunnableTask JAXBContext fail!", je);
		}
	}

	@Autowired
	@Qualifier("postImgInfoDao")
	private PostImgInfoDao postDao;
	private String url = null;
	private String content_type = null;
	private byte[] reqbody = null;

	/**
	 * 处理post请求的主要方法
	 * <p>
	 * Title: run
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try {
			log.debug("body:" + (new String(reqbody, "utf-8")));
			// 对象反序列化
			ByteArrayInputStream bisteam = new ByteArrayInputStream(reqbody);
			Unmarshaller um = jc.createUnmarshaller(); // Unmarshaller对象需要每请求建一个，否则多线程情况下如果正在parsing,会拒绝另外的请求。
			Target target = (Target) um.unmarshal(bisteam);
			log.debug("unmarshal over");

			handleAlarm(target);
			handleRTSub(target);
			persistImgInfo(target);

		} catch (UnsupportedEncodingException ue)
		{
			log.error("picXmlPost fail", ue);
		} catch (JAXBException je)
		{
			log.error("create postRunnableTask Unmarshaller fail!", je);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public byte[] getReqbody() {
		return reqbody;
	}

	public void setReqbody(byte[] reqbody) {
		this.reqbody = reqbody;
	}

	/**
	 * 处理接收结构化信息的持久化
	 * 
	 * @Title: persistImgInfo
	 * @Description: TODO
	 * @param @param target
	 * @return void
	 * @throws
	 */
	private void persistImgInfo(Target target)
	{
		//postDao.persistImgInfo(target); //对收到的结构化数据进行单条持久化处理
		postDao.persistImgInfoBatch(target); //对收到的结构化数据进行批量持久化处理
	}

	/**
	 * 处理接收图片，是否产生告警
	 * 
	 * @Title: handleAlarm
	 * @Description: 待后继版本补充
	 * @param @param target
	 * @return void
	 * @throws
	 */
	private void handleAlarm(Target target)
	{

	}

	/**
	 * 处理接收图片，是否需要实时转发
	 * 
	 * @Title: handleRTSub
	 * @Description: 待后继版本补充
	 * @param @param target
	 * @return void
	 * @throws
	 */
	private void handleRTSub(Target target)
	{

	}

}
