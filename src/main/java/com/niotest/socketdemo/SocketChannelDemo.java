package com.niotest.socketdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
/**
 * an example demonstrate how to achieve a client send message to server
 * and display the message which got from server by NIO API
 * 
 * 
 * @author HarveySally
 * @date Sep 8, 2016
 * @time 1:17:04 PM
 */
public class SocketChannelDemo {

	public static void start() throws IOException, InterruptedException{
		SocketChannel schannel = null;
		try {
			schannel = SocketChannel.open();
			schannel.connect(new InetSocketAddress("localhost", 19000));
			//schannel.configureBlocking(false);
			
			String message = "Hello Server, this is clent 01";
			ByteBuffer bb = ByteBuffer.wrap(message.getBytes("UTF-8"));
			schannel.write(bb);
			System.out.println("finish send");
			bb.clear();
			
			/**
			 * after sending, reveive message from server
			 * attention: dont allocate odd number for bytebuffer, because one char will cover 2 bytes 
			 */
			ByteBuffer readbuff = ByteBuffer.allocate(12);
			int size = schannel.read(readbuff);
			
			while(size>0){
				readbuff.flip();
				Charset charset = Charset.forName("UTF-8");
				System.out.print(charset.newDecoder().decode(readbuff));
				readbuff.clear();
				size = schannel.read(readbuff);
				
			}
			readbuff.clear();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			schannel.close();
		}
		
	}
	public static void main(String[] args) {
		try {
			SocketChannelDemo.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
