package io.nio.socketdemo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientReader extends Thread{

	private Selector selector;

	public ClientReader(Selector selector) {
		super();
		this.selector = selector;
	}
	
	@Override
	public void run() {
		
		try {
			//waiting fro request
			while(selector.select()>0){
				
				for(SelectionKey key: selector.keys()){
					//get relevent channel by key
					SocketChannel schannel = (SocketChannel)key.channel();
					ByteBuffer bf = ByteBuffer.allocate(40);
					int size = schannel.read(bf);
					
					while(size>0){
						bf.flip();
						Charset charset = Charset.forName("UTF-8");
						System.out.println(charset.newDecoder().decode(bf));
						size = schannel.read(bf);
					}
					bf.clear();
					//remove key when finish operation
					selector.selectedKeys().remove(key);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
