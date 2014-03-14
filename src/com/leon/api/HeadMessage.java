package com.leon.api;

import java.nio.ByteBuffer;


/**
 * ��Ϣͷ
 * */
public class HeadMessage {

	public static final int HEAD_LEN = 12;//��Ϣͷ����

	private int commandId; // ͨ����Ϣ������͸�ֵ

	private int total_length; // 4�ֽ��ļ��Ĵ�С���ȣ������������������������ô�С
	
	private  int file_seq;

	public int getTotal_length() {
		return total_length;
	}

	public void setTotal_length(int total_length) {
		this.total_length = total_length;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}

	public HeadMessage() {}
	
    /**
     * ������Ϣͷ
     * */
	public HeadMessage(byte[] buffer) {

		this.commandId = byte4ToInteger(buffer, 0);
		this.total_length = byte4ToInteger(buffer, 4);
		this.file_seq = byte4ToInteger(buffer, 8);
	}
	
	public HeadMessage(ByteBuffer buffer) {
        
		this.commandId = buffer.getInt();
		this.total_length = buffer.getInt();
		this.file_seq = buffer.getInt();
		debugData("rev head array: ",buffer.array());
		System.out.println("**************file_seq is: "+file_seq+"**********************");
	}
    /**
     * ��ȡ��Ϣͷ
     * */
	public byte[] getHead() {
		byte[] bytes = new byte[HEAD_LEN];
		byte[] byteId = integerToByte(commandId);
		byte[] bytetotal = integerToByte(total_length);
		byte[] fileseq = integerToByte(file_seq);
		System.arraycopy(byteId, 0, bytes, 0, 4);
		System.arraycopy(bytetotal, 0, bytes, 4, 4);
      
		System.arraycopy(fileseq, 0, bytes, 8, 4);
		return bytes;
	}
    /**
     * ����ת�ֽ�
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
     * �ֽ�ת����
     * */
	public static int byte4ToInteger(byte[] b, int offset) {
		return (0xff & b[offset]) << 24 | (0xff & b[offset + 1]) << 16
				| (0xff & b[offset + 2]) << 8 | (0xff & b[offset + 3]);
	}
	public static void debugData(String desc,byte[] data){
		System.out.println("��Ϣ�ܳ�:"+data.length +" "+desc);		
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
