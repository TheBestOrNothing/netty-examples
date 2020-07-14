package com.mycompany.app;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test for simple App.
 */
public class AbsIntegerEncoderTest 
{
    @Test
    public void testAbsIntegerEncoder() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 9; i++) {
            buf.writeInt(i* -1);
        }
    
        EmbeddedChannel channel = new EmbeddedChannel(
            new AbsIntegerEncoder());
        // write bytes
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());
        
        for (int i = 1; i < 9; i++) {
            assertEquals(i, (int)channel.readOutbound());
        }

        assertNull(channel.readOutbound());
    }
}
