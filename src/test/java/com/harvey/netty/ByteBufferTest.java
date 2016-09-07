package com.harvey.netty;

import org.junit.Test;

import com.niotest.buffdemo.ByteBufferDemo;

public class ByteBufferTest{

	@Test
	public void byteBufferWriteTest(){
		ByteBufferDemo.readFile("files/test.txt");
	}
}
