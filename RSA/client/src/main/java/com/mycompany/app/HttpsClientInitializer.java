package com.mycompany.app;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;


public class HttpsClientInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //To add the certification of server to the trust Manager
        FileInputStream certificateInput = new FileInputStream("serverrsa.cert");
        X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(certificateInput);
        SslContext sslCtx = SslContextBuilder.forClient().trustManager(cert).build();

        /*
         * Or you can test SSL with InsecureTrustManagerFactory in Netty
         * InsecureTrustManagerFactory trust any server
        */
        
        //SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        
        SslHandler sslHandler = sslCtx.newHandler(ch.alloc(), "localhost", 8080);
        sslHandler.setHandshakeTimeoutMillis(1000);

        pipeline.addLast("ssl", sslHandler);
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        pipeline.addLast(new HttpsClientHandler());

        /*
         * Or you can extend the SslHandler in Netty to catch the detail infromation or exception
         * when SSL handshake
        */

        //SSLEngine engine = sslCtx.newEngine(ch.alloc(), "localhost", 8080);
        //pipeline.addLast(new HttpsSSLHandler(engine));
    }

}