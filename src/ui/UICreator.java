package ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Hashtable;

import game.Controller;
import game.GameListener;

public class UICreator extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String ultimateMonopolyImagePath = "resources/ultimate_monopoly.jpg";

	private String message = "UICREATOR/";
	private JTextField playerCountTextField;
	private JSlider botSlider;
	private int playerCount = 0;
	private int botCount = 0;

	private int width = Toolkit.getDefaultToolkit().getScreenSize().width / 3;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

	private Image ultimateMonopolyImage = new ImageIcon(ultimateMonopolyImagePath).getImage().getScaledInstance(width,
			-1, Image.SCALE_SMOOTH);

	private int chooserVal = -1;

	/**
	 * Create the frame.
	 * 
	 */
	public UICreator() {
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(width, height / 2, width, height);
		getContentPane().setLayout(null);

		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(ultimateMonopolyImage));
		image.setBounds(0, 0, width, ultimateMonopolyImage.getHeight(this));
		getContentPane().add(image);

		int gamePanelHeight = height - 32;
		JPanel gamePanel = new JPanel();
		gamePanel.setBounds(0, ultimateMonopolyImage.getHeight(this), width,
				gamePanelHeight - ultimateMonopolyImage.getHeight(this));
		gamePanel.setLayout(null);

		JPanel playerCountPanel = new JPanel();
		playerCountPanel.setBounds(width / 25, (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 7 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		playerCountPanel.setLayout(null);

		JLabel playerCountLabel = new JLabel("Number of Players Found:");
		playerCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerCountLabel.setBounds(0, 0, 11 * width / 25,
				3 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		playerCountPanel.add(playerCountLabel);

		playerCountTextField = new JTextField();
		playerCountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		playerCountTextField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		playerCountTextField.setBounds(0, 3 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 4 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		playerCountTextField.setEditable(false);
		playerCountTextField.setEnabled(true);
		playerCountPanel.add(playerCountTextField);

		gamePanel.add(playerCountPanel);

		JPanel botPanel = new JPanel();
		botPanel.setBounds(width / 25, 9 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 7 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		botPanel.setLayout(null);

		JLabel botLabel = new JLabel("Number of Bots:");
		botLabel.setBounds(0, 0, 11 * width / 25, 2 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		botLabel.setHorizontalAlignment(SwingConstants.CENTER);
		botLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		botPanel.add(botLabel);

		Hashtable<Integer, JLabel> labelTable = createLabelTable();
		botSlider = new JSlider(0, 9, 0);
		botSlider.setBounds(0, 2 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25, 11 * width / 25,
				5 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		botSlider.setLabelTable(labelTable);
		botSlider.setPaintLabels(true);
		botPanel.add(botSlider);
		botSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				botCount = botSlider.getValue();
				playerCountTextField.setText(playerCount + botCount + "");
			}
		});

		gamePanel.add(botPanel);

		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		loadGameButton.setBounds(width / 25, 17 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 7 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		gamePanel.add(loadGameButton);

		JFileChooser chooser = new JFileChooser();
		// We are going to filter file options.

		loadGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooserVal = chooser.showOpenDialog(UICreator.this);
				if (chooserVal == JFileChooser.APPROVE_OPTION) {
					String fileMessage = message + "LOADGAME/" + chooser.getSelectedFile().getPath();
					Controller.getInstance().dispatchMessage(fileMessage);
				}
			}
		});

		JPanel playerNamePanel = new JPanel();
		playerNamePanel.setBounds(13 * width / 25, (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 7 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		playerNamePanel.setLayout(null);

		JLabel playerNameLabel = new JLabel("Player Name:");
		playerNameLabel.setBounds(0, 0, 11 * width / 25,
				3 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		playerNamePanel.add(playerNameLabel);

		JTextField playerNameField = new JTextField();
		playerNameField.setBounds(0, 3 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 4 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		playerNameField.setHorizontalAlignment(SwingConstants.CENTER);
		playerNameField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		playerNamePanel.add(playerNameField);

		gamePanel.add(playerNamePanel);

		String[] colorNames = { "Red", "Green", "Blue", "Yellow", "Cyan", "Pink", "Orange", "Magenta", "Gray",
				"Black" };
		JComboBox<String> colorBox = new JComboBox<String>(colorNames);
		colorBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		colorBox.setBounds(13 * width / 25, 19 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 50,
				11 * width / 25, 6 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		gamePanel.add(colorBox);

		JButton startGameButton = new JButton("Start Game");
		startGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		startGameButton.setBounds(13 * width / 25, 17 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25,
				11 * width / 25, 7 * (gamePanelHeight - ultimateMonopolyImage.getHeight(this)) / 25);
		gamePanel.add(startGameButton);

		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerCount + botCount > 1) {
					if (chooserVal != JFileChooser.APPROVE_OPTION) {
						String name = playerNameField.getText();
						if (name.isEmpty()) {
							JOptionPane.showMessageDialog(UICreator.this, "Please enter a name!", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						Controller.getInstance().dispatchMessage(message + "CREATE/" + name + "/"
								+ colorNames[colorBox.getSelectedIndex()] + "/" + botCount);
					}
					// Should handle load game in here
				} else
					JOptionPane.showMessageDialog(UICreator.this, "You need at least 2 players to play!", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		getContentPane().add(gamePanel);

	}

	@Override
	public void onGameEvent(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "PLAYERCOUNT":
			playerCount = Integer.parseInt(parsed[1]);
			playerCountTextField.setText(playerCount + botCount + "");
			break;
		case "START":
			dispose();
			break;
		}
	}

	private Hashtable<Integer, JLabel> createLabelTable() {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>(10);
		labelTable.put(0, new JLabel("0"));
		labelTable.put(1, new JLabel("1"));
		labelTable.put(2, new JLabel("2"));
		labelTable.put(3, new JLabel("3"));
		labelTable.put(4, new JLabel("4"));
		labelTable.put(5, new JLabel("5"));
		labelTable.put(6, new JLabel("6"));
		labelTable.put(7, new JLabel("7"));
		labelTable.put(8, new JLabel("8"));
		labelTable.put(9, new JLabel("9"));
		return labelTable;
	}

}
