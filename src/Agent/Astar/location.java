package Agent.Astar;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class location {
	Point2D point;
	Point2D goal;
	location parent;
	double startValue;
	double heuristicValue;
	
	public location(Point2D n, double n2, Point2D n3, location nparent){
		point = n;
		startValue = n2;
		goal = n3;
		setHeuristicValue(goal);
		parent = nparent;
	}
	public double getX(){
		return point.getX();
	}
	public double getY(){
		return point.getY();
	}
	public Point2D getPoint(){
		return point;
	}
	public void setHeuristicValue(Point2D goal){
		double dx = goal.getX()-point.getX();
		double dy = goal.getY()-point.getY();
		heuristicValue = dx+dy;
	}
	public double getValue(){
		return startValue+heuristicValue;
	}
	public location getParent(){
		return parent;
	}
	public double getHeuristicValue(){
		return heuristicValue;
	}
	public ArrayList<location> getNeighbours(){
		ArrayList<location> neighbours = new ArrayList<location>();
		Point2D p1 = new Point2D.Double(point.getX(), point.getY()-1);
		location g1 = new location(p1, startValue+1, goal, this);
		neighbours.add(g1);
		
		Point2D p2 = new Point2D.Double(point.getX(), point.getY()+1);
		location g2 = new location(p2, startValue+1, goal, this);
		neighbours.add(g2);
		
		Point2D p3 = new Point2D.Double(point.getX()+1, point.getY());
		location g3 = new location(p3, startValue+1, goal, this);
		neighbours.add(g3);
		
		Point2D p4 = new Point2D.Double(point.getX()-1, point.getY());
		location g4 = new location(p4, startValue+1, goal, this);
		neighbours.add(g4);
		return neighbours;
	}
}
