package Agent;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Random;

import Agent.Astar.Astar;
import GameObjects.Marker;
import Map.Map;

public class Agent {
	public static InternalMap internalMap;
	private boolean explorationFinished = false;
	
	private Map map; 
    private double speed = 1.4;
    private static int defaultSize = 4; 
	private int maxTurn;
	private boolean onSentryTower; 
	protected double visionRange;
	private int viewingAngle; 
    private Point2D topLeft;
    private Point2D bottomRight;
    private ArrayList<Point2D> path= new ArrayList<Point2D>();

	
	public Agent(Point2D topLeft, Point2D bottomRight, Map map){
		this.topLeft = topLeft; 
		this.bottomRight = bottomRight; 
		this.map = map;
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
			Point2D goal = new Point2D.Double((int) (Math.random()*map.getWidth()), (int) (Math.random()*map.getHeight()));
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
		brickAndMortar();
	}
	public void brickAndMortar() {
		int[] pos = getDiscretePosition(); 
		ArrayList<int[]> explored = internalMap.getCellsAround(pos[0], pos[1], 3);
		ArrayList<int[]> unexplored = internalMap.getCellsAround(pos[0], pos[1], 0);
		
		//marking step
		
		if (!internalMap.blockingPath(pos)) {
			//System.out.println("marked as visited: " + pos[0] + " " + pos[1]); 
			internalMap.setCell(pos, 4); 
		}
		else {
			//System.out.println("marked as explored: " + pos[0] + " " + pos[1]); 
			internalMap.setCell(pos, 3); 
		}
		
		//navigation step
		//if at least one of the four cells around is unexplored
		if (!unexplored.isEmpty()) {
			//System.out.println("unexplored cells exist"); 
			int[] p = internalMap.getBestCell(unexplored);
			//System.out.println(p[0] + " " + p[1] + " " + pos[0] + " " + pos[1]);
			moveTo(p);
		}
		
		else if (!explored.isEmpty()) {
			//System.out.println("explored cells exist"); 
			Random rnd = new Random(); 
			moveTo(explored.get(rnd.nextInt(explored.size()))); //todo
		}
		
		else {
			System.out.println("exploration finished");
			explorationFinished = true; 
		}
	}
	
	public void moveTo(int[] pos) {
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
    
    public void placeMarker(int type) {    	
    	Marker marker = new Marker(topLeft, type); 
    	map.addMarker(marker);
    }


}