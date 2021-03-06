package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Hashtable;

import game.Controller;
import game.GameListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class UICreator extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String ultimateMonopolyVideoPath = "resources/trailer.mp4";

	private String message = "UICREATOR/";
	private String file;

	private JTextField playerCountTextField;
	private JSlider botSlider;
	private int playerCount = 0;
	private int botCount = 0;

	private Color choosenColor;

	private int width = Toolkit.getDefaultToolkit().getScreenSize().width / 3;
	private int height = 3 * Toolkit.getDefaultToolkit().getScreenSize().height / 5;

	private Font font = new Font("Tahoma", Font.PLAIN, width * 3 / 80);
	private Font labelFont = new Font("Tahoma", Font.PLAIN, width * 3 / 96);

	public UICreator() {
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(width, height / 3, width, height);
		getContentPane().setLayout(null);

		int vHeight = 5 * height / 9;

		JFXPanel video = new JFXPanel();
		File videoFile = new File(ultimateMonopolyVideoPath);
		try {
			Media media = new Media(videoFile.toURI().toURL().toExternalForm());
			final MediaPlayer player = new MediaPlayer(media);
			player.setAutoPlay(true);
			MediaView mediaView = new MediaView(player);
			mediaView.setFitWidth(width);
			mediaView.setSmooth(true);
			Group root = new Group();
			root.getChildren().add(mediaView);
			Scene scene = new Scene(root);
			video.setScene(scene);
			video.setBounds(0, 0, width, vHeight);
			getContentPane().add(video);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		int gamePanelHeight = height - vHeight - 32;
		JPanel gamePanel = new JPanel();
		gamePanel.setBounds(0, vHeight, width, gamePanelHeight);
		gamePanel.setLayout(null);

		JPanel playerCountPanel = new JPanel();
		playerCountPanel.setBounds(width / 25, gamePanelHeight / 25, 11 * width / 25, 7 * gamePanelHeight / 25);
		playerCountPanel.setLayout(null);

		JLabel playerCountLabel = new JLabel("Number of Players Found:");
		playerCountLabel.setFont(labelFont);
		playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerCountLabel.setBounds(0, 0, 11 * width / 25, 3 * gamePanelHeight / 25);
		playerCountPanel.add(playerCountLabel);

		playerCountTextField = new JTextField();
		playerCountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		playerCountTextField.setFont(font);
		playerCountTextField.setBounds(0, 3 * gamePanelHeight / 25, 11 * width / 25, 4 * gamePanelHeight / 25);
		playerCountTextField.setEditable(false);
		playerCountTextField.setEnabled(true);
		playerCountPanel.add(playerCountTextField);

		gamePanel.add(playerCountPanel);

		JPanel botPanel = new JPanel();
		botPanel.setBounds(width / 25, 9 * gamePanelHeight / 25, 11 * width / 25, 7 * gamePanelHeight / 25);
		botPanel.setLayout(null);

		JLabel botLabel = new JLabel("Number of Bots:");
		botLabel.setBounds(0, 0, 11 * width / 25, 2 * gamePanelHeight / 25);
		botLabel.setHorizontalAlignment(SwingConstants.CENTER);
		botLabel.setFont(labelFont);
		botPanel.add(botLabel);

		Hashtable<Integer, JLabel> labelTable = createLabelTable();
		botSlider = new JSlider(0, 9, 0);
		botSlider.setBounds(0, 2 * gamePanelHeight / 25, 11 * width / 25, 5 * gamePanelHeight / 25);
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
		loadGameButton.setFont(font);
		loadGameButton.setBounds(width / 25, 17 * gamePanelHeight / 25, 11 * width / 25, 7 * gamePanelHeight / 25);
		gamePanel.add(loadGameButton);

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Ultimate Monopoly Save Files", "umsf");
		chooser.setFileFilter(filter);

		loadGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					file = chooser.getSelectedFile().getPath();
			}
		});

		JPanel playerNamePanel = new JPanel();
		playerNamePanel.setBounds(13 * width / 25, gamePanelHeight / 25, 11 * width / 25, 7 * gamePanelHeight / 25);
		playerNamePanel.setLayout(null);

		JLabel playerNameLabel = new JLabel("Player Name:");
		playerNameLabel.setBounds(0, 0, 11 * width / 25, 3 * gamePanelHeight / 25);
		playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerNameLabel.setFont(labelFont);
		playerNamePanel.add(playerNameLabel);

		JTextField playerNameField = new JTextField();
		playerNameField.setBounds(0, 3 * gamePanelHeight / 25, 11 * width / 25, 4 * gamePanelHeight / 25);
		playerNameField.setHorizontalAlignment(SwingConstants.CENTER);
		playerNameField.setFont(font);
		playerNamePanel.add(playerNameField);

		gamePanel.add(playerNamePanel);

		JButton colorButton = new JButton("Choose a color");
		colorButton.setFont(font);
		colorButton.setBounds(13 * width / 25, 9 * gamePanelHeight / 25, 11 * width / 25, 7 * gamePanelHeight / 25);
		gamePanel.add(colorButton);

		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choosenColor = JColorChooser.showDialog(UICreator.this, "Choose Background Color",
						UICreator.this.getBackground());
			}
		});

		JButton startGameButton = new JButton("Start Game");
		startGameButton.setFont(font);
		startGameButton.setBounds(13 * width / 25, 17 * gamePanelHeight / 25, 11 * width / 25,
				7 * gamePanelHeight / 25);
		gamePanel.add(startGameButton);

		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerCount + botCount > 1) {
					String name = playerNameField.getText();
					if (name.isEmpty()) {
						JOptionPane.showMessageDialog(UICreator.this, "Please enter a name!", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (file == null) {
						if (choosenColor == null) {
							JOptionPane.showMessageDialog(UICreator.this, "Please choose a color!", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						Controller.getInstance().dispatchMessage(
								message + "CREATE/" + name + "/" + choosenColor.getRGB() + "/" + botCount);
					} else {
						Controller.getInstance().dispatchMessage(message + "LOAD/" + name + "/" + file);
					}
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
