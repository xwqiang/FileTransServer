/**   
* Copyright: Copyright (c) 2012
* @Title: MainService.java 
* @Package com.leon.main 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author Leon
* @date 2012-9-14 ����01:44:38 
* @version V1.0   
*/
package com.leon.main;

import com.leon.thread.FileServer;

/**
 * @author Leon
 * @����01:44:38
 */
public class MainService {

	/** 
	 * @Title: main 
	 * @Description: TODO
	 * @param @param args  
	 * @return void 
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        FileServer server1 = new FileServer(5555,"/usr/local/file_server/sendFileSrc/");
        server1.start();
        FileServer server2 = new FileServer(6666,"/usr/local/file_server/reportFileSrc/");
        server2.start();
        FileServer server3 = new FileServer(7777,"/usr/local/file_server/serverFileSrc/");
        server3.start();
	}

}
