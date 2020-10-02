package com.mycompany.app;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;
import io.netty.handler.codec.DecoderException;
//import sun.security.validator.ValidatorException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;

public class HttpsSSLHandler extends SslHandler {


    public HttpsSSLHandler(SSLEngine engine) {
        super(engine);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, java.lang.Throwable cause){
        System.out.println("Catch sslHandler exception !!");
        //cause.printStackTrace();

        if (cause instanceof DecoderException){
            System.out.println("It is a ValidatorException!!");
        }

        if (cause.getCause() instanceof SSLHandshakeException){
            System.out.println("It is a SSLHandshakeException!!");
        }        

        while(cause != null){
            System.out.println(cause.toString());
            cause = cause.getCause();
        }       

        ctx.close();
    }

}