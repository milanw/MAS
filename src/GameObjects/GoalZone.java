package GameObjects;

import java.awt.Point;

public class GoalZone extends InanimateObject{
	private static int defaultSize = 30; 	

	public GoalZone(Point x, Point y) {
		super(x,y);
	}
	
	public int getSize() {
		return defaultSize; 
	}

}
