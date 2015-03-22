package GameObjects;

import java.awt.geom.Point2D;


public class SentryTower extends InanimateObject{
	private static final int DEFAULTSIZE = 12; 
    private int visionRange = 18;
    private int size = DEFAULTSIZE;

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
    	return size;
    }
    
    public static int getDefaultSize() {
    	return DEFAULTSIZE; 
    }


}
