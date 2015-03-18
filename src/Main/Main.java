package Main;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import Algorithm.SimpleAlgorithm;
import GUI.MainFrame;
import GUI.Menu;
import Map.Map;
import Map.mapGenerator;


public class Main {
	private boolean simulationRunning = true;
	private boolean simulationPaused = false;
	private int fps = 60;
	private int frameCount = 0;
	private MainFrame frame;
	private Map map; 
	private ArrayList<Agent> agents;
    private Point2D[] currentMove;
    private SimpleAlgorithm alg;


	public Main(Map nmap) {

		map = nmap;	
		agents = new ArrayList<Agent>();
		Point2D.Double s1 = new Point2D.Double(Math.random()*map.getHeight(), Math.random()*map.getWidth());
		Point2D.Double s2 = new Point2D.Double(Math.random()*map.getHeight(), Math.random()*map.getWidth());
		while(!map.emptySpot(s1)||!map.emptySpot(s2)){
			s1 = new Point2D.Double(Math.random()*map.getHeight(), Math.random()*map.getWidth());
			s2 = new Point2D.Double(Math.random()*map.getHeight(), Math.random()*map.getWidth());
		}
		agents.add(new IntruderAgent(new Point2D.Double(100, 100), new Point2D.Double(105, 105)));
		agents.add(new SurveillanceAgent(new Point2D.Double(200, 200), new Point2D.Double(205, 205))); 
		frame = new MainFrame(map, agents);
		startGameLoop();
        alg = new SimpleAlgorithm(map);
	}

	public void startGameLoop() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}

	public void gameLoop() {
		//This value would probably be stored elsewhere.
		final double GAME_HERTZ = 30.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
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
					System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
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

	public void updateSimulation() {
		for (Agent a : agents) {

            currentMove = a.getNextMove();
            if (isMoveValid(currentMove)) {
                a.setTopLeft(currentMove[0]);
                a.setBottomRight(currentMove[1]);
            }
        }



	}
	
	
	public boolean isMoveValid(Point2D[] move) {
		int width = (int)(move[1].getX()-move[0].getX());
		int height = (int)(move[1].getY()-move[0].getY());
		Rectangle collisionRectangle = new Rectangle((int)move[0].getX(), (int)move[0].getY(), width, height);
		
		return map.checkCollisions(collisionRectangle);
	}

	public void drawSimulation() {
		frame.repaint();
	}
}
