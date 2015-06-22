package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import Agent.InternalMap;
import Agent.InfluenceMap.InfluenceMap;

public class InternalMapComponent extends JComponent {
	private InternalMap map; 
	
	public InternalMapComponent(InternalMap map) {
		this.map = map; 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		int[][] grid = map.getMap(); 
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1) g2d.setColor(Color.BLACK);
				if (grid[i][j] == 0) g2d.setColor(Color.WHITE);
				if (grid[i][j] == 3) g2d.setColor(Color.RED);
				if (grid[i][j] == 4) g2d.setColor(Color.BLUE);
				g2d.fillRect(j*map.getCellWidth(), i*map.getCellWidth(), map.getCellWidth(), map.getCellWidth());
			}
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawString("Wall", 10, 220);
		g2d.setColor(Color.RED);
		g2d.drawString("Explored", 10, 240);
		g2d.setColor(Color.BLUE);
		g2d.drawString("Visited", 10, 260);
	}
	
	public void reset(InternalMap in) {
		map = in; 
	}
}
