package com.harvey.chapter01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;


/**
 * 本来要实现ChannelInboundHandler接口，但是这是一个简单的程序，继承ChannelInboundHandlerAdapter
 * 这个类已经实现了ChannelInboundHandler接口的方法，只要override就可以了。
 * @author HarveySally
 * @date Sep 4, 2016
 * @time 1:04:46 AM
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	
	/**
	 * 每个信息入站都会调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		ByteBuf in = (ByteBuf)msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);
	}
	
	/**
	 * 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//冲刷所有待审消息到远程节点。添加一个future 监听，当关闭通道后，操作完成 
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
		.addListener(ChannelFutureListener.CLOSE);
	}
	
	/**
	 * 读操作时捕获到异常时调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
