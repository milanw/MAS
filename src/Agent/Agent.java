package Agent;

import java.awt.geom.Point2D;

public class Agent {

    private double speed = 1.4;
	private int maxTurn;
	private int visionRange;
    Point2D topLeft;
    Point2D bottomRight;

	
	public Agent(){
		
	}
	public void move(int direction){
        // moving up
        if(direction == 0){
            double topy = topLeft.getY();
            double topx =  topLeft.getX();
            double bottomy = bottomRight.getY();
            double bottomx = bottomRight.getX();
            topy--;
            bottomy--;

            topLeft.setLocation(topx, topy);
            bottomRight.setLocation(bottomx, bottomy);
        }

        // moving left
        if(direction == 1){

            double topy = topLeft.getY();
            double topx =  topLeft.getX();
            double bottomy = bottomRight.getY();
            double bottomx = bottomRight.getX();
            topx--;
            bottomx--;

            topLeft.setLocation(topx, topy);
            bottomRight.setLocation(bottomx, bottomy);

        }

        // moving right
        if(direction == 2){

            double topy = topLeft.getY();
            double topx =  topLeft.getX();
            double bottomy = bottomRight.getY();
            double bottomx = bottomRight.getX();
            topx++;
            bottomx++;

            topLeft.setLocation(topx, topy);
            bottomRight.setLocation(bottomx, bottomy);

        }

        // moving down
        if(direction == 3){

            double topy = topLeft.getY();
            double topx =  topLeft.getX();
            double bottomy = bottomRight.getY();
            double bottomx = bottomRight.getX();
            topy++;
            bottomy++;

            topLeft.setLocation(topx, topy);
            bottomRight.setLocation(bottomx, bottomy);

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
