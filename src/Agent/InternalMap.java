package Agent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import Agent.Astar.Astar;

//1 wall, , 3 explored, 4 visited


public class InternalMap {
	private int[][] map; 
	private int cellWidth = 5; 
	
	public InternalMap(int width, int height) {
		map = new int[height/cellWidth][width/cellWidth]; 
		
	}
	
	public InternalMap(int width, int height, int cellWidth) {
		this.cellWidth = cellWidth; 
		map = new int[height/cellWidth][width/cellWidth]; 
	}
	
	public void setCell(int x, int y, int type) {
		map[y][x] = type; 
	}
	
	public void setCell(int[] pos, int type) {
		setCell(pos[0], pos[1], type);
	}
	
	public int getCell(int x, int y) {
		return map[y][x]; 
	}
	
	public int[][] getMap() {
		return map; 
	}
	
	public void setMap(int[][] m) {
		map = m; 
	}
	
	public ArrayList<int[]> get8CellsAround(int x, int y) {
		ArrayList<int[]> cells = getCellsAround(x, y); 
		if (x-1 >= 0 && y-1 >=0) cells.add(new int[] {x-1, y-1});
		if (x+1 < map[0].length && y+1 < map.length) cells.add(new int[] {x+1, y+1});
		if (x-1 >= 0 && y+1 < map.length) cells.add(new int[] {x-1, y+1});
		if (x+1 < map[0].length && y-1 >=0) cells.add(new int[] {x+1, y-1});
		
		return cells;		
	}
	
	public ArrayList<int[]> getCellsAround(int x, int y) {
		ArrayList<int[]> cells = new ArrayList<int[]>(); 
		if (x-1 >= 0) cells.add(new int[] {x-1, y});
		if (x+1 < map[0].length) cells.add(new int[] {x+1, y});
		if (y-1 >= 0) cells.add(new int[] {x, y-1});
		if (y+1 < map.length) cells.add(new int[] {x, y+1});
		
		return cells;
	}
	
	//get at most 4 cells around position that are unexplored
	public ArrayList<int[]> getCellsAround(int x, int y, int type) {
		ArrayList<int[]> cells = getCellsAround(x, y);
		ArrayList<int[]> matches = new ArrayList<int[]>(); 
		
		for (int[] c : cells) {
			if(map[c[1]][c[0]] == type)
				matches.add(c); 
		}
		
		return matches; 
	}
	
	public int[] getBestCell(ArrayList<int[]> candidates) {
		int[] best = null; 
		int wallVisitedAround = 0; 
		
		for (int[] c : candidates) {
			int sum = getCellsAround(c[0], c[1], 1).size() +  getCellsAround(c[0], c[1], 4).size(); 
			if (sum >= wallVisitedAround) {
				best = c;
				wallVisitedAround = sum;
			}
		}
		
		return best; 
	}
	
	public int[][] getSubgrid(int[] pos) {
		int[][] tmpMap = map.clone();
		
		tmpMap[pos[1]][pos[0]] = 1; 
		int rangeY = 3; 
		
		int endX = pos[0]+2 > tmpMap.length ? pos[0]+1 : pos[0]+2;
		
		int endY = pos[1]+2;
		if (pos[1]+2 > tmpMap[0].length) {
			endY = pos[1]+1;
			rangeY--;
		}
		
		int x = pos[0] == 0 ? pos[0] : pos[0] -1;
		
		int y = pos[1] -1; 
		if (pos[1] == 0) {
			y = pos[1]; 
			rangeY--; 
		}
		
	    int[][] subgrid = new int[rangeY][];
	    for (int i = y; i < endY; i++) {
	        subgrid[i-y] = Arrays.copyOfRange(tmpMap[i], x, endX);
	    }
	    
	  
	    return subgrid;
	}
	
	public ArrayList<int[]> getAccessibleCells(int[][] grid) {
		ArrayList<int[]> accessible = new ArrayList<int[]>(); 
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != 1 && grid[i][j] != 4) 
					accessible.add(new int[] {j, i});  //weird
			}
		}
		return accessible; 
	}
	
	
	public boolean blockingPath(int[] pos) {		
		ArrayList<int[]> accessible = getAccessibleCells(getSubgrid(pos)); 
		for (int i = 0; i < accessible.size(); i++) {
			for (int j = i+1; j < accessible.size(); j++) {
				if (!pathBetween(getSubgrid(pos), accessible.get(i), accessible.get(j)))
					return true;
			}
		}
		return false; 
	}
	
	// fills in all cells in discovered that are reachable from (x,y) as true
	public void connectedComponent(int[][] grid, boolean[][] discovered, int x, int y) {
		discovered[y][x] = true; 
		for (int[] v : getAdjacent(grid, x, y)) {
			if (discovered[v[1]][v[0]] != true) 
				connectedComponent(grid, discovered, v[0], v[1]);
		}
		
	}
	
	
	public ArrayList<int[]> getAdjacent(int[][] grid, int x, int y) {
		ArrayList<int[]> adjacent = new ArrayList<int[]>(); 
		if (x > 0 && grid[y][x-1] != 1 && grid[y][x-1] != 4) 
			adjacent.add(new int[] {x-1, y});
		if (x+1 < grid[0].length && grid[y][x+1] != 1 && grid[y][x+1] != 4) 
			adjacent.add(new int[] {x+1, y});
		if (y > 0 && grid[y-1][x] != 1 && grid[y-1][x] != 4) 
			adjacent.add(new int[] {x, y-1});
		if (y+1 < grid.length && grid[y+1][x] != 1 && grid[y+1][x] != 4) 
			adjacent.add(new int[] {x, y+1});
		
		return adjacent; 
	}
	
	public boolean pathBetween(int[][] grid, int[] start, int[] goal) {
		boolean[][] reachable = new boolean[grid.length][grid[0].length];
		connectedComponent(grid, reachable, start[0], start[1]); 
		return reachable[goal[1]][goal[0]]; 
		
	}
	
	// is cell occupied by an agent?
	public boolean cellOccupied(int[] pos) {
		for (Agent a : Agent.getAllAgents()) {
			if (a.getDiscretePosition()[0] == pos[0] && a.getDiscretePosition()[1] == pos[1])
				return true;
		}
		return false;
	}
	
	public ArrayList<int[]> removeOccupied(ArrayList<int[]> cells) {
		ArrayList<int[]> cleaned = new ArrayList<int[]>();
		for (int[] c : cells) {
			if (!cellOccupied(c))
				cleaned.add(c);
		}
		return cleaned;
	}
	
	public int getCellWidth() {
		return cellWidth; 
	}
	
	
}
