package com.niotest.buffdemo;

import io.nio.buffdemo.ByteBufferDemo;

import org.junit.Test;

public class ByteBufferTest{

	@Test
	public void byteBufferWriteTest(){
		ByteBufferDemo.readFileInString("files/test.txt");
		ByteBufferDemo.readFileInChar("files/test.txt");
	}
}
