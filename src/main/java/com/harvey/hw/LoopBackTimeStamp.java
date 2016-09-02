package com.harvey.hw;

import java.nio.ByteBuffer;

public class LoopBackTimeStamp {

	private long sendTimeStamp;
	private long recvTimeStamp;
	 
	public long getSendTimeStamp() {
		return sendTimeStamp;
	}

	public void setSendTimeStamp(long sendTimeStamp) {
		this.sendTimeStamp = sendTimeStamp;
	}

	public long getRecvTimeStamp() {
		return recvTimeStamp;
	}

	public void setRecvTimeStamp(long recvTimeStamp) {
		this.recvTimeStamp = recvTimeStamp;
	}

	public LoopBackTimeStamp() {
		 //set current system time
		 this.sendTimeStamp = System.nanoTime();
	 }
	 /**
	  * return recvTimeStamp - sendTimeStamp
	  * time spent on the trip
	  * @return
	  */
	 public long timeLapseInNanoSecond() {
		 return recvTimeStamp - sendTimeStamp;
	 }
	 
	 /**
	   * Transfer 2 long number to a 16 byte-long byte[], every 8 bytes represent a long number.
	   * @return
	   */
	 public byte[] toByteArray() {
		 
	   final int byteOfLong = Long.SIZE / Byte.SIZE;//8
	   byte[] ba = new byte[byteOfLong * 2];
	   //buffer里面分配8byte的长度 把一个长整型sendTimeStamp放进去 在转换成byte数组
	   byte[] t1 = ByteBuffer.allocate(byteOfLong).putLong(sendTimeStamp).array();
	   byte[] t2 = ByteBuffer.allocate(byteOfLong).putLong(recvTimeStamp).array();
	   //sendTimeStamp的长整型数组放入ba数组中
	   for (int i = 0; i < byteOfLong; i++) {
	     ba[i] = t1[i];
	   }
	   //放入第二个到ba数组中
	   for (int i = 0; i < byteOfLong; i++) {
	     ba[i + byteOfLong] = t2[i];
	   }
	   return ba;
	 }
	 
	 /**
	   * Transfer a 16 byte-long byte[] to 2 long numbers, every 8 bytes represent a long number.
	   * @param content
	   */
	  public void fromByteArray(byte[] content) {
	    int len = content.length;
	    final int byteOfLong = Long.SIZE / Byte.SIZE;
	    if (len != byteOfLong * 2) {
	      System.out.println("Error on content length");
	      return;
	    }
	    //buffer接受放入content 前面8 个byte放入第一个buffer里面， 后面8个byte放入buf2里面
	    ByteBuffer buf1 = ByteBuffer.allocate(byteOfLong).put(content, 0, byteOfLong);
	    ByteBuffer buf2 = ByteBuffer.allocate(byteOfLong).put(content, byteOfLong, byteOfLong);
	    /**
	     * Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
	     * Buffer添加数据的时候  position头指针会随数据加入而指向最大buffer 容量（capicitry-1）的值。
	     */
	    
	    buf1.rewind();
	    buf2.rewind();
	    //读取当前position的long值
	    this.sendTimeStamp = buf1.getLong();
	    this.recvTimeStamp = buf2.getLong();
	  }
}
