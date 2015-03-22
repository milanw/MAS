package GameObjects;

import java.awt.geom.Point2D;

public class Marker {
	private static final int DEFAULT_SIZE = 2; 
	private Point2D topLeft;
	private Point2D bottomRight; 
	private int type; 				// 5 different types
	
	public Marker(Point2D topRight, int type) {
		this.topLeft = topRight; 
		this.bottomRight = new Point2D.Double(topRight.getX()+DEFAULT_SIZE, topRight.getY()+DEFAULT_SIZE); 
		this.type = type; 
	}
	
	public Point2D getTopRight() {
		return topLeft;
	}

	public void setTopRight(Point2D topRight) {
		this.topLeft = topRight;
	}

	public Point2D getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Point2D bottomRight) {
		this.bottomRight = bottomRight;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	
}
