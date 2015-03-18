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

    public SimpleAlgorithm(Map map){
        this.map = map;
        gameObjects = map.getGameObjects();
        sAgent = new SurveillanceAgent();


    }

    public ArrayList<Agent> algorithm(ArrayList<Agent> agents){
        ArrayList<Agent> updatedAgents = new ArrayList<Agent>();

        for(int i = 0; i < agents.size(); i++) {

            int c = 0;
            while( c != 1){

                if (agents.get(i).getClass().equals(sAgent.getClass()) ) {
                    sAgent = (SurveillanceAgent) agents.get(i);


                    boolean add = false;

                    int random = (int) Math.random() * 4;
                    sAgent.move(random);

                    Point2D currentTop = sAgent.getTopLeft();
                    Point2D currentBottom = sAgent.getBottomRight();

                    if (emptySpot(currentBottom) && emptySpot(currentTop)) {
                        boolean m = false;
                        for (int j = 0; j < gameObjects.size(); j++) {
                            if (sAgent.isInside(gameObjects.get(j).getTopLeft()) == true) {
                                m = true;
                            }
                        }
                        if (m == false) {
                            updatedAgents.add(sAgent);
                            add = true;
                            c++;

                        }
                    }
                }
                else{
                        iAgent = (IntruderAgent) agents.get(i);


                        boolean add = false;

                        int random = (int) Math.random() * 4;
                        iAgent.move(random);

                        Point2D currentTop = iAgent.getTopLeft();
                        Point2D currentBottom = iAgent.getBottomRight();

                        if (emptySpot(currentBottom) && emptySpot(currentTop)) {
                            boolean m = false;
                            for (int j = 0; j < gameObjects.size(); j++) {
                                if (iAgent.isInside(gameObjects.get(j).getTopLeft()) == true) {
                                    m = true;
                                }
                            }
                            if (m == false) {
                                updatedAgents.add(iAgent);
                                add = true;
                                c++;

                            }
                        }
                }
            }
        }
        return updatedAgents;

    }

    public void addSAgents(SurveillanceAgent agent, ArrayList<Agent> agents){
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
    public void addIAgent(IntruderAgent agent, ArrayList<Agent> agents){
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

    //public ArrayList<Agent> getAgents(){
//        return agents;
//    }


}
