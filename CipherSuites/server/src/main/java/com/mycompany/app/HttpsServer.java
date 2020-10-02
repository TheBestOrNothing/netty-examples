package com.mycompany.app;

import java.util.ArrayList;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

public class HttpsServer{
	private int port;
	
	public HttpsServer(int port){
		this.port = port;
	}

	public void run() throws Exception{

        final SslContext sslCtx;
        //Create Client/Server/Third party Certification to test
        SelfSignedCertificate clientssc = new SelfSignedCertificate("RSA", "client");
        SelfSignedCertificate serverssc = new SelfSignedCertificate("RSA", "server");
        //SelfSignedCertificate thirdssc = new SelfSignedCertificate("RSA", "third");

        ArrayList<String> enabledSuites = new ArrayList<String>();
        enabledSuites.add("TLS_RSA_WITH_AES_128_GCM_SHA256");
        enabledSuites.add("TLS_RSA_WITH_AES_128_CBC_SHA");
        enabledSuites.add("TLS_RSA_WITH_AES_256_CBC_SHA");
        
        sslCtx = SslContextBuilder.forServer(serverssc.certificate(), serverssc.privateKey())
                                        .trustManager(clientssc.cert())
                                        .ciphers(enabledSuites)
                                        .build();
        
        System.out.println(serverssc.privateKey().toString());
        System.out.println(serverssc.certificate().toString());
        System.out.println();
        System.out.println(serverssc.key().toString());
        System.out.println();
        System.out.println(serverssc.pubKey().toString());

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
     
