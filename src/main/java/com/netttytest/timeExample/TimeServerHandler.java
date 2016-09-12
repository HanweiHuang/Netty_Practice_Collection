package com.netttytest.timeExample;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeServerHandler  extends ChannelHandlerAdapter{
	
	private int counter;
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
//		ByteBuf byteBuf = (ByteBuf)msg;
//		byte[] temp = new byte[byteBuf.readableBytes()];
//		byteBuf.readBytes(temp);
//		
//		String body = new String(temp,"UTF-8").substring(0,temp.length - System.getProperty("line.separator").length());
		String body = (String)msg;
		System.out.println("the time server receive order: "+ body + "; the counter is:"+ ++counter);
		
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) 
					? new java.util.Date(System.currentTimeMillis()).toString()
					: "BAD QUERY";
		
		currentTime = currentTime + System.getProperty("line.separator");			
					
		//put date to buffer
		ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes());	
		
		ctx.writeAndFlush(response);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		ctx.close();
	}

}
