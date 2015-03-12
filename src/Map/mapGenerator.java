package Map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;







import GameObjects.InanimateObjects;
import GameObjects.OuterWall;
import GameObjects.Structure;

public class mapGenerator {
	ArrayList<InanimateObjects> map = new ArrayList<InanimateObjects>();
	int structureAmount = 10;
	int height;
	int width;
	
	public mapGenerator(int height, int width){
		this.height = height;
		this.width= width;
		addOuterWalls();
		addStructures();
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
	public Map getMap(){
		return new Map(width, height, map);
	}
	
	public ArrayList<InanimateObjects> getObjects() {
		return map;
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
	public void addOuterWalls(){	
		
		//left
		Point2D.Double x = new Point2D.Double(0, 0);
		Point2D.Double y = new Point2D.Double(2, height);
		OuterWall d = new OuterWall(x, y);
		
		//top
		Point2D.Double x1 = new Point2D.Double(0, 0);
		Point2D.Double y1 = new Point2D.Double(width, 2);
		OuterWall d1 = new OuterWall(x1, y1);
		
		//right
		Point2D.Double x2 = new Point2D.Double(width-2, 0);
		Point2D.Double y2 = new Point2D.Double(width, height);
		OuterWall d2 = new OuterWall(x2, y2);
		
		//bottom
		Point2D.Double x3 = new Point2D.Double(0, height-2);
		Point2D.Double y3 = new Point2D.Double(width, height);
		OuterWall d3 = new OuterWall(x3, y3);
		
		map.add(d);
		map.add(d1);
		map.add(d2);
		map.add(d3);
	}
}

