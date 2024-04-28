package GameModel;

import control.Dimension2d;
import control.Point2D;

public class Explosion {
	protected final Object target;
	protected final Missile missile;
	private final boolean boom;
	
	public Explosion (Object target, Missile missile) {
		this.target = target;
		this.missile = missile;
		this.boom = detectExplosion();
	}
	public boolean isHit(){
		return boom;
	}

	private boolean detectExplosion() {
		Point2D p1 = missile.getPosition();
		Dimension2d d1 = missile.getSize();

		Point2D p2 = target.getPosition();
		Dimension2d d2 = target.getSize();
		
		boolean above = p1.y() + d1.height() < p2.y();
		boolean below = p1.y() > p2.y() + d2.height();
		boolean right = p1.x() + d1.width() < p2.x();
		boolean left = p1.x() > p2.x() + d2.width();

		return !above && !below && !right && !left;
	}
}
