package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Agent.InternalMap;
import Agent.InfluenceMap.InfluenceMap;

public class DeveloperFrame extends JFrame {
	private InternalMap internalMap; 
	private InfluenceMap influenceMap; 
	private InternalMapComponent component; 
	private InfluenceMapComponent influenceComponent;

	public DeveloperFrame(InternalMap internalMap, InfluenceMap influenceMap) {
		this.influenceMap = influenceMap; 
		this.internalMap = internalMap;

		this.setTitle("Internal Map");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(300, 600); 
		//this.setLocationRelativeTo(null);

		//map view
		JPanel panel = new JPanel();
		component = new InternalMapComponent(internalMap);
		influenceComponent = new InfluenceMapComponent(influenceMap);
		component.setPreferredSize(new Dimension(300, 300));
		influenceComponent.setPreferredSize(new Dimension(300, 300));
		panel.add(component);
		panel.add(influenceComponent);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}
	
	public void reset(InfluenceMap inf, InternalMap in) {
		component.reset(in);
		influenceComponent.reset(inf);
		repaint();
	}
}
