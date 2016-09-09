package com.netttytest.timeExample;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.SocketChannel;


public class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel socketchannel) throws Exception {
		socketchannel.pipeline().addLast(new TimeServerHandler());
	}
	
}
