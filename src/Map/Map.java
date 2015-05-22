package Map;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.Marker;
import GameObjects.SentryTower;

public class Map {
	private ArrayList<InanimateObject> gameObjects; 
	private ArrayList<Marker> markers = new ArrayList<Marker>(); 
	private GoalZone goalZone; 	 
	private int width; 
	private int height; 
	
	public Map(int width, int height, ArrayList<InanimateObject> gameObjects, GoalZone goalZone) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects; 
		this.goalZone = goalZone; 
	}
	
	/*
	public Map(int width, int height, ArrayList<InanimateObject> gameObjects) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects;
		goalZone = (GoalZone) gameObjects.get(0);
		gameObjects.remove(0);
		for(int i = 0;i<gameObjects.size();i++){
			System.out.println(gameObjects.get(i).getClass());
		}
	}*/
	
	//copy constructor
	public Map(Map map) {
		this(map.getWidth(), map.getHeight(), new ArrayList<InanimateObject>(map.getGameObjects()), map.getGoalZone());
	}
	
	public ArrayList<InanimateObject> getGameObjects() {
		return gameObjects; 
	}
	
	public int getWidth() {
		return width; 
	}
	
	public int getHeight() {
		return height; 
	}
	
	public void setGoalZone(GoalZone g) {
		goalZone = g; 
	}
	
	public GoalZone getGoalZone() {
		return goalZone; 
	}
	
	public boolean addObject(InanimateObject o) {
		if (checkCollisions(getObjectRectangle(o))) {
			gameObjects.add(o); 
			return true;
		}
		
		return false; 
	}
	
	public boolean checkCollisions(Rectangle objectRectangle) {
		for (InanimateObject i : gameObjects) {
			Rectangle r = getObjectRectangle(i);
			if (r.intersects(objectRectangle)) 
				return false;
		}
		
		return true;
	}
	
	public boolean collidesWithTower(Rectangle objectRectangle) {		
		for (InanimateObject i : gameObjects) {
			if (i instanceof SentryTower) {
				Rectangle r = getObjectRectangle(i);
				if (r.intersects(objectRectangle)) 
					return true;
			}			
		}
		
		return false;
	}
	
	public Rectangle getObjectRectangle(InanimateObject o) {
		int objectWidth = (int)(o.getBottomRight().getX() - o.getTopLeft().getX()); 
		int objectHeight = (int)(o.getBottomRight().getY() - o.getTopLeft().getY()); 
		
		return new Rectangle((int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), objectWidth, objectHeight);		
	}
	
	public boolean emptySpot(Point2D n){
		for(InanimateObject o : gameObjects){
			if(o.isInside(n))
				return false;			
		}
		
		return true;
	}

	public Point2D findEmptySpot(int length) {
		Point2D spot = new Point2D.Double((int)(Math.random()*width), (int)(Math.random()*height));
		Rectangle spotRectangle = new Rectangle((int)spot.getX(), (int)spot.getY(), length, length);
		while (!checkCollisions(spotRectangle)) {
			spot = new Point2D.Double((int)(Math.random()*width),(int) (Math.random()*height));			
			spotRectangle = new Rectangle((int)spot.getX(), (int)spot.getY(), length, length);
		}
		
		return spot; 
	}
	
	public void addMarker(Marker marker) {
		markers.add(marker); 
	}
	
	public ArrayList<Marker> getMarkers() {
		return markers; 
	}
	
	public int markersIn(Rectangle area) {
		int count = 0; 
		for (Marker m : markers) {
			Rectangle r = getObjectRectangle(m);
			if (area.intersects(r)) 
				count++; 
		}
		return count;
	}
	
	public int[][] getDiscretizedMap(int squareSize) {		
		int[][] grid = new int[width/squareSize][height/squareSize];
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				Rectangle r = new Rectangle(squareSize*j, squareSize*i, squareSize, squareSize); 
				if (!checkCollisions(r)) {
					grid[i][j] = 1; 
				}
			}
		}
		return grid; 
	}
	
	public void resetMarkers() {
		markers = new ArrayList<Marker>();
	}
}


