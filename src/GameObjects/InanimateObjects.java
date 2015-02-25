package GameObjects;
import java.awt.Point;
import java.awt.geom.Point2D;



public class InanimateObjects {
	Point2D topLeft;
	Point2D bottomRight;
	
	public boolean isInside(Point2D n){
		return n.getX() > topLeft.getX() && n.getX() < bottomRight.getX() && n.getY() > topLeft.getY() && n.getY() < bottomRight.getY();
	}
	
	public Point2D getTopLeft() {
		return topLeft;
	}
	
	public Point2D getBottomRight() {
		return bottomRight;
	}
}
