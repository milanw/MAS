package Agent;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import Agent.Astar.AStarSearch;
import Agent.Astar.Node;
import Agent.InfluenceMap.InfluenceNode;
import GameObjects.GoalZone;
import Map.Map;

public class IntruderAgent extends Agent{
	
	private double sprintspeed = 3.0;
	private int maxSprintTurn = 10;
    private int viewingAngle = 45;
	
	public IntruderAgent(){
		super();
		visionRange = 7.5;
	}
	
	public IntruderAgent(Point2D topLeft, Point2D bottomRight, Map map){
		super(topLeft, bottomRight, map);
		visionRange = 7.5;
		queue = new ArrayList<Node>();
	}
	
	public void sprint(){
		
	}
	
	public void getMove() {
		//influenceMap.propagate(new InfluenceNode(super.getDiscretePosition()[0], super.getDiscretePosition()[1]), 0.8, 1);
		if (queue.isEmpty()) {
			GoalZone g = map.getGoalZone();
			Point2D to = new Point2D.Double((g.getTopLeft().getX()+g.getBottomRight().getX())/2, (g.getTopLeft().getY()+g.getBottomRight().getY())/2);
			if (!map.inGoalZone(this))
				queue = findPath(to);			
		}
		
		if (!queue.isEmpty()) {
			Node move = queue.remove(0);
			moveTo(new int[] {move.x, move.y});
		}
	}	
	
	public boolean isInVicinity(Agent a) {
		if ((Math.abs(this.getCenter().getX() -a.getCenter().getX()) + Math.abs(this.getCenter().getY() -a.getCenter().getY())) < 5)
			return true;
		else
			return false;
	}
	
	
	

	public ArrayList<Node> findPath(Point2D to) {
		int[] start = getDiscretePosition();
		int[] goal = map.getDiscretePosition(to, 5);
		AStarSearch a = new AStarSearch(map.getDiscretizedMap(5));
		
		return a.findPath(new Node(start[0], start[1]), new Node(goal[0], goal[1]));
		
	}
	
	public int[] getDiscretePosition() {
		int ax = (int)(topLeft.getX()+bottomRight.getX()/2.0) / 5;
		int ay = (int)(topLeft.getY()+bottomRight.getY()/2.0) / 5;
		return new int[] {ax, ay};
		
	}
	
	
	
	public static void main(String[] args) {
		IntruderAgent i = new IntruderAgent(new Point2D.Double(5,5), new Point2D.Double(10, 10), new Map(200, 200, null, null));
		System.out.println(i.getCenter());
		System.out.println(i.getDiscretePosition()[0] + " " + i.getDiscretePosition()[1]);
		
		
	}

    
}
