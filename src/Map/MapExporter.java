package Map;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.OuterWall;
import GameObjects.SentryTower;
import GameObjects.Structure;

public class MapExporter {
	private static final String DIR = "src/savedMaps/"; 
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
		//write width and height
		bw.write(map.getWidth() + " " + map.getHeight() + "\n");
		
		//write goal zone
		GoalZone g = map.getGoalZone(); 
		if (g != null) 
			bw.write(InanimateObject.GOAL_TYPE + " " + (int)g.getTopLeft().getX() + " " + (int)g.getTopLeft().getY() + " " + (int)g.getBottomRight().getX() + " " + (int)g.getBottomRight().getY() + "\n");
		else 
			bw.write(InanimateObject.GOAL_TYPE + " " + 0 + " " + 0 + " " + 0 + " " + 0 + "\n");
			
		//write arraylist
		for (InanimateObject o : map.getGameObjects()) {
			bw.write(getType(o) + " " + (int)o.getTopLeft().getX() + " " + (int)o.getTopLeft().getY() + " " + (int)o.getBottomRight().getX() + " " + (int)o.getBottomRight().getY());
			bw.newLine();
		}
	 
		bw.close();		
	}
	
	public int getType(InanimateObject object) {
		if (object instanceof SentryTower) 
			return InanimateObject.SENTRY_TYPE; 
		else if (object instanceof GoalZone) 
			return InanimateObject.GOAL_TYPE; 
		else if (object instanceof OuterWall) 
			return InanimateObject.OUTERWALL_TYPE; 
		else if (object instanceof Structure) 
			return InanimateObject.STRUCTURE_TYPE; 
		else 
			return InanimateObject.EMPTY_TYPE;
	}
	
	
}
