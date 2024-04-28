package GameModel;

import GameController.GameBoard;
import control.Dimension2d;
import control.Point2D;

public abstract class Alien implements Object {
    //Attributes
    private int lives;
    private int points;
    protected Point2D position;
    private String iconLocation;
    private static final int SPEED = 1;
    private static final int DEFAULT_ALIEN_WIDTH = 60;
    private Dimension2d size;
    protected GameBoard board;
    public Alien(Point2D position, GameBoard board) {
        this.position = position;
        this.board = board;
    }
    public void move() {
        double newY = this.position.y() + SPEED;
        this.position = new Point2D(this.position.x(), newY);
    }
    // checks if alien reached the bottom
    public boolean check() {
        return position.y() + size.height() > board.getSize().height();
    }
    //Getters
    public String getIconLocation() {
        return this.iconLocation;
    }
    public Point2D getPosition() {
        return position;
    }
    public Dimension2d getSize() {
        return this.size;
    }
    public int getLives() {
        return lives;
    }
    public int getPoints() {
        return points;
    }
    public void setLives(int lives) {
    	this.lives = lives;
    }
	public void setPoints(int points) {
		this.points = points;
	}
    public void setSize(int height) {
        this.size = new Dimension2d(DEFAULT_ALIEN_WIDTH, height);
    }
    public void setIconLocation(String iconLocation){
        this.iconLocation = iconLocation;
    }
    public void fireMissile() {}
}
