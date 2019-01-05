package game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import game.bot.Bot;

public class SaveGame implements Serializable {
	private static final long serialVersionUID = 1L;

	private ConcurrentLinkedDeque<Player> players;
	private ConcurrentHashMap<String, LinkedList<Bot>> bots;

	public SaveGame(ConcurrentLinkedDeque<Player> players, ConcurrentHashMap<String, LinkedList<Bot>> bots) {
		this.players = players;
		this.bots = bots;
	}

	public ConcurrentLinkedDeque<Player> getPlayers() {
		return players;
	}

	public ConcurrentHashMap<String, LinkedList<Bot>> getBots() {
		return bots;
	}

}
