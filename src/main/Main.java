package main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.Controller;
import game.MonopolyGame;
import ui.UICreator;
import ui.UIScreen;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MonopolyGame game = new MonopolyGame();
		Controller gameController = new Controller(game);
		UIScreen screen = new UIScreen(gameController);
		UICreator gameCreator = new UICreator(gameController, screen);
		gameCreator.setVisible(true);
	}

}