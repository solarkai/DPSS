/**
 * 苏州科达通信集团 
 * 版权:Copyright © Suzhou Keda Technology Co.Ltd. All Rights Reserved.  
 * 文件名: DSocketConnector.java
 * 包名: com.kedacom.kdm.dpss.common.communication 
 * 描述: 使用socket通信的连接器，接收和处理消息数据 
 * 作者:zhangkai 
 * 创建日期:2013-11-8  
 * 修改人:zhangkai   
 * 修改日期: 2013-11-8
 * 版本: V1.0   
 */
package com.kedacom.dpss.dtdu.util;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;

import kedanet.log.KedaLogger;

/**
 * @author zhangkai
 * 
 */
public abstract class DSocketConnector {

	private static final KedaLogger log = KedaLogger.getLogger(DSocketConnector.class);

	public static final int READ_BUFFER_SIZE = 1024;

	private SocketChannel channel = null; // 与客户端连过来的socketchannel

	/**
	 * 在连接的socketchannel开始工作
	 * 
	 * @Title: readData
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void startWork()
	{
		(new ListenThread()).start();
	}

	/**
	 * 向通道中写入数据
	 * 
	 * @Title: writeData
	 * @Description: TODO
	 * @param @param bb
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public void writeData(ByteBuffer bb) throws IOException
	{
		if ((null != channel) && (channel.isConnected()) && (null != bb))
		{
			channel.write(bb);
		} else
		{
			log.info("write to channel " + channel.toString() + " fail!");
		}
	}

	/**
	 * 读取通道中的数据
	 * 
	 * @Title: readData
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public abstract void readData();


	/**
	 * 作为客户端连接服务器
	 * 
	 * @Title: connectServer
	 * @Description: TODO
	 * @param @param ip
	 * @param @param port
	 * @return void
	 * @throws
	 */
	public void connectServer(String ip, int port) throws IOException
	{
		InetSocketAddress saddr = null;
		if (null == ip)
		{
			saddr = new InetSocketAddress(port);
		} else
		{
			saddr = new InetSocketAddress(ip, port);
		}
		channel = SocketChannel.open(saddr);
		channel.configureBlocking(false);

	}

	public SocketChannel getChannel() {
		return channel;
	}

	public void setChannel(SocketChannel channel) {
		this.channel = channel;
	}

	/**
	 * 通道接收监听线程
	 * 
	 * @author zhangkai
	 * 
	 */
	private class ListenThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			DSocketConnector.this.readData();
		}
	}

}
