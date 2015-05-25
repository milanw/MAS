package Agent.InfluenceMap;

import java.util.ArrayList;





public class InfluenceMap {
	private static double PROPAGATION_CONSTANT = 0.9; 
	private int width;
	private int height; 
	private double[][] map; 
	private int cellWidth = 5;

	public InfluenceMap(int width, int height) {
		this.width = width/cellWidth;
		this.height = height/cellWidth; 
		this.map = new double[this.height][this.width];
	}

	
	public void propagate(Node start) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int distance = Math.max(Math.abs(start.x-j), Math.abs(start.y-i)); 
				map[i][j] += Math.pow(PROPAGATION_CONSTANT, distance);
			}			
		}
	}
	
	public void propagate2(Node start) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int distance = Math.max(Math.abs(start.x-j), Math.abs(start.y-i)); 
				map[i][j] -= Math.pow(0.7, distance);
			}			
		}
	}
	
	public ArrayList<Node> getNeighbours(Node node) {
		ArrayList<Node> neighbours = new ArrayList<Node>(); 
		if (node.x+1 <map[0].length)
			neighbours.add(new Node(node.x+1, node.y));
		if (node.y+1 <map.length)
			neighbours.add(new Node(node.x, node.y+1));
		if (node.x-1 >= 0)
			neighbours.add(new Node(node.x-1, node.y));
		if (node.y-1 >= 0)
			neighbours.add(new Node(node.x, node.y-1));
		return neighbours;
	}
	
	public double[][] getMap() {
		return map; 
	}
	
	public int getCellWidth() {
		return cellWidth;
	}
	
	public static void main(String[] args) {
		InfluenceMap m = new InfluenceMap(100, 100);
		m.propagate(new Node(5,5));
		//m.propagate2(new Node(10,10));
		
		double[][] grid = m.getMap(); 
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				//System.out.print(grid[i][j] + " ");
				if (grid[i][j] > 1)
					System.out.println(grid[i][j] + " " + i + " " + j);
			}
			//System.out.println();
		}
	}
}
