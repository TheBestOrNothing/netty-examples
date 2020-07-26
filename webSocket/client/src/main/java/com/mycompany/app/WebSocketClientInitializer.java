package com.mycompany.app;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;


public class WebSocketClientInitializer extends ChannelInitializer<Channel> {

    private WebSocketClientHandshaker handshaker;

    public WebSocketClientInitializer(WebSocketClientHandshaker handshaker){
        this.handshaker = handshaker;
    }

	@Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        pipeline.addLast(new HttpClientHandler(this.handshaker));
        pipeline.addLast(new WebSocketClientHandler());
    }
    
}