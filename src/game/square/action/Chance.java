package game.square.action;

import game.Player;
import game.card.ActionCards;

public class Chance extends Action {
	
	
	public Chance() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		game.card.Chance chanceCard = ActionCards.getInstance().getChanceCard();
		player.pickCard(chanceCard);
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}