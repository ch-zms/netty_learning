package com.netty.learning.demo05.ChatClient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author ch
 * @date 2021-07-19 16:37
 * @desc
 */
public class ChatInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new ChatClientHandler());

    }
}
