package com.mycompany.app;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test for simple App.
 */
public class FrameChunkDecoderTest {
    @Test
    public void testFramesChunkDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(4));
        //Too Long exception
        //channel.writeInbound(input.readBytes(5));

        try {
            channel.writeInbound(input.readBytes(5));
            //Assertions.fail();
        } catch (TooLongFrameException e) {
            // expected exception
            System.out.println(e.getMessage());
        }

        assertTrue(channel.writeInbound(input.readBytes(4)));
        assertTrue(channel.finish());
    }
}
