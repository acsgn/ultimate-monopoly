package game.square.action;

import game.Player;
import game.card.ActionCards;
import network.NetworkFaçade;

public class Chance extends Action {
	
	
	public Chance() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		game.card.Chance card = ActionCards.getInstance().getChanceCard();
		player.pickCard(card);
		NetworkFaçade.getInstance().sendMessageToOthers(player.getName()+"/CARD/"+card.getCardType().ordinal());
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}