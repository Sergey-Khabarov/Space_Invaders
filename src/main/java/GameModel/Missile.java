package GameModel;

import control.Dimension2d;
import control.Point2D;


public abstract class Missile implements Object {
    private Point2D position;
    private String iconLocation;
    protected static final int SPEED = 30;
    private Dimension2d size;

    public Missile(Point2D position) {
        this.position = position;
    }
    public abstract void move();
    // getters and setters
	public Point2D getPosition() {
		return position;
	}
	public String getIconLocation() {
        return iconLocation;
	}
    public Dimension2d getSize() {
		return size;
	}
    public void setSize(double width, double height) {
        this.size = new Dimension2d(width, height);
    }
    public void setIconLocation(String iconLocation){
        this.iconLocation = iconLocation;
    }
    public void setPosition(double newY) {
        this.position = new Point2D(this.getPosition().x(), newY);
    }
}
