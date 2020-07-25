package com.mycompany.app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class HttpsServer{
	private int port;
	
	public HttpsServer(int port){
		this.port = port;
	}

	public void run() throws Exception{

        final SslContext sslCtx;
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        System.out.println(ssc.privateKey().toString());
        System.out.println(ssc.certificate().toString());

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpsServerInitializer(sslCtx))
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true);

                ChannelFuture f = b.bind(port).sync();
                f.channel().closeFuture().sync();
                
        }finally{
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
        
        public static void main (String[] args) throws Exception{
            int port = 8080;
            if(args.length > 0){
                port = Integer.parseInt(args[0]);
            }

            new HttpsServer(port).run();
        }
		
    }
     
