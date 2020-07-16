
package com.mycompany.app;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.channel.ChannelHandlerContext;



 public class TimeDecoder extends ReplayingDecoder<Void>{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        out.add(new UnixTime(in.readLong()));
    }
 }
