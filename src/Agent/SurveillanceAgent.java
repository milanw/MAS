package Agent;

import java.awt.geom.Point2D;

public class SurveillanceAgent extends Agent{
    public boolean onSentryTower;
    public int viewingAngle = 45;

	public SurveillanceAgent(){
		super();
		visionRange = 6.0;
	}
	
	public SurveillanceAgent(Point2D topLeft, Point2D bottomRight){
		super(topLeft, bottomRight);
		visionRange = 6.0;
	}
	
    public boolean IsOnSentryTower(){
        return onSentryTower;
    }

    public void setOnSentryTower(boolean b) {
        if (b) {
            onSentryTower = b;
            visionRange = 15.0;
            viewingAngle = 30;
        }
        else {
            onSentryTower = b;
            visionRange = 6.0;
            viewingAngle = 45;
        }
    }
}