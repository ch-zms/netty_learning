package com.netty.learning.demo02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 服务端
 *
 * @author ch
 * @date 2021-07-15 13:16
 * @desc
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        //创建一个ServerSocketChannel对象 -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //获取一个Selector
        Selector selector = Selector.open();
        //服务端绑定接口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非堵塞
        serverSocketChannel.configureBlocking(false);
        //把 serverSocketChannel 注册到 selector 关心事件为 OP_ACCEPT
        // int OP_ACCEPT : 有新的网络连接可以 accept 值为 16
        // int OP_CONNECT : 代表连接已经建立 值为8
        // int OP_READ : 代表读操作 值为1
        // int OP_WRITE : 带表写操作 值为4
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            //等待1秒，如果没有事件发生，返回
            if(selector.select(1000) == 0){
                System.out.println("服务器等待1秒，无连接");
                continue;
            }
            //如果返回>0 获取到相关的 selectionKey 集合
            //如果返回>0 表示已经取得关注事件
            //selector.selectedKeys()返回关注事件的合集
            //通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //使用迭代器遍历
            Iterator<SelectionKey> keyIterable = selectionKeys.iterator();

            while (keyIterable.hasNext()){
                //获取到 SelectionKey
                SelectionKey key = keyIterable.next();
                //根据 key 对应的通道发生的事件做相应处理

                //如果是 OP_ACCEPT 表示有新客户端连接
                if(key.isAcceptable()){
                    //该客户端生成一个 socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel "+socketChannel.hashCode());
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将 socketChannel 注册到 selector 关注事件为 OP_READ 同时给 socketChannel 关联一个 buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                //如果是 OP_READ
                if(key.isReadable()){
                    //通过 key 反向获取到对应 channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该 chanel 关联的 buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("接收到客户端消息："+new String(buffer.array()));
                }
                //手动从集合中移动当前的 selectionKey 防止重复操作
                keyIterable.remove();
            }
        }
    }
}
