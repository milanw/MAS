package Agent;

import java.awt.geom.Point2D;

import Map.Map;

public class IntruderAgent extends Agent{
	
	private double sprintspeed = 3.0;
	private int maxSprintTurn = 10;
    private int viewingAngle = 45;
	
	public IntruderAgent(){
		super();
		visionRange = 7.5;
	}
	
	public IntruderAgent(Point2D topLeft, Point2D bottomRight, Map map){
		super(topLeft, bottomRight, map);
		visionRange = 7.5;
	}
	
	public void sprint(){
		
	}
	public Point2D[] getNextMove() {
		
		
		return null;
	}

    
}
