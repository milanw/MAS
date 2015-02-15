package GameObjects;

import java.awt.Point;

public class OuterWall extends InanimateObjects{

	public OuterWall(Point x, Point y) {
		topLeft = x;
		bottomRight = y;
	}
}
