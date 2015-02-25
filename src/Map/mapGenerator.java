package Map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;





import GameObjects.InanimateObjects;
import GameObjects.Structure;

public class mapGenerator {
	ArrayList<InanimateObjects> map = new ArrayList<InanimateObjects>();
	int structureAmount = 10;
	int height;
	int width;
	
	public mapGenerator(int height, int width){
		this.height = height;
		this.width= width;
		addStructures();
	}
	public void addStructures(){
		while(map.size() < structureAmount){
			double x = Math.random()*width;
			double y = Math.random()*height;
			Point2D.Double topleft = new Point2D.Double(x, y);
			double nx = x + (Math.random()*width)/(structureAmount*2);
			double ny = y + (Math.random()*height)/(structureAmount*2);
			Point2D.Double bottomright = new Point2D.Double(nx, ny);
			if(emptySpot(topleft) && emptySpot(bottomright)){
				Structure s = new Structure(topleft, bottomright);
				boolean m = false;
				for(int j = 0;j<map.size();j++){
					if(s.isInside(map.get(j).getTopLeft()) == true){
						m = true;
					}
				}
				if(m == false){
					map.add(s);
				}
			}
	
		}
	}
	
	public boolean emptySpot(Point2D n){
		boolean answer = true;
		for(int i=0;i<map.size();i++){
			if(map.get(i).isInside(n)){
				answer = false;
			}
		}
		return answer;
	}
	public ArrayList<InanimateObjects> getMap(){
		return map;
	}
}
