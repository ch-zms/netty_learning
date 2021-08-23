package com.netty.learning.demo02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO 客户端
 *
 * @author root
 * @date 2021-07-15 13:17
 * @desc
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        //创建一个客户端 socketChannel 对象
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非堵塞
        socketChannel.configureBlocking(false);
        //提供服务器地址与端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("连接中");
            }
        }
        //连接成功则发送数据
        String str = "你好";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        System.in.read();

    }
}
