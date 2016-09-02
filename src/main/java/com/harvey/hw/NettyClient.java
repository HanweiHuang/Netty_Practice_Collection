package com.harvey.hw;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	public static void main(String[] args) {
		NioEventLoopGroup workgroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workgroup);
		b.channel(NioSocketChannel.class);
		
		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new TimeStampEncoder(),new TimeStampDecoder(), new ClientHandler());
			}
		});
		
		String serverIp = "192.168.0.5";
		b.connect(serverIp, 19000);
	}
}
