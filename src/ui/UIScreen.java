package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Hashtable;

import game.Controller;
import game.GameListener;

public class UIScreen extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String boardImagePath = "resources/board.png";

	private Controller controller;
	private Animator animator;
	private PathFinder pathFinder;
	private Hashtable<String, Piece> pieces;
	private Hashtable<String, JButton> buttons = new Hashtable<String, JButton>();

	private String message;
	private JTextArea infoText;
	private JTextArea playerText;
	private JPanel playerArea;
	private JBoard board;

	/// UI constants
	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	private int controlPaneWidth = screenHeight / 4;
	private int controlPaneHeight = screenHeight;

	private int controlPaneAreaWidth = 8 * controlPaneWidth / 9;
	private int controlPaneAreaHeight = controlPaneHeight / 4;

	private int controlPaneButtonWidth = 8 * controlPaneWidth / 9;
	private int controlPaneButtonHeight = controlPaneHeight / 18;

	private int controlPaneXSpace = controlPaneWidth / 18;
	private int controlPaneYSpace = controlPaneHeight / 72;

	private int screenX = (screenWidth - screenHeight - controlPaneWidth) / 2;
	private int screenY = 0;

	private Image boardImage = new ImageIcon(boardImagePath).getImage().getScaledInstance(screenHeight, -1,
			Image.SCALE_SMOOTH);

	private double scaleFactor = ((double) screenHeight) / new ImageIcon(boardImagePath).getIconHeight();

	private final int unscaledPieceSize = 80;
	private int pieceSize = (int) (scaleFactor * unscaledPieceSize);

	/**
	 * Create the panel.
	 */
	public UIScreen() {
		controller = Controller.getInstance();
		pieces = new Hashtable<String, Piece>();

		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(null);

		board = new JBoard();
		board.setIcon(new ImageIcon(boardImage));
		board.setBounds(screenX, screenY, screenHeight, screenHeight);
		getContentPane().add(board);

		animator = new Animator();
		animator.addComponentToAnimate(board);
		new Thread(animator, "Animator").start();

		pathFinder = new PathFinder(scaleFactor);

		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(screenX + screenHeight, screenY, controlPaneWidth, controlPaneHeight);
		controlPanel.setLayout(null);

		playerArea = new JPanel();
		playerArea.setLayout(null);
		playerArea.setOpaque(true);
		playerArea.setBounds(controlPaneXSpace, controlPaneYSpace, controlPaneAreaWidth, controlPaneAreaHeight);

		playerText = new JTextArea();
		playerText.setEditable(false);
		JScrollPane playerScroll = new JScrollPane(playerText);
		playerScroll.setBounds(controlPaneXSpace, 2 * controlPaneYSpace, controlPaneAreaWidth - 2 * controlPaneXSpace,
				controlPaneAreaHeight - 2 * controlPaneYSpace);
		playerArea.add(playerScroll);
		controlPanel.add(playerArea);

		JButton endGameButton = new JButton("End Game");
		endGameButton.setBounds(controlPaneXSpace, controlPaneAreaHeight + 2 * controlPaneYSpace,
				controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(endGameButton);

		infoText = new JTextArea();
		infoText.setEditable(false);
		JScrollPane infoArea = new JScrollPane(infoText);
		infoArea.setBounds(controlPaneXSpace, controlPaneAreaHeight + controlPaneButtonHeight + 3 * controlPaneYSpace,
				controlPaneAreaWidth, controlPaneAreaHeight);
		controlPanel.add(infoArea);

		JComboBox<String> propertiesList = new JComboBox<String>();
		propertiesList.setBounds(controlPaneXSpace, getButtonY(6) + 30, controlPaneButtonWidth,
				controlPaneButtonHeight - 30);
		controlPanel.add(propertiesList);

		JButton bailButton = new JButton("Save Game");
		bailButton.setBounds(controlPaneXSpace, getButtonY(5), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(bailButton);
		bailButton.setEnabled(false);

		JButton buildingButton = new JButton("Build/Sell Building");
		buildingButton.setBounds(controlPaneXSpace, getButtonY(4), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(buildingButton);
		buildingButton.setEnabled(false);

		JButton buyPropertyButton = new JButton("Buy Property");
		buyPropertyButton.setBounds(controlPaneXSpace, getButtonY(3), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(buyPropertyButton);
		buyPropertyButton.setEnabled(false);

		JButton rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setBounds(controlPaneXSpace, getButtonY(2), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(rollDiceButton);
		rollDiceButton.setEnabled(false);

		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(controlPaneXSpace, getButtonY(1), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(endTurnButton);
		endTurnButton.setEnabled(false);

		getContentPane().add(controlPanel);
		getContentPane().setBackground(Color.BLACK);

		bailButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(UIScreen.this, "Please enter name of the file: ",
						"Save Game", JOptionPane.QUESTION_MESSAGE);
				message = "UISCREEN/SAVEGAME/" + input;
				Controller.getInstance().dispatchMessage(message);
			}
		});

		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "UISCREEN/ROLLDICE";
				controller.dispatchMessage(message);
				rollDiceButton.setEnabled(false);
			}
		});

		buyPropertyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "UISCREEN/BUYPROPERTY";
				controller.dispatchMessage(message);
			}
		});

		buildingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String squareName = (String) propertiesList.getSelectedItem();
				message = "UISCREEN/BUYBUILDING," + squareName;
				controller.dispatchMessage(message);
			}
		});

		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (JButton button : buttons.values())
					button.setEnabled(false);
				message = "UISCREEN/ENDTURN";
				controller.dispatchMessage(message);
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

		buttons.put("ROLL", rollDiceButton);
		buttons.put("BUY", buyPropertyButton);
		buttons.put("BUILDING", buildingButton);
		buttons.put("BAIL", bailButton);
		buttons.put("ENDTURN", endTurnButton);
	}

	private int getButtonY(int i) {
		return controlPaneHeight - (i * controlPaneButtonHeight + i * controlPaneYSpace);
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
			Path path = pathFinder.findPath(toInt(parsed[2]), toInt(parsed[3]), toInt(parsed[4]), toInt(parsed[5]));
			pieces.get(parsed[1]).path = path;
			pieces.get(parsed[1]).isActive = true;
			board.repaint();
			break;
		case "JUMP":
			int[] point = pathFinder.getLocation(toInt(parsed[2]), toInt(parsed[3]));
			pieces.get(parsed[1]).path.addPoint(new Point(point[0], point[1]));
			board.repaint();
			break;
		case "PIECE":
			Piece piece = new Piece();
			piece.color = new Color(toInt(parsed[2]), true);
			int[] initialPoint = pathFinder.getLocation(toInt(parsed[3]), toInt(parsed[4]));
			piece.lastPoint = new Point(initialPoint[0], initialPoint[1]);
			board.repaint();
			pieces.put(parsed[1], piece);
			break;
		case "PLAYERDATA":
			playerText.setText("");
			for (int i = 1; i < parsed.length; i++) {
				playerText.append(parsed[i] + "\n");
			}
			break;
		case "PLAY":
			for (JButton button : buttons.values()) {
				button.setEnabled(true);
			}
			break;
		case "REMOVEPIECE":
			if (pieces.remove(parsed[1]).isActive)
				animator.stopAnimator();
			board.repaint();
			break;
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
		}

		public void paint(Graphics g) {
			g.setColor(color);
			g.fillRect(lastPoint.x, lastPoint.y, pieceSize, pieceSize);
			if (isActive) {
				if (animator.isStopped())
					animator.startAnimator();
				if (path != null && path.hasMoreSteps())
					lastPoint = path.nextPosition();
				else {
					animator.stopAnimator();
					isActive = false;
					controller.dispatchMessage("UISCREEN/ANIMATIONEND");
				}
			}
		}

	}

	private class JBoard extends JLabel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for (Piece piece : pieces.values()) {
				piece.paint(g);
			}
		}
	}

}
