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
		boolean xanswer = false;
		boolean yanswer = false;
//		System.out.println("point n: x = " + n.getX() + " y = "+ n.getY());
//		System.out.println("topleft: x = "+ topLeft.getX()+ " y = " + topLeft.getY());
//		System.out.println("bottomright: x = "+bottomRight.getX() + " y = "+bottomRight.getY());
		if(n.getX() > topLeft.getX() && n.getX() < bottomRight.getX()){
			xanswer = true;
		}
		if(n.getY() > topLeft.getY() && n.getY() < bottomRight.getY()){
			yanswer = true;
		}
		boolean answer = false;
		if(xanswer&&yanswer){
			answer = true;
		}
		return answer;

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
