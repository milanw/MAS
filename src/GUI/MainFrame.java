package GUI;



import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import GameObjects.InanimateObjects;
import Map.Map;
import Map.MapExporter;
import Map.mapGenerator;

public class MainFrame extends JFrame {
	private static final int RIGHTMENU_WIDTH = 300; 
	private int width;
	private int height;
	private Map map;	
	private GUI mapView; 
	private JPanel panel;
	public int objectSelected = 0;
	
	public MainFrame(int w, int h, ArrayList<InanimateObjects> gameObjects) {
		this.width = w;
		this.height = h; 
		this.map = new Map(w, h, gameObjects);
		
		this.setTitle("Multi-Agent Surveillance");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        this.setSize(width+RIGHTMENU_WIDTH, height);
        this.setLocationRelativeTo(null);
        

        Container container = this.getContentPane(); 
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        mapView = new GUI(map);
        
        //right Menu
        JTabbedPane tabbedPane = new JTabbedPane(); 
        
        ///////////////////
        //map editing menu
        JPanel editingMenu = new JPanel();
        editingMenu.setLayout(new FlowLayout());
        JButton sentryButton = new JButton("Sentry Tower"); 
        sentryButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("sentry");                         
                        selectObject(InanimateObjects.SENTRY_TYPE);}});
       // sentryButton.setPreferredSize(new Dimension(100, 40));
        
        JButton goalZoneButton = new JButton("Goal Zone"); 
        goalZoneButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("goal zone");                        
                        selectObject(InanimateObjects.GOAL_TYPE);}});
        
        JButton structureButton = new JButton("Structure"); 
        structureButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("structure");                         
                        selectObject(InanimateObjects.STRUCTURE_TYPE);}});
        
        editingMenu.add(sentryButton); 
        editingMenu.add(goalZoneButton); 
        editingMenu.add(structureButton);
        
        // simulation menu
        //////////
        JPanel simulationMenu = new JPanel(); 
        
        tabbedPane.addTab("Map Editing", editingMenu);
        tabbedPane.addTab("Simulation", simulationMenu);
        //////
        
        
        panel.add(mapView,BorderLayout.WEST);
        panel.add(tabbedPane,BorderLayout.EAST); 
        container.add(panel); 
        
        Dimension d = new Dimension(width,height);
        mapView.setPreferredSize(d);
        Dimension d2 = new Dimension(RIGHTMENU_WIDTH,height);
        tabbedPane.setPreferredSize(d2);
        
        ////////////////
        // menu bar
        JMenuBar menu = new JMenuBar();
        
        // build the map menu
        JMenu mapMenu = new JMenu("Map");
        
        //doesnt work yet
        JMenuItem genRndMapItem = new JMenuItem("Generate new random Map");
        genRndMapItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        map = new Map(width, height, new mapGenerator(width, height).getMap());
                        //mapView.setVisible(false);
                        mapView = new GUI(map);
                        mapView.revalidate();
                        mapView.repaint();
                        //mapView.setVisible(true);
                        panel.revalidate();
                        panel.repaint();
                        
                        revalidate();
                        repaint();
                        getContentPane().repaint(); 
        }});
        genRndMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        JMenuItem impMapItem = new JMenuItem("Import Map");
        impMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        
        JMenuItem expMapItem = new JMenuItem("Export Map");
        expMapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String mapName = JOptionPane.showInputDialog(null,
                        "How do you want to call the map?",
                        "Enter map name",
                        JOptionPane.QUESTION_MESSAGE);
               
               MapExporter mExp = new MapExporter(mapName, map); 
               try {
				mExp.export();
               } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
               }
        }});
        expMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        
        JMenuItem undoMapItem = new JMenuItem("undo");
        undoMapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               mapView.undo();
        }});
        undoMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        
        mapMenu.add(genRndMapItem);
        mapMenu.add(impMapItem);
        mapMenu.add(expMapItem);
        mapMenu.add(undoMapItem);
        
        menu.add(mapMenu);    
        this.setJMenuBar(menu);
       
        
        this.setVisible(true);
	}
	
	public void selectObject(int type) {
		mapView.selectObject(type); 
	}
	
	
}
