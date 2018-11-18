package game.square.action;

import game.Player;
import game.card.ActionCards;
import network.NetworkFaçade;

public class CommunityChest extends Action {
	
	public CommunityChest() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		game.card.CommunityChest card = ActionCards.getInstance().getCommunityChestCard();
		player.pickCard(card);
		NetworkFaçade.getInstance().sendMessageToOthers(player.getName()+"/CARD/"+card.getCardType().ordinal());
	}

	@Override
	public void executeWhenPassed(Player player) {
	}
	
}