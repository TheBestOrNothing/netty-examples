package com.mycompany.app;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;


/**
 * Echo handler
 */

public class HttpServerHandler extends SimpleChannelInboundHandler <FullHttpRequest> {

    private WebSocketServerHandshaker handshaker;
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // TODO Auto-generated method stub

        FullHttpRequest req = (FullHttpRequest)msg;
        if ("Upgrade".equalsIgnoreCase(req.headers().get(HttpHeaderNames.CONNECTION)) &&
                    "WebSocket".equalsIgnoreCase(req.headers().get(HttpHeaderNames.UPGRADE))){

            //ctx.pipeline().replace(this, "WebSocketServerHandler", new WebSocketServerHandler());
            //Hand Shake        

            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    "ws://localhost:8080/websocket", null, false);
            handshaker = wsFactory.newHandshaker(req);
         if (handshaker == null) {
                WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
                System.out.println("handshake successfully!!");
         }

        }
    }
}