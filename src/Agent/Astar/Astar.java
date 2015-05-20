package Agent.Astar;

import java.awt.geom.Point2D;
import java.util.ArrayList;


import Map.Map;

public class Astar {
	ArrayList<Point2D> path = new ArrayList<Point2D>();
	
	public Astar(Point2D start, Point2D goal, Map map){
		System.out.println("start = " + start);
		System.out.println("goal = " + goal);
		ArrayList<location> closed = new ArrayList<location>();
		ArrayList<location> open = new ArrayList<location>();
		location s = new location(start, 0, goal, null);
		open.add(s);
		location current = s;
		
		int counter = 0;
		while(open.size() != 0 && current.getHeuristicValue() != 0.0){ //TODO check if this works
			//System.out.print("goal = " + goal);
			current = open.get(0);


			
			
			for(int i = 0;i<open.size();i++){
				if(open.get(i).getValue()<current.getValue()){
					current = open.get(i);
				}
			}
			//System.out.println("open = " + open.size());
			//System.out.println("current = " + current.getPoint());
			open.remove(current);

			closed.add(current);
			ArrayList<location> neighbours = current.getNeighbours();

			for(int i=0;i<neighbours.size();i++){
				//System.out.println("checked neighbour" + neighbours.get(i).getPoint());
				boolean inOpen = false;
				boolean inClosed = false;
				for(int j = 0;j<open.size();j++){
					if(neighbours.get(i).getPoint().getX() == open.get(j).getPoint().getX() && neighbours.get(i).getPoint().getY() == open.get(j).getPoint().getY()){
						inOpen = true;
						if(neighbours.get(i).getValue() < open.get(j).getValue()){
							open.remove(j);
							open.add(neighbours.get(i));
						}
					}
				}
				for(int j = 0;j<closed.size();j++){
					if(neighbours.get(i).getPoint().getX() == closed.get(j).getPoint().getX() && neighbours.get(i).getPoint().getY() == closed.get(j).getPoint().getY()){
						inClosed = true;
						if(neighbours.get(i).getValue() < closed.get(j).getValue()){
							closed.remove(j);
							open.add(neighbours.get(i));
						}
					}
				}
				
				if(inOpen == false && inClosed == false && map.emptySpot(neighbours.get(i).getPoint())){
					open.add(neighbours.get(i));
				}
			}
			counter++;
		}
		ArrayList<Point2D> rpath = new ArrayList<Point2D>();
		rpath.add(current.getPoint());
		while(current.getParent() != null){
			current = current.getParent();
			rpath.add(current.getPoint());
		}
		int t = rpath.size();
		for(int i=0;i<rpath.size();i++){
			path.add(rpath.get(t-i-1));
		}
		path.remove(0);
		System.out.println("Astar finished!!!");
	}
	
	public Astar(Point2D start, Point2D goal, int[][] map){
		System.out.println("start = " + start);
		System.out.println("goal = " + goal);
		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		Node s = new Node(start, 0, goal, null, map);
		open.add(s);
		Node current = s;
		
		int counter = 0;
		while(open.size() != 0 && current.getHeuristicValue() != 0.0){ //TODO check if this works
			//System.out.print("goal = " + goal);
			current = open.get(0);


			
			
			for(int i = 0;i<open.size();i++){
				if(open.get(i).getValue()<current.getValue()){
					current = open.get(i);
				}
			}
			//System.out.println("open = " + open.size());
			//System.out.println("current = " + current.getPoint());
			open.remove(current);

			closed.add(current);
			ArrayList<Node> neighbours = current.getNeighbours();

			for(int i=0;i<neighbours.size();i++){
				//System.out.println("checked neighbour" + neighbours.get(i).getPoint());
				boolean inOpen = false;
				boolean inClosed = false;
				for(int j = 0;j<open.size();j++){
					if(neighbours.get(i).getPoint().getX() == open.get(j).getPoint().getX() && neighbours.get(i).getPoint().getY() == open.get(j).getPoint().getY()){
						inOpen = true;
						if(neighbours.get(i).getValue() < open.get(j).getValue()){
							open.remove(j);
							open.add(neighbours.get(i));
						}
					}
				}
				for(int j = 0;j<closed.size();j++){
					if(neighbours.get(i).getPoint().getX() == closed.get(j).getPoint().getX() && neighbours.get(i).getPoint().getY() == closed.get(j).getPoint().getY()){
						inClosed = true;
						if(neighbours.get(i).getValue() < closed.get(j).getValue()){
							closed.remove(j);
							open.add(neighbours.get(i));
						}
					}
				}
				
				if(inOpen == false && inClosed == false){
					open.add(neighbours.get(i));
				}
			}
			counter++;
		}
		ArrayList<Point2D> rpath = new ArrayList<Point2D>();
		rpath.add(current.getPoint());
		while(current.getParent() != null){
			current = current.getParent();
			rpath.add(current.getPoint());
		}
		int t = rpath.size();
		for(int i=0;i<rpath.size();i++){
			path.add(rpath.get(t-i-1));
		}
		path.remove(0);
		System.out.println("Astar finished!!!");
	}
	
	public ArrayList<Point2D> getPath(){
		return path;
	}
}
