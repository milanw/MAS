package Agent;

import java.awt.geom.Point2D;

public class Agent {

    private double speed = 1.4;
	private int maxTurn;
	private int visionRange;
    Point2D topLeft;
    Point2D bottomRight;

	
	public Agent(Point2D topLeft, Point2D bottomRight){
		this.topLeft = topLeft; 
		this.bottomRight = bottomRight; 
	}
	
	public Agent(){
		
	}
	public void move(int direction){
        // moving up
        if(direction == 0){
            double currenty = topLeft.getY();
            double currentx =  topLeft.getX();
            currenty--;

            topLeft.setLocation(currentx, currenty);
        }

        // moving left
        if(direction == 1){

            double currenty = topLeft.getY();
            double currentx =  topLeft.getX();
            currentx--;

            topLeft.setLocation(currentx, currenty);

        }

        // moving right
        if(direction == 2){

            double currenty = topLeft.getY();
            double currentx =  topLeft.getX();
            currentx++;

            topLeft.setLocation(currentx, currenty);

        }

        // moving down
        if(direction == 3){

            double currenty = topLeft.getY();
            double currentx =  topLeft.getX();
            currenty++;

            topLeft.setLocation(currentx, currenty);

        }
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



}
