package game.card.action;

import game.Card;
import game.CardType;
import game.Player;

public class ActionCard extends Card{
	
	private static final CardType type = CardType.ACTION;
	private String text;
	
	public String getText() {
		return text;
	}
	
	public ActionType getActionType() {
		return actionType;
	}

	public void executeAction(Player player);
	
	public CardType getType() {
		return type;
	}
	
}
