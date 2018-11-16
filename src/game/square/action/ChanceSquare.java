package game.square.action;

import game.ActionCards;
import game.Player;
import game.card.action.Chance;
import game.square.Square;

public class ChanceSquare {
	

	public void executeAction(Player player) {
		Chance chanceCard = ActionCards.getInstance().getChanceCard();
		player.pickCard(chanceCard);
	}
	
}