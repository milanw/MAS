package GameObjects;
import java.awt.geom.Point2D;



public class InanimateObject {
	public static final int SENTRY_TYPE = 1;
	public static final int GOAL_TYPE = 2; 
	public static final int OUTERWALL_TYPE = 3; 
	public static final int STRUCTURE_TYPE = 4; 
	public static final int EMPTY_TYPE = 0; 
	
	protected Point2D topLeft;
	protected Point2D bottomRight;
	protected static int defaultSize = 30; 
	
	public InanimateObject(Point2D topLeft, Point2D bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight; 
	}
	
	public boolean isInside(Point2D n){
		return n.getX() > topLeft.getX() && n.getX() < bottomRight.getX() && n.getY() > topLeft.getY() && n.getY() < bottomRight.getY();
	}
	
	public Point2D getTopLeft() {
		return topLeft;
	}
	
	public Point2D getBottomRight() {
		return bottomRight;
	}
	
	public int getWidth() {
		return (int)(bottomRight.getX()-topLeft.getX());
	}
	
	public int getHeight() {
		return (int)(bottomRight.getY()-topLeft.getY()); 
	}
	
	/*
	 * return x coordinate of top left point
	 */
	public int getX() {
		return (int)topLeft.getX();
	}
	
	/* 
	 * return y coordinate of top left point
	 */
	public int getY() {
		return (int)topLeft.getY(); 
	}
	
	public int getSize() {
		return defaultSize; 
	}
}
