package com.netty.learning.demo05.ChatClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author ch
 * @date 2021-07-19 16:28
 * @desc
 */
public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.channel(NioSocketChannel.class)
                    .group(eventExecutors)
                    .handler(new ChatInitializer());
            ChannelFuture channelFuture=bootstrap.connect("127.0.0.1",9991).sync();

            Channel channel = channelFuture.channel();
            System.out.println("-----"+channel.localAddress()+"-----");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                channel.writeAndFlush(msg);
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
