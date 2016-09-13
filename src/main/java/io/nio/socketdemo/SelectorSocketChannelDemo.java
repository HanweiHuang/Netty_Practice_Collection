package io.nio.socketdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SelectorSocketChannelDemo {
	
	public static void start(){
		SocketChannel socketChannel;
		try {
			//settings
			socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress("localhost",19000));
			socketChannel.configureBlocking(false);
			
			//open a selector and register it in channel
			Selector selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_READ);
			
			//write message to channel
			ByteBuffer byteBuffer = ByteBuffer.wrap("Hello server,this selector client".getBytes("UTF-8"));
			socketChannel.write(byteBuffer);
			byteBuffer.clear();
			
			//new thread for receiving message
			new ClientReader(selector).start();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		SelectorSocketChannelDemo.start();
	}

}
