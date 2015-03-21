package GameObjects;
import java.awt.Point;
import java.awt.geom.Point2D;



public class InanimateObjects {
	public static final int SENTRY_TYPE = 1;
	public static final int GOAL_TYPE = 2; 
	public static final int OUTERWALL_TYPE = 3; 
	public static final int STRUCTURE_TYPE = 4; 
	public static final int EMPTY_TYPE = 0; 
	
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
