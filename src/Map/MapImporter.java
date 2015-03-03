package Map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObjects;
import GameObjects.OuterWall;
import GameObjects.SentryTower;
import GameObjects.Structure;
 
public class MapImporter {
	private static final String DIR = "src/savedMaps/"; 
	
	public Map importMap(String mapName) {
		int width = 0;
		int height = 0; 
		ArrayList<InanimateObjects> gameObjects = new ArrayList<InanimateObjects>(); 
		GoalZone goalZone = null; 
		BufferedReader br = null;		
		boolean firstLine = true; 
		
		try {
 
			String currentLine;
 
			br = new BufferedReader(new FileReader(DIR + mapName));
 
			while ((currentLine = br.readLine()) != null) {
				if (firstLine) {
					String[] size = currentLine.split("\\s+"); 
					width = Integer.parseInt(size[0]);
					height = Integer.parseInt(size[1]); 
					firstLine = false; 
				}
				else {
					String[] gameObject = currentLine.split("\\s+"); 
					Point topLeft = new Point(Integer.parseInt(gameObject[1]), Integer.parseInt(gameObject[2])); 
					Point bottomRight = new Point(Integer.parseInt(gameObject[3]), Integer.parseInt(gameObject[4])); 
					
					if (Integer.parseInt(gameObject[0]) == InanimateObjects.SENTRY_TYPE) {
						gameObjects.add(new SentryTower(topLeft, bottomRight)); 
					}
					else if (Integer.parseInt(gameObject[0]) == InanimateObjects.GOAL_TYPE) {
						goalZone = new GoalZone(topLeft, bottomRight); 
					}
					else if (Integer.parseInt(gameObject[0]) == InanimateObjects.OUTERWALL_TYPE) {
						gameObjects.add(new OuterWall(topLeft, bottomRight)); 
					}
					else if (Integer.parseInt(gameObject[0]) == InanimateObjects.STRUCTURE_TYPE) {
						gameObjects.add(new Structure(topLeft, bottomRight)); 
					}
				}
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Map map = new Map(width, height, gameObjects);
		if (goalZone != null) 
			map.setGoalZone(goalZone); 
 
		return map;
	}
}