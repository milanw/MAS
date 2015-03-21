package GameObjects;

import java.awt.geom.Point2D;

public class Structure extends InanimateObject{

    private boolean door, window;
    private int visionRange = 10;
    private static int defaultSize = 30; 

	public Structure(Point2D x, Point2D y) {
		super(x,y);
		
	}
	
	public boolean isInside(Point2D n){
		if(n.getX() < topLeft.getX() || n.getX() > bottomRight.getX())
		return false;	
		
		if(n.getY() < topLeft.getY() || n.getY() > bottomRight.getY())
		return false;		
	
		return true; 
	}
	
	public Point2D getTopLeft(){
		return topLeft;
	}

    public void setDoor(boolean b){
        door = b;
    }
    public boolean hasDoor(){
        return door;
    }

    public void setWindow(boolean b){
        window = b;
    }
    public boolean hasWindow(){
        return window;
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

}
