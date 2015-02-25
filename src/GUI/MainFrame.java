package GUI;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GameObjects.InanimateObjects;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	
	
	public MainFrame(int width, int height, ArrayList<InanimateObjects> gameObjects) {
		frame = new JFrame("Multi-Agent Surveillance");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, width, height);
		frame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.add(new GUI(width, height, gameObjects), BorderLayout.CENTER);
		setContentPane(contentPane);
		frame.getContentPane().add(new GUI(width, height, gameObjects));
		frame.setVisible(true);
	}
}
