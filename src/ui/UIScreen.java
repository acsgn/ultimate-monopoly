package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ObserverPattern.Observer;
import ObserverPattern.Subject;

public class UIScreen extends JFrame implements Subject {
	private static final long serialVersionUID = 1L;
	private static final String boardImage = "resources/board.png";

	private String message;
	private JTextArea infoText;
	private JTextArea playerText;
	private Color playerColor = new Color(50, 205, 50);

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

	/// obeservers
	private ArrayList<Observer> observers;

	/**
	 * Create the panel.
	 */
	public UIScreen(Observer o) {
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(null);

		/// Adding the observer
		observers = new ArrayList<>();
		this.registerObserver(o);
		///
		
		JComponent board = new JComponent() {
			private static final long serialVersionUID = 1L;
			Image board = new ImageIcon(boardImage).getImage().getScaledInstance(screenHeight, -1, Image.SCALE_SMOOTH);

			public void paint(Graphics g) {
				g.drawImage(board, 0, 0, this);
			}
		};
		board.setBounds(screenX, screenY, screenHeight, screenHeight);
		add(board);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(null);
		
		JPanel playerArea = new JPanel();
		playerArea.setBackground(playerColor);
		playerArea.setLayout(null);
		playerText = new JTextArea();
		playerText.setEditable(false);
		updateTextArea(playerText);
		JScrollPane playerScroll = new JScrollPane(playerText);
		playerScroll.setBounds(controlPaneXSpace, 2*controlPaneYSpace, controlPaneAreaWidth-2*controlPaneXSpace, controlPaneAreaHeight-2*controlPaneYSpace);
		playerArea.add(playerScroll);
		playerArea.setBounds(controlPaneXSpace, controlPaneYSpace, controlPaneAreaWidth, controlPaneAreaHeight);
		controlPanel.add(playerArea);
		
		JButton endGameButton = new JButton("End Game");
		endGameButton.setBounds(controlPaneXSpace, controlPaneAreaHeight+2*controlPaneYSpace, controlPaneButtonWidth, controlPaneButtonHeight);
		controlPanel.add(endGameButton);
		
		infoText = new JTextArea();
		infoText.setEditable(false);
		updateTextArea(infoText);
		JScrollPane infoArea = new JScrollPane(infoText);
		infoArea.setBounds(controlPaneXSpace, controlPaneAreaHeight+controlPaneButtonHeight+3*controlPaneYSpace, controlPaneAreaWidth, controlPaneAreaHeight);
		controlPanel.add(infoArea);
		
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
				message = "ROLLDICE";
				notifyObservers();
			}
		});
		
		buyPropertyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "BUYPROP";
				notifyObservers();
			}
		});
		
		buildingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "BUILDING";
				notifyObservers();
			}
		});
		
		endTurnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "ENDTURN";
				notifyObservers();
			}
		});
		
		endGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "ENDGAME";
				notifyObservers();
			}
		});
	}

	/**
	 * Just for demonstration purpose, don't forget to delete this
	 * @param playerText
	 */
	private void updateTextArea(JTextArea tA) {
		for(int i = 0; i < 20; i++) {
			tA.insert("Deneme "+i+"\n", 0);
		}
	}

	private int getButtonY(int i) {
		return controlPaneHeight-(i*controlPaneButtonHeight+i*controlPaneYSpace);
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update(message);
		}
	}

	public void receiveMessage(String message) {
		// Should parse message
		infoText.insert(message + "\n", 0);
		playerText.insert(message, 0);
	}

}
