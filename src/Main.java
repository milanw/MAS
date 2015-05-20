import java.awt.geom.Point2D;
import java.util.ArrayList;

import Agent.Agent;
import Agent.InternalMap;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import GUI.MainFrame;
import Map.Map;
import Map.MapGenerator;
import Simulation.Simulator;

public class Main {
	public static void main(String[] args){
		int mapWidth = 200;
		int mapHeight = 200;
		Map map = new MapGenerator(mapWidth, mapHeight).getMap();
		InternalMap internalMap = new InternalMap(mapWidth, mapHeight); 
		Agent.internalMap = internalMap; 
		
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		int size = Agent.getSize(); 
		Point2D position = map.findEmptySpot(size);
		agents.add(new IntruderAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		position = map.findEmptySpot(size);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+size, position.getY()+size), map));
		
		
		Simulator main = new Simulator(map, agents);
		MainFrame frame = new MainFrame(map, agents, main);
		main.setFrame(frame);
	
	}
}
