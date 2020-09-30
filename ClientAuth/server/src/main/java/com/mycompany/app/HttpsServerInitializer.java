package com.mycompany.app;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import javax.net.ssl.SSLEngine;
import io.netty.handler.ssl.SslHandler;

public class HttpsServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public HttpsServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO Auto-generated method stub
        SSLEngine engine = sslCtx.newEngine(ch.alloc(), "localhost", 8080);
        engine.setNeedClientAuth(true);
        SslHandler sslHandler = new SslHandler(engine);

        ChannelPipeline pipeline = ch.pipeline();  
        
        pipeline.addLast(sslHandler);
        pipeline.addLast(new HttpServerCodec());  
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));  
        pipeline.addLast(new HttpsServerHandler());  
    }
    
}