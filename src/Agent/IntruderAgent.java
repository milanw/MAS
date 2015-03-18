package Agent;

import java.awt.geom.Point2D;

public class IntruderAgent extends Agent{
	
	private double sprintspeed = 3.0;
	private int maxSprintTurn = 10;
    private int viewingAngle = 45;
	
	public IntruderAgent(){
		super();
		visionRange = 7.5;
	}
	
	public IntruderAgent(Point2D topLeft, Point2D bottomRight){
		super(topLeft, bottomRight);
		visionRange = 7.5;
	}
	
	public void sprint(){
		
	}

    

    
}
