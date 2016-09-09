package com.netttytest.timeExample;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeServerHandler  extends ChannelHandlerAdapter{
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		ByteBuf byteBuf = (ByteBuf)msg;
		byte[] temp = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(temp);
		
		String body = new String(temp,"UTF-8");
		System.out.println("the time server receive order: "+body);
		
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) 
					? new java.util.Date(System.currentTimeMillis()).toString()
					: "BAD QUERY";
		
		//put date to buffer
		ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes());	
		
		ctx.write(response);
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
