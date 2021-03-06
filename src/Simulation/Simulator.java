package Simulation;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import Agent.Agent;
import Agent.InternalMap;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import Agent.InfluenceMap.InfluenceMap;
import Agent.InfluenceMap.InfluenceNode;
import Agent.InfluenceMap.RecentMap;
import GUI.DeveloperFrame;
import GUI.MainFrame;
import Map.Map;
import GUI.MapViewer;


public class Simulator {
	public static final double GAME_HERTZ = 5.0;	
	public static final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	
	private boolean simulationRunning = false;
	private boolean simulationPaused = false;
	private int fps = 60;
	private int frameCount = 0;
	private MainFrame frame;
    private MapViewer mapView;
    private BufferedImage imageRotate;
	private Map map; 
	private ArrayList<Agent> agents;
	public static IntruderAgent intruder;
	private boolean intruderSpawned = false;
	private int goalZoneCounter = 0;
	private long start;
	private int steps = 0;
    
	private double timebetweenupdates;
	DeveloperFrame developerFrame = new DeveloperFrame(Agent.internalMap, Agent.influenceMap);
	
	
	public Simulator(Map map, ArrayList<Agent> agents) {		
		this.map = map;	
		this.agents = agents; 
	}
	
	

	public void startSimulation() {
		start = System.currentTimeMillis();
		map = frame.getMap();
		map.resetMarkers();
		simulationRunning = true; 
		
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}

	public void gameLoop() {
		//This value would probably be stored elsewhere.
		
		timebetweenupdates = TIME_BETWEEN_UPDATES;
       // final double TIME_BETWEEN_UPDATES = 1000000000 ;// GAME_HERTZ;
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		//We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		//Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		//Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (simulationRunning) {
			double now = System.nanoTime();
			int updateCount = 0;

			if (!simulationPaused) {
				//Do as many game updates as we need to, potentially playing catchup.
				while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
					updateSimulation();
					//System.out.println("Time = " + TIME_BETWEEN_UPDATES);
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
					
				}

				//If for some reason an update takes forever, we don't want to do an insane number of catchups.
				//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
				if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES) 
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				
				//Render. To do so, we need to calculate interpolation for a smooth render.
				//float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
				//drawGame(interpolation);
				drawSimulation();
				lastRenderTime = now;

				//Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					//System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
					fps = frameCount;
					frameCount = 0;
					lastSecondTime = thisSecond;
				}

				//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
				while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();

					//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
					//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
					//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
					try {Thread.sleep(1);} catch(Exception e) {} 

					now = System.nanoTime();
				}
			}
		}
		
	}

	//needs some cleaning up
	public void updateSimulation() {
		steps++;
		if (Agent.internalMap.explorationComplete(map)) {
			long time = System.currentTimeMillis() - start;
			System.out.println(steps + "exploration complete: " + time + "ms, real wall percent: " + map.wallPercent()+ ", internal wall percent: " + Agent.internalMap.wallPercent() + ", grid size: " + Agent.internalMap.getCellWidth());
			
			
		}
		
		
		if (goalZoneCounter >= 3) {
			//stopSimulation();
			System.out.println("intruder won");
		}
		
		Agent.influenceMap.decay();
		
		Agent.recentMap.increment();
		
		//this prevents comodification exceptions
		if (intruderSpawned) {
			agents.add(intruder); 			
			//map.setAgents(agents);
			intruderSpawned = false;
		}
		if (intruder != null) {
			goalZoneCounter = map.inGoalZone(intruder) ? ++goalZoneCounter : 0;			
		}
		for (Agent a : agents) {
			//a.getNextMove(timebetweenupdates);
			/*if (a instanceof IntruderAgent) {
				a.getNextMove(timebetweenupdates);
			
				System.out.println("intruder");
			}
			else  */
			if (intruder != null && a instanceof SurveillanceAgent && intruder.isInVicinity(a)) {
				//stopSimulation();
				System.out.println("guards win");
			}
				
			a.getMove();
			
		}

		//Agent.influenceMap.propagate2();
		

		developerFrame.repaint();
		//if (intruder != null) intruder.getMove();
//			Point2D[] move = a.getNextMove();
//			if(move == null){System.out.println("WHY IS IT NULL!!! ;A;");}
//			int width = (int)(move[1].getX()-move[0].getX());
//			int height = (int)(move[1].getY()-move[0].getY());
//			Rectangle collisionRectangle = new Rectangle((int)move[0].getX(), (int)move[0].getY(), width, height);
//
//            mapView = frame.getMapViewer();
//            imageRotate = mapView.getImage(a);
//            double angle = Math.toRadians(90);
//
//            currentMove = a.getNextMove();
//            if (map.collidesWithTower(collisionRectangle)) {
//            	a.setTopLeft(currentMove[0]);
//                a.setBottomRight(currentMove[1]);
//                a.setOnSentryTower(true);
//                mapView.rotate(imageRotate, angle, a);
//
//            }
//            else if (map.checkCollisions(collisionRectangle)) {
//                a.setTopLeft(currentMove[0]);
//                a.setBottomRight(currentMove[1]);
//                a.setOnSentryTower(false);
//                mapView.rotate(imageRotate, angle, a);
//            }
//        }
		drawSimulation();
	}

	public void drawSimulation() {
		frame.repaint();
	}
	
	public boolean isRunning() {
		return simulationRunning; 
	}
	
	public boolean isPaused() {
		return simulationPaused; 
	}
	
	public void stopSimulation() {
		simulationRunning = false;
		Agent.internalMap = new InternalMap(map.getWidth(), map.getHeight()); 
		Agent.influenceMap = new InfluenceMap(map.getWidth(), map.getHeight());
		Agent.recentMap = new RecentMap(map.getWidth(), map.getHeight(), map.getDiscretizedMap(5));
		developerFrame.reset(Agent.influenceMap, Agent.internalMap);
		
		
		//reset agent locations
		ArrayList<Agent> newAgents = new ArrayList<Agent>();
		for (int i = 0; i < agents.size(); i++) {
			Point2D position = map.findEmptySpot(4);
			newAgents.add(new SurveillanceAgent(position, new Point2D.Double(position.getX()+Agent.getSize(), position.getY()+Agent.getSize()), map));
		}
		agents = newAgents;
		frame.reset(newAgents);
		
		
	}
	
	public void pauseSimulation() {
		simulationPaused = true; 
	}
	
	public void continueSimulation() {
		simulationPaused = false; 
	}
	
	public void setFrame(MainFrame frame) {
		this.frame = frame;	
		
	}
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	
	public void spawnIntruder() {
		Point2D topLeft =  findSpawnPoint();//new Point2D.Double(150,4); 
		Point2D bottomRight = new Point2D.Double(topLeft.getX()+Agent.getSize(), topLeft.getY()+Agent.getSize());
		intruder = new IntruderAgent(topLeft, bottomRight, map); 
		intruderSpawned = true;
	}
	
	public Point2D findSpawnPoint() {
		Point2D point = new Point2D.Double();
		Random rnd = new Random();
		boolean horizontal = rnd.nextBoolean(); 
		boolean side = rnd.nextBoolean();
		
		
		if (horizontal) {
			if (side) 
				point.setLocation(rnd.nextInt(map.getWidth()), 4);			
			else 
				point.setLocation(rnd.nextInt(map.getWidth()), 194);
		}
		else {
			if (side) 
				point.setLocation(4, rnd.nextInt(map.getHeight()));			
			else 
				point.setLocation(194, rnd.nextInt(map.getHeight()));
			
		}
		//System.out.println(point);
		return point;
	}
	
	
}
