package GameObjects;

import java.awt.geom.Point2D;

public class Structure extends InanimateObjects{

	public Structure(Point2D x, Point2D y) {
		topLeft = x;
		bottomRight = y;
	}
	public boolean isInside(Point2D n){
		return n.getX() < topLeft.getX() && n.getX() > bottomRight.getX() && n.getY() < topLeft.getY() && n.getY() > bottomRight.getY();
	}
	public Point2D getTopLeft(){
		return topLeft;
	}
}
