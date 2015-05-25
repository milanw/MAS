
import java.awt.geom.Point2D;
import java.util.ArrayList;

import Agent.Agent;
import Agent.InternalMap;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import Agent.InfluenceMap.InfluenceMap;
import GUI.MainFrame;
import GameObjects.InanimateObject;
import Map.Map;
import Map.MapGenerator;
import Simulation.Simulator;

public class Main {
	public static Map map; 
	
	public static void main(String[] args){
		int mapWidth = 200;
		int mapHeight = 200;
		map = new MapGenerator(mapWidth, mapHeight).getMap();
		//Map map = new Map(mapWidth, mapHeight, new ArrayList<InanimateObject>(), null);
		InternalMap internalMap = new InternalMap(mapWidth, mapHeight); 
		InfluenceMap influenceMap = new InfluenceMap(mapWidth, mapHeight);
		//internalMap.setMap(map.getDiscretizedMap(internalMap.getCellWidth()));
		Agent.internalMap = internalMap; 
		Agent.influenceMap = influenceMap;
		
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		int size = Agent.getSize(); 
		Point2D position = new Point2D.Double(3, 50);
		//agents.add(new IntruderAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(5, 5);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(194, 2);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = new Point2D.Double(2, 194);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position =new Point2D.Double(194, 194);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		
		
		
		
		Simulator main = new Simulator(map, agents);
		MainFrame frame = new MainFrame(map, main);
		main.setFrame(frame);
		
	
	}
	
	
}

