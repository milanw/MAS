package GUI;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import Agent.Agent;
import Agent.IntruderAgent;
import Agent.SurveillanceAgent;
import GameObjects.GoalZone;
import GameObjects.InanimateObject;
import GameObjects.SentryTower;
import GameObjects.Structure;
import Map.Map;
import Map.MapExporter;
import Map.MapGenerator;
import Map.MapImporter;
import Simulation.Simulator;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int RIGHTMENU_WIDTH = 300; 
	private static final int FRAME_WIDTH = 1024; 
	private static final int FRAME_HEIGHT = 768; 
	
	private JButton startStopButton; 
	private JButton pauseContinueButton;
	private JButton spawnIntruderButton;
	
	private int width;
	private int height;
	public int objectSelected = 0;
	
	private Map map;	
	private MapViewer mapView; 
	private JPanel panel;	
	private Simulator simulation;

	public MainFrame(Map map, Simulator simulation) {
		this.simulation = simulation;
		this.width = map.getWidth();
		this.height = map.getHeight(); 
		this.map = map;

		this.setTitle("Multi-Agent Surveillance");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT); 
		this.setLocationRelativeTo(null);

		//map view
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		int remainingWidth = FRAME_WIDTH - RIGHTMENU_WIDTH; 
		mapView = new MapViewer(map, simulation.getAgents(), remainingWidth, FRAME_HEIGHT);      
		
		mapView.setPreferredSize(new Dimension(remainingWidth, FRAME_HEIGHT));
		panel.add(mapView, BorderLayout.WEST);

		//right Menu
		JTabbedPane tabbedPane = new JTabbedPane(); 
		tabbedPane.addTab("Map Editing", createEditingMenu());
		tabbedPane.addTab("Simulation", createSimulationMenu());
		tabbedPane.setPreferredSize(new Dimension(RIGHTMENU_WIDTH, height));
		panel.add(tabbedPane, BorderLayout.EAST);
		panel.add(new JLabel(Integer.toString(map.getWidth()) + "x" + Integer.toString(map.getHeight()) + "m"), BorderLayout.SOUTH);
		

		//set menu bar
		this.setJMenuBar(createMenuBar());

		this.getContentPane().add(panel);
		this.setVisible(true);
		
		this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {            	
            	int newWidth = (int) getBounds().getSize().getWidth(); 
            	int newHeight = (int) getBounds().getSize().getHeight(); 
            	mapView.setPreferredSize(new Dimension(newWidth-RIGHTMENU_WIDTH, newHeight));
                mapView.resize((double)newWidth, newHeight);
            }
        });
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
		
		
		JMenuItem createEmptMapItem = new JMenuItem("Create new empty Map");
		createEmptMapItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map = new Map(200, 200, new ArrayList<InanimateObject>(), null);                        
				mapView.setMap(map);                        
			}});

		JMenuItem genRndMapItem = new JMenuItem("Generate new random Map");
		genRndMapItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map = new Map(new MapGenerator(width, height).getMap());                        
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
		
		
		

		mapMenu.add(createEmptMapItem);
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
				selectObject(new SentryTower(null, null));
			}});


		JButton goalZoneButton = new JButton("Goal Zone"); 
		goalZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {             
				selectObject(new GoalZone(null, null));
			}});

		JButton structureButton = new JButton("Structure"); 
		structureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {               
				selectObject(new Structure(null, null));
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


		startStopButton = new JButton("Start"); 		 
		startStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (simulation.isRunning()) {
					simulation.stopSimulation(); 
					startStopButton.setText("Start");
				}
				else {
					simulation.startSimulation();
					startStopButton.setText("Stop & Reset");
				}

			}});	      

		pauseContinueButton = new JButton("Pause"); 		 
		pauseContinueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (simulation.isPaused()) {
					simulation.pauseSimulation();
					pauseContinueButton.setText("Continue");
				}
				else {
					simulation.continueSimulation();
					pauseContinueButton.setText("Pause");
				}
			}});
		
		spawnIntruderButton = new JButton("Spawn Intruder"); 		 
		spawnIntruderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				simulation.spawnIntruder();
			}});

		JCheckBox visionCheckBox = new JCheckBox("Show vision range", true);
		visionCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {            
				mapView.toggleVisionCircle();
				mapView.repaint();
			}});

		JCheckBox imagesCheckBox = new JCheckBox("Show images", true);
		imagesCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {            
				mapView.toggleShowImages();
				mapView.repaint();
			}});
		
		JCheckBox markersCheckBox = new JCheckBox("Show markers", true);
		markersCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {            
				mapView.toggleShowMarkers();
				mapView.repaint();
			}});

		simulationMenu.add(startStopButton); 
		simulationMenu.add(pauseContinueButton);
		simulationMenu.add(spawnIntruderButton);

		simulationMenu.add(visionCheckBox); 
		simulationMenu.add(imagesCheckBox); 
		simulationMenu.add(markersCheckBox);

		return simulationMenu;
	}

	public void selectObject(InanimateObject object) {
		mapView.selectObject(object); 
	}

	public Map getMap() {
		return map;
	}

    public MapViewer getMapViewer() { return mapView; }
	/*
	public static void main(String[] args) {
		int width = 600;
		int height = 600;
		Map m = new MapGenerator(width, height).getMap();
		//ArrayList<Agent> agents = new SimpleAlgorithm(m).getAgents();
		ArrayList<Agent> agents = new ArrayList<Agent>();
		agents.add(new IntruderAgent(new Point2D.Double(100, 100), new Point2D.Double(105, 105)));
		agents.add(new SurveillanceAgent(new Point2D.Double(200, 200), new Point2D.Double(205, 205)));
		Simulator main = new Simulator(m, agents);
		MainFrame frame = new MainFrame(m, agents, main);
		main.setFrame(frame);
	}*/
}
