package Map;

import java.awt.Rectangle;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObjects;

public class Map {
	private ArrayList<InanimateObjects> gameObjects; 
	private GoalZone goalZone; 	 
	private int width; 
	private int height; 
	
	public Map(int width, int height, ArrayList<InanimateObjects> gameObjects) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects; 
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
	
	public void addObject(InanimateObjects o) {
		if (checkCollisions(o))
			gameObjects.add(o); 
	}
	
	public boolean checkCollisions(InanimateObjects o) {
		Rectangle objectRectangle = getObjectRectangle(o);
		
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
}


