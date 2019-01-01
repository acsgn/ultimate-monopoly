package game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import game.bot.Bot;

public class SavedGame implements Serializable {
	private static final long serialVersionUID = 1L;

	private ConcurrentLinkedDeque<Player> players;
	private Player currentPlayer;
	private ConcurrentHashMap<String, LinkedList<Bot>> bots;

	public SavedGame(ConcurrentLinkedDeque<Player> players, Player currentPlayer,
			ConcurrentHashMap<String, LinkedList<Bot>> bots) {
		this.players = players;
		this.currentPlayer = currentPlayer;
		this.bots = bots;
	}

	// Getters
	public ConcurrentLinkedDeque<Player> getPlayers() {
		return players;
	}

	public Player getCurreentPlayer() {
		return currentPlayer;
	}

	public ConcurrentHashMap<String, LinkedList<Bot>> getBots() {
		return bots;
	}

	// Setters
	public void setPlayers(ConcurrentLinkedDeque<Player> players) {
		this.players = players;
	}

	public void setPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setBots(ConcurrentHashMap<String, LinkedList<Bot>> bots) {
		this.bots = bots;
	}
}
