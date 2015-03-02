package GUI; 

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.sun.media.sound.Toolkit;

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
	
	private Map map;
	private int selectedObject; 
	BindMouseMove movingAdapt = new BindMouseMove();
	private int currentMouseX;
	private int currentMouseY;
	private boolean showRectangle;
	
    
	public GUI(Map map) {
		
		this.map = map;
		this.setDoubleBuffered(true);
		this.addMouseListener(movingAdapt);
		this.addMouseMotionListener(movingAdapt);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(COLOR_GRASS);
        g2d.fillRect (0, 0, map.getWidth(), map.getHeight());    
        
        for (InanimateObjects s : map.getGameObjects()) {
        	if (s instanceof OuterWall) 
        		g2d.setColor(COLOR_OUTERWALL); 
        	
        	if (s instanceof SentryTower) 
        		g2d.setColor(COLOR_SENTRY); 
        	
        	if (s instanceof Structure) 
        		g2d.setColor(Color.CYAN);
        		
        	
        	double width = s.getBottomRight().getY() - s.getTopLeft().getY(); 
        	double height = s.getBottomRight().getX() - s.getTopLeft().getX();         	
            g2d.fillRect((int)s.getTopLeft().getX(), (int)s.getTopLeft().getY(), (int)width, (int)height);
        }
        
        if (map.getGoalZone() != null) {
	        GoalZone goalZone = map.getGoalZone(); 
	        g2d.setColor(COLOR_GOALZONE); 
	        double width = goalZone.getBottomRight().getY() - goalZone.getTopLeft().getY(); 
	    	double height = goalZone.getBottomRight().getX() - goalZone.getTopLeft().getX();         	
	        g2d.fillRect((int)goalZone.getTopLeft().getX(), (int)goalZone.getTopLeft().getY(), (int)width, (int)height);
        }
        
        
        g2d.setColor(Color.WHITE); 
        if (showRectangle)
        	g2d.drawRect(currentMouseX, currentMouseY, 30, 30);

       
    }
    
    class BindMouseMove extends MouseAdapter {
		private int x;
		private int y;

		@Override
		public void mousePressed(MouseEvent event) {
			x = event.getX();
			y = event.getY();
			if (selectedObject == InanimateObjects.SENTRY_TYPE)
				map.addObject(new SentryTower(new Point(x,y), new Point(x+30, y+30)));
			else if (selectedObject == InanimateObjects.GOAL_TYPE)
				map.setGoalZone(new GoalZone(new Point(x,y), new Point(x+30, y+30)));
			else if (selectedObject == InanimateObjects.STRUCTURE_TYPE)
				map.addObject(new Structure(new Point(x,y), new Point(x+30, y+30)));
			
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
    
    public void selectObject(int type) {
    	selectedObject = type; 
    }
}

