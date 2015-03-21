package Agent;

import java.awt.geom.Point2D;
import java.util.Random;

public class Agent {

    private double speed = 1.4;
	private int maxTurn;
	protected double visionRange;
	private int viewingAngle; 
    private Point2D topLeft;
    private Point2D bottomRight;

	
	public Agent(Point2D topLeft, Point2D bottomRight){
		this.topLeft = topLeft; 
		this.bottomRight = bottomRight; 
	}
	
	public Agent(){
		
	}
	
	public Point2D[] getNextMove() {
		Random rnd = new Random(); 
//		if (rnd.nextBoolean())
//			return move(rnd.nextInt(4));
//		else
//			return move(1);

        return move(rnd.nextInt(4));
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



}
