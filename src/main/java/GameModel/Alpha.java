package GameModel;

import GameController.GameBoard;
import control.Point2D;

public class Alpha extends Alien {
	
    public static int LIVES = 1;
    public static int POINTS = 100;
    private static final int DEFAULT_ALIEN_HEIGHT = 40;
    private static final int MISSILE_SHIFT = 25;

    public Alpha(Point2D position, GameBoard board) {
        super(position, board);
        String icon = "alpha.gif";
        setIconLocation(icon);
        setLives(LIVES);
        setPoints(POINTS);
        setSize(DEFAULT_ALIEN_HEIGHT);
    }
    @Override
    public void fireMissile() {
        Point2D missilePosition = new Point2D(this.position.x() + MISSILE_SHIFT, this.position.y());
        Missile missile = new EnemyMissile(missilePosition);
        board.getMissiles().add(missile);
    }
}
