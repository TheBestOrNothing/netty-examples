package com.mycompany.app;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Echo handler
 */

public class WebSocketServerHandler extends SimpleChannelInboundHandler <TextWebSocketFrame> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // TODO Auto-generated method stub
        System.out.println(msg.text());
        TextWebSocketFrame tws = new TextWebSocketFrame("ServerID - " + ctx.channel().id().toString());
        ctx.writeAndFlush(tws);

    }
}