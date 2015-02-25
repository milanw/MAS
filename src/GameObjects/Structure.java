package GameObjects;

import java.awt.geom.Point2D;

public class Structure extends InanimateObjects{

    public boolean door, window;
    public int visionRange = 10;


	public Structure(Point2D x, Point2D y) {
		topLeft = x;
		bottomRight = y;
	}
	public boolean isInside(Point2D n){
		return n.getX() < topLeft.getX() && n.getX() > bottomRight.getX() && n.getY() < topLeft.getY() && n.getY() > bottomRight.getY();
	}
	public Point2D getTopLeft(){
		return topLeft;
	}

    public void setDoor(boolean b){
        boolean door = b;
    }
    public boolean hasDoor(){
        return door;
    }

    public void setWindow(boolean b){
        boolean window = b;
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

}
