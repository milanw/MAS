package Agent.Astar;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Astar {
	ArrayList<Point2D> path = null;
	
	public Astar(Point2D start, Point2D goal){
		ArrayList<location> closed = null;
		ArrayList<location> open = null;
		location s = new location(start, 0, goal, null);
		open.add(s);
		location current = s;
		while(open.size() != 0 && current.getPoint() != goal){ //TODO check if this works
			current = open.get(0);
			for(int i = 0;i<open.size();i++){
				if(open.get(i).getValue()>current.getValue()){
					current = open.get(i);
				}
			}
			open.remove(current);
			closed.add(current);
			ArrayList<location> neighbours = current.getNeighbours();
			for(int i=0;i<neighbours.size();i++){
				boolean inOpen = false;
				boolean inClosed = false;
				for(int j = 0;j<open.size();j++){
					if(neighbours.get(i).getPoint() == open.get(j).getPoint()){
						inOpen = true;
						if(neighbours.get(i).getValue() < open.get(j).getValue()){
							open.remove(j);
							open.add(neighbours.get(i));
						}
					}
				}
				for(int j = 0;j<closed.size();j++){
					if(neighbours.get(i).getPoint() == closed.get(j).getPoint()){
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
		}
		path.add(current.getPoint());
		while(current.getParent() != null){
			current = current.getParent();
			path.add(current.getPoint());
		}
	}
}
