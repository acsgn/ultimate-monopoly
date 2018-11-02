package game;

import java.util.ArrayList;
import java.util.List;

public class MonopolyGame {
	private List<Player> players; 
	private List<Dice> dices;
	private Board board; 
	
	public MonopolyGame(int numPlayers){
		players = new ArrayList<>();
		dices = new ArrayList<>();
		board = new Board();
	}
	public void runGame(){
		for(Player p : players){
			p.play();
		}
	}
}
