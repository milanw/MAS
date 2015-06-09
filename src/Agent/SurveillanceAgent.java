package Agent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import Agent.InfluenceMap.InfluenceNode;
import Map.Map;
import Simulation.Simulator;

public class SurveillanceAgent extends Agent{
    public boolean onSentryTower;
    public int viewingAngle = 45;
    private boolean explorationFinished = false;
	

	public SurveillanceAgent(){
		super();
		visionRange = 6.0;
	}
	
	public SurveillanceAgent(Point2D topLeft, Point2D bottomRight, Map map){
		super(topLeft, bottomRight, map);
		visionRange = 6.0;
	}
	
    public boolean IsOnSentryTower(){
        return onSentryTower;
    }

    public void setOnSentryTower(boolean b) {
        if (b) {
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
    
    public void getMove() {
		if (intruderEntered) {
			//if (Simulator.intruder.isInVicinity(this))
			//if (influenceMap.getInfluence(getDiscretePosition()) < 0.8)	{
			if (!Simulator.intruder.isInVicinity(this)) {
				greedy();
			}
			else
				return;
			
		}
		else		
			brickAndMortar();
		/*if(Simulator.intruder != null && !Simulator.intruder.isInVicinity(this))
			influenceMap.propagate(new InfluenceNode(getDiscretePosition()[0], getDiscretePosition()[1]), 0.6, -1);*/
		
		influenceMap.propagate(new InfluenceNode(getDiscretePosition()[0], getDiscretePosition()[1]), 0.6, -1);
	}
	
	public void greedy() {
		
		internalMap.setMap(map.getDiscretizedMap(5));
		ArrayList<InfluenceNode> adjacent = influenceMap.getNeighbours(new InfluenceNode(getDiscretePosition()[0], getDiscretePosition()[1])); 
		double[][] influence = influenceMap.getMap();
		int[][] internal = internalMap.getMap();
		double bestValue = -2.0; 
		int[] bestCell = new int[2]; 
		int[] secondBestCell = new int[2]; 
		
		for (InfluenceNode node : adjacent) {
			
			if (internal[node.y][node.x] == 1)
				continue;
			
			if (influence[node.y][node.x] > bestValue) {
				bestValue = influence[node.y][node.x];
				secondBestCell = bestCell;
				bestCell = new int[] {node.x, node.y};
			}
				
		}
		if (lastPosition[0] == bestCell[0] && lastPosition[1] == bestCell[1])
			moveTo(secondBestCell);
		else
			moveTo(bestCell);
	}
	public void brickAndMortar() {
		int[] pos = getDiscretePosition(); 
		detectWalls(pos);
		
		//marking step
		
		if (!internalMap.blockingPath(pos)) {
			//System.out.println("marked as visited: " + pos[0] + " " + pos[1]); 
			internalMap.setCell(pos, 4); 
		}
		else {
			//System.out.println("marked as explored: " + pos[0] + " " + pos[1]); 
			internalMap.setCell(pos, 3); 
		}
		
		ArrayList<int[]> explored = internalMap.removeOccupied(internalMap.getCellsAround(pos[0], pos[1], 3));
		ArrayList<int[]> unexplored = internalMap.removeOccupied(internalMap.getCellsAround(pos[0], pos[1], 0));
		
		//navigation step
		//if at least one of the four cells around is unexplored
		if (!unexplored.isEmpty()) {
			//System.out.println("unexplored cells exist"); 
			int[] p = internalMap.getBestCell(unexplored);
			//System.out.println(p[0] + " " + p[1] + " " + pos[0] + " " + pos[1]);
			moveTo(p);
		}
		
		else if (!explored.isEmpty()) {
			int[] move = explored.remove(id%explored.size());
			Random rnd = new Random();
			//int[] move = explored.remove(rnd.nextInt(explored.size()));
			if (move[0] == lastPosition[0] && move[1] == lastPosition[1] && explored.size()>0)
				move = explored.get(id%explored.size());
			moveTo(move);
		}
		
		else {
			//System.out.println("exploration finished " + unexplored.size());
			explorationFinished = true; 
		}
		lastPosition = pos; 
	}
}