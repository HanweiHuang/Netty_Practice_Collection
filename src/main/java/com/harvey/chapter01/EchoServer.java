package com.harvey.chapter01;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	private final int port;
	
	public EchoServer(int port){
		this.port = port;
	}
	
	private void start() throws InterruptedException {
		NioEventLoopGroup group = null;
		ServerBootstrap bootstrap = null;
		try {
			group = new NioEventLoopGroup();
			bootstrap = new ServerBootstrap();
			bootstrap.group(group)                        //4
	        .channel(NioServerSocketChannel.class)        //5
	        .localAddress(new InetSocketAddress(port))    //6
	        .childHandler(new ChannelInitializer<SocketChannel>() { //7
	            @Override
	            public void initChannel(SocketChannel ch) throws Exception {
	                ch.pipeline().addLast(
	                        new EchoServerHandler());
	            }
	        });
			
			ChannelFuture f = bootstrap.bind().sync();
			System.out.println(EchoServer.class.getName()+": started and listening to" + f.channel().localAddress());
			f.channel().closeFuture().sync();
		}
		finally{
			group.shutdownGracefully().sync();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
//		if (args.length != 1) {
//            System.err.println(
//                    "Usage: " + EchoServer.class.getSimpleName() +
//                    " <port>");
//            return;
//        }
//		
//		int port = Integer.parseInt(args[0]);
		new EchoServer(19000).start();
	}

	
}
