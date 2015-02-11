package Map;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JTextField txtWidth;
	private JTextField txtHeight;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("Map Generator");
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton newRandom = new JButton("New Random");
		newRandom.setBounds(173, 243, 117, 29);
		contentPane.add(newRandom);
		
		
		txtWidth = new JTextField();
		txtWidth.setText("600");
		txtWidth.setBounds(50, 203, 134, 28);
		contentPane.add(txtWidth);
		txtWidth.setColumns(10);
		
		
		txtHeight = new JTextField();
		txtHeight.setText("600");
		txtHeight.setBounds(270, 203, 134, 28);
		contentPane.add(txtHeight);
		txtHeight.setColumns(10);
		
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setBounds(50, 175, 61, 16);
		contentPane.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(270, 175, 61, 16);
		contentPane.add(lblHeight);
		
		
		newRandom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				int width = Integer.parseInt(txtWidth.getText());
				int height = Integer.parseInt(txtHeight.getText());

				Map map = new Map(width, height);
			}
			
		});
		
	}
}
