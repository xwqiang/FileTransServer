package com.leon.api;

import java.nio.ByteBuffer;

/**
 * 服务器端正确获取文件后给客户端返回的响应
 * */
public class FileResponse {
	
	
	
	private  int commandId ;//响应类型,这里是正确响应
    private int file_seq;
    public  FileResponse(int file_seq){
    	this.file_seq = file_seq;
    	
    }
	public int getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}
	/**
	 * 将消息头整体放进缓冲区
	 * */
	public ByteBuffer getMessage() {
		
		ByteBuffer bb = ByteBuffer.allocate(HeadMessage.HEAD_LEN);
		HeadMessage head = new HeadMessage();
		head.setCommandId(commandId);
		head.setTotal_length(12);
		head.setFile_seq(file_seq);
		System.out.println("head getMessage file_seq is: "+file_seq);
		bb.put(head.getHead());
		
		debugData("response head is: ",bb.array());
		bb.flip();
		return bb;
	}
    /**
     * 整型转字节
     * */
	public static byte[] integerToByte(int n) {
		byte b[] = new byte[4];
		b[0] = (byte) (n >> 24);
		b[1] = (byte) (n >> 16);
		b[2] = (byte) (n >> 8);
		b[3] = (byte) n;
		return b;
	}
	/**
	 * @return the commandId
	 */
	public int getCommandId() {
		return commandId;
	}
	/**
	 * @param commandId the commandId to set
	 */
	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}
	public static void debugData(String desc,byte[] data){
		System.out.println("消息总长:"+data.length +" "+desc);		
		int count=0;
	      for(int i=0;i<data.length;i++){
	    	 int b=data[i];
	    	  if(b<0){b+=256;}
	    	 String hexString= Integer.toHexString(b);
	         hexString = (hexString.length() == 1) ? "0" + hexString : hexString;
	    	 System.out.print(hexString+"  ");
	    	 count++;
	    	 if(count%4==0){
	    		 System.out.print( "  ");
	    	 }
	    	 if(count%16==0){
	    		 System.out.println();
	    	 }
	      }
	      System.out.println();
    }
}
