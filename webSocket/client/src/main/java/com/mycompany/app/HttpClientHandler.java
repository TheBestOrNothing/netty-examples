package com.mycompany.app;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Listing 2.3 ChannelHandler for the client
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */

@Sharable
public class HttpClientHandler
    extends SimpleChannelInboundHandler <HttpObject>{


    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public HttpClientHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {


        if (!handshaker.isHandshakeComplete()) {
            try {

                FullHttpResponse response = (FullHttpResponse)msg;

                //List the informaiton in the response object
                System.err.println("STATUS: " + response.status());
                System.err.println("VERSION: " + response.protocolVersion());
                System.err.println();
                if (!response.headers().isEmpty()) {
                    for (CharSequence name: response.headers().names()) {
                        for (CharSequence value: response.headers().getAll(name)) {
                            System.err.println("HEADER: " + name + " = " + value);
                        }
                    }
                    System.err.println();
                }

                //Try to set the handshaker finished!!!
                handshaker.finishHandshake(ctx.channel(), response);
                System.out.println("WebSocket Client connected!");
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                System.out.println("WebSocket Client failed to connect");
                handshakeFuture.setFailure(e);
            }
            return;
        }    
      
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}