package com.harvey.nio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * From internet an example for implement of Netty read
 * @author HarveySally
 * @date Sep 3, 2016
 * @time 11:33:30 PM
 */
public class test01 {
	 public void selector() throws IOException {
	        ByteBuffer buffer = ByteBuffer.allocate(1024);//创建buffer
	        Selector selector = Selector.open(); //添加选择器
	        ServerSocketChannel ssc = ServerSocketChannel.open();//建立channel
	        ssc.configureBlocking(false);//设置为非阻塞方式
	        ssc.socket().bind(new InetSocketAddress(8080));//帮定接口
	        ssc.register(selector, SelectionKey.OP_ACCEPT);//把channel 注册到 selector上 注册监听的事件
	        while (true) {
	            Set selectedKeys = selector.selectedKeys();//取得所有key集合
	            Iterator it = selectedKeys.iterator();
	            while (it.hasNext()) {
	                SelectionKey key = (SelectionKey) it.next();
	                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
	                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
	                    SocketChannel sc = ssChannel.accept();//接受到服务端的请求
	                    sc.configureBlocking(false);
	                    sc.register(selector, SelectionKey.OP_READ);
	                    it.remove();
	                } else if 
	                ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
	                    SocketChannel sc = (SocketChannel) key.channel();
	                    while (true) {
	                        buffer.clear();
	                        int n = sc.read(buffer);//读取数据 放到buffer里面
	                        if (n <= 0) {
	                            break;
	                        }
	                        buffer.flip();//flip 之后操作系统就可以读取这个数据了。
	                    }
	                    it.remove();
	                }
	            }
	        }
	}
}
