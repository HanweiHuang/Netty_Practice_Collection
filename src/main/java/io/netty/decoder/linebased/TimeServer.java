package io.netty.decoder.linebased;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * Server for test of Packet Fragmentation and Assembly
 * @author HarveySally
 * @date Sep 13, 2016
 * @time 6:04:18 PM
 */
public class TimeServer {
	
	public static void start(int port) throws Exception{

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			//settings
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChildChannelHandler());
			
			//bind port
			ChannelFuture future = boot.bind(port).sync();
			System.out.println("server start");
			future.channel().closeFuture().sync();
		} finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		try {
			TimeServer.start(19000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


