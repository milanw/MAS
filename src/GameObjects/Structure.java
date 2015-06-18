package GameObjects;

import java.awt.geom.Point2D;

public class Structure extends InanimateObject{

    private int visionRange = 10;
    private static int defaultSize = 30;
    private Segment door;
    private Segment window;

	public Structure(Point2D x, Point2D y) {
		super(x,y);
		//createDoorWindow();
		
	}
	
	public boolean isInside(Point2D n){
		if(n.getX() < topLeft.getX() || n.getX() > bottomRight.getX())
			return false;	
		
		if(n.getY() < topLeft.getY() || n.getY() > bottomRight.getY())
			return false;		
	
		return true; 
	}
	public void createDoorWindow(){
		int side = (int) (Math.random()*4);
		if(side == 0){
			double side0 = topLeft.getX()-bottomRight.getX();
			double doorpointx = topLeft.getX() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, topLeft.getY());
			Point2D p2 = new Point2D.Double(doorpointx+10, topLeft.getY());
			door = new Segment(p1 , p2);
		}else if(side ==1){
			double side0 = topLeft.getY()-bottomRight.getY();
			double doorpointx = topLeft.getY() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, topLeft.getX());
			Point2D p2 = new Point2D.Double(doorpointx+10, topLeft.getX());
			door = new Segment(p1 , p2);
		}else if(side == 2){
			double side0 = topLeft.getX()-bottomRight.getX();
			double doorpointx = topLeft.getX() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, bottomRight.getY());
			Point2D p2 = new Point2D.Double(doorpointx+10, bottomRight.getY());
			door = new Segment(p1 , p2);
		}else{
			double side0 = topLeft.getY()-bottomRight.getY();
			double doorpointx = topLeft.getY() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, bottomRight.getX());
			Point2D p2 = new Point2D.Double(doorpointx+10, bottomRight.getX());
			door = new Segment(p1 , p2);
		}
		int side2 = (int) (Math.random()*4);
		while(side2 == side){
			 side2 = (int) (Math.random()*4);
		}
		if(side == 0){
			double side0 = topLeft.getX()-bottomRight.getX();
			double doorpointx = topLeft.getX() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, topLeft.getY());
			Point2D p2 = new Point2D.Double(doorpointx+10, topLeft.getY());
			window = new Segment(p1 , p2);
		}else if(side ==1){
			double side0 = topLeft.getY()-bottomRight.getY();
			double doorpointx = topLeft.getY() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, topLeft.getX());
			Point2D p2 = new Point2D.Double(doorpointx+10, topLeft.getX());
			window = new Segment(p1 , p2);
		}else if(side == 2){
			double side0 = topLeft.getX()-bottomRight.getX();
			double doorpointx = topLeft.getX() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, bottomRight.getY());
			Point2D p2 = new Point2D.Double(doorpointx+10, bottomRight.getY());
			window = new Segment(p1 , p2);
		}else{
			double side0 = topLeft.getY()-bottomRight.getY();
			double doorpointx = topLeft.getY() + (Math.random()*(side0-10));
			Point2D p1 = new Point2D.Double(doorpointx, bottomRight.getX());
			Point2D p2 = new Point2D.Double(doorpointx+10, bottomRight.getX());
			window = new Segment(p1 , p2);
		}
	}
	public Point2D getTopLeft(){
		return topLeft;
	}


    public boolean hasDoor(){
    	if(door == null){
    		return false;
    	}else{
    		return true;
    	}
    }

    public boolean hasWindow(){
    	if(window == null){
    		return false;
    	}else{
    		return true;
    	}
    }
    public void setVisionRange(int i){
        visionRange = i;
    }
    public int getVisionRange(){
        return visionRange;
    }
    
    public int getSize() {
    	return defaultSize; 
    }
    
    public static int getDefaultSize() {
    	return defaultSize;
    }

}
