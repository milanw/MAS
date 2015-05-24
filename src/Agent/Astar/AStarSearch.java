package Agent.Astar;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;




public class AStarSearch {
	private int[][] map; 
	
	public AStarSearch(int[][] map) {
		this.map = map;
	}
	
	public ArrayList<Node> findPath(Node start, Node goal) {
		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		
		start.g = 0;
		start.f = start.g + heuristicEstimate(start, goal);
		
		while (!open.isEmpty()) {
			Node current = lowestF(open);
			
			if (current.equals(goal)) {
				return reconstructPath(current);
			}
				
			open.remove(current); 
			closed.add(current);
			
			for (Node neighbour : getNeighbours(current)) {
				
				if (closed.contains(neighbour)) 
					continue; 
				double tentativeG = current.g + distance(current, neighbour);
				
				if (!open.contains(neighbour) || tentativeG < neighbour.g) {
					neighbour.parent = current; 
					neighbour.g = tentativeG; 
					neighbour.f = neighbour.g + heuristicEstimate(neighbour, goal);
					if (!open.contains(neighbour))
						open.add(neighbour);
				}
				
			}
		}		
		
		return null; 
	}
	
	public ArrayList<Node> reconstructPath(HashMap<Node, Node> cameFrom, Node current) {
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(current);
		System.out.println(cameFrom.containsKey(current));
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current); //remove?
			path.add(current);
		}
		return path; 
	}
	
	public ArrayList<Node> reconstructPath(Node current) {
		ArrayList<Node> path = new ArrayList<Node>(); 
		path.add(current); 
		Node parent = current.parent; 
		while (parent != null) {
			path.add(parent); 
			parent = parent.parent; 
		}
		Collections.reverse(path); 
		path.remove(0);
		return path;
	}
	
	public double heuristicEstimate(Node start, Node goal) {
		return sqrt(pow(start.x-goal.x, 2) + pow(start.y - goal.y, 2));
	}
	
	
	
	public double distance(Node start, Node goal) {
        return Math.max(Math.abs(start.x-goal.x), Math.abs(start.y-goal.y)); 
	}	
	
	public ArrayList<Node> getNeighbours(Node node) {
		ArrayList<Node> neighbours = new ArrayList<Node>(); 
		if (node.x+1 <map[0].length)
			neighbours.add(new Node(node.x+1, node.y));
		if (node.y+1 <map.length)
			neighbours.add(new Node(node.x, node.y+1));
		if (node.x-1 >= 0)
			neighbours.add(new Node(node.x-1, node.y));
		if (node.y-1 >= 0 && node.x+1 <map[0].length)
			neighbours.add(new Node(node.x, node.y-1));
		return neighbours;
	}
	
	public Node lowestF(ArrayList<Node> list) {
		Node lowest = null;
		double value = Double.POSITIVE_INFINITY;
		for (Node n : list) {
			if (n.f < value) {
				lowest = n;
				value = n.f;
			}
		}
		return lowest; 
	}
	
	
	
	
	public static void main(String[] args) {
		AStarSearch a = new AStarSearch(new int[10][10]); 
		
		Node start = new Node(0,0);
		Node goal = new Node(0,3);
		System.out.println(a.findPath(start, goal));
	}
}
