package GUI;



import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import GameObjects.InanimateObjects;
import Map.Map;
import Map.MapExporter;
import Map.mapGenerator;

public class MainFrame extends JFrame {
	private static final int RIGHTMENU_WIDTH = 200; 
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width+RIGHTMENU_WIDTH, height);
       
        

        Container container = this.getContentPane(); 
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        mapView = new GUI(map);
        
        ///////////////////
        //right Menu for choosing gameObjects     
        JPanel rightMenu = new JPanel();
        rightMenu.setLayout(new GridLayout(0, 1));
        JButton sentryButton = new JButton("Sentry Tower"); 
        sentryButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("sentry");                         
                        selectObject(InanimateObjects.SENTRY_TYPE);}});
        
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
        
        rightMenu.add(sentryButton); 
        rightMenu.add(goalZoneButton); 
        rightMenu.add(structureButton);
        //////////
        
        
        panel.add(mapView,BorderLayout.WEST);
        panel.add(rightMenu,BorderLayout.EAST); 
        container.add(panel); 
        
        Dimension d = new Dimension(width,height);
        mapView.setPreferredSize(d);
        Dimension d2 = new Dimension(RIGHTMENU_WIDTH,height);
        rightMenu.setPreferredSize(d2);
        
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
                        mapView.setVisible(false);
                        mapView = new GUI(map);
                        mapView.revalidate();
                        mapView.repaint();
                        mapView.setVisible(true);
                        panel.revalidate();
                        panel.repaint();
                        
                        revalidate();
                        repaint();
        }});
        
        JMenuItem impMapItem = new JMenuItem("Import Map");
        JMenuItem expMapItem = new JMenuItem("Export Map");
        expMapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               MapExporter mExp = new MapExporter("test", map); 
               try {
				mExp.export();
               } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
               }
        }});
        
        mapMenu.add(genRndMapItem);
        mapMenu.add(impMapItem);
        mapMenu.add(expMapItem);
        
        menu.add(mapMenu);    
        this.setJMenuBar(menu);
       
        
        this.setVisible(true);
	}
	
	public void selectObject(int type) {
		mapView.selectObject(type); 
	}
	
	
}
