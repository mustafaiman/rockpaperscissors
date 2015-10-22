package me.mkamil.rockpaperscissors;

import junit.framework.TestCase;
import me.mkamil.rockpaperscissors.message.ShapeMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class ShapeMessageTest extends TestCase {

    @Test
    public void testMessageExtractor()
            throws Exception {
        ShapeMessage msg = new ShapeMessage("SHAPE\r\nROCK\r\nROCK\r\n\r\n");
        assertEquals("SHAPE\r\nROCK\r\nROCK\r\n\r\n", new String(msg.getProtocolMessage()));
    }
}
