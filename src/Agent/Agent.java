package Agent;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Random;

import Agent.Astar.AStarSearch;
import Agent.Astar.Astar;
import Agent.Astar.Node;
import Agent.InfluenceMap.InfluenceMap;
import Agent.InfluenceMap.InfluenceNode;
import GameObjects.GoalZone;
import GameObjects.Marker;
import Map.Map;
import Simulation.Simulator;

public class Agent {
	private static int idCount = 0; 
	private static ArrayList<Agent> agents = new ArrayList<Agent>();
	public static InternalMap internalMap;
	public static InfluenceMap influenceMap;
	private boolean explorationFinished = false;
	public static boolean intruderEntered = false;
	
	private int id; 
	protected Map map; 
    private double speed = 1.4;
    private static int defaultSize = 4; 
	private int maxTurn;
	private boolean onSentryTower; 
	protected double hearingRange; 
	protected double visionRange;
	private int viewingAngle; 
    protected Point2D topLeft;
    protected Point2D bottomRight;
    private ArrayList<Point2D> path= new ArrayList<Point2D>();
    protected ArrayList<Node> queue = new ArrayList<Node>();
    private int[] lastPosition = new int[] {-1, -1}; 

	
	public Agent(Point2D topLeft, Point2D bottomRight, Map map){
		this.id = idCount++; 		
		this.topLeft = topLeft; 
		this.bottomRight = bottomRight; 
		this.map = map;
		this.agents.add(this);
	}
	
	public Agent(){
		
	}	
	
	//NOT USED ANYMORE!!
	public Point2D[] pheromoneMove() {
		Point2D left = new Point2D.Double(topLeft.getX()-getWidth(), topLeft.getY());
		Point2D right = new Point2D.Double(topLeft.getX()+getWidth(), topLeft.getY());
		Point2D top = new Point2D.Double(topLeft.getX(), topLeft.getY()-getHeight());
		Point2D bottom = new Point2D.Double(topLeft.getX(), topLeft.getY()+getHeight());
		Point2D[] directions = {left, top, bottom, right}; 
		
		Point2D result = new Point2D.Double(); 
		int minMarkers = Integer.MAX_VALUE;
		
		for (Point2D d : directions) {
			Rectangle r = new Rectangle((int)d.getX(), (int)d.getY(), getWidth(), getHeight());
			Rectangle r2 = new Rectangle((int)d.getX(), (int)d.getY(), getWidth(), getHeight());
			if (map.checkCollisions(r2)) { 
				int amountMarkers = map.markersIn(r);
				if (amountMarkers < minMarkers) {
					minMarkers = amountMarkers; 
					result = d; 
				}
			}
		}
		
		return new Point2D[] {result, new Point2D.Double(result.getX()+defaultSize, result.getY()+defaultSize)}; 
	}
	

