package com.netty.learning.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author ch
 * @date 2021-07-19 10:17
 * @desc
 */
public class NettyClientHandler implements ChannelInboundHandler {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("连接生成...");
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，服务端", CharsetUtil.UTF_8));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString(CharsetUtil.UTF_8));

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("你好客户端呀1...", CharsetUtil.UTF_8));
                System.out.println("channel code:"+ctx.channel().hashCode());
            } catch (Exception e) {
                System.out.println("发生异常");
            }
        });
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("你好客户端呀2...", CharsetUtil.UTF_8));
                System.out.println("channel code:" + ctx.channel().hashCode());
            } catch (Exception e) {
                System.out.println("发生异常");
            }
        });
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("你好客户端呀2...", CharsetUtil.UTF_8));
                System.out.println("channel code:" + ctx.channel().hashCode());
            } catch (Exception e) {
                System.out.println("发生异常");
            }
        }, 5, TimeUnit.MINUTES);
        System.out.println("go on...");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) throws Exception {
        ctx.close();
    }
}
