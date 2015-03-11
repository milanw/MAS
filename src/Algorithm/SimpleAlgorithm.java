package Algorithm;


import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import GameObjects.InanimateObjects;
import Map.Map;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SimpleAlgorithm {

    private SurveillanceAgent sAgent;
    private IntruderAgent iAgent;
    private Map map;
    private ArrayList<InanimateObjects> gameObjects;
    private ArrayList<Agent> agents;

    public SimpleAlgorithm(Map map){
        this.map = map;
        sAgent = new SurveillanceAgent();
        iAgent = new IntruderAgent();
        gameObjects = map.getGameObjects();


    }

    public void algorithm(){



    }

    public void addSAgents(SurveillanceAgent agent){
            double x = Math.random()*map.getWidth();
            double y = Math.random()*map.getHeight();
            Point2D.Double topleft = new Point2D.Double(x, y);
            double nx = x + (Math.random()*map.getWidth());
            double ny = y + (Math.random()*map.getHeight());
            Point2D.Double bottomright = new Point2D.Double(nx, ny);
            if(emptySpot(topleft) && emptySpot(bottomright)){
                agent.setTopLeft(topleft);
                agent.setBottomRight(bottomright);
                boolean m = false;
                for(int j = 0;j<gameObjects.size();j++){
                    if(agent.isInside(gameObjects.get(j).getTopLeft()) == true){
                        m = true;
                    }
                }
                if(m == false){
                    agents.add(agent);
                }
            }
    }
    public void addIAgent(IntruderAgent agent){
        double x = Math.random()*map.getWidth();
        double y = Math.random()*map.getHeight();
        Point2D.Double topleft = new Point2D.Double(x, y);
        double nx = x + (Math.random()*map.getWidth());
        double ny = y + (Math.random()*map.getHeight());
        Point2D.Double bottomright = new Point2D.Double(nx, ny);
        if(emptySpot(topleft) && emptySpot(bottomright)){
            agent.setTopLeft(topleft);
            agent.setBottomRight(bottomright);
            boolean m = false;
            for(int j = 0;j<gameObjects.size();j++){
                if(agent.isInside(gameObjects.get(j).getTopLeft()) == true){
                    m = true;
                }
            }
            if(m == false){
                agents.add(agent);
            }
        }
    }
    public boolean emptySpot(Point2D n){
        boolean answer = true;
        for(int i=0;i<gameObjects.size();i++){
            if(gameObjects.get(i).isInside(n)){
                answer = false;
            }
        }
        return answer;
    }

    public ArrayList<Agent> getAgents(){
        return agents;
    }


}
