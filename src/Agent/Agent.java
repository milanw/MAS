package Agent;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import GameObjects.Marker;
import Map.Map;

public class Agent {
	private Map map; 
    private double speed = 1.4;
    private static int defaultSize = 5; 
	private int maxTurn;
	private boolean onSentryTower; 
	protected double visionRange;
	private int viewingAngle; 
    private Point2D topLeft;
    private Point2D bottomRight;
	
	public Agent(Point2D topLeft, Point2D bottomRight, Map map){
		this.topLeft = topLeft; 
		this.bottomRight = bottomRight; 
		this.map = map; 
	}
	
	public Agent(){
		
	}	
	
	public Point2D[] pheromoneMove() {
		Point2D left = new Point2D.Double(topLeft.getX()-getWidth(), topLeft.getY());
		Point2D right = new Point2D.Double(topLeft.getX()+getWidth(), topLeft.getY());
		Point2D top = new Point2D.Double(topLeft.getX(), topLeft.getY()-getHeight());
		Point2D bottom = new Point2D.Double(topLeft.getX(), topLeft.getY()+getHeight());
		Point2D[] directions = {left, top, bottom, right}; 
		
		Point2D result = null; 
		int minMarkers = Integer.MAX_VALUE;
		
		for (Point2D d : directions) {
			Rectangle r = new Rectangle((int)d.getX(), (int)d.getY(), getWidth(), getHeight());
			Rectangle r2 = new Rectangle((int)d.getX(), (int)d.getY(), getWidth(), getHeight());
			if (map.checkCollisions(r2)) { 
				int amountMarkers = map.markersIn(r);
				//System.out.println(amountMarkers); 
				if (amountMarkers < minMarkers) {
					minMarkers = amountMarkers; 
					result = d; 
				}
			}
		}
		
		return new Point2D[] {result, new Point2D.Double(result.getX()+defaultSize, result.getY()+defaultSize)}; 
	}
	
	public Point2D[] getNextMove() {
		placeMarker(3); 
		return pheromoneMove(); 
		
		/*
		Random rnd = new Random(); 
		if (rnd.nextBoolean()) {
			if (rnd.nextBoolean()){placeMarker(3); }
			return move(rnd.nextInt(4));
		}
		else
			return move(1);
		*/
        //return move(rnd.nextInt(4));
    }
	
	public Point2D[] move(int direction){
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
