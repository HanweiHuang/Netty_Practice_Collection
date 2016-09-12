package com.netttytest.timeExample;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeClientHandler extends ChannelHandlerAdapter {
	
	private byte[] message;
	
	private int counter;

	public TimeClientHandler() {
		
		message = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
		
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
//		ByteBuf bf = (ByteBuf)msg;
//		byte[] req = new  byte[bf.readableBytes()];
//		bf.readBytes(req);
//		String str = new String(req,"UTF-8");
		String str = (String)msg;
		System.out.println("Now is: " + str);
		System.out.println("The counter is "+ ++counter);
	}
	
	/**
	 * send 100 packages to servers
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		ByteBuf firstmessage = null;
		for (int i = 0; i < 100; i++) {
			firstmessage = Unpooled.buffer(message.length);
			firstmessage.writeBytes(message);
			ctx.writeAndFlush(firstmessage);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		cause.printStackTrace();
		ctx.close();
		
	}
}
