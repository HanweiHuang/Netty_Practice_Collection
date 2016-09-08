package com.niotest.socketdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * This example demonstrate the way of server 
 * how to receive the data from client and send back to client
 * by NIO API
 * 
 * 1.open ServerSocketChannel
 * 2.bind socket with channel
 * 3.configure blocking is false
 * 4.waiting for the data from client
 *  	1.build bytebuffer if channel accept data.
 *  	2.read bytebuffer repeatedly until size return 0;
 *  		*flip the buffer every read
 *  		*set charset 
 *  		*add decoder for bytes
 *  		*clear buffer for next read.  
 *  	
 * 5.after read send message back to client
 * 
 * 6.close channel
 * @author HarveySally
 * @date Sep 8, 2016
 * @time 12:07:01 PM
 */
public class ServerSocketChannelDemo {

	public static void start() throws Exception{
		ServerSocketChannel sschannel = null;
		try {
			sschannel = ServerSocketChannel.open();
			sschannel.socket().bind(new InetSocketAddress(19000));
			sschannel.configureBlocking(false);
			System.out.println("Server Start");
			while(true){
				
				SocketChannel schannel = sschannel.accept();
				if(schannel!=null){
					ByteBuffer bf = ByteBuffer.allocate(36);
					int size = schannel.read(bf);
					while(size>0){
						bf.flip();
						Charset charset = Charset.forName("UTF-8");
						System.out.print(charset.newDecoder().decode(bf));
						
						System.out.println(bf.hasRemaining());
						if(bf.capacity()>bf.position()){
							size = 0;
						}else{
							bf.compact();
							size = schannel.read(bf);
						}
					}
					bf.clear();
					
					
					/**
					 * after read send message back to client
					 */
					ByteBuffer response = ByteBuffer.wrap("Thank you clients, get your message".getBytes("UTF-8"));
					schannel.write(response);					
					response.clear();
					
					schannel.close();
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
		}
	}
	
	public static void main(String[] args) {
		try {
			ServerSocketChannelDemo.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
