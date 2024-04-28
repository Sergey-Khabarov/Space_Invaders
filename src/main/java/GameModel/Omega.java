package GameModel;

import GameController.GameBoard;
import control.Point2D;

public class Omega extends Alien{

    public static int LIVES = 3;
    public static int POINTS = 50;
    private static final int DEFAULT_ALIEN_HEIGHT = 30;

    public Omega(Point2D position, GameBoard board) {
        super(position, board);
        String icon = "omega.gif";
        setIconLocation(icon);
        setLives(LIVES);
        setPoints(POINTS);
        setSize(DEFAULT_ALIEN_HEIGHT);
    }
}
