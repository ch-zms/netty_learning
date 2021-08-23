package com.netty.learning.demo06;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * @author ch
 * @date 2021-07-20 11:46
 * @desc
 */
public class MyServerHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"--超时时间--"+eventType);
            System.out.println("服务器做出相应处理");

            ctx.channel().close();
        }
    }
}
