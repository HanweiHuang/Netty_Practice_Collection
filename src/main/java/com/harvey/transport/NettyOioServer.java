package com.harvey.transport;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;


public class NettyOioServer {

	public void server(int port) throws InterruptedException{
		
		final ByteBuf buf = Unpooled.unreleasableBuffer(
	                Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
			
		EventLoopGroup group = new OioEventLoopGroup();
		try{
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(group);
			
			//OioServerSocketChannel 表示是阻塞I/O
			serverBootstrap.channel(OioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			/**/
			.childHandler(new ChannelInitializer<SocketChannel>() {
	
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
						
						@Override
						public void channelActive(ChannelHandlerContext ctx)
								throws Exception {
							ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
							
						}
						
					});
				}
				
			});
			/**/
			ChannelFuture cf = serverBootstrap.bind().sync();
			cf.channel().closeFuture().sync();
		}finally {
			group.shutdownGracefully().sync();
		}
	}	
}
