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

	private Animator animator;
	private String message;
	private JTextArea infoText;
	private JTextArea playerText;
	private Color playerColor;
	private Hashtable<String, Color> colorTable;
	private PathFinder pathFinder;
	private JLabel board;

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
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(null);

		animator = new Animator(this);
		Thread animatorThread = new Thread(animator, "Animator");
		animatorThread.start();

		pathFinder = new PathFinder(screenHeight / 3000.0);

		createColorTable();

		board = new JLabel();
		board.setIcon(new ImageIcon(boardImage));
		board.setBounds(screenX, screenY, screenHeight, screenHeight);
		add(board);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(null);

		JPanel playerArea = new JPanel();
		playerArea.setBackground(playerColor);
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
		propertiesList.setBounds(controlPaneXSpace, getButtonY(5), controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(propertiesList);

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
			}
		});

		endGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "ENDGAME";
				controller.dispatchMessage(message);
				dispose();
			}
		});
	}

	private int getButtonY(int i) {
		return controlPaneHeight - (i * controlPaneButtonHeight + i * controlPaneYSpace);
	}

	@Override
	public void onGameEvent(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "DOMAIN":
			switch (parsed[1]) {
			case "ACTION":
				infoText.append(parsed[2]);
				break;
			case "PIECE":
				createPlayerPiece(parsed[2]);
			}
		}
	}

	private void createColorTable() {
		colorTable = new Hashtable<String, Color>(12);
		colorTable.put("Red", Color.RED);
		colorTable.put("Green", Color.GREEN);
		colorTable.put("Blue", Color.BLUE);
		colorTable.put("Yellow", Color.YELLOW);
		colorTable.put("Cyan", Color.CYAN);
		colorTable.put("Pink", Color.PINK);
		colorTable.put("Orange", Color.ORANGE);
		colorTable.put("Magenta", Color.MAGENTA);
		colorTable.put("Gray", Color.GRAY);
		colorTable.put("Black", Color.BLACK);
	}

	private void createPlayerPiece(String color) {
		Piece piece = new Piece(colorTable.get(color));
		piece.setPath(pathFinder.findPath(1, 25, 3, 10));
		pieces.add(piece);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Piece piece : pieces) {
			piece.paint(g);
		}
	}

	public class Piece {

		private Path path;
		private Color color;
		Point lastPoint;

		public Piece(Color color) {
			this.color = color;
		}

		public void paint(Graphics g) {
			g.setColor(color);
			g.fillRect(screenX+lastPoint.x, lastPoint.y, pieceSize, pieceSize);
			if (path.hasMoreSteps())
				lastPoint = path.nextPosition();
		}

		public void setPath(Path path) {
			this.path = path;
			lastPoint = path.nextPosition();
		}

	}

}
