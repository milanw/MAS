package Agent;

public class IntruderAgent extends Agent{
	
	private double sprintspeed = 3.0;
	private int maxSprintTurn = 10;
    public double visionRange = 7.5;
    public int viewingAngle = 45;
	
	public IntruderAgent(){
		
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
