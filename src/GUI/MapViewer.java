package GUI; 

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import Agent.Agent;
import Agent.SurveillanceAgent;
import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.Marker;
import GameObjects.OuterWall;
import GameObjects.SentryTower;
import GameObjects.Structure;
import Map.Map;



public class MapViewer extends JComponent{

	private static final long serialVersionUID = 1L;
	private static final Color COLOR_GRASS = new Color(5, 128, 60);			// dark green
	private static final Color COLOR_GOALZONE = new Color(255, 0, 0, 128);  //transparent red
	private static final Color COLOR_SENTRY = Color.MAGENTA; 
	private static final Color COLOR_OUTERWALL = Color.BLACK;
	private static final Color COLOR_STRUCTURE = Color.CYAN;	
	private static final Color COLOR_INTRUDER = Color.RED;
	private static final Color COLOR_SURVEILLANCE = Color.GREEN;

	private static final Color[] COLOR_MARKER = {new Color(0, 255, 255, 128), new Color(0, 255, 0, 128),
		new Color(0, 0, 255, 128), new Color(255, 0, 255, 128), new Color(255, 255, 0, 128)}; 


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
	private boolean showMarkers = true; 
	private Deque<Map> undoStack = new ArrayDeque<Map>(); 
	private Deque<Map> redoStack = new ArrayDeque<Map>(); 

	private double factor = 2.0; 
	private Shape arc;
	
	public MapViewer(Map map, ArrayList<Agent> agents, int width, int height) {		
		this.map = map;
		this.agents = agents; 
		this.factor = getFactor(width, height-60); 
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

		if (showMarkers) paintMarkers(g2d);

		paintGoalZone(g2d);
		paintCursorRectangle(g2d);
	}

	public void paintGrass(Graphics2D g) {
		if (showImages) 
			g.drawImage(grassImg, 0, 0, scale(map.getWidth()), scale(map.getHeight()), COLOR_GRASS, null);
		else {
			g.setColor(COLOR_GRASS);
			g.fillRect (0, 0, scale(map.getWidth()), scale(map.getHeight()));
		}
	}

	public void paintAgent(Agent a, Graphics2D g) {	  	
		if (showImages) 
			g.drawImage(getImage(a), scale(a.getX()), scale(a.getY()), scale(a.getWidth()), scale(a.getHeight()), null);
		else {
			g.setColor(getColor(a));
			g.fillRect(scale(a.getX()), scale(a.getY()), scale(a.getWidth()), scale(a.getHeight()));
		}


		if (showVisionCircle) 
			paintVisionRange(a, g);         
	}

	public void paintVisionRange(Agent a, Graphics2D g) {
		double range = 20.0; //a.getVisionRange();
		double angle = 45;
		
		double middleX = a.getCenter().getX();
		double middleY = a.getCenter().getY();
		
		Point2D.Double midPoint = a.getCenter();
		
		double p1x = midPoint.getX() - range*Math.sin(angle);
		double p1y = midPoint.getY() - range*Math.cos(angle);
		
		double p2x = midPoint.getX() + range*Math.sin(angle);
		double p2y = midPoint.getY() - range*Math.cos(angle);
		g.setColor(Color.BLACK);
		
		Point2D.Double p1 = new Point2D.Double(p1x, p1y);
		Point2D.Double p2 = new Point2D.Double(p2x, p2y);
		
		Shape arc = new Arc2D.Double(scale(middleX-range), scale(middleY-range), scale(2*range), scale(2*range), (int)(90 - angle/2), 45, 2);
		this.arc = arc;	
		
		Color c = new Color(1f, 0f, 0f, 0.3f);
		g.setColor(c);
		g.fill(arc);
		
		
	}
	
	public void paintHearingRange(Agent a, Graphics2D g) {
		double range = a.getHearingRange();
		double middleX = (a.getTopLeft().getX() + a.getBottomRight().getX()) / 2.0;
		double middleY = (a.getTopLeft().getY() + a.getBottomRight().getY()) / 2.0;
		g.setColor(Color.RED);
		g.drawOval(scale(middleX-range), scale(middleY-range), scale(2*range), scale(2*range));;
	}

	/* 
	 * paint an inanimate object
	 */
	public void paintObject(InanimateObject o, Graphics2D g) {
		if (showImages) 
			g.drawImage(getImage(o), scale(o.getX()), scale(o.getY()), scale(o.getWidth()), scale(o.getHeight()), null);
		else {
			g.setColor(getColor(o));
			g.fillRect(scale(o.getX()), scale(o.getY()), scale(o.getWidth()), scale(o.getHeight()));        	
		} 
	}

	public void paintMarkers(Graphics2D g) {
		ArrayList<Marker> markers = map.getMarkers();  
		for (int i = 0; i < markers.size(); i++) {
			g.setColor(COLOR_MARKER[markers.get(i).getType()]);
			g.fillRect(scale(markers.get(i).getX()), scale(markers.get(i).getY()), scale(markers.get(i).getWidth()), scale(markers.get(i).getHeight()));
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

		else if(o instanceof GoalZone){
			return COLOR_GOALZONE;
			
		}else return COLOR_GRASS;
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
    public void setImage(Agent a, BufferedImage b){
        if (a instanceof SurveillanceAgent)
            surveillanceImg = b;

        else
            intruderImg = b;
    }

	public void paintCursorRectangle(Graphics2D g) {
		g.setColor(Color.WHITE); 
		if (showRectangle)
			g.drawRect(currentMouseX, currentMouseY, scale(selectedObject.getSize()), scale(selectedObject.getSize()));
	}

	public void paintGoalZone(Graphics2D g) {
		
		if (map.getGoalZone() != null) {
			GoalZone goalZone = map.getGoalZone(); 	 

			g.setColor(COLOR_GOALZONE);
			g.fillRect(scale(goalZone.getX()), scale(goalZone.getY()), scale(goalZone.getWidth()), scale(goalZone.getHeight()));
		}
	}

	class BindMouseMove extends MouseAdapter {
		private int x;
		private int y;

		@Override
		public void mousePressed(MouseEvent event) {
			x = (int)(event.getX()/factor);
			y = (int)(event.getY()/factor);
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
				System.out.println("add");
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
	
	public Map getMap() {
		return map; 
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
	
	public void toggleShowMarkers() {
		showMarkers = !showMarkers; 
	}

	public int scale(double value) {
		return (int)(value*factor); 
	}

	public double getFactor(double width, double height) {
		double xFactor = (double)width/ (double)map.getWidth(); 
		double yFactor = (double)height/ (double)map.getHeight(); 
		return xFactor < yFactor ? xFactor : yFactor;
	}

	public void resize(double width, double height) {		 
		this.factor = getFactor(width, height-60); 
	}


    //the desired angle of rotating the image is calculated as the following
    // double angle = Math.toRadians(degree);
    public void rotate(BufferedImage image, double angle, Agent a) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(angle, w/2, h/2);
        g.drawRenderedImage(image, null);
        g.dispose();
        this.setImage(a, result);
        //return result;
    }

    public static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    
    public void reset(ArrayList<Agent> a) {
    	agents = a;
    	repaint();
    }
}

