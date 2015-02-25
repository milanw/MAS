package Map;

import java.util.ArrayList;
import GameObjects.InanimateObjects;

public class Map {
	private ArrayList<InanimateObjects> gameObjects; 
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
}


