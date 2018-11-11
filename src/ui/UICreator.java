package ui;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Hashtable;

import game.Controller;
import game.GameListener;

public class UICreator extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String ultimateMonopoly = "resources/ultimate_monopoly.jpg";

	private String message = "UICREATOR/";

	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 * Create the frame.
	 * 
	 * @param screen
	 */
	public UICreator(Controller controller) {
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBounds((screenWidth - 636) / 2, (screenHeight - 450) / 2, 636, 498);
		getContentPane().setLayout(null);

		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(ultimateMonopoly));
		image.setBounds(0, 0, 630, 252);
		getContentPane().add(image);

		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton serverButton = new JRadioButton("Server");
		serverButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buttonGroup.add(serverButton);
		serverButton.setBounds(30, 275, 100, 75);
		getContentPane().add(serverButton);

		JRadioButton clientButton = new JRadioButton("Client");
		clientButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buttonGroup.add(clientButton);
		clientButton.setSelected(true);
		clientButton.setBounds(30, 375, 100, 75);
		getContentPane().add(clientButton);

		JPanel playerCountPanel = new JPanel();
		playerCountPanel.setBounds(165, 275, 200, 87);
		playerCountPanel.setLayout(null);

		JLabel playerCountLabel = new JLabel("Number of Players:");
		playerCountLabel.setBounds(24, 0, 151, 20);
		playerCountPanel.add(playerCountLabel);
		playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		Hashtable<Integer, JLabel> labelTable = createLabelTable();
		JSlider slider = new JSlider(2, 10, 4);
		slider.setBounds(0, 23, 200, 64);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 18));
		slider.setLabelTable(labelTable);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setEnabled(false);
		playerCountPanel.add(slider);

		getContentPane().add(playerCountPanel);

		JPanel IPPanel = new JPanel();
		IPPanel.setBounds(165, 375, 200, 75);
		IPPanel.setLayout(null);

		JLabel IPLabel = new JLabel("Server IP:");
		IPLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IPLabel.setBounds(35, 10, 130, 20);
		IPPanel.add(IPLabel);

		JTextField IPTextField = new JTextField();
		IPTextField.setHorizontalAlignment(SwingConstants.CENTER);
		IPTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IPTextField.setBounds(0, 35, 200, 30);
		IPPanel.add(IPTextField);

		getContentPane().add(IPPanel);

		serverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setEnabled(true);
				IPTextField.setEnabled(false);
			}
		});

		clientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setEnabled(false);
				IPTextField.setEnabled(true);
			}
		});

		JLabel playerNameLabel = new JLabel("Player Name:");
		playerNameLabel.setBounds(435, 275, 130, 20);
		getContentPane().add(playerNameLabel);
		playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JTextField playerNameField = new JTextField();
		playerNameField.setBounds(400, 300, 200, 30);
		playerNameField.setHorizontalAlignment(SwingConstants.CENTER);
		playerNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(playerNameField);

		String[] colorNames = { "Red", "Green", "Blue", "Yellow", "Cyan", "Pink", "Orange", "Magenta", "Gray",
				"Black" };
		JComboBox<String> colorBox = new JComboBox<String>(colorNames);
		colorBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		colorBox.setBounds(400, 342, 200, 30);
		colorBox.setSelectedIndex(2);
		getContentPane().add(colorBox);

		JButton startGameButton = new JButton("Start Game");
		startGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		startGameButton.setBounds(400, 385, 200, 65);
		getContentPane().add(startGameButton);

		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String networkMessage;
				if (buttonGroup.isSelected(serverButton.getModel()))
					networkMessage = "SERVER/" + slider.getValue();
				else {
					String IP = IPTextField.getText();
					if (isLegitIP(IP))
						networkMessage = "CLIENT/" + IP;
					else {
						JOptionPane.showMessageDialog(UICreator.this,
								"Please enter a valid IP Address (e.g. 192.168.1.2) !", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				String name = playerNameField.getText();
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(UICreator.this, "Please enter a name!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				controller.dispatchMessage(message + networkMessage);
				controller.dispatchMessage(message + "PLAYERNAME/" + name);
				controller.dispatchMessage(message + "PLAYERCOLOR/" + colorNames[colorBox.getSelectedIndex()]);
			}

			private boolean isLegitIP(String IP) {
				String[] areas = IP.split("\\.");
				if (areas.length != 4)
					return false;
				for (String ip : areas) {
					int number = 0;
					try {
						number = Integer.parseInt(ip);
					} catch (NumberFormatException numberException) {
						return false;
					}
					if (number < 1 || number > 255) {
						return false;
					}
				}
				return true;
			}
		});

	}

	@Override
	public void onGameEvent(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "START":
			dispose();
			break;
		case "NETWORKERROR":
			JOptionPane.showMessageDialog(UICreator.this, "No server found on given IP Address!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private Hashtable<Integer, JLabel> createLabelTable() {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>(9);
		labelTable.put(2, new JLabel("2"));
		labelTable.put(3, new JLabel("3"));
		labelTable.put(4, new JLabel("4"));
		labelTable.put(5, new JLabel("5"));
		labelTable.put(6, new JLabel("6"));
		labelTable.put(7, new JLabel("7"));
		labelTable.put(8, new JLabel("8"));
		labelTable.put(9, new JLabel("9"));
		labelTable.put(10, new JLabel("10"));
		return labelTable;
	}

}
