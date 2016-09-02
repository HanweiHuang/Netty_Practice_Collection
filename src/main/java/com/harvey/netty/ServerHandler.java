package com.harvey.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 服务器端
 * @author HarveySally
 * @date Sep 2, 2016
 * @time 4:58:30 PM
 */
public class ServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//获得接受时间
		LoopBackTimeStamp lbts = (LoopBackTimeStamp)msg;
		lbts.setRecvTimeStamp(System.nanoTime());
		
	    System.out.println("loop delay in ms : " + 1.0 * lbts.timeLapseInNanoSecond() / 1000000L);
	}
	
	// Here is how we send out heart beat for idle to long
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
	  if (evt instanceof IdleStateEvent) {
	    IdleStateEvent event = (IdleStateEvent) evt;
	    if (event.state() == IdleState.ALL_IDLE) { // idle for no read and write
	      ctx.writeAndFlush(new LoopBackTimeStamp());
	    }
	  }
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	    // Close the connection when an exception is raised.
	    cause.printStackTrace();
	    ctx.close();
	}
	
	
}
