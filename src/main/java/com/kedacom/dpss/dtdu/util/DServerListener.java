/**
* 苏州科达通信集团 
* 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
* 文件名: DServerListener.java 2013-11-8
* 包名: com.kedacom.kdm.dpss.common.communication 
* 描述: DPSS TCP连接监听类 
* 作者:zhangkai    
* 修改日期: 2013-11-8 下午3:26:23 
* 版本: V1.0   
*/
package com.kedacom.dpss.dtdu.util;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.net.InetSocketAddress;

import kedanet.log.KedaLogger;

/**
 * @author zhangkai
 *
 */
public class DServerListener {
	
	private static final KedaLogger log = KedaLogger.getLogger(DServerListener.class);
	
	private String ip = null;
	private int port ;
	private ServerSocketChannel ssc = null; //服务器端socket
	private InetSocketAddress  sockAddr = null; //服务器端socket绑定地址
	private Selector svrSelector= null; //服务器端的接收选择器
	
	/**
	 * DServerListener的构造函数，完成服务器端socket的监听
	 * @param ipaddress
	 * @param port
	 */
	public DServerListener(String ipaddress, int port)
	{
		this.setIp(ipaddress);
		this.setPort(port);
	}
	
	/**
	 * 构造函数函数
	 */
	public DServerListener()
	{
		
	}
	
	/**
	 * 监听函数，监听来自客户端的连接
	* @Title: listen 
	* @Description: TODO 
	* @param @param dSocketConnectorClass  连接的客户端类  
	* @return void
	* @throws
	 */
	public void listen(Class<?> dSocketConnectorClass) throws IOException,InterruptedException,IllegalAccessException,InstantiationException
	{
	    if(null == ip)	
	    {
	    	sockAddr = new InetSocketAddress(port);
	    }else
	    {
	    	sockAddr = new InetSocketAddress(ip,port);
	    }
	    
	    ssc = ServerSocketChannel.open();
	    ssc.configureBlocking(false); //设置非阻塞模式
	    ssc.socket().bind(sockAddr);
	    svrSelector = Selector.open();
	    SelectionKey acceptKey = ssc.register(svrSelector,SelectionKey.OP_ACCEPT );
	    log.info(sockAddr.toString()+" is listening!");
	    
	    while(true) //服务器端循环监听
	    {
	    	svrSelector.select();
	    	if(acceptKey.isAcceptable()) //有客户端连上来了
	    	{
	    		SocketChannel sc = ((ServerSocketChannel) acceptKey.channel()).accept();
	    		log.info("client :"+sc.socket().getRemoteSocketAddress()+" connected!");
	    		sc.configureBlocking(false); //设置socketchannel为非阻塞
	    		DSocketConnector d1 = (DSocketConnector)dSocketConnectorClass.newInstance();
	    		d1.setChannel(sc);
	    		d1.startWork();
	    	}
	    	Thread.sleep(3); //隔3毫秒进行循环	
	    }
		
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
