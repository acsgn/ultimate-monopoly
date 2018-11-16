package game.card.action;

import game.Card;
import game.CardType;
import game.Player;

public class ActionCard extends Card{
	
	private static final CardType type = CardType.ACTION;
	private ActionCardType actionCardType;
	
	public ActionCardType getActionType() {
		return actionCardType;
	}

	public void executeAction(Player player) {
		
	}
	
	public CardType getType() {
		return type;
	}
	
}
