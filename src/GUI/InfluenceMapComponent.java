package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import Agent.InfluenceMap.InfluenceMap;

public class InfluenceMapComponent extends JComponent{
	
private InfluenceMap map; 
	
	public InfluenceMapComponent(InfluenceMap map) {
		this.map = map; 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		double[][] grid = map.getMap(); 
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				g2d.setColor(colorFor(grid[i][j]));
				g2d.fillRect(j*map.getCellWidth(), i*map.getCellWidth(), map.getCellWidth(), map.getCellWidth());
			}
		}
		
		g2d.setColor(Color.BLUE);
		g2d.drawString("Low Activity", 10, 220);
		g2d.setColor(Color.RED);
		g2d.drawString("High Activity", 10, 240);
		
	}
	
	private static Color colorFor(double value) {
		Color c;
	    if (value < 0) 
	    	c = new Color(0, 0, (int)(Math.abs(value)*255));	    
	    else 
	    	c = new Color((int)(value*255), 0, 0);
	    	
	    return c; 
	}
	
	public void reset(InfluenceMap inf) {
		map = inf; 
	}
}
