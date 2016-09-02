package com.harvey.hw;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * 输出数据的 编码器
 * @author HarveySally
 * @date Sep 2, 2016
 * @time 4:48:30 PM
 */
public class TimeStampEncoder extends MessageToByteEncoder<LoopBackTimeStamp>{

	@Override
	protected void encode(ChannelHandlerContext ctx, LoopBackTimeStamp msg,
			ByteBuf out) throws Exception {
		//loopBackTimeStamp 类型的数据msg转换成byte数组
		out.writeBytes(msg.toByteArray());
	}

}
