package com.mycompany.app;

import io.netty.channel.ChannelHandler.Sharable;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;


/**
 * Listing 2.1 EchoServerHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable
public class EchoServerOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("O1 bind");
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
            ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("O2 connect");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }
}
