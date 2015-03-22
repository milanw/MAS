import java.awt.geom.Point2D;
import java.util.ArrayList;

import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import GUI.MainFrame;
import Map.Map;
import Map.MapGenerator;
import Simulation.Simulator;


public class Main {
	public static void main(String[] args){
		int width = 600;
		int height = 600;
		Map map = new MapGenerator(width, height).getMap();
		//ArrayList<Agent> agents = new SimpleAlgorithm(m).getAgents();
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		Point2D position = map.findEmptySpot(5);
		agents.add(new IntruderAgent(position, new Point2D.Double(position.getX()+5, position.getY()+5)));
		position = map.findEmptySpot(5);
		agents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+5, position.getY()+5)));
		
		Simulator main = new Simulator(map, agents);
		MainFrame frame = new MainFrame(map, agents, main);
		main.setFrame(frame);
	
	}
}
