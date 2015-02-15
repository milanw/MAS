package GameObjects;

import java.awt.Point;

public class GoalZone extends InanimateObjects{

	public GoalZone(Point topLeft, Point bottomRight) {
		x = topLeft;
		y = bottomRight;
	}

}
