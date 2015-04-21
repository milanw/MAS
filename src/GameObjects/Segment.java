package GameObjects;


import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Segment {
	private Point2D.Double point1;
	private Point2D.Double point2;
	


	public Segment(Point2D p1, Point2D p2) {
		point1 = (Double) p1;
		point2 = (Double) p2;

	}
}
