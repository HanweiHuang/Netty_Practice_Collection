package com.niotest.buffdemo;

import org.junit.Test;

import com.niotest.buffdemo.ByteBufferDemo;

public class ByteBufferTest{

	@Test
	public void byteBufferWriteTest(){
		ByteBufferDemo.readFileInString("files/test.txt");
		ByteBufferDemo.readFileInChar("files/test.txt");
	}
}
