package GameObjects;

import java.awt.geom.Point2D;

public class OuterWall extends InanimateObjects{

	public OuterWall(Point2D x, Point2D y) {
		topLeft = x;
		bottomRight = y;
	}
}
