package com.niotest.socketdemo;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * use selector build connection to read and write
 * 
 * 1.build ServerSocketChannel
 * 2.bind ServerSocketChannel
 * 3.Configure channel blocking is false
 * 4.open a selector
 * 5.waiting for connection
 * 6.if selector > 0 
 * 7.iterator all the keys, if key is acceptable, read the data from channel to buffer
 * 		
 * 
 * @author HarveySally
 * @date Sep 8, 2016
 * @time 4:11:23 PM
 */
public class SelectorServerSocketChannelDemo {
	
	public static void selectorStart(){
		ServerSocketChannel channel = null;
		Selector selector = null;
		try {
			//open a serversocketchannel
			channel = ServerSocketChannel.open();
			//bind port
			channel.bind(new InetSocketAddress(19000));
			//block false
			channel.configureBlocking(false);
			
			selector = Selector.open();
			channel.register(selector, SelectionKey.OP_ACCEPT);			
			
			while(true){
				//ready for I/O operations , return a set of keys
				int select = selector.select();
				
				if(select > 0){
					//iterator every key
					for(SelectionKey key:selector.selectedKeys()){
						if(key.isAcceptable()){
							
							SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
							ByteBuffer bf = ByteBuffer.allocate(40);
							
							int size = sc.read(bf);
							
							while(size >0){
								bf.flip();
								Charset charset = Charset.forName("UTF-8");
								System.out.println(charset.newDecoder().decode(bf).toString());
								//bf.clear();
								size = sc.read(bf);
							}
							bf.clear();
							
							ByteBuffer writebf = ByteBuffer.wrap("已经收到您的请求".getBytes("UTF-8"));
							sc.write(writebf);
							//writebf.clear();
							sc.close();
							
							//remove the key 
							selector.selectedKeys().remove(key);
							
						}
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		SelectorServerSocketChannelDemo.selectorStart();
	}
}
