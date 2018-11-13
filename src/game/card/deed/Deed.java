package game.card.deed;

import game.Card;
import game.Player;

public abstract class Deed extends Card {
	
	private Player owner;

	public Deed(String name, Player owner) {
		super(name);
		this.owner = owner;		
	}
	
	
	
}
