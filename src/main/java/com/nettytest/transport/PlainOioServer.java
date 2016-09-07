package com.nettytest.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * use java api achieve an implement of I/O without netty.
 * 1.bind serversockert with port
 * 2.waiting for the connection
 * 3.open a new thread for write out "HI" to server
 * 4.close socket
 * @author HarveySally
 * @date Sep 6, 2016
 * @time 12:47:12 AM
 */
public class PlainOioServer {
	
	public void serve(int port) throws IOException{
		try {
			final ServerSocket serverSocket = new ServerSocket(port);//1
			for(;;){
				final Socket clientSocket = serverSocket.accept(); //2
				
				new Thread(new Runnable() {
					
					public void run() {
						OutputStream out;
						try {
							out = clientSocket.getOutputStream();
							out.write("hello world".getBytes(Charset.forName("UTF-8")));
							out.flush();
							clientSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally{
							try {
								clientSocket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
