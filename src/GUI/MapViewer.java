package GUI; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import Agent.Agent;
import Agent.SurveillanceAgent;
import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.OuterWall;
import GameObjects.SentryTower;
import GameObjects.Structure;
import Map.Map;



class MapViewer extends JComponent{
	
	private static final long serialVersionUID = 1L;
	private static final Color COLOR_GRASS = new Color(5, 128, 60);			// dark green
	private static final Color COLOR_GOALZONE = new Color(255, 0, 0, 128);  //transparent red
	private static final Color COLOR_SENTRY = Color.MAGENTA; 
	private static final Color COLOR_OUTERWALL = Color.BLACK;
	private static final Color COLOR_STRUCTURE = Color.CYAN;	
	private static final Color COLOR_INTRUDER = Color.RED;
	private static final Color COLOR_SURVEILLANCE = Color.GREEN;

	private BufferedImage sentryImg, structureImg, outerWallImg, 
	intruderImg, surveillanceImg, grassImg = null;

	private Map map;
	private List<Agent> agents;
	private InanimateObject selectedObject = new InanimateObject(null, null); 
	private BindMouseMove movingAdapt = new BindMouseMove();
	private int currentMouseX;
	private int currentMouseY;
	private boolean showRectangle;
	private boolean showVisionCircle = true; 
	private boolean showImages = true; 								//rectangles or images?
	private Deque<Map> undoStack = new ArrayDeque<Map>(); 
	private Deque<Map> redoStack = new ArrayDeque<Map>(); 

	public MapViewer(Map map, ArrayList<Agent> agents) {		
		this.map = map;
		this.agents = agents; 
		this.setDoubleBuffered(true);
		this.addMouseListener(movingAdapt);
		this.addMouseMotionListener(movingAdapt);

		try {
			sentryImg = ImageIO.read(new File("src/Resources/fortress.png"));
			outerWallImg = ImageIO.read(new File("src/Resources/brick.jpg"));
			structureImg = ImageIO.read(new File("src/Resources/bush.png"));
			intruderImg = ImageIO.read(new File("src/Resources/iAgent.jpg"));
			surveillanceImg = ImageIO.read(new File("src/Resources/sAgent.jpg"));
			grassImg = ImageIO.read(new File("src/Resources/grassBackground.jpg"));
		} catch (IOException e) {
			System.out.println("Failed to load image.");
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		paintGrass(g2d);

		for (InanimateObject o : map.getGameObjects()) {
			paintObject(o, g2d);
		}

		for (Agent a : agents) {
			paintAgent(a, g2d); 
		}

		paintGoalZone(g2d);
		paintCursorRectangle(g2d);
	}

	public void paintGrass(Graphics2D g) {
		if (showImages) 
			g.drawImage(grassImg, 0, 0, map.getWidth(), map.getHeight(), COLOR_GRASS, null);
		else {
			g.setColor(COLOR_GRASS);
			g.fillRect (0, 0, map.getWidth(), map.getHeight());
		}
	}

	public void paintAgent(Agent a, Graphics2D g) {	  	
		if (showImages) 
			g.drawImage(getImage(a), a.getX(), a.getY(), a.getWidth(), a.getHeight(), null);
		else {
			g.setColor(getColor(a));
			g.fillRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
		}


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
	 public void paintObject(InanimateObject o, Graphics2D g) {
		 if (showImages) 
			 g.drawImage(getImage(o), o.getX(), o.getY(), o.getWidth(), o.getHeight(), null);
		 else {
			 g.setColor(getColor(o));
			 g.fillRect(o.getX(), o.getY(), o.getWidth(), o.getHeight());        	
		 } 
	 }

	 /* 
	  * return color corresponding to inanimate object
	  */
	 public Color getColor(InanimateObject o) {
		 if (o instanceof OuterWall) 
			 return COLOR_OUTERWALL;

		 else if (o instanceof SentryTower) 
			 return COLOR_SENTRY; 

		 else if (o instanceof Structure) 
			 return COLOR_STRUCTURE;

		 else 
			 return COLOR_GRASS;
	 }

	 public Color getColor(Agent a) {
		 if (a instanceof SurveillanceAgent) 
			 return COLOR_SURVEILLANCE;

		 else 
			 return COLOR_INTRUDER; 
	 }

	 /* 
	  * return image corresponding to inanimate object
	  */
	 public BufferedImage getImage(InanimateObject o) {
		 if (o instanceof OuterWall) 
			 return outerWallImg;

		 else if (o instanceof SentryTower) 
			 return sentryImg; 

		 else if (o instanceof Structure) 
			 return structureImg;

		 else 
			 return null;
	 }

	 public BufferedImage getImage(Agent a) {
		 if (a instanceof SurveillanceAgent) 
			 return surveillanceImg;

		 else 
			 return intruderImg; 
	 }

	 public void paintCursorRectangle(Graphics2D g) {
		 g.setColor(Color.WHITE); 
		 if (showRectangle)
			 g.drawRect(currentMouseX, currentMouseY, selectedObject.getSize(), selectedObject.getSize());
	 }

	 public void paintGoalZone(Graphics2D g) {
		 if (map.getGoalZone() != null) {
			 GoalZone goalZone = map.getGoalZone(); 	 
			 
			 g.setColor(COLOR_GOALZONE);
			 g.fillRect(goalZone.getX(), goalZone.getY(), goalZone.getWidth(), goalZone.getHeight());
		 }
	 }

	 class BindMouseMove extends MouseAdapter {
		 private int x;
		 private int y;

		 @Override
		 public void mousePressed(MouseEvent event) {
			 x = event.getX();
			 y = event.getY();
			 InanimateObject o = null;
			 if (selectedObject instanceof SentryTower)
				 o = new SentryTower(new Point(x,y), new Point(x+selectedObject.getSize(), y+selectedObject.getSize()));
			 else if (selectedObject instanceof GoalZone)
				 map.setGoalZone(new GoalZone(new Point(x,y), new Point(x+selectedObject.getSize(), y+selectedObject.getSize())));
			 else if (selectedObject instanceof Structure)
				 o = new Structure(new Point(x,y), new Point(x+selectedObject.getSize(), y+selectedObject.getSize()));

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

	 public void selectObject(InanimateObject object) {
		 selectedObject = object; 
	 }

	 public void toggleVisionCircle() {
		 showVisionCircle = !showVisionCircle; 
	 }

	 public void toggleShowImages() {
		 showImages = !showImages; 
	 }
}
