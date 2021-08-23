package com.netty.learning.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author ch
 * @date 2021-07-19 11:49
 * @desc
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("pipeline hashcode：" + ctx.pipeline().hashCode() + "HttpServerHandler hash:" + this.hashCode());
        System.out.println("msg类型：" + msg.getClass());
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        if (msg instanceof HttpRequest) {

            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if ("/a.ico".equals(uri.getPath())) {
                System.out.println("请求了...不做反应");
                return;
            }

            ByteBuf buf = Unpooled.copiedBuffer("你好，我是服务器", CharsetUtil.UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/pain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

            ctx.writeAndFlush(response);
        }
    }
}
