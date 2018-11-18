package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Hashtable;

import game.Controller;
import game.GameListener;

public class UIScreen extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String boardImagePath = "resources/board.png";

	private ArrayList<Piece> pieces = new ArrayList<Piece>();

	private volatile Animator animator;
	private Controller controller;
	private String message;
	private volatile JTextArea infoText;
	private volatile JTextArea playerText;
	private volatile Color playerColor;
	private PathFinder pathFinder;
	private volatile JPanel playerArea;

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

	private int pieceSize = (int) (screenHeight * 80 / 3000.0);

	/**
	 * Create the panel.
	 */
	public UIScreen(Controller controller) {
		this.controller = controller;

		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(null);

		pathFinder = new PathFinder(screenHeight / 3000.0);

		JBoard board = new JBoard();
		board.setIcon(new ImageIcon(boardImage));
		board.setBounds(screenX, screenY, screenHeight, screenHeight);
		add(board);

		animator = new Animator(board);
		new Thread(animator, "Animator").start();

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(null);

		playerArea = new JPanel();
		playerArea.setLayout(null);
		playerText = new JTextArea();
		playerText.setEditable(false);
		JScrollPane playerScroll = new JScrollPane(playerText);
		playerScroll.setBounds(controlPaneXSpace, 2 * controlPaneYSpace, controlPaneAreaWidth - 2 * controlPaneXSpace,
				controlPaneAreaHeight - 2 * controlPaneYSpace);
		playerArea.add(playerScroll);
		playerArea.setBounds(controlPaneXSpace, controlPaneYSpace, controlPaneAreaWidth, controlPaneAreaHeight);
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

		JButton bailButton = new JButton("Pay Bail");
		bailButton.setBounds(controlPaneXSpace, getButtonY(5), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(bailButton);

		JButton buildingButton = new JButton("Build/Sell Building");
		buildingButton.setBounds(controlPaneXSpace, getButtonY(4), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(buildingButton);

		JButton buyPropertyButton = new JButton("Buy Property");
		buyPropertyButton.setBounds(controlPaneXSpace, getButtonY(3), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(buyPropertyButton);

		JButton rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setBounds(controlPaneXSpace, getButtonY(2), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(rollDiceButton);

		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(controlPaneXSpace, getButtonY(1), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(endTurnButton);

		controlPanel.setBounds(screenX + screenHeight, screenY, controlPaneWidth, controlPaneHeight);
		add(controlPanel);

		getContentPane().setBackground(Color.BLACK);

		// Action Listeners
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "UISCREEN/ROLLDICE";
				controller.dispatchMessage(message);
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
				message = "UISCREEN/ENDTURN";
				controller.dispatchMessage(message);
			}
		});

		endGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "UISCREEN/ENDGAME";
				animator.destruct();
				controller.dispatchMessage(message);
				dispose();
			}
		});
	}

	private int getButtonY(int i) {
		return controlPaneHeight - (i * controlPaneButtonHeight + i * controlPaneYSpace);
	}

	public void start() {
		controller.dispatchMessage("UISCREEN/START");
		setVisible(true);
	}

	@Override
	public void onGameEvent(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "ACTION":
			infoText.append(parsed[1]);
			break;
		case "COLOR":
			playerColor = colorTable.get(parsed[1]);
			playerArea.setBackground(playerColor);
			break;
		case "MOVE2":
			Path path = pathFinder.findPath(toInt(parsed[2]), toInt(parsed[3]));
			pieces.get(toInt(parsed[1])).path = path;
			animator.startAnimator();
			break;
		case "MOVE":
			Point point = pathFinder.getLocation(toInt(parsed[2]), toInt(parsed[3]));
			pieces.get(toInt(parsed[1])).lastPoint = point;
			repaint();
			break;
		case "PIECE":
			Piece piece = new Piece();
			piece.color = colorTable.get(parsed[1]);
			int trackID = toInt(parsed[2]);
			int location = toInt(parsed[3]);
			pathFinder.setInitialValues(trackID, location);
			piece.lastPoint = pathFinder.getLocation(trackID, location);
			repaint();
			pieces.add(piece);
			break;
		case "PLAYERDATA":
			infoText.setText("");
			for(int i = 1; i< parsed.length; i++) {
				infoText.append(parsed[i]);
			}
			break;
		default:
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

		public Piece() {
		}

		public void paint(Graphics g) {
			g.setColor(color);
			g.fillRect(lastPoint.x, lastPoint.y, pieceSize, pieceSize);
			if (path != null && path.hasMoreSteps()) {
				lastPoint = path.nextPosition();
			} else
				animator.stopAnimator();
		}

	}

	private class JBoard extends JLabel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for (Piece piece : pieces) {
				piece.paint(g);
			}
		}
	}

	private static Hashtable<String, Color> colorTable = new Hashtable<String, Color>() {
		private static final long serialVersionUID = 1L;
		{
			put("Red", Color.RED);
			put("Green", Color.GREEN);
			put("Blue", Color.BLUE);
			put("Yellow", Color.YELLOW);
			put("Cyan", Color.CYAN);
			put("Pink", Color.PINK);
			put("Orange", Color.ORANGE);
			put("Magenta", Color.MAGENTA);
			put("Gray", Color.GRAY);
			put("Black", Color.BLACK);
		}
	};

}
