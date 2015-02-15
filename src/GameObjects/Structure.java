package GameObjects;

import java.awt.geom.Point2D;

public class Structure extends InanimateObjects{

	public Structure(Point2D topLeft, Point2D bottomRight) {
		x = topLeft;
		y = bottomRight;
	}
	public boolean isInside(Point2D n){
		return n.getX() < x.getX() && n.getX() > y.getX() && n.getY() < x.getY() && n.getY() > y.getY();
	}
}
