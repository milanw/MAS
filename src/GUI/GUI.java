package GUI; 

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.sun.media.sound.Toolkit;

import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import GameObjects.GoalZone;
import GameObjects.InanimateObjects;
import GameObjects.OuterWall;
import GameObjects.SentryTower;
import GameObjects.Structure;
import Map.Map;



class GUI extends JComponent{
	
	private static final Color COLOR_GRASS = new Color(5, 128, 60);			// dark green
	private static final Color COLOR_GOALZONE = new Color(255, 0, 0, 128);  //transparent red
	private static final Color COLOR_SENTRY = Color.MAGENTA; 
	private static final Color COLOR_OUTERWALL = Color.BLACK; 
	
	private static final Color COLOR_INTRUDER = Color.RED;
	private static final Color COLOR_SURVEILLANCE = Color.GREEN;
	
	private Map map;
	private List<Agent> agents;
	private int selectedObject; 
	private BindMouseMove movingAdapt = new BindMouseMove();
	private int currentMouseX;
	private int currentMouseY;
	private boolean showRectangle;
	private Deque<Map> undoStack = new ArrayDeque<Map>(); 
	private Deque<Map> redoStack = new ArrayDeque<Map>(); 
	
    
	public GUI(Map map, ArrayList<Agent> agents) {		
		this.map = map;
		this.agents = agents; 
		this.setDoubleBuffered(true);
		this.addMouseListener(movingAdapt);
		this.addMouseMotionListener(movingAdapt);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(COLOR_GRASS);
        g2d.fillRect (0, 0, map.getWidth(), map.getHeight());  
        
        for (InanimateObjects o : map.getGameObjects()) {
        	paintObject(o, g2d);   
        }
        
        for (Agent a : agents) {
        	paintAgent(a, g2d); 
        }
        
        paintGoalZone(g2d);
        paintCursorRectangle(g2d);
    }
	
	public void paintAgent(Agent a, Graphics2D g) {
		if (a instanceof IntruderAgent) 
    		g.setColor(COLOR_INTRUDER);
		else if (a instanceof SurveillanceAgent) 
			g.setColor(COLOR_SURVEILLANCE); 
		
		double width = a.getBottomRight().getY() - a.getTopLeft().getY(); 
    	double height = a.getBottomRight().getX() - a.getTopLeft().getX();         	
        g.fillRect((int)a.getTopLeft().getX(), (int)a.getTopLeft().getY(), (int)width, (int)height);
	}
	
	
	/* 
	 * paint an inanimate object
	 */
	public void paintObject(InanimateObjects o, Graphics2D g) {
		g.setColor(getColor(o));
		double width = o.getBottomRight().getY() - o.getTopLeft().getY(); 
    	double height = o.getBottomRight().getX() - o.getTopLeft().getX();         	
        g.fillRect((int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), (int)width, (int)height);
	}
	
	/* 
	 * return color corresponding to inanimate object
	 */
	public Color getColor(InanimateObjects o) {
		if (o instanceof OuterWall) 
    		return COLOR_OUTERWALL;
    	
		else if (o instanceof SentryTower) 
    		return COLOR_SENTRY; 
    	
		else if (o instanceof Structure) 
    		return Color.CYAN;
		
		else 
			return COLOR_GRASS;
	}
	
	public void paintCursorRectangle(Graphics2D g) {
		g.setColor(Color.WHITE); 
        if (showRectangle)
        	g.drawRect(currentMouseX, currentMouseY, 30, 30);
	}
	
	public void paintGoalZone(Graphics2D g) {
		if (map.getGoalZone() != null) {
	        GoalZone goalZone = map.getGoalZone(); 	        
	        double width = goalZone.getBottomRight().getY() - goalZone.getTopLeft().getY(); 
	    	double height = goalZone.getBottomRight().getX() - goalZone.getTopLeft().getX();         	
	    	g.setColor(COLOR_GOALZONE); 
	    	g.fillRect((int)goalZone.getTopLeft().getX(), (int)goalZone.getTopLeft().getY(), (int)width, (int)height);
        }
	}
    
    class BindMouseMove extends MouseAdapter {
		private int x;
		private int y;

		@Override
		public void mousePressed(MouseEvent event) {
			x = event.getX();
			y = event.getY();
			InanimateObjects o = null;
			if (selectedObject == InanimateObjects.SENTRY_TYPE)
				o = new SentryTower(new Point(x,y), new Point(x+30, y+30));
			else if (selectedObject == InanimateObjects.GOAL_TYPE)
				map.setGoalZone(new GoalZone(new Point(x,y), new Point(x+30, y+30)));
			else if (selectedObject == InanimateObjects.STRUCTURE_TYPE)
				o = new Structure(new Point(x,y), new Point(x+30, y+30));
			
			//if possible, insert game object
			if (o != null) {
				if (map.checkCollisions(o))
					undoStack.push(new Map(map));
				map.addObject(o);
			}
			repaint(); 
		}
		
		@Override
		public void mouseMoved(MouseEvent event) {
			currentMouseX = event.getX();
			currentMouseY = event.getY();
			repaint();
			
		}
		
		@Override
		public void mouseEntered(MouseEvent event) {
			//hide cursor and show white rectangle instead
			showRectangle = true;
			setCursor(getToolkit().createCustomCursor(
		            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
		            "null"));
			repaint();
			
		}
		
		@Override
		public void mouseExited(MouseEvent event) {
			showRectangle = false;
			repaint();
			
		}
	}
    
    public void setMap(Map m) {
    	map = new Map(m); 
    	undoStack = new ArrayDeque<Map>();
    	repaint();
    }
    
    public void undo() {    	
    	if (!undoStack.isEmpty()) {
    		GoalZone g = map.getGoalZone(); 
    		redoStack.push(map);
    		map = new Map(undoStack.pop());
    		map.setGoalZone(g);
    	}
    	repaint();    	
    }
    
    public void redo() {    	
    	if (!redoStack.isEmpty()) {
    		GoalZone g = map.getGoalZone(); 
    		undoStack.push(map);
    		map = new Map(redoStack.pop());
    		map.setGoalZone(g);
    	}
    	repaint();    	
    }
    
    public void selectObject(int type) {
    	selectedObject = type; 
    }
}

