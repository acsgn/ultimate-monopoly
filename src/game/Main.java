package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ui.UICreator;
import ui.UIScreen;
import ObserverPattern.Observer;

import javax.swing.ImageIcon;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MonopolyGame game = new MonopolyGame();
		Controller gameController = new Controller(game);
		UIScreen screen = new UIScreen(gameController);
		game.getPlayers().get(0).addGamelistener(screen);
		UICreator gameCreator = new UICreator(gameController, screen);
		gameCreator.setVisible(true);
	}

}