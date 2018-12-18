package game;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SavedGame implements Serializable {
	private static final long serialVersionUID = 1L;
	private ConcurrentLinkedDeque<Player> players;
	private Player currentPlayer;
	private int order;

	public SavedGame(ConcurrentLinkedDeque<Player> players, Player currentPlayer, int order) {
		this.players = players;
		this.currentPlayer = currentPlayer;
		this.order = order;
	}

	// Getters
	public ConcurrentLinkedDeque<Player> getPlayers() {
		return players;
	}

	public Player getCurreentPlayer() {
		return currentPlayer;
	}

	public int getOrder() {
		return order;
	}

	// Setters
	public void setPlayers(ConcurrentLinkedDeque<Player> players) {
		this.players = players;
	}

	public void setPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
