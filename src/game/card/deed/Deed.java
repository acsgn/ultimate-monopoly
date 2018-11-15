package game.card.deed;

import game.Card;
import game.CardType;

public abstract class Deed extends Card {
	
	private static final CardType type = CardType.DEED;

	public CardType getType() {
		return type;
	}

	public abstract DeedType getDeedType();
	
}
