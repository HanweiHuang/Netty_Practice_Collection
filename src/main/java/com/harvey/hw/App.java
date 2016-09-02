package com.harvey.hw;

import java.nio.ByteBuffer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final int byteOfLong = Long.SIZE / Byte.SIZE;
    	System.out.println(byteOfLong);
    	System.out.println(System.nanoTime());
    }
}
