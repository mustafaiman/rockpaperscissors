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

    public GameResult play(Shape opponent) {
        if(this == opponent) {
            return GameResult.TIE;
        } else if(this.winsOver == opponent) {
            return GameResult.WIN;
        } else {
            return GameResult.LOSS;
        }
    }
}
