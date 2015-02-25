package GameObjects;

import java.awt.Point;


public class SentryTower extends InanimateObjects{

    public int visionRange = 18;

	public SentryTower(Point x, Point y) {
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
