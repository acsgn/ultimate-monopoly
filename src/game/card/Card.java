package game.card;

import game.Player;

public abstract class Card {
	private String name; 
	private Player owner; 
	
	public Card(String name, Player owner){
		this.name = name; 
		this.owner = owner; 
	}
	public abstract void executeAction();
}