	public void getNextMove(double TIME_BETWEEN_UPDATES) {
//		placeMarker(3); 
//		return pheromoneMove(); 
	
		//get a new path if none is specified yet
		
		if(path.isEmpty()){
			Point2D start = new Point2D.Double(topLeft.getX()+defaultSize/2, topLeft.getY()+defaultSize/2);
			GoalZone goalZone = map.getGoalZone();
			//Point2D goal = new Point2D.Double((int) (Math.random()*map.getWidth()), (int) (Math.random()*map.getHeight()));
			Point2D goal = new Point2D.Double(goalZone.getTopLeft().getX(), goalZone.getTopLeft().getY());
			while(map.emptySpot(goal)==false){
				goal = new Point2D.Double((int) (Math.random()*map.getWidth()), (int) (Math.random()*map.getHeight()));
			}
			Astar as = new Astar(start, goal, map);
			path = as.getPath();
			
			
			
		}
		
		//System.out.println("next move = " + path.get(0));
		//innitializing stuff, also new direction if the next part of the path is already found and the agent needs to move on
		Point2D current = new Point2D.Double(topLeft.getX()+defaultSize/2, topLeft.getY()+defaultSize/2);
		//System.out.println("current = " + current);
		//System.out.println();
		Point2D newCurrent = new Point2D.Double();
		int newDirection = 0;
		
		if(path.size()>1){
			if(path.get(1).getY()<path.get(0).getY()){
				newDirection = 0;
			}else if(path.get(1).getY()>path.get(0).getY()){
				newDirection = 180;
			}else if(path.get(1).getX()<path.get(0).getX()){
				newDirection = 270;
			}else if(path.get(1).getX()>path.get(0).getX()){
				newDirection = 90;
			}
		}
		System.out.println("size = "+ path.size());
		//moving and in case of reaching the current path goal, turn moving to the next one
		if(path.get(0).getY()<current.getY()){
			newCurrent = new Point2D.Double(current.getX(), current.getY() - 1.4 * TIME_BETWEEN_UPDATES/1000000000);
			if(path.get(0).getY()>newCurrent.getY()){
				if(path.size()==1){
					System.out.print("g");
					newCurrent = path.get(0);
					path.remove(0);
				}else{
					double amountToMove = (1.4 * TIME_BETWEEN_UPDATES/1000000000) - (path.get(0).getY()-current.getY());
					newCurrent = turnMove(newDirection, amountToMove);
				}
			}
		}else if(path.get(0).getY()>current.getY()){
			newCurrent = new Point2D.Double(current.getX(), current.getY() + 1.4 * TIME_BETWEEN_UPDATES/1000000000);
			if(path.get(0).getY()<newCurrent.getY()){
				if(path.size()==1){
					System.out.print("g");
					newCurrent = path.get(0);
					path.remove(0);
				}else{
					double amountToMove = (1.4 * TIME_BETWEEN_UPDATES/1000000000) - (current.getY()-path.get(0).getY());
					newCurrent = turnMove(newDirection, amountToMove);
				}
			}
		}else if(path.get(0).getX()<current.getX()){
			newCurrent = new Point2D.Double(current.getX() - 1.4 * TIME_BETWEEN_UPDATES/1000000000, current.getY());
			if(path.get(0).getX()>newCurrent.getX()){
				if(path.size()==1){
					System.out.print("g");
					newCurrent = path.get(0);
					path.remove(0);
				}else{
					double amountToMove = (1.4 * TIME_BETWEEN_UPDATES/1000000000) - (path.get(0).getX()-current.getX());
					newCurrent = turnMove(newDirection, amountToMove);
				}
			}
		}else if(path.get(0).getX()>current.getX()){
			newCurrent = new Point2D.Double(current.getX() + 1.4 * TIME_BETWEEN_UPDATES/1000000000, current.getY());
			if(path.get(0).getX()<newCurrent.getX()){
				if(path.size()==1){
					System.out.print("g");
					newCurrent = path.get(0);
					path.remove(0);
				}else{
					double amountToMove = (1.4 * TIME_BETWEEN_UPDATES/1000000000) - (current.getX()-path.get(0).getX());
					newCurrent = turnMove(newDirection, amountToMove);
				}
			}
		}
		if(!path.isEmpty()){
			if(newCurrent.getX() == path.get(0).getX()&&newCurrent.getY() == path.get(0).getY()){
				path.remove(0);
			}
		}
		topLeft = new Point2D.Double(newCurrent.getX()-(defaultSize/2), newCurrent.getY()-(defaultSize/2));
		bottomRight = new Point2D.Double(newCurrent.getX()+(defaultSize/2), newCurrent.getY()+(defaultSize/2));
    }

	public Point2D turnMove(int newDirection, double amountToMove){
		Point2D newCurrent = new Point2D.Double();
		if(path.size()==1){
			newCurrent = path.get(0);
			path.remove(0);
			System.out.println("g");
		}else{
			//System.out.println("amount to move = " + amountToMove);
			if(newDirection == 0){
				newCurrent = new Point2D.Double(path.get(0).getX(), path.get(0).getY()-amountToMove);
			}else if(newDirection == 180){
				newCurrent = new Point2D.Double(path.get(0).getX(), path.get(0).getY()+amountToMove);
			}else if(newDirection == 270){
				newCurrent = new Point2D.Double(path.get(0).getX()-amountToMove, path.get(0).getY());
			}else if(newDirection == 90){
				newCurrent = new Point2D.Double(path.get(0).getX()-amountToMove, path.get(0).getY());
			}
			path.remove(0);
		}
		return newCurrent;
	}
	
