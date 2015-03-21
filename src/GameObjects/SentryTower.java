package GameObjects;

import java.awt.geom.Point2D;


public class SentryTower extends InanimateObject{

    private int visionRange = 18;
    private int defaultSize = 12;

	public SentryTower(Point2D x, Point2D y) {
		super(x,y); 
	}

    public void setVisionRange(int i){
        visionRange = i;
    }

    public int getVisionRange(){
        return visionRange;
    }
    
    public int getSize() {
    	return defaultSize;
    }


}
