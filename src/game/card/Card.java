package game.card;

import game.Player;

public abstract class Card {

	private CardType cardType;
	
	public CardType getCardType() {
		return cardType;
	}

	public abstract void executeAction(Player player);
	
}
