package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Agent.InfluenceMap.InfluenceMap;

public class InfluenceMapFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InfluenceMap map; 

	public InfluenceMapFrame(InfluenceMap map) {
		this.map = map; 

		this.setTitle("Influence Map");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(300, 300); 
		//this.setLocationRelativeTo(null);

		//map view
		JPanel panel = new JPanel();
		InfluenceMapComponent component = new InfluenceMapComponent(map); 
		component.setPreferredSize(new Dimension(300, 300));
		panel.add(component);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}
}
