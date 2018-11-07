package game;

import java.util.ArrayList;
import java.util.List;

import ObserverPattern.Observer;
import game.dice.Dice;

public class MonopolyGame {
	private List<Player> players; 
	private Dice[] dices;
	private Board board; 
	
	public MonopolyGame(){
		players = new ArrayList<>();
		dices = new Dice[3];
		board = new Board();
		Player player = new Player("Waterfall", 3200, dices, board);
		players.add(player);
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
	
	public void setObserver(Observer ob){
		players.get(0).registerObserver(ob);

	}
	
}
