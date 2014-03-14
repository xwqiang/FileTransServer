/**   
* Copyright: Copyright (c) 2012
* @Title: FileServer.java 
* @Package com.leon.thread 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Leon
* @date 2012-9-14 上午10:06:40 
* @version V1.0   
*/
package com.leon.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.leon.api.CommonBean;
import com.leon.api.FileMessage;
import com.leon.api.FileResponse;

/**
 * @author Leon
 * @上午10:06:40
 */
public class FileServer extends Thread{
	private int port;
	private String storage_location;
	boolean running = false;
	public FileServer(int port,String storage_location){
		this.port = port;
		this.storage_location = storage_location;
	}
	
	public void run(){
		Selector selector = null;
		try {
			selector = Selector.open();
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("bind port is: "+port);
			ssc.socket().bind(new InetSocketAddress(port));
			System.out.println("----服务器"+port+"端口监听启动----");
			running = true;
			int clientCount =0;
			while(running){
				int keyCount = selector.select();
				if(keyCount>0){
					Iterator<SelectionKey> keys =  selector.selectedKeys().iterator();
					SelectionKey key = null;
					while(keys.hasNext()){
						key = keys.next();
						try{
							if(key.attachment()==null){
								key.attach(new CommonBean());
								System.out.println("--------------key attch success---------------");
							}
							if(key.isAcceptable()){
								
								ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
								SocketChannel socketChannel = serverSocketChannel.accept();
								socketChannel.configureBlocking(false);
								socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
								//绑定对象
							}
							if(key.isReadable()){
								SocketChannel socketChannel = (SocketChannel)key.channel();
								
								CommonBean commonBean = (CommonBean)key.attachment();
								commonBean.read(socketChannel);
								if(commonBean.isReady()){
									commonBean.getBodyBuffer().flip();
									boolean match_result = FileMessage.createFileByMessage(commonBean.getHeadMessage(), commonBean.getBodyBuffer(), storage_location,socketChannel);
									commonBean.reset();
									System.out.println("total_length is: "+commonBean.getHeadMessage().getTotal_length()+" seq  is: "+commonBean.getHeadMessage().getFile_seq());
									FileResponse response = new FileResponse(commonBean.getHeadMessage().getFile_seq());
									System.out.println(" header get file_seq is : "+commonBean.getHeadMessage().getFile_seq());
									if (match_result) {
										response.setCommandId(8);
										System.out.println("receive file success!");
									}else {
										response.setCommandId(6);
									}
									
									socketChannel.write(response.getMessage());
								
								}
							}
							sleep(1);
						}catch(Exception e){
							e.printStackTrace();
							key.cancel();
							key.channel().close();
						}finally{
							keys.remove();
						}
					}
				}
			}
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
