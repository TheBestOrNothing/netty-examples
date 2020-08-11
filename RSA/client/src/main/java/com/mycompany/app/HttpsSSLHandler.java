package com.mycompany.app;

import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;

public class HttpsSSLHandler extends SslHandler {

    public HttpsSSLHandler(SSLEngine engine) {
        super(engine);
        // TODO Auto-generated constructor stub
    }

    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Channel Active !!");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, java.lang.Throwable cause){
        System.out.println("Catch sslHandler exception !!");
        cause.printStackTrace();
        ctx.close();
    }

    
}