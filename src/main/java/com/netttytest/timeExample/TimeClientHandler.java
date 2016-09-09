package com.netttytest.timeExample;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeClientHandler extends ChannelHandlerAdapter {
	
	private final ByteBuf firstmessage;

	public TimeClientHandler() {
		
		byte[] message = "QUERY TIME ORDER".getBytes();
		
		firstmessage = Unpooled.buffer(message.length);
		
		firstmessage.writeBytes(message);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		ByteBuf bf = (ByteBuf)msg;
		byte[] req = new  byte[bf.readableBytes()];
		bf.readBytes(req);
		String str = new String(req,"UTF-8");
		System.out.println("Now is: " + str);
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(firstmessage);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		cause.printStackTrace();
		ctx.close();
		
	}
}
