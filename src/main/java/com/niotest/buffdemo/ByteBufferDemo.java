package com.niotest.buffdemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * write data from channel to buffer and red date from buffer to console
 * 1 readfile
 * 2 create channel
 * 3 allocate buffer
 * 4 read buffer
 * 5 id size > 0 means read sth -> print it directly
 * 6 if size = 0 nothing need to read
 * @author HarveySally
 * @date Sep 7, 2016
 * @time 11:59:58 PM
 */
public class ByteBufferDemo {
	
	public static void readFile(String filepath){
		RandomAccessFile raf = null;
		FileChannel channel = null;
		try {
			raf = new RandomAccessFile(filepath, "rw");
		    channel = raf.getChannel();
			ByteBuffer bb = ByteBuffer.allocate(88);
			int size = channel.read(bb);
			while(size>0){
				
				bb.flip();
//				while(bb.remaining()>8){
//					System.out.println(bb.getChar());
//				}
				
				/**
				 * function 2： read toString
				 */
				Charset charset = Charset.forName("UTF-8");
				System.out.println(charset.newDecoder().decode(bb).toString());
				
				bb.clear();//清空缓存给未读完的数据继续读
				size = channel.read(bb);//当size=0说明没有数据需要读了
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				raf.close();
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	
}
