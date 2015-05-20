package Agent.Astar;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Node {
	public int[][] map; 
	Point2D point;
	Point2D goal;
	Node parent;
	double startValue;
	double heuristicValue;

	public Node(Point2D n, double n2, Point2D n3, Node nparent, int[][] map){
		point = n;
		startValue = n2;
		goal = n3;
		setHeuristicValue(goal);
		parent = nparent;
		this.map = map; 
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
	public Node getParent(){
		return parent;
	}
	public double getHeuristicValue(){
		return heuristicValue;
	}
	public ArrayList<Node> getNeighbours(){
		ArrayList<Node> neighbours = new ArrayList<Node>();


		Point2D p1 = new Point2D.Double(point.getX(), point.getY()-1);
		if (p1.getY() >= 0 && map[(int)p1.getY()][(int)p1.getX()] != 1) {
			Node g1 = new Node(p1, startValue+1, goal, this, map);
			neighbours.add(g1);
		}

		Point2D p2 = new Point2D.Double(point.getX(), point.getY()+1);
		if (p2.getY() < map.length && map[(int)p2.getY()][(int)p2.getX()] != 1) {
			Node g2 = new Node(p2, startValue+1, goal, this, map);
			neighbours.add(g2);
		}

		Point2D p3 = new Point2D.Double(point.getX()+1, point.getY());
		if (p3.getX() < map[0].length && map[(int)p3.getY()][(int)p3.getX()] != 1) {
			Node g3 = new Node(p3, startValue+1, goal, this, map);
			neighbours.add(g3);
		}

		Point2D p4 = new Point2D.Double(point.getX()-1, point.getY());
		if (p4.getX() >= 0 && map[(int)p4.getY()][(int)p4.getX()] != 1) {
			Node g4 = new Node(p4, startValue+1, goal, this, map);
			neighbours.add(g4);
		}
		return neighbours;
	}
}
