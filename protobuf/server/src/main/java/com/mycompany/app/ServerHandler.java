package com.mycompany.app;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

/**
 * Echo handler
 */

public class ServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        System.out.println("The server is active!!!");
        Person.Builder person = Person.newBuilder();
        person.setId(1234);
        person.setName("Jone");
        person.setEmail("asd@google.com");

        ChannelFuture f = ctx.writeAndFlush(person);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}