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
		Map m = new MapGenerator(width, height).getMap();
		//ArrayList<Agent> agents = new SimpleAlgorithm(m).getAgents();
		ArrayList<Agent> agents = new ArrayList<Agent>();
		agents.add(new IntruderAgent(new Point2D.Double(100, 100), new Point2D.Double(105, 105)));
		agents.add(new SurveillanceAgent(new Point2D.Double(200, 200), new Point2D.Double(205, 205)));
		Simulator main = new Simulator(m, agents);
		MainFrame frame = new MainFrame(m, agents, main);
		main.setFrame(frame);
	
	}
}
