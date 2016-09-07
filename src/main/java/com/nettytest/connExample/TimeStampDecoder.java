package com.nettytest.connExample;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 输入数据的  解码器
 * @author HarveySally
 * @date Sep 2, 2016
 * @time 4:48:52 PM
 */
public class TimeStampDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		final int messageLength = Long.SIZE/Byte.SIZE *2;
	    if (in.readableBytes() < messageLength) {
	      return;
	    }
	     
	    byte [] ba = new byte[messageLength];
	    in.readBytes(ba, 0, messageLength);  // block until read 16 bytes from sockets
	    LoopBackTimeStamp loopBackTimeStamp = new LoopBackTimeStamp();
	    loopBackTimeStamp.fromByteArray(ba);
	    out.add(loopBackTimeStamp);
	}

}
