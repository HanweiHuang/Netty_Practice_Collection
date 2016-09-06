package com.harvey.transport;

import io.netty.channel.ServerChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * achieve an implement of I/O by NIO without Netty
 * 1.create ServerSocketChannel
 * 2.configure block == false
 * 3.create socket
 * 4.create InetSocketAddress
 * 5.socket bind address
 * 6.open a selector
 * 7.select register channel
 * 8.set message
 * 9.waiting for request
 * 10.get selectedkeys from selector.
 * 11.iterator the selectedkeys, if key is a new connection, prepare for receiving 
 * 12.if key is ready for writing, the data will write to client
 * 13.close the connection
 * @author HarveySally
 * @date Sep 6, 2016
 * @time 11:48:44 AM
 */
public class PlainNioServer {
	public void serve(int port){
		try {
			ServerSocketChannel serverSocChannel = ServerSocketChannel.open();
			serverSocChannel.configureBlocking(false);
			ServerSocket serverSocket = serverSocChannel.socket();
			InetSocketAddress isAddress = new InetSocketAddress(port);
			serverSocket.bind(isAddress);
			Selector selector = Selector.open();
			
			serverSocChannel.register(selector, SelectionKey.OP_ACCEPT);
			final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
			
			for(;;){
				selector.select();
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = readyKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey key = iterator.next();
					iterator.remove();
					try{
						//新的连接
						if(key.isAcceptable()){
							ServerSocketChannel server = (ServerSocketChannel)key.channel();
							SocketChannel client = server.accept();
							client.configureBlocking(false);
							client.register(selector, 
									SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
							System.out.println("Accept connection from "+client);
						}
						
						//ready for writing
						if(key.isWritable()){
							SocketChannel client = (SocketChannel)key.channel();
							ByteBuffer buffer = (ByteBuffer)key.attachment();
							while(buffer.hasRemaining()){
								if(client.write(buffer)==0){
									break;
								} 
							}
							
							client.close();
						}
						
					} catch(Exception e){
						e.printStackTrace();
					} finally {
						key.cancel();
						key.channel().close();
					}
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
