package Map;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObjects;

public class Map {
	private ArrayList<InanimateObjects> gameObjects; 
	private GoalZone goalZone; 	 
	private int width; 
	private int height; 
	
	public Map(int width, int height, ArrayList<InanimateObjects> gameObjects, GoalZone goalZone) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects; 
		this.goalZone = goalZone; 
	}
	
	public Map(int width, int height, ArrayList<InanimateObjects> gameObjects) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects; 
	}
	
	//copy constructor
	public Map(Map map) {
		this(map.getWidth(), map.getHeight(), new ArrayList<InanimateObjects>(map.getGameObjects()), map.getGoalZone());
	}
	
	public ArrayList<InanimateObjects> getGameObjects() {
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
	
	public boolean addObject(InanimateObjects o) {
		if (checkCollisions(getObjectRectangle(o))) {
			gameObjects.add(o); 
			return true;
		}
		
		return false; 
	}
	
	public boolean checkCollisions(Rectangle objectRectangle) {
		
		for (InanimateObjects i : gameObjects) {
			Rectangle r = getObjectRectangle(i);
			if (r.intersects(objectRectangle)) 
				return false;
		}
		
		return true; 
	}
	
	public Rectangle getObjectRectangle(InanimateObjects o) {
		int objectWidth = (int)(o.getBottomRight().getX() - o.getTopLeft().getX()); 
		int objectHeight = (int)(o.getBottomRight().getY() - o.getTopLeft().getY()); 
		
		return new Rectangle((int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), objectWidth, objectHeight);		
	}
	public boolean emptySpot(Point2D n){
		boolean answer = true;
		for(int i=0;i<gameObjects.size();i++){
			if(gameObjects.get(i).isInside(n)){
				answer = false;
			}
		}
		return answer;
	}
}


