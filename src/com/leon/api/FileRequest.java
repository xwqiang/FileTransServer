package com.leon.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * �������������ļ�
 * ��������Ϊ�����ͻ��˴��͵���Ӧ�����Ը���Ϳͻ��˵������ౣ��һ��
 */
public class FileRequest {

	public static final int FILE_LEN = 4; // ��Ϣ�峤��

	public static final int commandId = 2;//��������

	public int titleLength;//�ļ��ܳ���
	
	public int file_seq ;
	
	
	public String title;//�ļ���

	
	
	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTitleLength() {
		return titleLength;
	}

	public void setTitleLength(int titleLength) {
		this.titleLength = titleLength;
	}

	public FileRequest() {
	}

	
	/**
	 * �����ļ����ȣ��ļ���
	 * */
	public FileRequest(byte[] bytes,int stat) {
		if(stat==1){
		titleLength = byte4ToInteger(bytes, 0);}
		title = new String(bytes); 
	}
//	public ByteBuffer getMessage(File file) {
//
//		ByteBuffer bb = ByteBuffer.allocate(FILE_LEN + HeadMessage.HEAD_LEN);
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream(file);
//			FileChannel fc = fis.getChannel();
//			while (fc.read(bb, 4) != -1) {
//				bb.put(integerToByte(commandId), 0, 4);
//				bb.flip();
//			}
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return bb;
//	}
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

}
