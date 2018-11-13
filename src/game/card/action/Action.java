package game.card.action;

import game.Card;
import game.CardType;
import game.Player;

public abstract class Action implements Card{
	
	private static final CardType type = CardType.ACTION;
	private String text;
	
	public String getText() {
		return text;
	}
	
	public abstract ActionType getActionType();

	public abstract void executeAction(Player player);
	
	public CardType getType() {
		return type;
	}
	
}
