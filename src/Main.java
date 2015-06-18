
import java.awt.geom.Point2D;
import java.util.ArrayList;

import Agent.Agent;
import Agent.InternalMap;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import Agent.InfluenceMap.InfluenceMap;
import Agent.InfluenceMap.RecentMap;
import GUI.MainFrame;
import GameObjects.InanimateObject;
import Map.Map;
import Map.MapGenerator;
import Map.MapImporter;
import Simulation.Simulator;

public class Main {
	public static Map map; 
	
	public static void main(String[] args){
		int mapWidth = 200;
		int mapHeight = 200;
		MapImporter importer = new MapImporter();
		map = importer.importMap("pres.map"); //new MapGenerator(mapWidth, mapHeight).getMap();
		//map = new MapGenerator(mapWidth, mapHeight).getMap();
		//map = new Map(mapWidth, mapHeight, new ArrayList<InanimateObject>(), null);
		InternalMap internalMap = new InternalMap(mapWidth, mapHeight); 
		InfluenceMap influenceMap = new InfluenceMap(mapWidth, mapHeight);
		RecentMap recentMap = new RecentMap(mapWidth, mapHeight, map.getDiscretizedMap(5));
		//internalMap.setMap(map.getDiscretizedMap(internalMap.getCellWidth()));
		Agent.internalMap = internalMap; 
		Agent.influenceMap = influenceMap;
		Agent.recentMap = recentMap;
		
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		int size = Agent.getSize(); 
		Point2D position = new Point2D.Double(3, 50);
		//agents.add(new IntruderAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		/*position = new Point2D.Double(2, 50);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(194, 2);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(2, 194);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position =new Point2D.Double(194, 194);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(2, 100);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(100, 194);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(100, 2);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(194, 50);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(50, 2);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(194, 150);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(150, 2);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(4);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(4);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(4);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(4);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(4);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(4);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		*/
		
		
		int numberOfAgents = 10; 
		for (int i = 0; i < numberOfAgents; i++) {
			position = map.findEmptySpot(4);
			agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		}
		
		
		
		Simulator main = new Simulator(map, agents);
		MainFrame frame = new MainFrame(map, main);
		main.setFrame(frame);
		
		
	
	}
	
	
}

