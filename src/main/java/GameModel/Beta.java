package GameModel;

import GameController.GameBoard;
import control.Point2D;

public class Beta extends Alien{

    public static int LIVES= 1;
    public static int POINTS = 10;
    private static final int DEFAULT_ALIEN_HEIGHT = 30;

    public Beta(Point2D position, GameBoard board) {
        super(position, board);
        String icon = "beta.gif";
        setIconLocation(icon);
        setLives(LIVES);
        setPoints(POINTS);
        setSize(DEFAULT_ALIEN_HEIGHT);
    }
}
