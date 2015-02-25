package Map; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import GameObjects.GoalZone;
import GameObjects.InanimateObjects;
import GameObjects.SentryTower;


class GUI extends JComponent {
	private static final int FRAME_WIDTH = 1024; 
	private static final int FRAME_HEIGHT = 768; 
	private static final Color COLOR_GRASS = new Color(5, 128, 60);
	private static final Color COLOR_GOALZONE = Color.RED; 
	private static final Color COLOR_SENTRY = Color.BLACK; 
	
	private int mapWidth; 
	private int mapHeight;
	private ArrayList<InanimateObjects> map = new ArrayList<InanimateObjects>();
    
	public GUI(int width, int height, ArrayList<InanimateObjects> gameObjects) {
		this.mapWidth = width; 
		this.mapHeight = height; 
		this.map = gameObjects; 
		
	}

	public void paintComponent(Graphics g) {
		g.setColor(COLOR_GRASS);
        g.fillRect (0, 0, mapWidth, mapHeight);    
        
        for (InanimateObjects s : map) {
        	if (s instanceof GoalZone) 
        		g.setColor(COLOR_GOALZONE); 
        	
        	if (s instanceof SentryTower) 
        		g.setColor(COLOR_SENTRY);  
        	
        	double width = s.getBottomRight().getY() - s.getTopLeft().getY(); 
        	double height = s.getBottomRight().getX() - s.getTopLeft().getX();         	
            g.fillRect((int)s.getTopLeft().getX(), (int)s.getTopLeft().getY(), (int)width, (int)height);
        }

       
    }
    
    public static void main(String[] a) {
    	ArrayList<InanimateObjects> gameObjects = new ArrayList<InanimateObjects>(); 
    	gameObjects.add(new GoalZone(new Point(100, 100), new Point(150, 150))); 
    	gameObjects.add(new SentryTower(new Point(200, 300), new Point(400, 500))); 
    	
    	//probably shouldnt be hardcoded
    	int mapWidth = 600; 
    	int mapHeight = 600; 
    	
        JFrame window = new JFrame();
        window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new GUI(mapWidth, mapHeight, gameObjects));
        window.setVisible(true);
    }
}

