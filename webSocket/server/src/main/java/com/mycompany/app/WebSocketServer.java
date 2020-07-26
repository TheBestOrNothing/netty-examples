package com.mycompany.app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer{
	private int port;
	
	public WebSocketServer(int port){
		this.port = port;
	}

	public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer())
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

            new WebSocketServer(port).run();
        }
		
    }
     
