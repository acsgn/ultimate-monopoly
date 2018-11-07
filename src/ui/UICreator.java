package ui;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;

public class UICreator extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String ultimateMonopoly = "resources/ultimate_monopoly.jpg";
	
	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 * Create the frame.
	 */
	public UICreator() {
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setType(Type.UTILITY);
		setResizable(false);
		setBounds((screenWidth-636)/2, (screenHeight-450)/2, 636, 497);
		getContentPane().setLayout(null);

		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(ultimateMonopoly));
		image.setBounds(0, 0, 630, 252);
		getContentPane().add(image);

		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton serverButton = new JRadioButton("Server");
		serverButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buttonGroup.add(serverButton);
		serverButton.setBounds(30, 272, 100, 75);
		getContentPane().add(serverButton);

		JRadioButton clientButton = new JRadioButton("Client");
		clientButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buttonGroup.add(clientButton);
		clientButton.setSelected(true);
		clientButton.setBounds(30, 367, 100, 75);
		getContentPane().add(clientButton);

		JSlider slider = new JSlider();
		slider.setFont(new Font("Tahoma", Font.PLAIN, 24));
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(4);
		slider.setMaximum(10);
		slider.setMinimum(2);
		slider.setBounds(165, 317, 200, 80);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(2, new JLabel("2"));
		labelTable.put(3, new JLabel("3"));
		labelTable.put(4, new JLabel("4"));
		labelTable.put(5, new JLabel("5"));
		labelTable.put(6, new JLabel("6"));
		labelTable.put(7, new JLabel("7"));
		labelTable.put(8, new JLabel("8"));
		labelTable.put(9, new JLabel("9"));
		labelTable.put(10, new JLabel("10"));
		slider.setLabelTable(labelTable);
		slider.setEnabled(false);
		
		serverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(true);
			}
		});
		
		clientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(false);
			}
		});
		
		getContentPane().add(slider);
		
		JButton startGameButton = new JButton("Start Game");
		startGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		startGameButton.setBounds(400, 317, 200, 80);
		getContentPane().add(startGameButton);
	}

}
