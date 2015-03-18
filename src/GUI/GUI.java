package GUI; 

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

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
    private static final Color TRANSPARENT_COLOR =new Color(5, 200 , 10, 10 );
	
	private Map map;
	private List<Agent> agents;
	private int selectedObject; 
	private BindMouseMove movingAdapt = new BindMouseMove();
	private int currentMouseX;
	private int currentMouseY;
	private boolean showRectangle;
	private boolean showVisionCircle = true; 
	private boolean showImages = true; 								//rectangles or images?
	private Deque<Map> undoStack = new ArrayDeque<Map>(); 
	private Deque<Map> redoStack = new ArrayDeque<Map>(); 
	BufferedImage sentryImg = null;
	BufferedImage structureImg = null;
	BufferedImage outerWallImg = null;
    BufferedImage goalZoneImg = null;

    BufferedImage iAgent = null;
    BufferedImage sAgent = null;

    BufferedImage image = null;

    BufferedImage current = null;
    
	public GUI(Map map, ArrayList<Agent> agents) {		
		this.map = map;
		this.agents = agents; 
		this.setDoubleBuffered(true);
		this.addMouseListener(movingAdapt);
		this.addMouseMotionListener(movingAdapt);
		
		try {
		    sentryImg = ImageIO.read(new File("src/Resources/fortress.png"));
		    outerWallImg = ImageIO.read(new File("src/Resources/brick.jpg"));
		    structureImg = ImageIO.read(new File("src/Resources/bush.png"));
            goalZoneImg = ImageIO.read(new File("src/Resources/goalZone.jpg"));
            iAgent = ImageIO.read(new File("src/Resources/iAgent.jpg"));
            sAgent = ImageIO.read(new File("src/Resources/sAgent.jpg"));
            image = ImageIO.read(new File("src/Resources/grassBackground.jpg"));
		} catch (IOException e) {}

		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//g2d.setColor(COLOR_GRASS);
        g2d.fillRect (0, 0, map.getWidth(), map.getHeight());

        g.drawImage(image, 0,0, map.getWidth(),map.getHeight(),this);

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
    		//g.setColor(COLOR_INTRUDER);
            current = iAgent;
		else if (a instanceof SurveillanceAgent) 
			//g.setColor(COLOR_SURVEILLANCE);
            current = sAgent;
		
		double width = a.getBottomRight().getX() - a.getTopLeft().getX(); 
    	double height = a.getBottomRight().getY() - a.getTopLeft().getY();         	
        //g.fillRect((int)a.getTopLeft().getX(), (int)a.getTopLeft().getY(), (int)width, (int)height);
        g.drawImage(current, (int)a.getTopLeft().getX(), (int)a.getTopLeft().getY(), (int)width, (int)height, COLOR_SURVEILLANCE, null);
        if (showVisionCircle) 
        	paintVisionRange(a, g);         
	}
	
	public void paintVisionRange(Agent a, Graphics2D g) {
		double range = a.getVisionRange();
		double middleX = (a.getTopLeft().getX() + a.getBottomRight().getX()) / 2;
		double middleY = (a.getTopLeft().getY() + a.getBottomRight().getY()) / 2;
		g.setColor(Color.BLACK);
		g.drawOval((int)(middleX-range), (int)(middleY-range), (int)(2*range), (int)(2*range));		
	}

	
	
	/* 
	 * paint an inanimate object
	 */
	public void paintObject(InanimateObjects o, Graphics2D g) {
		g.setColor(getColor(o));
		int width = (int)(o.getBottomRight().getX() - o.getTopLeft().getX()); 
    	int height = (int)(o.getBottomRight().getY() - o.getTopLeft().getY());         	
    	g.fillRect((int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), width, height);
    	if (showImages) 
    		g.drawImage(getImage(o), (int)o.getTopLeft().getX(), (int)o.getTopLeft().getY(), width, height, getColor(o), null);
    	else {} //once there are images for everything this should contain g.fillRect..
    	
        
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
    		return TRANSPARENT_COLOR;
		
		else 
			return COLOR_GRASS;
	}
	
	/* 
	 * return image corresponding to inanimate object
	 */
	public BufferedImage getImage(InanimateObjects o) {
		if (o instanceof OuterWall) 
    		return outerWallImg;
    	
		else if (o instanceof SentryTower) 
    		return sentryImg; 
    	
		else if (o instanceof Structure) 
    		return structureImg;
		
		else 
			return null;
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
	    	//g.setColor(COLOR_GOALZONE);
	    	//g.fillRect((int)goalZone.getTopLeft().getX(), (int)goalZone.getTopLeft().getY(), (int)width, (int)height);
            if (showImages)
                g.drawImage(goalZoneImg , (int)goalZone.getTopLeft().getX(), (int)goalZone.getTopLeft().getY(), (int)width, (int)height, COLOR_GOALZONE , null);
            else {} //once there are images for everything this should contain g.fillRect..
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
				if (map.checkCollisions(map.getObjectRectangle(o)))
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
    
    public void toggleVisionCircle() {
    	showVisionCircle = !showVisionCircle; 
    }
    
    public void toggleShowImages() {
    	showImages = !showImages; 
    }
}

