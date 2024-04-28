package GameModel;

import GameController.GameBoard;
import GameView.GameBoardUI;
import control.Dimension2d;
import control.Point2D;

public class Spaceship implements Object {
    private static final int SPEED = 20;
    private Point2D position=new Point2D(START_X_COORDINATE,START_Y_COORDINATE);
    private static final double START_X_COORDINATE = 345.0;
    private static final double START_Y_COORDINATE = 450.0;
    private static final String ICON_LOCATION = "spaceship.gif";
    private final Dimension2d gameBoardSize= GameBoardUI.getPreferredSize();
    private static final int DEFAULT_SPACESHIP_WIDTH = 60;
    private static final int DEFAULT_SPACESHIP_HEIGHT = 50;
    private static final int MISSILE_SHIFT = 25;
	private static final int MAX_ANGLE = 360;
    private final Dimension2d size = new Dimension2d(DEFAULT_SPACESHIP_WIDTH, DEFAULT_SPACESHIP_HEIGHT);
    private int direction;
    private int lives;
    private boolean moving = false;
    private final GameBoard gameboard;
    
    public Spaceship(GameBoard gameboard){
    	this.gameboard = gameboard;
        this.lives = 3;
    }
    public void move() {
        if(!isMoving()) {
        	return;
        }
        double maxX = gameBoardSize.width();
        double deltaX = SPEED * Math.sin(Math.toRadians(this.direction));
        double newX = this.position.x() + deltaX;
        if (newX < 0) {
			newX = 0;
			moving = false;
		} else if (newX + this.size.width() > maxX) {
			newX = maxX - this.size.width();
			moving = false;
		}
        this.position = new Point2D(newX, this.position.y());
    }
    public void fireMissile() {
        Point2D missilePosition = new Point2D(this.position.x() + MISSILE_SHIFT, this.position.y());
    	Missile missile = new OurMissile(missilePosition);
    	gameboard.addMissile(missile);
    }
    public void hit() {
        this.lives--;
    }
    public void bonus() {
        this.lives++;
    }
    //getters and setters
    public Point2D getPosition() {
        return position;
    }
    public String getIconLocation() {
        return ICON_LOCATION;
    }
    public boolean isDead() {
        return lives == 0;
    }
    public Dimension2d getSize() {
        return size;
    }
    public boolean isMoving() {
        return moving;
    }
    public int getLives() {
        return this.lives;
    }
    public void setDirection(int direction) {
		if (direction < 0 || direction >= MAX_ANGLE) {
			throw new IllegalArgumentException("Direction must be between 0 (inclusive) and 360 (exclusive)");
		}
		this.direction = direction;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
}
