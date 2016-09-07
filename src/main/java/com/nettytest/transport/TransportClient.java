package com.nettytest.transport;

import java.net.InetSocketAddress;

import com.nettytest.helloworld.EchoServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

/**
 * 客户端引导需要 host 、port 两个参数连接服务器。
 * @author HarveySally
 * @date Sep 5, 2016
 * @time 4:05:37 PM
 */
public class TransportClient {

	private final String host;
	
	private final int port;
	
	public TransportClient(String host, int port){
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
					ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>(){
						@Override
						public void channelActive(ChannelHandlerContext ctx)
								throws Exception {
							ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
							
						}
						
						@Override
						public void channelRead0(ChannelHandlerContext ctx,
								ByteBuf msg) throws Exception {
							System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));
							
						}
						
						
					});
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
		
		new TransportClient("192.168.0.5", 19000).start();
		
	}
}
