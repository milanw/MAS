package GameObjects;

import java.awt.Point;
import java.awt.geom.Point2D;


public class SentryTower extends InanimateObjects{

    public int visionRange = 18;

	public SentryTower(Point2D x, Point2D y) {
		topLeft = x;
		bottomRight = y;
	}

    public void setVisionRange(int i){
        visionRange = i;
    }

    public int getVisionRange(){
        return visionRange;
    }


}
