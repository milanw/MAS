package Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameObjects.Drawable;
import GameObjects.GoalZone;
import GameObjects.SentryTower;

public class Map extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	List<Drawable> gameObjects;
	
	private Point2D.Double gzx = new Point2D.Double(250, 5);
	private Point2D.Double gzy = new Point2D.Double(350, 50);
	
	private Point2D.Double stx = new Point2D.Double(100, 100);
	private Point2D.Double sty = new Point2D.Double(130, 130);

	/**
	 * Create the frame.
	 */
	public Map(int width, int height) {
	
		
		gameObjects = new ArrayList<Drawable>();
		
		
		frame = new JFrame("Multi-Agent Surveillance");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, width, height);
		frame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		frame.setVisible(true);
		
		
		GoalZone gz = new GoalZone(gzx, gzy);
		SentryTower st = new SentryTower(stx, sty);
		
		gameObjects.add(gz);
		gameObjects.add(st);
		
		frame.add(st);
		frame.add(gz);
		
		repaint();
	
	}
	
//	public void paint(Graphics g) {
//		for (Drawable d : gameObjects) {
//			d.draw(g);
//			System.out.println("lololo");
//			}
//		}
	}


