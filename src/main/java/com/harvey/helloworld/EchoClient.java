package com.harvey.helloworld;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端引导需要 host 、port 两个参数连接服务器。
 * @author HarveySally
 * @date Sep 5, 2016
 * @time 4:05:37 PM
 */
public class EchoClient {

	private final String host;
	
	private final int port;
	
	public EchoClient(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void start() throws InterruptedException {
		/**
		 * 处理event的 事件处理器
		 */
		
		EventLoopGroup group = null;
		
		try{
		group = new NioEventLoopGroup();
		
		Bootstrap b = new Bootstrap();
		
		b.group(group)
		.channel(NioSocketChannel.class)
		.remoteAddress(new InetSocketAddress(host,port))
		.handler(new ChannelInitializer<SocketChannel>() {

		/**
		 * 当建立一个连接和一个新的通道时，创建添加到 EchoClientHandler 实例 到 channel pipeline
		 */
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientHandler());
			}
		});
		//连接到远程;等待连接完成
		ChannelFuture f = b.connect().sync();
		f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
//		if(args.length!=2){
//			System.err.println("Usage: " + EchoClient.class.getSimpleName() +
//                    " <host> <port>");
//			return;
//		}
		
//		final String host = args[0];
//		
//		final int port = Integer.parseInt(args[1]);
		
		new EchoClient("192.168.0.5", 19000).start();
		
	}
}
