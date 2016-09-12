package com.netttytest.timeExample;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * add linebasedframedevoder avoid split package
 * @author HarveySally
 * @date Sep 12, 2016
 * @time 3:46:10 PM
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel socketchannel) throws Exception {
		socketchannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
		socketchannel.pipeline().addLast(new StringDecoder());
		socketchannel.pipeline().addLast(new TimeServerHandler());
	}
	
}
