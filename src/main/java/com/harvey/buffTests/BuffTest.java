package com.harvey.buffTests;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class BuffTest {

	public static void main(String[] args) {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1
		
		/**
		 * slice() example
		 */
		ByteBuf sliced = buf.slice(0, 14);          //slice buf from index 0 - 14
		System.out.println(sliced.toString(utf8));  //print
		
		buf.setByte(0, (byte) 'J');                 //set first byte of buf is 'J';
		//assert runs successful since function slice works on the same object
		assert buf.getByte(0) == sliced.getByte(0); 
		
		/**
		 * copy() example
		 */
		ByteBuf copy = buf.copy(0, 14);               //2
		System.out.println(copy.toString(utf8));      //3

		buf.setByte(0, (byte) 'J');                   
		//assert runs successful since function copy() copied the other object
		assert buf.getByte(0) != copy.getByte(0);
	}
}
