package com.mycompany.app;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Listing 2.1 EchoServerHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable
public class EchoServerInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("1: channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("6: channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("2: channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("5: channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("3: channelRead");
        ByteBuf b = (ByteBuf)msg;
        b.release();    
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("4: channelReadComplete");
        //Notice: The ctx.write is different the ctx.channel().write
        /*
        Channel.write(..) always start from the tail of the ChannelPipeline 
        and so pass through all the ChannelOutboundHandlers. 
        ChannelHandlerContext.write(...) starts from the current position of the ChannelHandler 
        which is bound to the ChannelHandlerContext 
        and so only pass those ChannelOutboundHandlers that are in front of it.
        */
        ctx.channel().write(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        System.out.println("Warnning: An exception happened!");
        cause.printStackTrace();
        ctx.close();
    }
}
