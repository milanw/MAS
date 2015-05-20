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
		int[] best = candidates.get(0); 
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
		int tmp = map[pos[1]][pos[0]]; 
		map[pos[1]][pos[0]] = 1; 
		int rangeY = 3; 
		
		int endX = pos[0]+2 > map.length ? pos[0]+1 : pos[0]+2;
		
		int endY = pos[1]+2;
		if (pos[1]+2 > map[0].length) {
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
	        subgrid[i-y] = Arrays.copyOfRange(map[i], x, endX);
	    }
	    
	    map[pos[1]][pos[0]] = tmp;
	    return subgrid;
	}
	
	public ArrayList<int[]> getAccessibleCells(int[][] grid) {
		ArrayList<int[]> accessible = new ArrayList<int[]>(); 
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[j][i] != 1) 
					accessible.add(new int[] {i, j});
			}
		}
		return accessible; 
	}
	
	public boolean blockingPath(int[] pos) {
		//ArrayList<int[]> accessible = getCellsAround(pos[0], pos[1], 0);
		//accessible.addAll(getCellsAround(pos[0], pos[1], 3));
		ArrayList<int[]> accessible = getAccessibleCells(getSubgrid(pos));
		
		for (int i = 0; i < accessible.size(); i++) {
			for (int j = i+1; j < accessible.size(); j++) {
				Point2D start = new Point2D.Double(accessible.get(i)[0], accessible.get(i)[1]);
				Point2D goal = new Point2D.Double(accessible.get(j)[0], accessible.get(j)[1]);
				Astar a = new Astar(start, goal, getSubgrid(pos));
				if (a.getPath().isEmpty() || a.getPath() == null)
					return true;
			}
		}
		
		return false;
	}
	
	public int getCellWidth() {
		return cellWidth; 
	}
}
