package game;

import java.util.ArrayList;
import java.util.List;

import ObserverPattern.Observer;

public class MonopolyGame {
	private List<Player> players; 
	private Board board; 
	private Player currentPlayer; 
	public MonopolyGame(){
		players = new ArrayList<>();
		board = new Board();
		currentPlayer = new Player("Waterfall", 3200, board);
		players.add(currentPlayer);
	}
	public void runGame(){
		for(Player p : players){
			p.play();
		}
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	/*public Board getboard(){
		return board;
	}*/
	
	public void executeMessage(String message){
		if(message.equals("Roll Dice")){
			
		}else if(message.equals("End Turn")){
			
		}else if(message.equals("Buy Property")){
			
		}else if(message.equals("Build/Sell Building")){
			
		}else if(message.equals("End Game")){
			
		}
	}
	
}
