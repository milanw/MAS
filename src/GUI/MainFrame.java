package GUI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import Algorithm.SimpleAlgorithm;
import GameObjects.InanimateObjects;
import Main.Main;
import Map.Map;
import Map.MapExporter;
import Map.MapImporter;
import Map.mapGenerator;

public class MainFrame extends JFrame {
	private static final int RIGHTMENU_WIDTH = 300; 
	private int width;
	private int height;
	private Map map;	
	private GUI mapView; 
	private JPanel panel;
	public int objectSelected = 0;
	
	public MainFrame(Map map, ArrayList<Agent> agents) {
		
		this.width = map.getWidth();
		this.height = map.getHeight(); 
		this.map = map;
		
		this.setTitle("Multi-Agent Surveillance");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        this.setSize(width+RIGHTMENU_WIDTH, height+100);
        this.setLocationRelativeTo(null);
        
        //map view
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        mapView = new GUI(map, agents);       
        mapView.setPreferredSize(new Dimension(width,height));
       // mapView.setBorder(BorderFactory.createLineBorder(Color.GRAY,25));
        panel.add(mapView,BorderLayout.WEST);
        
        
        //right Menu
        JTabbedPane tabbedPane = new JTabbedPane(); 
        tabbedPane.addTab("Map Editing", createEditingMenu());
        tabbedPane.addTab("Simulation", createSimulationMenu());
        tabbedPane.setPreferredSize(new Dimension(RIGHTMENU_WIDTH,height));
        panel.add(tabbedPane,BorderLayout.EAST); 
        
        //set menu bar
        this.setJMenuBar(createMenuBar());
        
        this.getContentPane().add(panel); 
        this.setVisible(true);
	}
	
	/*
	 * creates the top menu bar
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
        menu.add(createMapMenu()); 
        
        return menu;
	}
	
	/*
	 * creates the map menu located in the top menu bar
	 */
	public JMenu createMapMenu() {
		JMenu mapMenu = new JMenu("Map");
		
        JMenuItem genRndMapItem = new JMenuItem("Generate new random Map");
        genRndMapItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        map = new Map(new mapGenerator(width, height).getMap());                        
                        mapView.setMap(map);                        
        }});
        genRndMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        JMenuItem impMapItem = new JMenuItem("Import Map");
        impMapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String fileName = ""; 
            	JFileChooser fc = new JFileChooser("src/savedMaps/"); 
            	
            	//only look for .map files
            	FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files", "map");
            	fc.setFileFilter(filter);
            	
                int returnVal = fc.showOpenDialog(MainFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileName = fc.getSelectedFile().getName();
                    MapImporter mapImporter = new MapImporter();                       
                    mapView.setMap(mapImporter.importMap(fileName));     
                }
                               
        }});
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
        
        JMenuItem undoMapItem = new JMenuItem("Undo");
        undoMapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               mapView.undo();
        }});
        undoMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        
        JMenuItem redoMapItem = new JMenuItem("Redo");
        redoMapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               mapView.redo();
        }});
        redoMapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        
        mapMenu.add(genRndMapItem);
        mapMenu.add(impMapItem);
        mapMenu.add(expMapItem);
        mapMenu.add(undoMapItem);
        mapMenu.add(redoMapItem);
        
        return mapMenu;
	}
	
	/*
	 * creates the map editing menu on the right
	 */
	public JPanel createEditingMenu() {
		JPanel editingMenu = new JPanel();
        editingMenu.setLayout(new FlowLayout());
        JButton sentryButton = new JButton("Sentry Tower"); 
        sentryButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {            
                        selectObject(InanimateObjects.SENTRY_TYPE);
        }});
       
        
        JButton goalZoneButton = new JButton("Goal Zone"); 
        goalZoneButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {             
                        selectObject(InanimateObjects.GOAL_TYPE);
        }});
        
        JButton structureButton = new JButton("Structure"); 
        structureButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {               
                        selectObject(InanimateObjects.STRUCTURE_TYPE);
        }});
        
        editingMenu.add(sentryButton); 
        editingMenu.add(goalZoneButton); 
        editingMenu.add(structureButton);
        
        return editingMenu; 
	}
	
	/*
	 * creates the simulation menu on the right
	 */
	public JPanel createSimulationMenu() {
		  JPanel simulationMenu = new JPanel(); 
		  simulationMenu.setLayout(new FlowLayout());
		  
		  String startStop = "Start/Stop"; 
		  JButton startStopButton = new JButton(startStop); 		 
	      startStopButton.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e) {            
	    		  Main v = new Main(map);
	      }});
	      
	      JCheckBox visionCheckBox = new JCheckBox("Show vision range", true);
	      visionCheckBox.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {            
                  mapView.toggleVisionCircle();
          }});
	      
	      JCheckBox imagesCheckBox = new JCheckBox("Show images", true);
	      imagesCheckBox.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {            
                  mapView.toggleShowImages();
          }});
	      
	      simulationMenu.add(startStopButton); 
	      simulationMenu.add(visionCheckBox); 
	      simulationMenu.add(imagesCheckBox); 
		  
		  return simulationMenu;
	}
	
	public void selectObject(int type) {
		mapView.selectObject(type); 
	}
	
	public static void main(String[] args) {
		int width = 600;
		int height = 600;
		Map m = new mapGenerator(width, height).getMap();
		//ArrayList<Agent> agents = new SimpleAlgorithm(m).getAgents();
		ArrayList<Agent> agents = new ArrayList<Agent>();
		agents.add(new IntruderAgent(new Point2D.Double(100, 100), new Point2D.Double(105, 105)));
		agents.add(new SurveillanceAgent(new Point2D.Double(200, 200), new Point2D.Double(205, 205)));
		MainFrame frame = new MainFrame(m, agents);
	}
}
