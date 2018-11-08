package game;

import java.util.ArrayList;
import java.util.List;

import network.Network;

public class MonopolyGame {
	private List<Player> players;
	private Board board;
	private Player currentPlayer;

	public MonopolyGame() {
		players = new ArrayList<>();
		board = new Board();
		currentPlayer = new Player("Waterfall", 3200, board);
		players.add(currentPlayer);
	}

	public void runGame() {
		for (Player p : players) {
			p.play();
		}
	}

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
		switch(parsed[0]) {
		
		
		/*case "SERVER": 
			Network server = new Network(Integer.parseInt(parsed[1]));
			break;
		case "CLIENT":
			Network client = new Network(parsed[1]);
			if (!client.isConnected()) {
				// somehow inform the game creator
			}*/
		}
	}

}
