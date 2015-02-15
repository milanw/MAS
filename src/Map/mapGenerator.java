package Map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;




import GameObjects.Structure;

public class mapGenerator {
	ArrayList<Structure> map = new ArrayList<Structure>();
	int structureAmount = 10;
	int height;
	int width;
	public mapGenerator(int height, int width){
		this.height = height;
		this.width= width;
		addStructures();
	}
	public void addStructures(){
		while(map.size() != structureAmount){
			double x = Math.random()*width;
			double y = Math.random()*height;
			Point2D.Double topleft = new Point2D.Double(x, y);
			double nx = x + (Math.random()*width)/structureAmount;
			double ny = y + (Math.random()*width)/structureAmount;
			Point2D.Double bottomright = new Point2D.Double(nx, ny);
			if(emptySpot(topleft) && emptySpot(bottomright)){
				Structure s = new Structure(topleft, bottomright);
				if(notOutside(s) == true){
					map.add(s);
				}
			}
	
		}
	}
	
	public boolean emptySpot(Point2D n){
		boolean answer = false;
		for(int i=0;i<map.size();i++){
			if(map.get(i).isInside(n) == false){
				answer = true;
			}
		}
		return answer;
	}
	public boolean notOutside(Structure s){
		boolean answer = true;
		for(int i=0;i<map.size();i++){
			if(s.isInside(map.get(i).getTopLeft()));
			answer = false;
		}
		return answer;
	}
}
