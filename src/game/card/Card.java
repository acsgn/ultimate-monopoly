package game.card;

import java.io.Serializable;

import game.Player;

public abstract class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CardType cardType;
	
	public CardType getCardType() {
		return cardType;
	}

	public abstract void executeAction(Player player);
	
}
