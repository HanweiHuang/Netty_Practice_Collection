package com.netttytest.timeExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
	public static void start(String host,int port) throws Exception{
	
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap bs = new Bootstrap();
			
			bs.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
	
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
					sc.pipeline().addLast(new StringDecoder());
					sc.pipeline().addLast(new TimeClientHandler());
				}
				
			});
			
			ChannelFuture cf = bs.connect(host, port).sync();
			System.out.println("Client start");
			cf.channel().closeFuture().sync();
		}finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		try {
			TimeClient.start("localhost", 19000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
