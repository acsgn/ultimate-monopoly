package game;

import java.util.ArrayList;
import java.util.List;

import network.Network;

public class MonopolyGame {
	private List<Player> players;
	// private Board board;
	private Player currentPlayer;

	public MonopolyGame() {
		players = new ArrayList<>();
		// board = new Board();
		currentPlayer = new Player();
		players.add(currentPlayer);
	}

	/*
	 * public void runGame() { for (Player p : players) { p.play(); } }
	 */

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	/*
	 * public Board getboard(){ return board; }
	 */

	public void executeMessage(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "UISCREEN":
			switch (parsed[1]) {
			case "ROLLDICE":
				currentPlayer.play();
			}
		case "UICREATOR":
			switch (parsed[1]) {
			case "PLAYERNAME":
				currentPlayer.setName(parsed[2]);
				break;
			case "PLAYERCOLOR":
				currentPlayer.setColor(parsed[2]);
				break;
			case "SERVER":
				new Network(Integer.parseInt(parsed[2]));
				currentPlayer.startGame();
				break;
			case "CLIENT":
				new Network(parsed[2]);
				if (!Network.getInstance().isConnected()) {
					currentPlayer.networkError();
					break;
				}
				currentPlayer.startGame();
				break;
			}
		}
	}
	
}
