package com.mycompany.app;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.CharsetUtil;

/**
 * Echo handler
 */

public class HttpsServerHandler extends ChannelInboundHandlerAdapter{

	@Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        ByteBuf content = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);  

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);  

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");  

        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());   

        ChannelFuture f = ctx.writeAndFlush(response);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // TODO Auto-generated method stub
        super.userEventTriggered(ctx, evt);

        if (evt instanceof SslHandshakeCompletionEvent){
            SslHandshakeCompletionEvent complete = (SslHandshakeCompletionEvent) evt;
            if (complete.isSuccess()){
                System.out.println("Ssl Handshake Completion");
                SslHandler handler = ctx.pipeline().get(SslHandler.class);
                String cipherSuite = handler.engine().getSession().getCipherSuite();
                System.out.println(cipherSuite);
            }else{
                System.out.println("Ssl Handshake not Completion");
            }
        }
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}