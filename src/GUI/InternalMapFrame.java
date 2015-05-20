package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Agent.Agent;
import Agent.InternalMap;
import Map.Map;
import Simulation.Simulator;

public class InternalMapFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InternalMap map; 

	public InternalMapFrame(InternalMap map) {
		this.map = map; 

		this.setTitle("Internal Map");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(300, 300); 
		//this.setLocationRelativeTo(null);

		//map view
		JPanel panel = new JPanel();
		InternalMapComponent component = new InternalMapComponent(map); 
		component.setPreferredSize(new Dimension(300, 300));
		panel.add(component);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}
}
