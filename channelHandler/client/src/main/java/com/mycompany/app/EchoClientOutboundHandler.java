package com.mycompany.app;

import io.netty.channel.ChannelHandler.Sharable;

import java.net.SocketAddress;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;


/**
 * Listing 2.1 EchoServerHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable
public class EchoClientOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("O1: write");
        ChannelFuture f = ctx.writeAndFlush(msg);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
            ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("O1: connect");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        System.out.println("O2: flush");
        super.flush(ctx);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        System.out.println("O3: close");
        super.close(ctx, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        System.out.println("XX: disconnect");
        super.disconnect(ctx, promise);
    }  
}
