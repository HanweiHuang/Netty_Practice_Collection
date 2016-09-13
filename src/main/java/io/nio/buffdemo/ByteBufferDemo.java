package io.nio.buffdemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * This example demonstrate how to 
 * write data from channel to buffer and red date from buffer to console
 * by NIO API. This example provide 2 ways to read date which are read char or read string.
 * The steps as follows
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
	
	/**
	 * read file by char
	 * @param filepath
	 */
	public static void readFileInChar(String filepath){
		RandomAccessFile raf = null;
		FileChannel channel = null;
		try {
			raf = new RandomAccessFile(filepath, "rw");
		    channel = raf.getChannel();
		    
		    //function01
			ByteBuffer bb = ByteBuffer.allocate(10);
			int size = channel.read(bb);
			System.out.print("function 1: ");
			while(size>0){
				
				bb.flip(); //return position to 0， let date available to read.
				
				Charset charset = Charset.forName("UTF-8");
				//System.out.println(charset.newDecoder().decode(bb).toString());
				CharBuffer cc = charset.newDecoder().decode(bb);
				
				while(cc.remaining()>0){
					System.out.print(cc.get());
				}
				
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
	
	/**
	 * read file by string
	 * @param filepath
	 */
	public static void readFileInString(String filepath){
		RandomAccessFile raf = null;
		FileChannel channel = null;
		try {
			raf = new RandomAccessFile(filepath, "rw");
		    channel = raf.getChannel();
		    
		    //function01
			ByteBuffer bb = ByteBuffer.allocate(10);
			int size = channel.read(bb);
			System.out.print("Function 2: ");
			while(size>0){
				
				bb.flip(); //return position to 0， let date available to read.
				
				Charset charset = Charset.forName("UTF-8");
				System.out.print(charset.newDecoder().decode(bb).toString());
						
				bb.clear();//清空缓存给未读完的数据继续读
				size = channel.read(bb);//当size=-1说明没有数据需要读了
			}
			System.out.println();
			
			
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
