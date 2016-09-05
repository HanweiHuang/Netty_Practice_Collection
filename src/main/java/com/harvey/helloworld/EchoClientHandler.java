package com.harvey.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * ByteBuf（Netty的字节容器）
 * @author HarveySally
 * @date Sep 5, 2016
 * @time 4:03:43 PM
 */
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

	/**
	 * 数据从服务器接受后调用
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
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
