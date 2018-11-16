package game.square.deed;

import game.card.CardType;
import game.card.action.Card;

public abstract class Deed extends Card {
	
	private static final CardType type = CardType.DEED;

	public CardType getType() {
		return type;
	}

	public abstract DeedType getDeedType();
	
}
