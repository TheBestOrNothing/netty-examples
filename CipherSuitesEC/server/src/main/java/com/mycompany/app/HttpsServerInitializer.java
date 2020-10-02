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
        String[] cipherSuits = engine.getEnabledCipherSuites();
        for (int i=0; i< cipherSuits.length; i++){
            System.out.println(cipherSuits[i]);
            cipherSuits[i] = null;
        }

        /*
         * 2020.10.02 Get the following ciphersuites
         * TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384
         * TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
         * TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
         * TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
         * TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
         * TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA
         * TLS_RSA_WITH_AES_128_GCM_SHA256
         * TLS_RSA_WITH_AES_128_CBC_SHA
         * TLS_RSA_WITH_AES_256_CBC_SHA
         * */

        ChannelPipeline pipeline = ch.pipeline();  

        SslHandler sslHandler = new SslHandler(engine);
        pipeline.addLast(sslHandler);
        pipeline.addLast(new HttpServerCodec());  
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));  
        pipeline.addLast(new HttpsServerHandler());  
    }
    
}