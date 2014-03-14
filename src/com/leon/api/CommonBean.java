package com.leon.api;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class CommonBean {

	//private SmgpUserBean smgpUserBean;

	private ByteBuffer headerBuffer = ByteBuffer.allocate(HeadMessage.HEAD_LEN);

	private ByteBuffer bodyBuffer;

	private HeadMessage headMessage;
	
	private long start_time;

	/**
	 * 重置变量准备下次读取
	 */
	public void reset(){
		headerBuffer.clear();
		bodyBuffer = null;
		start_time = System.currentTimeMillis();
	}

	/**
	 * 从管道中读取数据
	 * @param socketChannel
	 * @throws Exception
	 */
	public void read(SocketChannel socketChannel) throws Exception{
		/*
		socketChannel.read(headerBuffer);
		headerBuffer.flip();
		smgpHeader = new SmgpHeader(headerBuffer);
		
		if(smgpHeader.getPacketLength() > 65536)
			throw new Exception("invalid packet length");
		
		bodyBuffer = ByteBuffer.allocate(smgpHeader.getPacketLength() - SmgpHeader.HeaderLength);
		socketChannel.read(bodyBuffer);
		*/
		System.out.println("request from : "+socketChannel.socket().getInetAddress()+" port is : "+socketChannel.socket().getLocalPort());
		if(headerBuffer.hasRemaining() && socketChannel.read(headerBuffer) == -1){
			throw new Exception("invalid packet length");
		}else if(bodyBuffer == null){
			headerBuffer.flip();
			headMessage = new HeadMessage(headerBuffer);
			//System.out.println("total_length is: "+headMessage.getTotal_length());
			//System.out.println(" total_length binary is :"+java.lang.Integer.toBinaryString(headMessage.getTotal_length()));
			if(headMessage.getTotal_length()>65536)
				throw new Exception("invalid packet length packet is much big");
			//System.out.println("total_length:"+headMessage.getTotal_length());
			
			bodyBuffer = ByteBuffer.allocate(headMessage.getTotal_length() - HeadMessage.HEAD_LEN);
		}
		
			start_time = System.currentTimeMillis();
		if(bodyBuffer.hasRemaining() && socketChannel.read(bodyBuffer) == -1){
			
			throw new Exception("invalid packet length,body is not read finish!");
		}
		
		System.out.println("read from socket !!!");
		
	}
	
	/**
	 * 数据包是否读取完成
	 * @return
	 */
	public boolean isReady(){
		
		return bodyBuffer != null && bodyBuffer.hasRemaining() == false;

	}


	
	public ByteBuffer getHeaderBuffer() {
		return headerBuffer;
	}

	public void setHeaderBuffer(ByteBuffer headerBuffer) {
		this.headerBuffer = headerBuffer;
	}

	public ByteBuffer getBodyBuffer() {
		return bodyBuffer;
	}

	public void setBodyBuffer(ByteBuffer bodyBuffer) {
		this.bodyBuffer = bodyBuffer;
	}

	/**
	 * @return the headMessage
	 */
	public HeadMessage getHeadMessage() {
		return headMessage;
	}

	/**
	 * @param headMessage the headMessage to set
	 */
	public void setHeadMessage(HeadMessage headMessage) {
		this.headMessage = headMessage;
	}

	/**
	 * @return the start_time
	 */
	public long getStart_time() {
		return start_time;
	}

	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
    

}
