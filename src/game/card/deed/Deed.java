package game.card.deed;

import game.Card;
import game.CardType;

public abstract class Deed implements Card {
	
	private static final CardType type = CardType.DEED;

	public CardType getType() {
		return type;
	}

}
