
package com.mycompany.app;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.channel.ChannelHandlerContext;


public class TimeDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        if(in.readableBytes() <4){
            return;
        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }
 }