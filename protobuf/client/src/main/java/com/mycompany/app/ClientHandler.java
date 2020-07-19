package com.mycompany.app;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

/**
 * Listing 2.3 ChannelHandler for the client
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */

@Sharable
public class ClientHandler
    extends SimpleChannelInboundHandler<Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
        // TODO Auto-generated method stub
        System.out.println(msg.getId());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}