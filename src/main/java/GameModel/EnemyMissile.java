package GameModel;

import control.Point2D;

public class EnemyMissile extends Missile{
    private static final int DEFAULT_MISSILE_WIDTH = 9;
    private static final int DEFAULT_MISSILE_HEIGHT = 30;
    private static final String ICON_LOCATION = "alienMissile.gif";
    public EnemyMissile(Point2D position) {
        super(position);
        setSize(DEFAULT_MISSILE_WIDTH, DEFAULT_MISSILE_HEIGHT);
        setIconLocation(ICON_LOCATION);
    }
    public void move() {
        double newY = this.getPosition().y() + SPEED;
        this.setPosition(newY);
    }
}
