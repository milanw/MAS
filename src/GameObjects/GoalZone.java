package GameObjects;

import java.awt.Point;
import java.awt.geom.Point2D;

public class GoalZone extends InanimateObject{
	public static int defaultSize = 5; 	

	public GoalZone(Point2D x, Point2D y) {
		super(x,y);
	}
	
	public int getSize() {
		return defaultSize; 
	}
}
