package Map;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.OuterWall;
import GameObjects.SentryTower;
import GameObjects.Structure;

public class MapGenerator {
	private ArrayList<InanimateObject> map = new ArrayList<InanimateObject>();
	private int structureAmount = 15;
	private int sentrytowerAmount = 4;
	private int height;
	private int width;
	
	public MapGenerator(int height, int width){
		this.height = height;
		this.width= width;
		addEndZone();
		addOuterWalls();
		addStructures();
		addSentryTowers();
		
		/*
		for(int i=0;i<map.size();i++){
			System.out.println(map.get(i).getClass() + " " + map.get(i).getTopLeft() + " " + map.get(i).getBottomRight());
		}*/
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
		return new Map(width, height, map, null);
	}
	
	public ArrayList<InanimateObject> getObjects() {
		return map;
	}
	
	public void addStructures(){
		int samount = structureAmount;
		while(0<samount){
			double x = 2+Math.random()*width-2;
			double y = 2+Math.random()*height-2;
			Point2D.Double topleft = new Point2D.Double(x, y);

			double nx = x + (width/structureAmount*1.5)+(Math.random()*width)/(structureAmount);
			double ny = y + (height/structureAmount*1.5)+(Math.random()*height)/(structureAmount);
			while(nx>width || ny>height){
				x = 2+Math.random()*width-2;
				y = 2+Math.random()*height-2;
				topleft = new Point2D.Double(x, y);

				nx = x + (width/structureAmount)+(Math.random()*width)/(structureAmount);
				ny = y + (height/structureAmount)+(Math.random()*height)/(structureAmount);
			}
			Point2D.Double bottomright = new Point2D.Double(nx, ny);
			Point2D.Double topright = new Point2D.Double(nx, y);
			Point2D.Double bottomleft = new Point2D.Double(x, ny);
			Point2D.Double middlePoint = new Point2D.Double(nx-x,ny-y);
			if(emptySpot(topleft) && emptySpot(bottomright) && emptySpot(topright)&&emptySpot(bottomleft)&&emptySpot(middlePoint)){
				Structure s = new Structure(topleft, bottomright);
				boolean m = false;
				for(int j = 0;j<map.size();j++){
					if(s.isInside(map.get(j).getTopLeft()) == true){
						m = true;
					}
				}
				if(m == false){
					map.add(s);
					samount--;
					//System.out.println("structureamount = "+ samount);
				}
			}
	
		}
	}
	
	public void addSentryTowers(){
		int samount = sentrytowerAmount;
		//System.out.println("sentryamount = "+ samount);
		while(0<samount){
		double x = 2+Math.random()*width-2;
		double y = 2+Math.random()*height-2;
		double nx = x + (SentryTower.getDefaultSize());
		double ny = y + (SentryTower.getDefaultSize());
		while(nx>width || ny>height){
			x = 2+Math.random()*width-2;
			y = 2+Math.random()*height-2;
			nx = x + (SentryTower.getDefaultSize());
			ny = y + (SentryTower.getDefaultSize());
		}

		Point2D.Double topleft = new Point2D.Double(x, y);
		Point2D.Double bottomright = new Point2D.Double(nx, ny);
		Point2D.Double topright = new Point2D.Double(nx, y);
		Point2D.Double bottomleft = new Point2D.Double(x, ny);
		Point2D.Double middlePoint = new Point2D.Double(nx-x,ny-y);
		if(emptySpot(topleft) && emptySpot(bottomright) && emptySpot(topright)&&emptySpot(bottomleft)&&emptySpot(middlePoint)){
			SentryTower s = new SentryTower(topleft, bottomright);
			boolean m = false;
			for(int j = 0;j<map.size();j++){
				if(s.isInside(map.get(j).getTopLeft()) == true){
					m = true;
				}
			}
			if(m == false){
				map.add(s);
				samount--;
				//System.out.println("sentyamount = " + samount);
			}
		}
		}

	}
	public void addEndZone(){
		boolean placed = false;
		while(placed == false){
		double x = 2+Math.random()*width-2;
		double y = 2+Math.random()*height-2;
		//System.out.println(x+ " " + y);
		double nx = x + (GoalZone.defaultSize);
		double ny = y + (GoalZone.defaultSize);
		while(nx>width || ny>height){
			x = 2+Math.random()*width-2;
			y = 2+Math.random()*height-2;
			nx = x + (GoalZone.defaultSize);
			ny = y + (GoalZone.defaultSize);
		}

		Point2D.Double topleft = new Point2D.Double(x, y);
		Point2D.Double bottomright = new Point2D.Double(nx, ny);
		Point2D.Double topright = new Point2D.Double(nx, y);
		Point2D.Double bottomleft = new Point2D.Double(x, ny);
		Point2D.Double middlePoint = new Point2D.Double(nx-x,ny-y);
		if(emptySpot(topleft) && emptySpot(bottomright) && emptySpot(topright)&&emptySpot(bottomleft)&&emptySpot(middlePoint)){
			
			GoalZone s = new GoalZone(topleft, bottomright);
			boolean m = false;
			for(int j = 0;j<map.size();j++){
				if(s.isInside(map.get(j).getTopLeft()) == true){
					m = true;
				}
			}
			if(m == false){
				map.add(s);
				placed = true;
				//System.out.println(s.getTopLeft() + " " + s.getBottomRight());
				//System.out.println("placed = " + placed);
			}
		}
		}
		//System.out.println("placed = " + placed);
		
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

