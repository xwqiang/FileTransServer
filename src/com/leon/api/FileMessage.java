package com.leon.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class FileMessage {
	
	
	public static boolean createFileByMessage(HeadMessage head ,ByteBuffer body,String location,SocketChannel socket){
		boolean match_result = false;
		try {
			int total_length = 0;
			int file_seq = 0;
			if(head.getCommandId()==2){
				total_length = head.getTotal_length();
				file_seq = head.getFile_seq();
				byte[] file_MD5_bytes= new byte[32];
				body.get(file_MD5_bytes);
				String file_MD5 = new String(file_MD5_bytes);
				int file_name_length = body.getInt();
				byte[] file_name = new byte[file_name_length];
				System.out.println("receive file  "+file_name);
				body.get(file_name);
				String file_name_str = new String(file_name);
				File file = new File(location+file_name_str);
				FileOutputStream fos = new FileOutputStream(file);
				FileChannel fc = fos.getChannel();
				body.compact();
				body.flip();
				fc.write(body);
				System.out.println("file_size is: "+body.position());
				fc.close();
				fos.close();
				String received_file_MD5 = FileDigest.getMD5(file);
				if(received_file_MD5.equals(file_MD5)||file_MD5==received_file_MD5){
					match_result  = true;
				}else {
					match_result = false;
					file.delete();
					System.out.println("======================file trans err,delete this file: "+file.getName()+" remoter md5 is:"+file_MD5+" local md5 is:"+received_file_MD5);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		return match_result;
	}

}
