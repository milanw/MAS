package Map;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObjects;
import GameObjects.OuterWall;
import GameObjects.SentryTower;

public class MapExporter {
	private static final String DIR = "src/savedMaps/"; 
	public enum ObjectType {SENTRY, GOAL, OUTERWALL, EMPTY}; 
	
	
	private String fileName; 
	private Map map; 
		
	public MapExporter(String fileName, Map map) {
		this.fileName = fileName; 
		this.map = map; 		
	}
	
	public void export() throws IOException {
		File file = new File(fileName + ".map");
		FileOutputStream fos = new FileOutputStream(DIR + file);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(map.getWidth() + " " + map.getHeight() + "\n");
		
		for (InanimateObjects o : map.getGameObjects()) {
			bw.write(getType(o) + " " + (int)o.getTopLeft().getX() + " " + (int)o.getTopLeft().getY() + " " + (int)o.getBottomRight().getX() + " " + (int)o.getBottomRight().getY());
			bw.newLine();
		}
	 
		bw.close();		
	}
	
	public int getType(InanimateObjects object) {
		if (object instanceof SentryTower) 
			return ObjectType.SENTRY.ordinal(); 
		if (object instanceof GoalZone) 
			return ObjectType.GOAL.ordinal(); 
		if (object instanceof OuterWall) 
			return ObjectType.OUTERWALL.ordinal(); 
		else 
			return ObjectType.EMPTY.ordinal(); 
	}
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<InanimateObjects> gameObjects = new ArrayList<InanimateObjects>(); 
		gameObjects.add(new GoalZone(new Point(100, 100), new Point(150, 150))); 
		gameObjects.add(new SentryTower(new Point(200, 300), new Point(400, 500))); 
    	Map m = new Map(600, 600, gameObjects);
    	
		MapExporter e = new MapExporter("test", m); 
		e.export();
		
	}
}
