package com.mycompany.app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.2 EchoServer class
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
        throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() +
                " <port>"
            );
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws Exception {

        final EchoServerInboundHandler serverInboundHandler = new EchoServerInboundHandler();
        final EchoServerOutboundHandler serverOutboundHandler = new EchoServerOutboundHandler();
        EventLoopGroup serverGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(serverGroup, childGroup);
            b.channel(NioServerSocketChannel.class);
            b.localAddress(new InetSocketAddress(port));
            b.handler(new ChannelInitializer<NioServerSocketChannel>() {
                @Override
                public void initChannel(NioServerSocketChannel ch) throws Exception {
                    //ch.pipeline().addLast("1", serverInboundHandler);
                    ch.pipeline().addLast("1", serverOutboundHandler);
                }
            });

            b.childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    public void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("1", serverInboundHandler);
                        //ch.pipeline().addLast("2", serverOutboundHandler);
                    }
                });
            
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() +
                " started and listening for connections on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
            System.out.println("Channel Closed!!\n");
        } finally {
            serverGroup.shutdownGracefully().sync();
            childGroup.shutdownGracefully().sync();
        }
    }
}