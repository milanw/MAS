package GameObjects;

import java.awt.Point;

public class OuterWall extends InanimateObjects{

	public OuterWall(Point topLeft, Point bottomRight) {
		x = topLeft;
		y = bottomRight;
	}
}