	public void getMove() {
		if (intruderEntered) {
			//if (Simulator.intruder.isInVicinity(this))
			//if (influenceMap.getInfluence(getDiscretePosition()) < 0.8)	{
			if (!Simulator.intruder.isInVicinity(this)) {
				greedy();
			}
			else
				return;
			
		}
		else		
			brickAndMortar();
		/*if(Simulator.intruder != null && !Simulator.intruder.isInVicinity(this))
			influenceMap.propagate(new InfluenceNode(getDiscretePosition()[0], getDiscretePosition()[1]), 0.6, -1);*/
		
		influenceMap.propagate(new InfluenceNode(getDiscretePosition()[0], getDiscretePosition()[1]), 0.6, -1);
	}
	
	public void greedy() {
		
		internalMap.setMap(map.getDiscretizedMap(5));
		ArrayList<InfluenceNode> adjacent = influenceMap.getNeighbours(new InfluenceNode(getDiscretePosition()[0], getDiscretePosition()[1])); 
		double[][] influence = influenceMap.getMap();
		int[][] internal = internalMap.getMap();
		double bestValue = -2.0; 
		int[] bestCell = new int[2]; 
		int[] secondBestCell = new int[2]; 
		
		for (InfluenceNode node : adjacent) {
			
			if (internal[node.y][node.x] == 1)
				continue;
			
			if (influence[node.y][node.x] > bestValue) {
				bestValue = influence[node.y][node.x];
				secondBestCell = bestCell;
				bestCell = new int[] {node.x, node.y};
			}
				
		}
		if (lastPosition[0] == bestCell[0] && lastPosition[1] == bestCell[1])
			moveTo(secondBestCell);
		else
			moveTo(bestCell);
	}
	public void brickAndMortar() {
		int[] pos = getDiscretePosition(); 
		detectWalls(pos);
		
		//marking step
		
		if (!internalMap.blockingPath(pos)) {
			//System.out.println("marked as visited: " + pos[0] + " " + pos[1]); 
			internalMap.setCell(pos, 4); 
		}
		else {
			//System.out.println("marked as explored: " + pos[0] + " " + pos[1]); 
			internalMap.setCell(pos, 3); 
		}
		
		ArrayList<int[]> explored = internalMap.removeOccupied(internalMap.getCellsAround(pos[0], pos[1], 3));
		ArrayList<int[]> unexplored = internalMap.removeOccupied(internalMap.getCellsAround(pos[0], pos[1], 0));
		
		//navigation step
		//if at least one of the four cells around is unexplored
		if (!unexplored.isEmpty()) {
			//System.out.println("unexplored cells exist"); 
			int[] p = internalMap.getBestCell(unexplored);
			//System.out.println(p[0] + " " + p[1] + " " + pos[0] + " " + pos[1]);
			moveTo(p);
		}
		
		else if (!explored.isEmpty()) {
			int[] move = explored.remove(id%explored.size());
			Random rnd = new Random();
			//int[] move = explored.remove(rnd.nextInt(explored.size()));
			if (move[0] == lastPosition[0] && move[1] == lastPosition[1] && explored.size()>0)
				move = explored.get(id%explored.size());
			moveTo(move);
		}
		
		else {
			//System.out.println("exploration finished " + unexplored.size());
			explorationFinished = true; 
		}
		lastPosition = pos; 
	}
	
	public void moveTo(int[] pos) {
		lastPosition = pos;
		int width = internalMap.getCellWidth();
		//path.add(new Point2D.Double(pos[0]*width, pos[1]*width));
		topLeft = new Point2D.Double(pos[0]*width, pos[1]*width);
		bottomRight = new Point2D.Double(pos[0]*width+defaultSize, pos[1]*width+defaultSize);
	}
	
	public int[] getDiscretePosition() {
		int x = (int)topLeft.getX() / internalMap.getCellWidth(); 
		int y = (int)topLeft.getY() / internalMap.getCellWidth();
		
		return new int[] {x, y};		
	}
	
