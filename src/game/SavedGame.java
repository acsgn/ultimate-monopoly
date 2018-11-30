package game;

import java.util.ArrayList;
import java.util.List;

public class SavedGame {
	private ArrayList<Player> players;
	private Player currentPlayer;
	
	public SavedGame(ArrayList<Player> players, Player currentPlayer){
		this.players = players;
		this.currentPlayer = currentPlayer;
	}

	
	// Getters
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Player getCurreentPlayer(){
		return currentPlayer;
	}
	// Setters
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public void setPlayer(Player currentPlayer){
		this.currentPlayer = currentPlayer;
	}
}
