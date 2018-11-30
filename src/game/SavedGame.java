package game;

import java.util.ArrayList;
import java.util.List;

public class SavedGame {
	private ArrayList<Player> players;
	private Player currentPlayer;
	private int order;
	
	public SavedGame(ArrayList<Player> players, Player currentPlayer, int order){
		this.players = players;
		this.currentPlayer = currentPlayer;
		this.order = order;
	}

	
	// Getters
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Player getCurreentPlayer(){
		return currentPlayer;
	}
	public int getOrder(){
		return order;
	}
	// Setters
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public void setPlayer(Player currentPlayer){
		this.currentPlayer = currentPlayer;
	}
	public void setOrder(int order){
		this.order = order;
	}
}
