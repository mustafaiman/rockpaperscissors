package me.mkamil.rockpaperscissors;

import junit.framework.TestCase;
import me.mkamil.rockpaperscissors.entity.Shape;
import org.junit.Test;

/**
 * Created by mustafaiman on 23/10/15.
 */
public class ShapeTest extends TestCase {

    @Test
    public void testRock() {
        assertEquals(-1, Shape.ROCK.play(Shape.PAPER));
        assertEquals(0, Shape.ROCK.play(Shape.ROCK));
        assertEquals(1, Shape.ROCK.play(Shape.SCISSORS));
    }

    @Test
    public void testPaper() {
        assertEquals(-1, Shape.PAPER.play(Shape.SCISSORS));
        assertEquals(0, Shape.PAPER.play(Shape.PAPER));
        assertEquals(1, Shape.PAPER.play(Shape.ROCK));
    }

    @Test
    public void testScissorsk() {
        assertEquals(-1, Shape.SCISSORS.play(Shape.ROCK));
        assertEquals(0, Shape.SCISSORS.play(Shape.SCISSORS));
        assertEquals(1, Shape.SCISSORS.play(Shape.PAPER));
    }
}
