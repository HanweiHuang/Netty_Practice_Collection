package com.nettytest.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * ByteBuf（Netty的字节容器）
 * @author HarveySally
 * @date Sep 5, 2016
 * @time 4:03:43 PM
 */
@Sharable
public class EchoClientHandler extends ChannelHandlerAdapter{

	/**
	 * 数据从服务器接受后调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf bf = (ByteBuf)msg;
		System.out.println("Client received: " + bf.toString(CharsetUtil.UTF_8));
	}
	
	
	/**
	 * 服务器连接被建立后调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		cause.printStackTrace();
		ctx.channel();
	}


}
