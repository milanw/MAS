package Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import GameObjects.GoalZone;
import GameObjects.SentryTower;

public class Map extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	
	private Point gzx = new Point(250, 5);
	private Point gzy = new Point(350, 50);
	
	private Point stx = new Point(100, 100);
	private Point sty = new Point(130, 130);

	/**
	 * Create the frame.
	 */
	public Map(int width, int height) {
	
		
		
		
		
		frame = new JFrame("Multi-Agent Surveillance");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, width, height);
		frame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		frame.setVisible(true);
		
		
		//frame.add();

		
		repaint();
	
	}
	
//	public void paint(Graphics g) {
//		for (Drawable d : gameObjects) {
//			d.draw(g);
//			System.out.println("lololo");
//			}
//		}
	}


