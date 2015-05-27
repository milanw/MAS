package Map;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.Marker;
import GameObjects.SentryTower;

public class Map {
	private ArrayList<InanimateObject> gameObjects; 
	private ArrayList<Marker> markers = new ArrayList<Marker>(); 
	private GoalZone goalZone; 	 
	private ArrayList<SurveillanceAgent> surveillanceAgents = new ArrayList<SurveillanceAgent>();
	private ArrayList<IntruderAgent> intruderAgents = new ArrayList<IntruderAgent>();

	private int width; 
	private int height; 

	public Map(int width, int height, ArrayList<InanimateObject> gameObjects, GoalZone goalZone) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects; 
		this.goalZone = goalZone; 
	}

	/*
	public Map(int width, int height, ArrayList<InanimateObject> gameObjects) {
		this.width = width; 
		this.height = height; 
		this.gameObjects = gameObjects;
		goalZone = (GoalZone) gameObjects.get(0);
		gameObjects.remove(0);
		for(int i = 0;i<gameObjects.size();i++){
			System.out.println(gameObjects.get(i).getClass());
		}
	}*/

	//copy constructor
	public Map(Map map) {
		this(map.getWidth(), map.getHeight(), new ArrayList<InanimateObject>(map.getGameObjects()), map.getGoalZone());
	}

	public ArrayList<InanimateObject> getGameObjects() {
		return gameObjects; 
	}

	public int getWidth() {
		return width; 
	}

	public int getHeight() {
		return height; 
	}

	public void setGoalZone(GoalZone g) {
		goalZone = g; 
	}

	public GoalZone getGoalZone() {
		return goalZone; 
	}

	public boolean addObject(InanimateObject o) {
		if (checkCollisions(getObjectRectangle(o))) {
			gameObjects.add(o); 
			return true;
		}

		return false; 
	}

	public void setAgents(ArrayList<Agent> agents) {
		for (Agent a : agents) {
			if (a instanceof IntruderAgent)
				intruderAgents.add((IntruderAgent) a);
			else if (a instanceof SurveillanceAgent)
				surveillanceAgents.add((SurveillanceAgent) a); 			
		}
	}

	public boolean checkAudio() {

		for (IntruderAgent ia : intruderAgents) {
			for (SurveillanceAgent sa : surveillanceAgents) {

				double middleIx = (ia.getTopLeft().getX() + ia.getBottomRight().getX()) / 2.0;
				double middleSx = (sa.getTopLeft().getX() + sa.getBottomRight().getX()) / 2.0;
				double middleIy = (ia.getTopLeft().getY() + ia.getBottomRight().getY()) / 2.0;
				double middleSy = (sa.getTopLeft().getY() + sa.getBottomRight().getY()) / 2.0;
				double range = ia.getHearingRange();
				Point2D.Double intruder = new Point2D.Double(middleIx, middleIy);
				Point2D.Double surveillance = new Point2D.Double(middleSx, middleSy);

				if(intruder.distance(surveillance) < range*2) {
					System.out.println("intersected!!");
					return true;
				}
			}
		}
		return false;
	}


	public boolean checkCollisions(Rectangle objectRectangle) {
		for (InanimateObject i : gameObjects) {
			Rectangle r = getObjectRectangle(i);
			if (r.intersects(objectRectangle)) 
				return false;
		}

		return true;
	}

	public boolean collidesWithTower(Rectangle objectRectangle) {		
		for (InanimateObject i : gameObjects) {
			if (i instanceof SentryTower) {
				Rectangle r = getObjectRectangle(i);
				if (r.intersects(objectRectangle)) 
					return true;
			}			
		}

		return false;
	}

	public Rectangle getObjectRectangle(InanimateObject o) {
		int objectWidth = (int)(o.getBottomRight().getX() - o.getTopLeft().getX()); 
		int objectHeight = (int)(o.getBottomRight().getY() - o.getTopLeft().getY()); 

		return new Rectangle((int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), objectWidth, objectHeight);		
	}

	public Rectangle getAgentRectangle(Agent o) {
		int objectWidth = (int)(o.getBottomRight().getX() - o.getTopLeft().getX()); 
		int objectHeight = (int)(o.getBottomRight().getY() - o.getTopLeft().getY()); 

		return new Rectangle((int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), objectWidth, objectHeight);		
	}

	public boolean emptySpot(Point2D n){
		for(InanimateObject o : gameObjects){
			if(o.isInside(n))
				return false;			
		}

		return true;
	}

	public Point2D findEmptySpot(int length) {
		Point2D spot = new Point2D.Double((int)(Math.random()*width), (int)(Math.random()*height));
		Rectangle spotRectangle = new Rectangle((int)spot.getX(), (int)spot.getY(), length, length);
		while (!checkCollisions(spotRectangle)) {
			spot = new Point2D.Double((int)(Math.random()*width),(int) (Math.random()*height));			
			spotRectangle = new Rectangle((int)spot.getX(), (int)spot.getY(), length, length);
		}

		return spot; 
	}

	public void addMarker(Marker marker) {
		markers.add(marker); 
	}

	public ArrayList<Marker> getMarkers() {
		return markers; 
	}

	public int markersIn(Rectangle area) {
		int count = 0; 
		for (Marker m : markers) {
			Rectangle r = getObjectRectangle(m);
			if (area.intersects(r)) 
				count++; 
		}
		return count;
	}

	public int[][] getDiscretizedMap(int squareSize) {		
		int[][] grid = new int[width/squareSize][height/squareSize];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				Rectangle r = new Rectangle(squareSize*j, squareSize*i, squareSize, squareSize); 
				if (!checkCollisions(r)) {
					grid[i][j] = 1; 
				}
			}
		}
		return grid; 
	}

	public int[] getDiscretePosition(Point2D position, int squareSize) {
		return new int[] {(int)position.getX()/squareSize, (int)position.getY()/squareSize}; 
	}

	public void resetMarkers() {
		markers = new ArrayList<Marker>();
	}

	public boolean inGoalZone(Agent a) {
		Rectangle goalRect = getObjectRectangle(goalZone);
		Rectangle agentRect= getAgentRectangle(a);

		return goalRect.intersects(agentRect);

	}
}


