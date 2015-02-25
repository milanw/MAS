package Agent;

public class SurveillanceAgent extends Agent{

    public double visionRange = 6.0;
    public boolean onSentryTower;
    public int viewingAngle = 45;

	public SurveillanceAgent(){
		
	}

    public double getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(double visionRange) {
        this.visionRange = visionRange;
    }

    public boolean getOnSentryTower(){
        return onSentryTower;
    }

    public void setOnSentryTower(boolean b) {
        if (b = true) {
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

    public int getViewingAngle() {
        return viewingAngle;
    }

    public void setViewingAngle(int viewingAngle) {
        this.viewingAngle = viewingAngle;
    }
}