package Agent;

import java.awt.geom.Point2D;

public class IntruderAgent extends Agent{
	
	private double sprintspeed = 3.0;
	private int maxSprintTurn = 10;
    public double visionRange = 7.5;
    public int viewingAngle = 45;
	
	public IntruderAgent(){
		super();
	}
	
	public IntruderAgent(Point2D topLeft, Point2D bottomRight){
		super(topLeft, bottomRight);
	}
	
	public void sprint(){
		
	}

    public double getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(double visionRange) {
        this.visionRange = visionRange;
    }
}
