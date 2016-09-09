package com.nettytest.transport;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Random;

import com.nettytest.helloworld.EchoServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class NettyNioServer {

	public void server(int port) throws InterruptedException{
		
		final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
		
		//1.event loop group
		NioEventLoopGroup group = new NioEventLoopGroup();
		//2.boot strap
		ServerBootstrap serverBootStrap = null;
		try {
			serverBootStrap = new ServerBootstrap();
			//register group
			serverBootStrap.group(group);
			//setting channel
			serverBootStrap.channel(NioServerSocketChannel.class)
			//bind address
			.localAddress(new InetSocketAddress(port))
			//add handler
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//add handler to pipeline
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//						@Override
//						public void channelActive(ChannelHandlerContext ctx)
//								throws Exception {
//							ctx.writeAndFlush(buf.duplicate())
//								.addListener(ChannelFutureListener.CLOSE);
//						}
						
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg)
								throws Exception {
							
							ByteBuf in = (ByteBuf)msg;
							while(in.isReadable()){
								System.out.print((char)in.readByte());
								System.out.flush();
							}
							ctx.write(buf);
						}
						@Override
						public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
							//冲刷所有待审消息到远程节点。添加一个future 监听，当关闭通道后，操作完成 
							ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
							.addListener(ChannelFutureListener.CLOSE);
						}
						@Override
						public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
								throws Exception {
							cause.printStackTrace();
							ctx.close();
						}
					});
				}
				
			});
			//bind a new link channel
			ChannelFuture cf = serverBootStrap.bind().sync();
			System.out.println(NettyNioServer.class.getName()+": started and listening to" + cf.channel().localAddress());
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully().sync();
		}
		
		
	}
	
	public static void main(String[] args) {
		try {
			new NettyNioServer().server(19000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