	public void detectWalls(int[] pos) {
		ArrayList<int[]> adjacent = internalMap.get8CellsAround(pos[0], pos[1]);
		for (int[] cell : adjacent) {
			if (map.getDiscretizedMap(internalMap.getCellWidth())[cell[1]][cell[0]] == 1) {
				internalMap.setCell(cell, 1);
			}
		}
	}
	
	//NOT USED ANYMORE
	public Point2D[] move(int direction){
		
		
		//invalid way to move, wrong speed etc.
		
		
		Point2D newTopLeft = new Point2D.Double();
		Point2D newBottomRight = new Point2D.Double();
		 double topy = topLeft.getY();
         double topx =  topLeft.getX();
         double bottomy = bottomRight.getY();
         double bottomx = bottomRight.getX();
        // moving up
        if(direction == 0){
        	newTopLeft.setLocation(topx, --topy);
            newBottomRight.setLocation(bottomx, --bottomy);
        }

        // moving left
        else if(direction == 1){
        	newTopLeft.setLocation(--topx, topy);
        	newBottomRight.setLocation(--bottomx, bottomy);
        }

        // moving right
        else if(direction == 2){
        	newTopLeft.setLocation(++topx, topy);
        	newBottomRight.setLocation(++bottomx, bottomy);
        }

        // moving down
        else if(direction == 3){
        	newTopLeft.setLocation(topx, ++topy);
        	newBottomRight.setLocation(bottomx, ++bottomy);
        }
        
        return new Point2D[] {newTopLeft, newBottomRight}; 
	}

	public ArrayList<Node> findPath(Point2D to) {
		int[] start = getDiscretePosition();
		int[] goal = map.getDiscretePosition(to, internalMap.getCellWidth());
		AStarSearch a = new AStarSearch(internalMap.getMap());
		return a.findPath(new Node(start[0], start[1]), new Node(goal[0], goal[1]));
		
	}
	
	public void turn() {

	}

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isInside(Point2D n){
        return n.getX() > topLeft.getX() && n.getX() < bottomRight.getX() && n.getY() > topLeft.getY() && n.getY() < bottomRight.getY();
    }

    public Point2D getTopLeft() {
        return topLeft;
    }

    public Point2D getBottomRight() {
        return bottomRight;
    }

    public void setTopLeft(Point2D point) {
        topLeft = point;
    }

    public void setBottomRight(Point2D point) {
        bottomRight = point;
    }
    
    public double getVisionRange() {
    	return visionRange; 
    }
    
    public void setVisionRange(double visionRange) {
        this.visionRange = visionRange;
    }
    
    public int getViewingAngle() {
        return viewingAngle;
    }

    public void setViewingAngle(int viewingAngle) {
        this.viewingAngle = viewingAngle;
    }
    
    public int getWidth() {
		return (int)(bottomRight.getX()-topLeft.getX());
	}
	
	public int getHeight() {
		return (int)(bottomRight.getY()-topLeft.getY()); 
	}
	
	/*
	 * return x coordinate of top left point
	 */
	public int getX() {
		return (int)topLeft.getX();
	}
	
	/* 
	 * return y coordinate of top left point
	 */
	public int getY() {
		return (int)topLeft.getY(); 
	}
	
	public boolean IsOnSentryTower(){
        return onSentryTower;
    }

    public void setOnSentryTower(boolean b) {
    	onSentryTower = b;
        if (onSentryTower) {            
            visionRange = 15.0;
            viewingAngle = 30;
        }
        else {
            visionRange = 6.0;
            viewingAngle = 45;
        }
    }
    
    public static int getSize() {
    	return defaultSize; 
    }
    
    public int getId() {
    	return id; 
    }
    
    
    
    public Point2D.Double getCenter() {
		double x = (topLeft.getX()+bottomRight.getX())/2;
		double y = (topLeft.getY()+bottomRight.getY())/2;
		return new Point2D.Double(x,y);
	}
    
    public void placeMarker(int type) {    	
    	Marker marker = new Marker(topLeft, type); 
    	map.addMarker(marker);
    }
    
    public static ArrayList<Agent> getAllAgents() {
    	return agents; 
    }
    
    public double getHearingRange() {
    	return hearingRange; 
    }


}