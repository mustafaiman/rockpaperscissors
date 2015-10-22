package me.mkamil.rockpaperscissors.entity;

/**
 * Created by mustafaiman on 22/10/15.
 */
public enum Shape {
    ROCK, PAPER, SCISSORS;

    private Shape winsOver;

    static {
        ROCK.winsOver = SCISSORS;
        PAPER.winsOver = ROCK;
        SCISSORS.winsOver = PAPER;
    }

    public int play(Shape opponent) {
        if(this == opponent) {
            return 0;
        } else if(this.winsOver == opponent) {
            return 1;
        } else {
            return -1;
        }
    }
}
