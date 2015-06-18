package Agent.InfluenceMap;

import java.util.ArrayList;

public class RecentMap {
	private int[][] map; 
	private int cellWidth = 5;
	private int[][] discreteMap; 
	
	public RecentMap(int width, int height, int[][] discreteMap) {
		map = new int[height/cellWidth][width/cellWidth];
		this.discreteMap = discreteMap; 
	}
	
	public void increment() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j< map[0].length; j++) {
				map[i][j]++; 
			}
		}
	}
	
	public void visit(int[] pos) {
		map[pos[1]][pos[0]] = 0;
	}
	
	public int[] findBest(int[] pos) {
		ArrayList<int[]> neighbours = getAdjacent(pos[0], pos[1]);
		
		int biggest = Integer.MIN_VALUE;
		int[] chosen = new int[2];
		for (int[] cell : neighbours) {
			if (map[cell[1]][cell[0]] > biggest) {
				biggest = map[cell[1]][cell[0]];
				chosen = cell; 
			}
		}
		visit(chosen);
		return chosen; 
	}
	
	/*public ArrayList<int[]> getNeighbours(int[] pos) {
		ArrayList<int[]> neighbours = new ArrayList<int[]>(); 
		if (!isWall(new int[] {pos[0], pos[1]-1}));
			neighbours.add(new int[] {pos[0], pos[1]-1});
		if (!isWall(new int[] {pos[0], pos[1]+1}));
			neighbours.add(new int[] {pos[0], pos[1]+1});
		if (!isWall(new int[] {pos[0]-1, pos[1]}));
			neighbours.add(new int[] {pos[0]-1, pos[1]});
		if (!isWall(new int[] {pos[0]+1, pos[1]}));
			neighbours.add(new int[] {pos[0]+1, pos[1]});
		return neighbours;
	}*/
	
	public ArrayList<int[]> getAdjacent(int x, int y) {
		ArrayList<int[]> adjacent = new ArrayList<int[]>(); 
		if (x > 0 && discreteMap[y][x-1] != 1 && discreteMap[y][x-1] != 4) 
			if (!isWall(x-1,y)) adjacent.add(new int[] {x-1, y});
		if (x+1 < discreteMap[0].length && discreteMap[y][x+1] != 1 && discreteMap[y][x+1] != 4) 
			if (!isWall(x+1,y)) adjacent.add(new int[] {x+1, y});
		if (y > 0 && discreteMap[y-1][x] != 1 && discreteMap[y-1][x] != 4) 
			if (!isWall(x,y-1)) adjacent.add(new int[] {x, y-1});
		if (y+1 < discreteMap.length && discreteMap[y+1][x] != 1 && discreteMap[y+1][x] != 4) 
			if (!isWall(x,y+1)) adjacent.add(new int[] {x, y+1});
		
		
		return adjacent; 
	}
	
	public boolean isWall(int x, int y) {
		
		if (discreteMap[y][x] == 1)
			return true;
		
		return false;
	}
}
