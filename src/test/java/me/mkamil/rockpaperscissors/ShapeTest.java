package me.mkamil.rockpaperscissors;

import junit.framework.TestCase;
import me.mkamil.rockpaperscissors.entity.GameResult;
import me.mkamil.rockpaperscissors.entity.Shape;
import org.junit.Test;

/**
 * Created by mustafaiman on 23/10/15.
 */
public class ShapeTest extends TestCase {

    @Test
    public void testRock() {
        assertEquals(GameResult.LOSS, Shape.ROCK.play(Shape.PAPER));
        assertEquals(GameResult.TIE, Shape.ROCK.play(Shape.ROCK));
        assertEquals(GameResult.WIN, Shape.ROCK.play(Shape.SCISSORS));
    }

    @Test
    public void testPaper() {
        assertEquals(GameResult.LOSS, Shape.PAPER.play(Shape.SCISSORS));
        assertEquals(GameResult.TIE, Shape.PAPER.play(Shape.PAPER));
        assertEquals(GameResult.WIN, Shape.PAPER.play(Shape.ROCK));
    }

    @Test
    public void testScissorsk() {
        assertEquals(GameResult.LOSS, Shape.SCISSORS.play(Shape.ROCK));
        assertEquals(GameResult.TIE, Shape.SCISSORS.play(Shape.SCISSORS));
        assertEquals(GameResult.WIN, Shape.SCISSORS.play(Shape.PAPER));
    }
}
