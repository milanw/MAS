package Agent.InfluenceMap;

import java.util.ArrayList;





public class InfluenceMap {
	private static double POSITIVE_DECAY = 0.9;
	private static double NEGATIVE_DECAY = 0.9; 
	private int width;
	private int height; 
	private double[][] positiveMap; 
	private double[][] negativeMap;
	private double[][] map; 
	
	
	private int cellWidth = 5;

	public InfluenceMap(int w, int h) {
		this.width = w/cellWidth;
		this.height = h/cellWidth; 
		this.positiveMap = new double[this.height][this.width];
		this.negativeMap = new double[this.height][this.width];
		this.map = new double[this.height][this.width];
		
	}

	
	public void decay() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				negativeMap[i][j] *= NEGATIVE_DECAY;
				//positiveMap[i][j] *= POSITIVE_DECAY;
				map[i][j] = positiveMap[i][j] + negativeMap[i][j];
			}			
		}
	}
	
	public void propagate(InfluenceNode start, double propagationConstant, int sign) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int distance =  Math.abs(start.x-j) + Math.abs(start.y-i);
				
				if (sign < 0) {
					negativeMap[i][j] -= Math.pow(propagationConstant, distance);
					negativeMap[i][j] = Math.max(-1.0, negativeMap[i][j]);
				}
				else {
					positiveMap[i][j] = 0; //POSITIVE_DECAY;
					positiveMap[i][j] += Math.pow(propagationConstant, distance);
					positiveMap[i][j] = Math.min(1.0, positiveMap[i][j]);
				}
				
				map[i][j] = positiveMap[i][j] + negativeMap[i][j];
					
				
			}			
		}
	}
	
	
	
	
	public ArrayList<InfluenceNode> getNeighbours(InfluenceNode node) {
		ArrayList<InfluenceNode> neighbours = new ArrayList<InfluenceNode>(); 
		if (node.x+1 < width)
			neighbours.add(new InfluenceNode(node.x+1, node.y));
		if (node.y+1 < height)
			neighbours.add(new InfluenceNode(node.x, node.y+1));
		if (node.x-1 >= 0)
			neighbours.add(new InfluenceNode(node.x-1, node.y));
		if (node.y-1 >= 0)
			neighbours.add(new InfluenceNode(node.x, node.y-1));
		return neighbours;
	}
	
	public ArrayList<InfluenceNode> getSurrounding(InfluenceNode node, int distance) {
		ArrayList<InfluenceNode> surrounding = new ArrayList<InfluenceNode>();
		for (int i = Math.max(0, node.y-distance); i < Math.min(node.y+distance, map.length); i++) {
			for (int j = Math.max(0, node.x-distance); j < Math.min(node.x+distance, map[0].length); j++) {
				surrounding.add(new InfluenceNode(j, i, map[i][j]));
			}
		}
		
		return surrounding; 
	}
	
	
	
	public double[][] getMap() {
		return map; 
	}
	
	public int getCellWidth() {
		return cellWidth;
	}
	
	public double getInfluence(int[] pos) {
		return map[pos[1]][pos[0]];
	}
	
	public double[][] getPositiveMap() {
		return positiveMap;
	}
	
	public double[][] getNegativeMap() {
		return negativeMap;
	}
	
	public InfluenceNode getBiggest() {
		double biggest = Double.MIN_VALUE;
		InfluenceNode best = new InfluenceNode(0,0);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] > biggest) {
					biggest = map[i][j];
					best = new InfluenceNode(j,i);
				}
			}
		}
		return best; 
	}
	
	public static void main(String[] args) {
		InfluenceMap m = new InfluenceMap(100, 100);
		m.propagate(new InfluenceNode(0,0), 0.9, 1);
		//m.propagate2(new Node(10,10));
		
		double[][] grid = m.getPositiveMap(); 
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");
				
			}
			System.out.println();
		}
	}
}
