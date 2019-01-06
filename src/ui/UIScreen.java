package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import game.Controller;
import game.GameListener;

public class UIScreen extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String boardImagePath = "resources/board.png";
	private static final String deedImagePath = "resources/deeds/";
	private static final String cardImagePath = "resources/cards/";
	private static final String jailImagePath = "resources/jail.png";

	private Controller controller;
	private Animator animator;
	private PathFinder pathFinder;
	private Hashtable<String, Piece> pieces;

	private String message;
	private JTextArea infoText;
	private JTextArea playerText;
	private JPanel playerArea;
	private JBoard board;
	private JButton buyBuildingButton;
	private JButton sellBuildingButton;
	private JButton mortgageButton;
	private JButton unmortgageButton;
	private JButton buySquareButton;
	private JButton pauseResumeButton;
	private JButton rollDiceButton;
	private JButton endTurnButton;
	private JButton saveGameButton;
	private JButton endGameButton;
	private JPanel pauseResumePanel;
	private JPanel jail;

	private JTextArea deedInformation;
	private JLabel deed;
	private JComboBox<String> deedComboBox;
	private JComboBox<String> playerComboBox;

	private final Object[] jailOptions = { "Roll for Doubles", "Pay Bail" };

	/// UI constants
	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	private int screenX = (screenWidth - screenHeight) / 2;
	private int screenY = 0;

	private int controlPaneWidth = (screenWidth - screenHeight) / 2;
	private int controlPaneHeight = screenHeight;

	private int controlPaneXMargin = controlPaneWidth / 30;
	private int controlPaneYMargin = controlPaneHeight / 108;

	private int controlPaneComponentWidth = controlPaneWidth / 2;
	private int controlPaneComponentHeight = controlPaneHeight / 12;

	private Image boardImage = new ImageIcon(boardImagePath).getImage().getScaledInstance(screenHeight, -1,
			Image.SCALE_SMOOTH);

	private double scaleFactor = ((double) screenHeight) / new ImageIcon(boardImagePath).getIconHeight();

	private final int unscaledPieceSize = 80;
	private int pieceSize = (int) (scaleFactor * unscaledPieceSize);
	private boolean active;

	/**
	 * Create the panel.
	 */
	public UIScreen() {
		controller = Controller.getInstance();
		pieces = new Hashtable<String, Piece>();
		animator = new Animator();
		pathFinder = new PathFinder(scaleFactor);

		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(null);

		board = new JBoard();
		board.setIcon(new ImageIcon(boardImage));
		board.setBounds(screenX, screenY, screenHeight, screenHeight);
		getContentPane().add(board);
		animator.addComponentToAnimate(board);
		new Thread(animator, "Animator").start();

		JPanel leftControlPanel = new JPanel();
		leftControlPanel.setBounds(0, 0, controlPaneWidth, controlPaneHeight);
		leftControlPanel.setLayout(null);

		deed = new JLabel();
		deed.setBounds(0, 0, controlPaneWidth, 6 * controlPaneComponentHeight);
		deed.setVerticalAlignment(JLabel.CENTER);
		leftControlPanel.add(deed);

		deedInformation = new JTextArea();
		deedInformation.setEditable(false);
		deedInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deedInformation.setBounds(controlPaneXMargin, controlPaneYMargin + 6 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				2 * controlPaneComponentHeight - 2 * controlPaneYMargin);
		leftControlPanel.add(deedInformation);

		deedComboBox = new JComboBox<String>();
		deedComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deedComboBox.setBounds(controlPaneXMargin, controlPaneYMargin + 8 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		leftControlPanel.add(deedComboBox);

		buyBuildingButton = new JButton("Buy Building");
		buyBuildingButton.setBounds(controlPaneXMargin, controlPaneYMargin + 9 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		buyBuildingButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buyBuildingButton.setEnabled(false);
		leftControlPanel.add(buyBuildingButton);

		sellBuildingButton = new JButton("Sell Building");
		sellBuildingButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 9 * controlPaneComponentHeight, controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		sellBuildingButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		sellBuildingButton.setEnabled(false);
		leftControlPanel.add(sellBuildingButton);

		mortgageButton = new JButton("Mortgage");
		mortgageButton.setBounds(controlPaneXMargin, controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		mortgageButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		mortgageButton.setEnabled(false);
		leftControlPanel.add(mortgageButton);

		unmortgageButton = new JButton("Unmortgage");
		unmortgageButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		unmortgageButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		unmortgageButton.setEnabled(false);
		leftControlPanel.add(unmortgageButton);

		buySquareButton = new JButton("Buy Square");
		buySquareButton.setBounds(controlPaneXMargin, controlPaneYMargin + 11 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		buySquareButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buySquareButton.setEnabled(false);
		leftControlPanel.add(buySquareButton);

		getContentPane().add(leftControlPanel);

		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(screenX + screenHeight, screenY, controlPaneWidth, controlPaneHeight);
		rightPanel.setLayout(null);

		endGameButton = new JButton("End Game");
		endGameButton.setBounds(controlPaneXMargin, controlPaneYMargin,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		endGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rightPanel.add(endGameButton);

		playerArea = new JPanel();
		playerArea.setLayout(null);
		playerArea.setOpaque(true);
		playerArea.setBounds(controlPaneXMargin, controlPaneYMargin + controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				4 * controlPaneComponentHeight - 2 * controlPaneYMargin);

		playerText = new JTextArea();
		playerText.setEditable(false);
		playerText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		JScrollPane playerScroll = new JScrollPane(playerText);
		playerScroll.setBounds(controlPaneXMargin, 2 * controlPaneYMargin,
				2 * controlPaneComponentWidth - 4 * controlPaneXMargin,
				4 * controlPaneComponentHeight - 4 * controlPaneYMargin);
		playerArea.add(playerScroll);
		rightPanel.add(playerArea);

		playerComboBox = new JComboBox<String>();
		playerComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		playerComboBox.setBounds(controlPaneXMargin, controlPaneYMargin + 5 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		rightPanel.add(playerComboBox);

		infoText = new JTextArea();
		infoText.setEditable(false);
		infoText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		JScrollPane infoArea = new JScrollPane(infoText);
		infoArea.setBounds(controlPaneXMargin, controlPaneYMargin + 6 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				4 * controlPaneComponentHeight - 2 * controlPaneYMargin);
		rightPanel.add(infoArea);

		pauseResumeButton = new JButton("Pause");
		pauseResumeButton.setBounds(controlPaneXMargin, controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		pauseResumeButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pauseResumeButton.setEnabled(false);
		rightPanel.add(pauseResumeButton);

		saveGameButton = new JButton("Save Game");
		saveGameButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		saveGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		saveGameButton.setEnabled(false);
		rightPanel.add(saveGameButton);

		rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setBounds(controlPaneXMargin, controlPaneYMargin + 11 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		rollDiceButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rollDiceButton.setEnabled(false);
		rightPanel.add(rollDiceButton);

		endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 11 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		endTurnButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		endTurnButton.setEnabled(false);
		rightPanel.add(endTurnButton);

		getContentPane().add(rightPanel);

		pauseResumePanel = new JPanel();
		pauseResumePanel.setBounds(screenWidth / 3 - screenX, screenHeight / 4, screenWidth / 3, screenHeight / 2);
		pauseResumePanel.setBackground(Color.WHITE);
		pauseResumePanel.setLayout(new GridBagLayout());
		JLabel pauseResumeLabel = new JLabel("PAUSED");
		pauseResumeLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		pauseResumePanel.setVisible(false);
		pauseResumePanel.add(pauseResumeLabel);
		board.add(pauseResumePanel);

		jail = new JPanel();
		Image jailImage = new ImageIcon(jailImagePath).getImage().getScaledInstance(screenWidth / 3, -1,
				Image.SCALE_SMOOTH);
		jail.setLayout(new BorderLayout(0, 0));
		jail.add(new JLabel(new ImageIcon(jailImage)), BorderLayout.CENTER);
		JLabel jailLabel = new JLabel("You are in Jail!");
		jailLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		jailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jail.add(jailLabel, BorderLayout.SOUTH);

		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rollDiceButton.setEnabled(false);
				endTurnButton.setEnabled(true);
				message = "UISCREEN/ROLLDICE";
				controller.dispatchMessage(message);
			}
		});

		buySquareButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buySquareButton.setEnabled(false);
				message = "UISCREEN/BUYPROPERTY";
				controller.dispatchMessage(message);
			}
		});

		buyBuildingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "UISCREEN/BUYBUILDING";
				controller.dispatchMessage(message);
			}
		});

		sellBuildingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message = "UISCREEN/SELLBUILDING";
				controller.dispatchMessage(message);
			}
		});

		mortgageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		unmortgageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				active = false;
				disableButtons();
				message = "UISCREEN/ENDTURN";
				controller.dispatchMessage(message);
			}
		});

		pauseResumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pauseResumeButton.getText().equals("Pause")) {
					pauseResumeButton.setText("Resume");
					message = "UISCREEN/PAUSE";
					Controller.getInstance().dispatchMessage(message);
				} else {
					pauseResumeButton.setText("Pause");
					message = "UISCREEN/RESUME";
					Controller.getInstance().dispatchMessage(message);
				}
			}
		});

		saveGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Ultimate Monopoly Save Files", "umsf"));
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String saveFile = chooser.getSelectedFile().getPath();
					message = "UISCREEN/SAVEGAME/" + saveFile;
					Controller.getInstance().dispatchMessage(message);
				}
			}
		});

		endGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				animator.destruct();
				controller.dispatchMessage("UISCREEN/ANIMATIONEND");
				controller.dispatchMessage("UISCREEN/ENDGAME");
				dispose();
			}
		});

		deedComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) deedComboBox.getSelectedItem();
				Image deedImage = new ImageIcon(deedImagePath + name + ".png").getImage()
						.getScaledInstance(controlPaneWidth, -1, Image.SCALE_SMOOTH);
				deed.setIcon(new ImageIcon(deedImage));
				controller.dispatchMessage("UISCREEN/DEEDINFO/" + name);
			}
		});

		playerComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) playerComboBox.getSelectedItem();
				controller.dispatchMessage("UISCREEN/PLAYERINFO/" + name);
			}
		});

	}

	private void disableButtons() {
		buyBuildingButton.setEnabled(false);
		sellBuildingButton.setEnabled(false);
		mortgageButton.setEnabled(false);
		unmortgageButton.setEnabled(false);
		buySquareButton.setEnabled(false);
		pauseResumeButton.setEnabled(false);
		rollDiceButton.setEnabled(false);
		endTurnButton.setEnabled(false);
	}

	private void enableButtons() {
		buyBuildingButton.setEnabled(true);
		sellBuildingButton.setEnabled(true);
		mortgageButton.setEnabled(true);
		unmortgageButton.setEnabled(true);
		pauseResumeButton.setEnabled(true);
		rollDiceButton.setEnabled(true);
	}

	@Override
	public void onGameEvent(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "START":
			setVisible(true);
			break;
		case "ACTION":
			infoText.insert(parsed[1] + "\n", 0);
			break;
		case "COLOR":
			repaint();
			playerArea.setBackground(new Color(toInt(parsed[1]), true));
			break;
		case "MOVE":
			Piece piecem = pieces.get(parsed[1]);
			pathFinder.addPath(piecem.path, toInt(parsed[2]), toInt(parsed[3]), toInt(parsed[4]), toInt(parsed[5]));
			piecem.isActive = true;
			board.repaint();
			break;
		case "JUMP":
			Piece piecej = pieces.get(parsed[1]);
			pathFinder.addPoint(piecej.path, toInt(parsed[2]), toInt(parsed[3]));
			piecej.lastPoint = piecej.path.nextPoint();
			piecej.isActive = true;
			board.repaint();
			break;
		case "PLAYERDATA":
			playerText.setText("");
			for (int i = 1; i < parsed.length; i++) {
				playerText.append(parsed[i] + "\n");
			}
			break;
		case "PLAY":
			active = true;
			enableButtons();
			break;
		case "REMOVEPIECE":
			if (pieces.remove(parsed[1]).isActive)
				animator.stopAnimator();
			board.repaint();
			break;
		case "PAUSET":
			disableButtons();
			pauseResumePanel.setVisible(true);
			pauseResumeButton.setEnabled(true);
			break;
		case "PAUSEF":
			disableButtons();
			pauseResumePanel.setVisible(true);
			break;
		case "RESUME":
			enableButtons();
			pauseResumePanel.setVisible(false);
			break;
		case "DOUBLE":
			if (active) {
				rollDiceButton.setEnabled(true);
				endTurnButton.setEnabled(false);
			}
			break;
		case "JAIL":
			if (active) {
				rollDiceButton.setEnabled(false);
				endTurnButton.setEnabled(true);
			}
			break;
		case "JAILACTION":
			if (active) {
				int n = JOptionPane.showOptionDialog(null, jail, "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, jailOptions, jailOptions[0]);
				if (n == JOptionPane.DEFAULT_OPTION) {
					// Rolled for doubles
				} else {
					// paid bail
				}
			}
		case "PLAYER":
			playerComboBox.addItem(parsed[1]);
			Piece piece = new Piece();
			piece.color = new Color(toInt(parsed[2]), true);
			pathFinder.addPoint(piece.path, toInt(parsed[3]), toInt(parsed[4]));
			piece.lastPoint = piece.path.nextPoint();
			board.repaint();
			pieces.put(parsed[1], piece);
			break;
		case "DEED":
			deedComboBox.addItem(parsed[1]);
			break;
		case "CARD2":
			Image cardImage = new ImageIcon(cardImagePath + parsed[1] + ".png").getImage()
					.getScaledInstance(screenWidth / 3, -1, Image.SCALE_SMOOTH);
			JOptionPane.showMessageDialog(null, new ImageIcon(cardImage), "", JOptionPane.PLAIN_MESSAGE);
			break;
		case "BUILDING":
			if (parsed[1].equals("YES")) {
				ArrayList<Object> possibilities = new ArrayList<>();
				for (int i = 2; i < parsed.length; i++) {
					possibilities.add(parsed[i]);
				}
				String s = (String) JOptionPane.showInputDialog(null, "Choose a color group!", "",
						JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(), possibilities.get(0));
				if (s != null)
					Controller.getInstance().dispatchMessage("UISCREEN/BUYBUILDING2/" + s.split(" ")[0] + "/");
			} else {
				JOptionPane.showMessageDialog(null, "You don't have any monopoly or majority ownership", "",
						JOptionPane.ERROR_MESSAGE);
			}

			break;
		case "BUILDING2":
			ArrayList<Object> possibilities = new ArrayList<>();
			if (parsed[1].equals("NO")) {
				JOptionPane.showMessageDialog(null, parsed[2], "", JOptionPane.PLAIN_MESSAGE);
			} else {
				for (int i = 1; i < parsed.length - 1; i++) {
					possibilities.add(parsed[i]);
				}
				String s = (String) JOptionPane.showInputDialog(null, "Choose a square", "", JOptionPane.PLAIN_MESSAGE,
						null, possibilities.toArray(), possibilities.get(0));
				if (s != null)
					Controller.getInstance()
							.dispatchMessage("UISCREEN/BUYBUILDING3/" + s + "/" + parsed[parsed.length - 1] + "/");
			}
			break;
		case "CARD":
			if (parsed[1].equals("HURRICANE")) {
				switch (parsed[2]) {
				case "CHOOSEPLAYER":
					HashMap<Object, ArrayList<Object>> possibilities1 = new HashMap<>();
					//
					int i = 3;
					while (i < parsed.length) {
						ArrayList<Object> groups = new ArrayList<>();
						String playerName = parsed[i];
						i++;
						while (!parsed[i].equals("END")) {
							groups.add(parsed[i]);
							i++;
						}
						possibilities1.put(playerName, groups);
						i++;
					}
					//
					String player = (String) JOptionPane.showInputDialog(null, "Choose a player from the following:\n",
							"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, possibilities1.keySet().toArray(),
							null);
					if (player != null) {
						String group = (String) JOptionPane.showInputDialog(null, "Choose a groups for that player:\n",
								"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null,
								possibilities1.get(player).toArray(), null);
						if (group != null) {
							message = "UISCREEN/HURRICANE/EXECUTE/" + player + "/" + group + "/";
							Controller.getInstance().dispatchMessage(message);
						}
					}
					break;
				}
			}
		}
	}

	private int toInt(String string) {
		return Integer.parseInt(string);
	}

	private class Piece {
		private Path path;
		private Point lastPoint;
		private Color color;
		private boolean isActive = false;

		public Piece() {
			path = new Path(scaleFactor);
		}

		public void paint(Graphics g) {
			g.setColor(color);
			g.fillOval(lastPoint.x, lastPoint.y, pieceSize, pieceSize);
			if (isActive) {
				if (animator.isStopped())
					animator.startAnimator();
				if (path != null && path.hasMoreSteps())
					lastPoint = path.nextPoint();
				else {
					animator.stopAnimator();
					isActive = false;
					controller.dispatchMessage("UISCREEN/ANIMATIONEND");
					
					// TODO move this to gameEvent, only for buyable squares
					if (active) {
						buySquareButton.setEnabled(true);
						saveGameButton.setEnabled(true);
					}
				}
			}
		}

	}

	private class JBoard extends JLabel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (Piece piece : pieces.values())
				piece.paint(g);
		}
	}

}
